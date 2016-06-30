/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Walter
 */
public class WaffeList<E> extends AbstractList<E> implements List<E> {

    private int size;
    private Object[] array;
    private final static int minimumCapacity = 8;

    public WaffeList() {
        this(minimumCapacity);
    }

    public WaffeList(int initialSize) {
        if (initialSize < minimumCapacity) {
            initialSize = minimumCapacity;
        }
        array = new Object[initialSize];
        size = 0;
    }

    public WaffeList(Collection<? extends E> c) {
        this(c.size());
        addAll(c);
    }

    @Override
    public E get(int i) {
        rangeCheck(i);
        @SuppressWarnings("unchecked")
        final E e = (E) array[i];
        return e;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(int i, E element) {
        if (i != size) {
            rangeCheck(i);
            for (int j = size; j > i; j--) {
                array[j] = array[j - 1];
            }
        }
        array[i] = element;

        size++;
        if (size >= array.length) {
            increaseArray();
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public E remove(int i) {
        if (isEmpty()) {
            return null;
        }
        rangeCheck(i);
        E e = (E) array[i];
        for (int j = i; j < size - 1; j++) {
            array[j] = array[j + 1];
        }
        array[size - 1] = null;
        size--;
        return e;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E set(int i, E element) {
        rangeCheck(i);
        E e = (E) array[i];
        array[i] = element;
        return e;
    }

    @Override
    public Object[] toArray() {
        Object[] rv = new Object[size];
        for (int i = 0; i < size; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    @Override
    public List<E> subList(int from, int to) {
        if (from > to) {
            int temp = to;
            to = from;
            from = temp;
        }
        rangeCheck(from);
        rangeCheck(to - 1);
        WaffeList<E> rv = new WaffeList(to - from);
        for (int i = from; i < to; i++) {
            rv.add(get(i));
        }
        return rv;
    }

    private void increaseArray() {
        array = Arrays.copyOf(array, size * 2);
    }

    private void rangeCheck(int i) {
        if (i < 0 || i >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

}
