/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Walter
 */
public class WaffeListTests {

    private class Dummy {}

    List<Dummy> list;

    public WaffeListTests() {}

    @Before
    public void setUp() {
        list = new WaffeList();
    }

    @Test
    public void testInit() {
        assertEquals(0, list.size());
        assertEquals(true, list.isEmpty());
        assertEquals(false, list.contains(new Dummy()));
    }

    @Test
    public void testAdd() {
        list.add(new Dummy());

        assertEquals(false, list.isEmpty());
        assertEquals(1, list.size());

        for (int i = 0; i < 6; i++) {
            list.add(new Dummy());
        }
        assertEquals(7, list.size());
    }

    @Test
    public void testAddAll() {
        List<Dummy> arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            arrayList.add(new Dummy());
        }
        list.addAll(arrayList);
        assertEquals(10, list.size());

        boolean equals = true;
        for (int i = 0; i < 10; i++) {
            if (!list.get(i).equals(arrayList.get(i))) {
                equals = false;
                break;
            }
        }
        assertTrue(equals);
    }

    @Test
    public void testGetRemoveClear() {
        Dummy d = new Dummy();
        list.add(d);
        for (int i = 0; i < 6; i++) {
            list.add(new Dummy());
        }
        int size = list.size();
        list.remove(new Dummy());
        assertTrue(size == list.size());
        list.remove(d);
        assertTrue(size == list.size() + 1);
        list.clear();
        assertEquals(0, list.size());
        list.add(d);
        assertEquals(d, list.get(0));
        assertEquals(d, list.remove(0));
        list.add(d);
        assertEquals(true, list.remove(d));
        assertEquals(false, list.remove(d));
        assertEquals(false, list.remove(null));
    }

    @Test
    public void testContainsContainsAll() {
        Dummy d = new Dummy();
        list.add(d);
        for (int i = 0; i < 6; i++) {
            list.add(new Dummy());
        }
        List<Dummy> arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            arrayList.add(new Dummy());
        }
        list.addAll(arrayList);

        assertEquals(true, list.contains(d));
        assertEquals(true, list.containsAll(arrayList));

        arrayList.add(new Dummy());
        assertEquals(false, list.containsAll(arrayList));
        list.remove(d);
        assertEquals(false, list.contains(d));
    }

    @Test
    public void testIteration() {
        for (int i = 0; i < 20; i++) {
            list.add(new Dummy());
        }
        int n = 0;
        for (Dummy dummy : list) {
            n++;
        }
        assertEquals(n, list.size());
    }

    @Test
    public void testBigAddition() {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 25000; i++) {
                list.add(new Dummy());
            }
            assertEquals(25000, list.size());
            list.clear();
        }
    }
}
