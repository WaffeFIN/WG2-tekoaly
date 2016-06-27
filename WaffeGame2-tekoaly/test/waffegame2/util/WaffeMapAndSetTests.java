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

    @Test
    public void testContainsKey() {
        HashMap<Dummy1, Dummy2> controlMap = new HashMap();

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
        map.put(d14, new Dummy2(9));
        map.put(d15, new Dummy2(18));
        map.put(d16, new Dummy2(27));

        assertEquals(true, map.containsKey(d11));
        assertEquals(true, map.containsKey(d12));
        assertEquals(true, map.containsKey(d13));
        assertEquals(true, map.containsKey(d14));
        assertEquals(true, map.containsKey(d15));
        assertEquals(true, map.containsKey(d16));
        assertEquals(false, map.containsKey(d17));

        map.put(d17, new Dummy2(27));
        assertEquals(true, map.containsKey(d17));
    }

    @Test
    public void testBigAdditionTimeComparison() {
        int testSize = 200000;

        Map<Dummy1, Dummy2> hashMap = new HashMap(27);
        Map<Dummy1, Dummy2> map = new WaffeMap(27);

        long l1;
        long l2;
        long l3;

        l1 = System.nanoTime();
        for (int j = 0; j < 3; j++) {
            bigPutTestInit(testSize, hashMap);
            assertEquals(testSize, hashMap.size());
            hashMap.clear();
        }
        l2 = System.nanoTime();
        for (int j = 0; j < 3; j++) {
            bigPutTestInit(testSize, map);
            assertEquals(testSize, map.size());
            map.clear();
        }
        l3 = System.nanoTime();
        System.out.println(".put HashMap performance:  " + (l2 - l1) / 1000 + "\n.put WaffeMap performance: " + (l3 - l2) / 1000);
        bigPutTestInit(testSize, hashMap);
        bigPutTestInit(testSize, map);
        System.out.println("");
        l1 = System.nanoTime();
        for (int j = 0; j < 3; j++) {
            bigGetTest(testSize, hashMap);
        }
        l2 = System.nanoTime();
        for (int j = 0; j < 3; j++) {
            bigGetTest(testSize, map);
        }
        l3 = System.nanoTime();
        System.out.println(".get HashMap performance:  " + (l2 - l1) / 1000 + "\n.get WaffeMap performance: " + (l3 - l2) / 1000);
    }

    private void bigGetTest(int testSize, Map<Dummy1, Dummy2> map) {
        for (int i = 0; i < testSize; i++) {
            map.get(new Dummy1("" + i));
        }
    }

    private void bigPutTestInit(int testSize, Map<Dummy1, Dummy2> map) {
        for (int i = 0; i < testSize; i++) {
            map.put(new Dummy1("" + i), new Dummy2(i));
        }
    }

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
