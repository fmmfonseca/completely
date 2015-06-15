package completely.text.index;

import completely.common.Strings;
import completely.text.match.Automaton;
import completely.text.match.EqualityAutomaton;
import completely.util.ArraySet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static completely.common.Precondition.checkPointer;

/**
 * Trie based implementation of the {@link FuzzyIndex} interface.
 *
 * <p>Note that this implementation is not synchronized.
 */
public class PatriciaTrie<V> extends AbstractIndex<V> implements FuzzyIndex<V>
{
    private Node root;

    public PatriciaTrie()
    {
        root = new Node();
    }

    @Override
    public void clear()
    {
        root = new Node();
    }

    @Override
    public Set<V> getAll(String key)
    {
        checkPointer(key != null);
        Node node = find(root, key);
        if (node != null)
        {
            return new HashSet<V>(node.values());
        }
        return new HashSet<V>();
    }

    @Override
    public Set<V> getAny(String fragment)
    {
        checkPointer(fragment != null);
        Set<V> result = new HashSet<V>();
        for (Node node : findAll(root, new EqualityAutomaton(fragment)))
        {
            result.addAll(values(node));
        }
        return result;
    }

    @Override
    public Set<V> getAny(Automaton matcher)
    {
        checkPointer(matcher != null);
        Set<V> result = new HashSet<V>();
        for (Node node : findAll(root, matcher))
        {
            result.addAll(values(node));
        }
        return result;
    }

    @Override
    public boolean isEmpty()
    {
        return root.isEmpty();
    }

    @Override
    public boolean putAll(String key, Collection<V> values)
    {
        checkPointer(key != null);
        checkPointer(values != null);
        return putAll(root, key, values);
    }

    @Override
    public boolean removeAll(Collection<V> values)
    {
        checkPointer(values != null);
        return removeAll(root, values);
    }

    @Override
    public Set<V> removeAll(String key)
    {
        checkPointer(key != null);
        return removeAll(root, key);
    }

    @Override
    public boolean removeAll(String key, Collection<V> values)
    {
        checkPointer(key != null);
        checkPointer(values != null);
        return removeAll(root, key, values);
    }

    @Override
    public int size()
    {
        return size(root);
    }

    private Node find(Node node, String key)
    {
        assert node != null;
        assert key != null;
        if (key.length() <= 0)
        {
            return node;
        }
        else
        {
            for (Entry<String, Node> entry : node.childEntries())
            {
                String edge = entry.getKey();
                Node child = entry.getValue();
                int commonPrefixLength = Strings.getCommonPrefixLength(edge, key);
                // Exact match
                if (commonPrefixLength >= edge.length())
                {
                    return find(child, key.substring(commonPrefixLength));
                }
            }
        }
        return null;
    }

    private Collection<Node> findAll(Node node, Automaton matcher)
    {
        assert node != null;
        assert matcher != null;
        if (matcher.isWordAccepted())
        {
            return Arrays.asList(node);
        }
        else if (!matcher.isWordRejected())
        {
            List<Node> result = new LinkedList<Node>();
            for (Entry<String, Node> entry : node.childEntries())
            {
                String edge = entry.getKey();
                result.addAll(findAll(entry.getValue(), matcher.stepUntilWordAccepted(edge)));
            }
            return result;
        }
        return Collections.<Node>emptyList();
    }

    private boolean putAll(Node node, String key, Collection<V> values)
    {
        assert node != null;
        assert key != null;
        assert values != null;
        if (key.length() <= 0)
        {
            return node.addAllValues(values);
        }
        else
        {
            Node child = null;
            int commonPrefixLength = 0;
            for (Entry<String, Node> entry : node.childEntries())
            {
                String edge = entry.getKey();
                commonPrefixLength = Strings.getCommonPrefixLength(edge, key);
                // Exact match
                if (commonPrefixLength >= edge.length())
                {
                    child = entry.getValue();
                    break;
                }
                // Prefix match
                else if (commonPrefixLength > 0)
                {
                    child = node.bisect(edge, commonPrefixLength);
                    break;
                }
            }
            if (child == null)
            {
                child = new Node();
                commonPrefixLength = key.length();
                node.putChild(key, child);
            }
            return putAll(child, key.substring(commonPrefixLength), values);
        }
    }

    private boolean removeAll(Node node, Collection<V> values)
    {
        assert node != null;
        assert values != null;
        boolean result = node.removeAllValues(values);
        List<String> legacyEdges = new LinkedList<String>();
        for (Iterator<Entry<String, Node>> iterator = node.childEntries().iterator(); iterator.hasNext();)
        {
            Entry<String, Node> entry = iterator.next();
            String edge = entry.getKey();
            Node child = entry.getValue();
            if (removeAll(child, values))
            {
                result = true;
            }
            if (child.isEmpty())
            {
                iterator.remove();
            }
            else if (child.isUnary())
            {
                legacyEdges.add(edge);
            }
        }
        for (String edge : legacyEdges)
        {
            node.squash(edge);
        }
        return result;
    }

    private Set<V> removeAll(Node node, String key)
    {
        assert node != null;
        assert key != null;
        if (key.length() <= 0)
        {
            return node.removeAllValues();
        }
        else
        {
            for (Entry<String, Node> entry : node.childEntries())
            {
                String edge = entry.getKey();
                int commonPrefixLength = Strings.getCommonPrefixLength(edge, key);
                // Exact match
                if (commonPrefixLength >= edge.length())
                {
                    Node child = entry.getValue();
                    Set<V> result = removeAll(child, key.substring(commonPrefixLength));
                    if (child.isEmpty())
                    {
                        node.removeChild(edge);
                    }
                    else if (child.isUnary())
                    {
                        node.squash(edge);
                    }
                    return result;
                }
            }
        }
        return Collections.<V>emptySet();
    }

    private boolean removeAll(Node node, String key, Collection<V> values)
    {
        assert node != null;
        assert key != null;
        assert values != null;
        if (key.length() <= 0)
        {
            return node.removeAllValues(values);
        }
        else
        {
            for (Entry<String, Node> entry : node.childEntries())
            {
                String edge = entry.getKey();
                int commonPrefixLength = Strings.getCommonPrefixLength(edge, key);
                // Exact match
                if (commonPrefixLength >= edge.length())
                {
                    Node child = entry.getValue();
                    boolean result = removeAll(child, key.substring(commonPrefixLength), values);
                    if (child.isEmpty())
                    {
                        node.removeChild(edge);
                    }
                    else if (child.isUnary())
                    {
                        node.squash(edge);
                    }
                    return result;
                }
            }
        }
        return false;
    }

    private int size(Node node)
    {
        assert node != null;
        int result = node.values().size();
        for (Node child : node.childNodes())
        {
            result += size(child);
        }
        return result;
    }

    private Set<V> values(Node node)
    {
        assert node != null;
        Set<V> result = new HashSet<V>(node.values());
        for (Node child : node.childNodes())
        {
            result.addAll(values(child));
        }
        return result;
    }

    private class Node
    {
        private Map<String, Node> children;
        private Set<V> values;

        boolean addAllValues(Collection<V> values)
        {
            assert values != null;
            if (this.values == null)
            {
                this.values = new ArraySet<V>();
            }
            return this.values.addAll(values);
        }

        Node bisect(String key, int pivot)
        {
            assert key != null;
            String prefix = key.substring(0, pivot);
            String suffix = key.substring(pivot);
            Node child = new Node();
            child.putChild(suffix, removeChild(key));
            putChild(prefix, child);
            return child;
        }

        Collection<Entry<String, Node>> childEntries()
        {
            if (children == null)
            {
                return Collections.<Entry<String, Node>>emptyList();
            }
            return children.entrySet();
        }

        Collection<Node> childNodes()
        {
            if (children == null)
            {
                return Collections.<Node>emptyList();
            }
            return children.values();
        }

        boolean isEmpty()
        {
            return (children == null || children.isEmpty()) && (values == null || values.isEmpty());
        }

        boolean isUnary()
        {
            return (values == null || values.isEmpty()) && children != null && children.size() == 1;
        }

        Node putChild(String key, Node value)
        {
            assert key != null;
            assert value != null;
            if (children == null)
            {
                children = new HashMap<String, Node>(4);
            }
            return children.put(key, value);
        }

        Set<V> removeAllValues()
        {
            if (values == null)
            {
                return new ArraySet<V>();
            }
            Set<V> result = values;
            values = null;
            return result;
        }

        boolean removeAllValues(Collection<V> values)
        {
            assert values != null;
            if (this.values == null)
            {
                return false;
            }
            return this.values.removeAll(values);
        }

        Node removeChild(String key)
        {
            assert key != null;
            if (children == null)
            {
                return null;
            }
            return children.remove(key);
        }

        Node squash(String key)
        {
            assert key != null;
            Node child = removeChild(key);
            for (Entry<String, Node> entry : child.childEntries())
            {
                String edge = entry.getKey();
                putChild(key.concat(edge), child.removeChild(edge));
            }
            return child;
        }

        Set<V> values()
        {
            if (values == null)
            {
                return Collections.<V>emptySet();
            }
            return values;
        }
    }
}
