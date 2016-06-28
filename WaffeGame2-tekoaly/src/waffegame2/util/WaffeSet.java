/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Note that the JavaDoc pre-exists in Set interface
 *
 * @author Walter
 */
public class WaffeSet<E> implements Set<E> {

    WaffeMap<E, Dummy> map;
    final static Dummy dummyValue = new Dummy();

    public WaffeSet() {
        map = new WaffeMap();
    }

    public WaffeSet(int capacity) {
        map = new WaffeMap(capacity);
    }
    
    public WaffeSet(Collection<? extends E> c) {
        map = new WaffeMap(c.size());
        addAll(c);
    }

    public boolean add(E e) {
        if (map.containsKey(e)) {
            return false;
        }
        map.put(e, dummyValue);
        return true;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o); //:D
    }

    @Override
    public boolean remove(Object o) {
        if (!map.containsKey(o)) {
            return false;
        }
        map.remove(o);
        return true; //:D
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            return true;
        }
        for (Object c1 : c) {
            if (!map.containsKey(c1)) {
                return false;
            }
        }
        return true; //:D
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean rv = false;
        for (E c1 : c) {
            if (add(c1)) {
                rv = true;
            }
        }
        return rv;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean rv = false;
        for (Object c1 : c) {
            if (remove(c1)) {
                rv = true;
            }
        }
        return rv;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return map.keyIterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Dummy class used for the map
     */
    private static class Dummy {
        public Dummy() {
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.map);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WaffeSet<?> other = (WaffeSet<?>) obj;
        return (other.size() == this.size() && other.containsAll(this) && this.containsAll(other));
    }
}
