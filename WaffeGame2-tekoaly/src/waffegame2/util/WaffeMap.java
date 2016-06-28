/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Note that the JavaDoc pre-exists in Map interface
 * 
 * @author Walter
 */
public class WaffeMap<K, V> implements Map<K, V> {//extends AbstractMap<K, V>

    private int size;
    private int maximumBeforeResize;
    private WaffeEntry<K, V>[] array;

    private final static int minimumCapacity = 9;
    private float loadingFactor = 0.75f;

    public WaffeMap() {
        this(minimumCapacity);
    }

    public WaffeMap(int capacity) {
        if (capacity < minimumCapacity) {
            capacity = minimumCapacity;
        }
        array = (WaffeEntry<K, V>[]) new WaffeEntry[capacity];
        maximumBeforeResize = (int) (capacity / loadingFactor);
    }

    public WaffeMap(int capacity, float loadingFactor) {
        array = (WaffeEntry<K, V>[]) new WaffeEntry[capacity];
        maximumBeforeResize = (int) (capacity / loadingFactor);
        this.loadingFactor = loadingFactor;
    }

    /**
     * Resizes the array larger. All entries are recalculated.
     */
    private void resize() {
        WaffeEntry<K, V>[] oldArray = array;
        array = (WaffeEntry<K, V>[]) new WaffeEntry[oldArray.length * 3];
        maximumBeforeResize = (int) (array.length / loadingFactor);
        size = 0;

        for (int i = 0; i < oldArray.length; i++) {
            WaffeEntry<K, V> e = oldArray[i];
            if (e == null) {
                continue;
            }
            put(e.key, e.value);
            while (e.next != null) {
                e = e.next;
                put(e.key, e.value);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        if (array[index] != null) {
            WaffeEntry<K, V> node = array[index];
            if (node.key == key) {
                V rv = node.value;
                node.value = value;
                return rv;
            }
            while (node.next != null) {
                if (node.next.key == key) {
                    node.next = node.next.next;
                    size--;
                } else {
                    node = node.next;
                }
            }
            WaffeEntry<K, V> e = new WaffeEntry(key, value);
            node.next = e;
            size++;
            return node.value;
        }
        array[index] = new WaffeEntry(key, value);
        size++;
        if (size > maximumBeforeResize) {
            resize();
        }
        return null;
    }

    private int hash(Object key) {
        if (key == null) {
            return 0;
        }
        return hash(key.hashCode());
    }

    /**
     * Generates the index used for the hash table from the hash
     */
    private int hash(int h) {
        h = h ^ (h >>> 16);
        h = h % array.length;
        if (h < 0) {
            h += array.length;
        }
        return h;
    }

    @Override
    public V get(Object key) {
        int index = hash(key);
        WaffeEntry<K, V> node = array[index];
        if (node == null) {
            return null;
        }
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = hash(key);
        WaffeEntry<K, V> node = array[index];
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            array[index] = node.next;
            size--;
            return node.value;
        }
        while (node.next != null) {
            if (node.next.key.equals(key)) {
                V rv = node.next.value;
                node.next = node.next.next;
                size--;
                return rv;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public void clear() {
        if (size > 0) {
            size = 0;
            for (int i = 0; i < array.length; i++) {
                array[i] = null;
            }
        }
    }

    /**
     * Returns an iterator for the keySet. Use this instead of keySet()!
     */
    public Iterator<K> keyIterator() {
        return new keyIterator();
    }

    /**
     * Returns an iterator for the values. Use this instead of values()!
     */
    public Iterator<V> valueIterator() {
        return new valueIterator();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return size == 0; //:D
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null; //:D
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    /**
     * A custom entry used by the hashtable. Necessary for the next value
     */
    public static class WaffeEntry<K, V> implements Entry<K, V> {

        K key;
        V value;
        WaffeEntry<K, V> next;

        public WaffeEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key; //:D
        }

        @Override
        public V getValue() {
            return value; //:D
        }

        @Override
        public V setValue(V value) {
            V prev = this.value;
            this.value = value;
            return prev;
        }
    }

    class keyIterator extends abstractIterator implements Iterator<K> {

        @Override
        public K next() {
            return nextNode().key;
        }
    }

    class valueIterator extends abstractIterator implements Iterator<V> {

        @Override
        public V next() {
            return nextNode().value;
        }
    }

    /**
     * Used by both key and value iterators
     */
    abstract class abstractIterator {

        int index;
        WaffeEntry<K, V> next;
        WaffeEntry<K, V>[] a;

        abstractIterator() {
            index = 0;
            a = (WaffeEntry<K, V>[]) array;
            nextNode();
        }

        public boolean hasNext() {
            return next != null;
        }

        public final WaffeEntry<K, V> nextNode() {
            WaffeEntry<K, V> current = next;
            if (next != null) {
                next = next.next;
            }

            while (next == null) {
                if (index >= array.length) {
                    break;
                }
                next = a[index]; //TODO
                index++;
            }
            return current;
        }
    }
}
