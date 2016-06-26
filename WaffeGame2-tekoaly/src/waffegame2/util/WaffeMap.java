/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Walter
 */
public class WaffeMap<K, V> implements Map<K, V> {//extends AbstractMap<K, V>

    private int size;
    private int maximumBeforeResize;
    private WaffeEntry<K, V>[] array;
    private final static int initialCapacity = 16;
    private float loadingFactor = 0.75f;

    public WaffeMap() {
        this(initialCapacity);
    }

    public WaffeMap(int capacity) {
        array = (WaffeEntry<K, V>[]) new WaffeEntry[capacity];
        maximumBeforeResize = (int) (capacity / loadingFactor);
    }

    public WaffeMap(int capacity, float loadingFactor) {
        array = (WaffeEntry<K, V>[]) new WaffeEntry[capacity];
        maximumBeforeResize = (int) (capacity / loadingFactor);
        this.loadingFactor = loadingFactor;
    }

    private void resize() {
        maximumBeforeResize *= 2;
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
                array[index] = new WaffeEntry(key.hashCode(), key, value);
                return node.value;
            }
            while (node.next != null) {
                if (node.next.key == key) {
                    size--;
                    node.next = node.next.next;
                } else {
                    node = node.next;
                }
            }
            WaffeEntry<K, V> newNode = new WaffeEntry(key.hashCode(), key, value);
            node.next = newNode;
            size++;
            return node.value;
        }
        array[index] = new WaffeEntry(key.hashCode(), key, value);
        if (size > maximumBeforeResize) {
            resize();
        }
        size++;
        return null;
    }

    private int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        h = h ^ (h >>> 16);
        return (h) % array.length;
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
                node.next = node.next.next;
                size--;
                return node.next.value;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public void clear() {
        if (size != 0) {
            array = (WaffeEntry<K, V>[]) new WaffeEntry[initialCapacity];
            size = 0;
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
//        for (int i = 0; i < array.length; i++) {
//            Node<K, V> node = array[i];
//            while(node != null) {
//            }
//        }
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
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    private static class WaffeEntry<K, V> implements Entry<K, V> {

        int hash;
        K key;
        V value;
        WaffeEntry<K, V> next;

        public WaffeEntry(int hash, K key, V value) {
            this.hash = hash;
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

}
