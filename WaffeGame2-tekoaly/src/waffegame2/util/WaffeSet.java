/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Walter
 */
public class WaffeSet<E> extends AbstractSet<E> implements Set<E> {

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            @Override
            public boolean hasNext() {
                return false; //:D
            }

            @Override
            public E next() {
                return null; //:D
            }
        }; //:D
    }

    @Override
    public int size() {
        return 0; //:D
    }

}
