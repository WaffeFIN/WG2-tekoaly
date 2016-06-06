/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Walter
 */
public class UtilTests {

    public UtilTests() {
    }

    @Test
    public void testSquare() {
        assertEquals(1, Util.sqr(1), 0.01);
        assertEquals(7, Util.sqr(2.646), 0.01);
    }

    @Test
    public void testIsInteger() {
        assertEquals(true, Util.isInteger("7"));
        assertEquals(true, Util.isInteger("-17"));
        assertEquals(false, Util.isInteger("-17asd"));
        assertEquals(false, Util.isInteger("0.3"));
    }
}
