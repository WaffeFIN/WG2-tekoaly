/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Walter
 */
public class HandAccessibilityTests {

    public HandAccessibilityTests() {
    }

    @Test
    public void testBooleans() {
        assertEquals(false, Hand.Accessibility.HIDDEN.isVisibleToOwner());
        assertEquals(true, Hand.Accessibility.PRIVATE.isVisibleToOwner());
        assertEquals(true, Hand.Accessibility.VISIBLE.isVisibleToOwner());
        assertEquals(true, Hand.Accessibility.PUBLIC.isVisibleToOwner());

        assertEquals(false, Hand.Accessibility.HIDDEN.isVisibleToEveryone());
        assertEquals(false, Hand.Accessibility.PRIVATE.isVisibleToEveryone());
        assertEquals(true, Hand.Accessibility.VISIBLE.isVisibleToEveryone());
        assertEquals(true, Hand.Accessibility.PUBLIC.isVisibleToEveryone());

        assertEquals(false, Hand.Accessibility.HIDDEN.isUsableByOwner());
        assertEquals(true, Hand.Accessibility.PRIVATE.isUsableByOwner());
        assertEquals(true, Hand.Accessibility.VISIBLE.isUsableByOwner());
        assertEquals(true, Hand.Accessibility.PUBLIC.isUsableByOwner());

        assertEquals(false, Hand.Accessibility.HIDDEN.isUsableByAnyone());
        assertEquals(false, Hand.Accessibility.PRIVATE.isUsableByAnyone());
        assertEquals(false, Hand.Accessibility.VISIBLE.isUsableByAnyone());
        assertEquals(true, Hand.Accessibility.PUBLIC.isUsableByAnyone());
    }
}
