/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Walter
 */
public class WaffeList<E> extends AbstractList<E> implements List<E> {

    private int size;
    private Object[] array;

    public WaffeList() {
        this(8);
    }

    public WaffeList(int initialSize) {
        array = new Object[initialSize];
        size = 0;
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

    private void increaseArray() {
        array = Arrays.copyOf(array, size * 2);
    }

    private void rangeCheck(int i) {
        if (i < 0 || i >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

}
