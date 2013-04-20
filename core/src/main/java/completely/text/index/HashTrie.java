package completely.text.index;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Trie based implementation of the {@link PrefixIndex} interface.
 *
 * <p>Note that this implementation is not synchronized.
 */
public class HashTrie<V> extends AbstractIndex<V> implements PrefixIndex<V>
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
        return node != null ? Collections.unmodifiableSet(node.values) : Collections.<V>emptySet();
    }

    @Override
    public Set<V> getAny(String fragment)
    {
        Node node = fragment != null ? find(root, fragment) : null;
        return node != null ? Collections.unmodifiableSet(values(node)) : Collections.<V>emptySet();
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
        return key != null ? removeAll(root, key) : Collections.<V>emptySet();
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
        Map<Character, Node> children;
        Set<V> values;

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
