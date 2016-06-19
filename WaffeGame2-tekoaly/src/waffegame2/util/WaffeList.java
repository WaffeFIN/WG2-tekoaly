/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.AbstractList;
import java.util.List;


/**
 *
 * @author Walter
 */
public class WaffeList<E> extends AbstractList<E> implements List<E>{
    
    private int size;
    private E[] array;

    public WaffeList() {
    }
    
    public WaffeList(int initialSize) {
    }

    @Override
    public E get(int index) {
        return null; //:D
    }

    @Override
    public int size() {
        return size; //:D
    }
    
}
