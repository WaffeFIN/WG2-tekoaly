/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Walter
 */
public class SuccessorFinderWaffeGame2Tests {

    SuccessorFinderWaffeGame2 finder;

    public SuccessorFinderWaffeGame2Tests() {
    }

    @Before
    public void setUp() {
        finder = new SuccessorFinderWaffeGame2();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void findSuccessorsToHandTest1() {
        finder.createSuccessors(null, 0);
    }
}
