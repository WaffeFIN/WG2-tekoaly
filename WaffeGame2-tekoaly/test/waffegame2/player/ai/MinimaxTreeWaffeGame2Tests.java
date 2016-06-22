/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import waffegame2.card.Card;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;

/**
 *
 * @author Walter
 */
public class MinimaxTreeWaffeGame2Tests {

    private MinimaxTreeWaffeGame2 tree;
    private HashSet<Card> myCards;
    private HashSet<Card> othersCards;
    private HashSet<Card> pileCards;

    public MinimaxTreeWaffeGame2Tests() {
    }

    @Before
    public void setUp() {
        tree = new MinimaxTreeWaffeGame2(new PileRuleWaffeGame2(), 1000000);
        myCards = new HashSet();
        othersCards = new HashSet();
        pileCards = new HashSet();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void treeGenerationTestSimpleWin() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MAX_VALUE, tree.root.value);
    }

    @Test
    public void treeGenerationTestSinglesWin() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        myCards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.SEVEN, Card.Suit.CLUBS));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MAX_VALUE, tree.root.value);
    }

    @Test
    public void treeGenerationTestSinglesLoss() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.SEVEN, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MIN_VALUE, tree.root.value);
    }

    @Test
    public void treeGenerationTestPairWin() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.SEVEN, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MAX_VALUE, tree.root.value);
    }

    @Test
    public void treeGenerationTestPairLoss() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.QUEEN, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.QUEEN, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.ACE, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MIN_VALUE, tree.root.value);
    }
    
    @Test
    public void treeGenerationTestPairsLoss() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        myCards.add(new Card(Card.Value.TWO, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.KING, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.JACK, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.FIVE, Card.Suit.SPADES));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MIN_VALUE, tree.root.value);
    }
    
    @Test
    public void treeGenerationTestGroupLoss() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.EIGHT, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.ACE, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.THREE, Card.Suit.DIAMONDS));


        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MIN_VALUE, tree.root.value);
    }
    
    @Test
    public void treeGenerationTestStraightWin() {
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.NINE, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.SIX, Card.Suit.CLUBS));
        myCards.add(new Card(Card.Value.JACK, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.SEVEN, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.TEN, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.FIVE, Card.Suit.SPADES));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MAX_VALUE, tree.root.value);
    }
    
    @Test
    public void treeGenerationTestStraightLoss() {
        myCards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        myCards.add(new Card(Card.Value.FOUR, Card.Suit.CLUBS));
        myCards.add(new Card(Card.Value.JACK, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.TWO, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.QUEEN, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.THREE, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MIN_VALUE, tree.root.value);
    }
    
    @Test
    public void treeGenerationTestSimple2() {
        myCards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
        myCards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.FOUR, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.SIX, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.TWO, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.FIVE, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.ACE, Card.Suit.DIAMONDS));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MIN_VALUE, tree.root.value);
    }
    
    @Test
    public void treeGenerationTestProblem1() {
        myCards.add(new Card(Card.Value.JACK, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.CLUBS));
        myCards.add(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.FOUR, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.TWO, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
        myCards.add(new Card(Card.Value.QUEEN, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.EIGHT, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.SEVEN, Card.Suit.SPADES));

        tree.generateTree(myCards, othersCards, null);

        assertEquals(Integer.MAX_VALUE, tree.root.value);
    }
    
//    @Test
//    public void treeGenerationTestProblem2() {
//        pileCards.add(new Card(Card.Value.TWO, Card.Suit.DIAMONDS));
//        pileCards.add(new Card(Card.Value.THREE, Card.Suit.SPADES));
//        myCards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
//        myCards.add(new Card(Card.Value.FOUR, Card.Suit.SPADES));
//        myCards.add(new Card(Card.Value.SEVEN, Card.Suit.CLUBS));
//        myCards.add(new Card(Card.Value.TEN, Card.Suit.DIAMONDS));
//        othersCards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
//        othersCards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
//        othersCards.add(new Card(Card.Value.JACK, Card.Suit.SPADES));
//
//        tree.generateTree(myCards, othersCards, null);
//
//        assertEquals(Integer.MAX_VALUE, tree.root.value);
//        /*
//        Winning line:
//        HA S4
//        C5
//        
//        2C
//        7C
//        
//        10D Win
//        */
//    }
    
    @Test
    public void roughScoreEstimationTest() {
//        tree.estimateScore(null, null, null);
    }

    @Test
    public void findMoveTest() {
//        tree.findBestMove(null, null, null, true)
    }
}
