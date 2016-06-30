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

    private class Dummy {
    }

    List<Dummy> list;

    public WaffeListTests() {
    }

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

        List<Dummy> list2 = new WaffeList();
        for (int i = 0; i < 10; i++) {
            list2.add(new Dummy());
        }
        list.addAll(list2);
        assertEquals(30, list.size());
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

    @Test
    public void testToArray() {
        Dummy d = new Dummy();
        list.add(d);
        for (int i = 0; i < 9; i++) {
            list.add(new Dummy());
        }
        list.add(d);
        for (int i = 0; i < 9; i++) {
            list.add(new Dummy());
        }
        Object[] array = list.toArray();
        assertEquals(20, array.length);
        assertEquals(d, array[0]);
        assertEquals(d, array[10]);
    }

    @Test
    public void testSubList() {
        Dummy d = new Dummy();
        list.add(d);
        for (int i = 0; i < 4; i++) {
            list.add(new Dummy());
        }
        list.add(d);
        for (int i = 0; i < 9; i++) {
            list.add(new Dummy());
        }
        list.add(d);
        for (int i = 0; i < 14; i++) {
            list.add(new Dummy());
        }
        List<Dummy> sl1 = list.subList(0, 5);
        List<Dummy> sl2 = list.subList(0, 15);
        List<Dummy> sl3 = list.subList(5, list.size());

        assertEquals(15, sl2.size());
        for (int i = 0; i < sl1.size(); i++) {
            assertEquals(list.get(i), sl1.get(i));
        }

        for (int i = 0; i < sl2.size(); i++) {
            assertEquals(list.get(i), sl2.get(i));
        }

        for (int i = 5; i < list.size(); i++) {
            assertEquals(list.get(i), sl3.get(i - 5));
        }
        assertEquals(d, sl3.get(0));
    }

}
