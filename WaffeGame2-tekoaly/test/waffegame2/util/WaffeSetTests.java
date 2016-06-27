/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Walter
 */
public class WaffeSetTests {

    Set<Dummy> set;

    public WaffeSetTests() {
    }

    @Before
    public void setUp() {
        set = new WaffeSet();
    }

    @Test(timeout=1000)
    public void initTests() {
        assertEquals(0, set.size());
        assertEquals(true, set.isEmpty());
        assertEquals(false, set.contains(new Dummy("asd")));
    }

    @Test(timeout=1000)
    public void addTests1() {
        set.add(new Dummy("40"));

        assertEquals(false, set.isEmpty());
        assertEquals(1, set.size());

        for (int i = 0; i < 6; i++) {
            set.add(new Dummy("" + i));
        }
        assertEquals(7, set.size());
    }

    @Test(timeout=1000)
    public void addTests2() {
        Dummy d = new Dummy("4");
        set.add(d);
        assertEquals(false, set.isEmpty());
        assertEquals(1, set.size());
        set.add(d);
        assertEquals(1, set.size());
    }

    @Test(timeout=1000)
    public void addAllTests() {
        Set<Dummy> set2 = new WaffeSet();

        for (int i = 0; i < 6; i++) {
            set2.add(new Dummy("" + i));
        }

        set.addAll(set2);
        assertEquals(set2.size(), set.size());
        set.addAll(set2);
        assertEquals(set2.size(), set.size());
    }

    @Test(timeout=1000)
    public void containsTests() {
        Dummy d = new Dummy("4");
        set.add(d);

        for (int i = 0; i < 6; i++) {
            set.add(new Dummy("" + i));
        }
        
        assertEquals(true, set.contains(d));
        assertEquals(false, set.contains(new Dummy("x")));
    }

    @Test
    public void removeClearTests() {
        Dummy d = new Dummy("4");
        set.add(d);
        assertEquals(true, set.remove(d));
        assertEquals(false, set.contains(d));
        assertEquals(false, set.remove(d));
        
        for (int i = 0; i < 6; i++) {
            set.add(new Dummy("" + i));
        }
        set.clear();
        assertEquals(true, set.isEmpty());
    }
    
    @Test(timeout=1000)
    public void iterationTests() {
        Dummy d = new Dummy("4");
        set.add(d);
        for (int i = 0; i < 6; i++) {
            set.add(new Dummy("" + i));
        }
        int n = 0;
        for (Dummy dummy : set) {
            n++;
        }
        assertEquals(n, set.size());
    }

    private static class Dummy {

        String name;

        public Dummy(String name) {
            this.name = name;
        }
    }
}
