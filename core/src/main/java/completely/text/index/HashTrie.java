package completely.text.index;

import completely.text.match.Automaton;

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

/**
 * Trie based implementation of the {@link FuzzyIndex} interface.
 *
 * <p>Note that this implementation is not synchronized.
 */
public class HashTrie<V> extends AbstractIndex<V> implements FuzzyIndex<V>
{
    private Node root;

    public HashTrie()
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
        Node node = key != null ? find(root, key) : null;
        if (node != null)
        {
            return new HashSet<V>(node.values);
        }
        return new HashSet<V>();
    }

    @Override
    public Set<V> getAny(String fragment)
    {
        Node node = fragment != null ? find(root, fragment) : null;
        if (node != null)
        {
            return values(node);
        }
        return new HashSet<V>();
    }

    @Override
    public Set<V> getAny(Automaton matcher)
    {
        Set<V> result = new HashSet<V>();
        if (matcher != null)
        {
            for (Node node : find(root, matcher))
            {
                result.addAll(values(node));
            }
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
        return key != null ? putAll(root, key, values) : false;
    }

    @Override
    public boolean removeAll(Collection<V> values)
    {
        return removeAll(root, values);
    }

    @Override
    public Set<V> removeAll(String key)
    {
        return key != null ? removeAll(root, key) : new HashSet<V>();
    }

    @Override
    public boolean removeAll(String key, Collection<V> values)
    {
        return key != null ? removeAll(root, key, values) : false;
    }

    @Override
    public int size()
    {
        return size(root);
    }

    private Node find(Node node, String key)
    {
        if (key.length() <= 0)
        {
            return node;
        }
        else
        {
            char character = key.charAt(0);
            Node child = node.children.get(character);
            if (child != null)
            {
                return find(child, key.substring(1));
            }
        }
        return null;
    }

    private Collection<Node> find(Node node, Automaton matcher)
    {
        if (matcher.isWordAccepted())
        {
            return Arrays.asList(node);
        }
        else if (!matcher.isWordRejected())
        {
            List<Node> result = new LinkedList<Node>();
            for (Entry<Character, Node> child : node.children.entrySet())
            {
                char character = child.getKey();
                result.addAll(find(child.getValue(), matcher.step(character)));
            }
            return result;
        }
        return Collections.<Node>emptyList();
    }

    private boolean putAll(Node node, String key, Collection<V> values)
    {
        if (key.length() <= 0)
        {
            return node.values.addAll(values);
        }
        else
        {
            char character = key.charAt(0);
            Node child = node.children.get(character);
            if (child == null)
            {
                child = new Node();
                node.children.put(character, child);
            }
            return putAll(child, key.substring(1), values);
        }
    }

    private boolean removeAll(Node node, Collection<V> values)
    {
        boolean result = node.values.removeAll(values);
        for (Iterator<Node> iterator = node.children.values().iterator(); iterator.hasNext();)
        {
            Node child = iterator.next();
            result = removeAll(child, values) || result;
            if (child.isEmpty())
            {
                iterator.remove();
            }
        }
        return result;
    }

    private Set<V> removeAll(Node node, String key)
    {
        if (key.length() <= 0)
        {
            Set<V> result = node.values;
            node.values = new HashSet<V>();
            return result;
        }
        else
        {
            char character = key.charAt(0);
            Node child = node.children.get(character);
            if (child != null)
            {
                Set<V> result = removeAll(child, key.substring(1));
                if (child.isEmpty())
                {
                    node.children.remove(character);
                }
                return result;
            }
        }
        return Collections.<V>emptySet();
    }

    private boolean removeAll(Node node, String key, Collection<V> values)
    {
        if (key.length() <= 0)
        {
            return node.values.removeAll(values);
        }
        else
        {
            char character = key.charAt(0);
            Node child = node.children.get(character);
            if (child != null)
            {
                boolean result = removeAll(child, key.substring(1), values);
                if (child.isEmpty())
                {
                    node.children.remove(character);
                }
                return result;
            }
        }
        return false;
    }

    private int size(Node node)
    {
        int result = node.values.size();
        for (Node child : node.children.values())
        {
            result += size(child);
        }
        return result;
    }

    private Set<V> values(Node node)
    {
        Set<V> result = new HashSet<V>(node.values);
        for (Node child : node.children.values())
        {
            result.addAll(values(child));
        }
        return result;
    }

    private class Node
    {
        private Map<Character, Node> children;
        private Set<V> values;

        Node()
        {
            children = new HashMap<Character, Node>();
            values = new HashSet<V>();
        }

        boolean isEmpty()
        {
            return children.isEmpty() && values.isEmpty();
        }
    }
}
