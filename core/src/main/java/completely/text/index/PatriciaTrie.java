package completely.text.index;

import completely.common.Strings;
import completely.text.match.Automaton;
import completely.text.match.EqualityAutomaton;

import java.util.ArrayList;
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
            return new HashSet<V>(node.values);
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
            for (Entry<String, Node> entry : node.children.entrySet())
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
            for (Entry<String, Node> entry : node.children.entrySet())
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
            return node.values.addAll(values);
        }
        else
        {
            Node child = null;
            int commonPrefixLength = 0;
            for (Entry<String, Node> entry : node.children.entrySet())
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
                node.children.put(key, child);
            }
            return putAll(child, key.substring(commonPrefixLength), values);
        }
    }

    private boolean removeAll(Node node, Collection<V> values)
    {
        assert node != null;
        assert values != null;
        boolean result = node.values.removeAll(values);
        List<String> legacyEdges = new ArrayList<String>();
        for (Iterator<Entry<String, Node>> iterator = node.children.entrySet().iterator(); iterator.hasNext();)
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
            Set<V> result = node.values;
            node.values = new HashSet<V>(4);
            return result;
        }
        else
        {
            for (Entry<String, Node> entry : node.children.entrySet())
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
                        node.children.remove(edge);
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
            return node.values.removeAll(values);
        }
        else
        {
            for (Entry<String, Node> entry : node.children.entrySet())
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
                        node.children.remove(edge);
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
        int result = node.values.size();
        for (Node child : node.children.values())
        {
            result += size(child);
        }
        return result;
    }

    private Set<V> values(Node node)
    {
        assert node != null;
        Set<V> result = new HashSet<V>(node.values);
        for (Node child : node.children.values())
        {
            result.addAll(values(child));
        }
        return result;
    }

    private class Node
    {
        private Map<String, Node> children;
        private Set<V> values;

        Node()
        {
            children = new HashMap<String, Node>(4);
            values = new HashSet<V>(4);
        }

        Node bisect(String key, int pivot)
        {
            assert key != null;
            String prefix = key.substring(0, pivot);
            String suffix = key.substring(pivot);
            Node child = new Node();
            child.children.put(suffix, children.remove(key));
            children.put(prefix, child);
            return child;
        }

        boolean isEmpty()
        {
            return children.isEmpty() && values.isEmpty();
        }

        boolean isUnary()
        {
            return values.isEmpty() && children.size() == 1;
        }

        Node squash(String key)
        {
            assert key != null;
            Node child = children.remove(key);
            for (Entry<String, Node> entry : child.children.entrySet())
            {
                String edge = entry.getKey();
                children.put(key.concat(edge), child.children.remove(edge));
            }
            return child;
        }
    }
}
