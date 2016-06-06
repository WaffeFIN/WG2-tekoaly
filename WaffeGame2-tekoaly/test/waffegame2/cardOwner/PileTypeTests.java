package waffegame2.cardOwner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Walter
 */
public class PileTypeTests {

    public PileTypeTests() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void pileTypeTest() {
        assertEquals("Open", PileTypeWaffeGame2.NULL.toString());
        assertEquals("Clubs", PileTypeWaffeGame2.CLUBS.toString());
        assertEquals("Diamonds", PileTypeWaffeGame2.DIAMONDS.toString());
        assertEquals("Hearts", PileTypeWaffeGame2.HEARTS.toString());
        assertEquals("Spades", PileTypeWaffeGame2.SPADES.toString());
        assertEquals("Straight", PileTypeWaffeGame2.STRAIGHT.toString());
        assertEquals("Pairs", PileTypeWaffeGame2.PAIRS.toString());
        assertEquals("Triples", PileTypeWaffeGame2.TRIPLES.toString());
        assertEquals("Quadruples", PileTypeWaffeGame2.QUADRUPLES.toString());
    }
}
