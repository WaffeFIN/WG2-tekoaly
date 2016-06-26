/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Walter
 */
public class WaffeMapAndSetTests {

    private Map<Dummy1, Dummy2> map;

    public WaffeMapAndSetTests() {
    }

    @Before
    public void setUp() {
        map = new WaffeMap();
    }

    @Test
    public void initTest() {
        assertEquals(0, map.size());
        assertEquals(true, map.isEmpty());
        assertEquals(false, map.containsKey(new Dummy1("asd")));
    }

    @Test
    public void testAdd1() {
        map.put(new Dummy1("4"), new Dummy2(0));

        assertEquals(false, map.isEmpty());
        assertEquals(1, map.size());

        for (int i = 0; i < 6; i++) {
            map.put(new Dummy1("" + i), new Dummy2(i));
        }
        assertEquals(7, map.size());
    }

    @Test
    public void testAdd2() {
        Dummy1 d1 = new Dummy1("");
        Dummy2 d2 = new Dummy2(0);
        map.put(d1, d2);
        map.put(d1, d2);

        assertEquals(1, map.size());
        map.put(d1, new Dummy2(12));
        assertEquals(1, map.size());
        map.put(new Dummy1("haha"), d2);
        assertEquals(2, map.size());
    }

    @Test
    public void testPutAll() {
        Map<Dummy1, Dummy2> hashMap = new HashMap();
        for (int i = 0; i < 10; i++) {
            hashMap.put(new Dummy1("" + i), new Dummy2(i));
        }
        assertEquals(10, hashMap.size());
        map.putAll(hashMap);
        assertEquals(10, map.size());

        Dummy1 d1 = new Dummy1("x");
        Dummy2 d2 = new Dummy2(213);
        hashMap.put(d1, d2);
        map.putAll(hashMap);
        assertEquals(11, map.size());
    }

    @Test
    public void testGetRemoveClear() {
        Dummy1 d11 = new Dummy1("H");
        Dummy1 d12 = new Dummy1("He");
        Dummy1 d13 = new Dummy1("Li");
        Dummy1 d14 = new Dummy1("Be");
        Dummy1 d15 = new Dummy1("B");
        Dummy1 d16 = new Dummy1("C");
        Dummy1 d17 = new Dummy1("N");

        map.put(d11, new Dummy2(1));
        map.put(d12, new Dummy2(2));
        map.put(d13, new Dummy2(3));
        map.put(d14, new Dummy2(4));
        map.put(d15, new Dummy2(5));
        map.put(d16, new Dummy2(6));
        assertEquals(6, map.size());
        
        assertEquals(6, map.get(d16).value);
        assertEquals(1, map.get(d11).value);
        assertEquals(3, map.get(d13).value);
        assertEquals(null, map.get(d17));
        
        assertEquals(6, map.size());
        assertEquals(5, map.remove(d15).value);
        assertEquals(5, map.size());
        
        map.clear();
        assertEquals(0, map.size());
        map.remove(d11);
        map.remove(d12);
        map.remove(d13);
        map.remove(d14);
        map.remove(d15);
        map.remove(d16);
        assertEquals(0, map.size());
    }

//    @Test
//    public void testContainsContainsAll() {
//        WaffeListTests.Dummy d = new WaffeListTests.Dummy();
//        list.add(d);
//        for (int i = 0; i < 6; i++) {
//            list.add(new WaffeListTests.Dummy());
//        }
//        List<WaffeListTests.Dummy> arrayList = new ArrayList();
//        for (int i = 0; i < 10; i++) {
//            arrayList.add(new WaffeListTests.Dummy());
//        }
//        list.addAll(arrayList);
//
//        assertEquals(true, list.contains(d));
//        assertEquals(true, list.containsAll(arrayList));
//
//        arrayList.add(new WaffeListTests.Dummy());
//        assertEquals(false, list.containsAll(arrayList));
//        list.remove(d);
//        assertEquals(false, list.contains(d));
//    }
//
//    @Test
//    public void testIteration() {
//        for (int i = 0; i < 20; i++) {
//            list.add(new WaffeListTests.Dummy());
//        }
//        int n = 0;
//        for (WaffeListTests.Dummy dummy : list) {
//            n++;
//        }
//        assertEquals(n, list.size());
//    }
//
//    @Test
//    public void testBigAddition() {
//        for (int j = 0; j < 3; j++) {
//            for (int i = 0; i < 25000; i++) {
//                list.add(new WaffeListTests.Dummy());
//            }
//            assertEquals(25000, list.size());
//            list.clear();
//        }
//    }

    private static class Dummy1 {

        String name;

        public Dummy1(String name) {
            this.name = name;
        }
    }

    private static class Dummy2 {

        int value;

        public Dummy2(int value) {
            this.value = value;
        }
    }
}
