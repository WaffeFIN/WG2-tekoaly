package waffegame2.cardOwner.pileRules;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import waffegame2.card.Card;
import waffegame2.cardOwner.*;

/**
 *
 * @author Walter
 */
public class PileRuleWaffeGame2Tests {

    private Pile pile;
    private List<Card> cards;

    public PileRuleWaffeGame2Tests() {
    }

    private void checkAdd(PileType type) {
        boolean r = pile.addCards(cards);
        assertEquals(type, pile.getType());
        assertTrue(r);
    }

    @Before
    public void setUp() {
        pile = new Pile(new PileRuleWaffeGame2());
        cards = new ArrayList();
        cards.add(new Card(Card.Value.TWO, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.DIAMONDS));
    }

    @Test
    public void addCardToPile() {
        assertTrue(pile.addCard(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS)));
    }

    @Test
    public void addCardsToPile() {
        cards.add(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
        assertTrue(pile.addCards(cards));
    }

    @Test
    public void addCardToPileWorks() {
        pile.addCard(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
        assertEquals(1, pile.cardAmount());
        assertEquals(PileTypeWaffeGame2.DIAMONDS, pile.getType());
    }

    @Test
    public void addNoCardsToPileWorks() {
        cards.clear();
        assertEquals(false, pile.addCards(cards));
    }

    @Test
    public void clearPileWorks() {
        pile.addCard(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
        pile.clear();
        assertEquals(PileTypeWaffeGame2.NULL, pile.getType());
        assertEquals(0, pile.cardAmount());
    }

    @Test
    public void emptyPileAddOnlyJokers() {
        cards.clear();
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        assertTrue(pile.addCards(cards));
        assertEquals(PileTypeWaffeGame2.OPEN, pile.getType());
    }

    @Test
    public void emptyPileIsInvalid() {
        cards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        assertTrue(!pile.addCards(cards));
        assertEquals(PileTypeWaffeGame2.NULL, pile.getType());
    }

    @Test
    public void emptyPileIsInvalidWithJokers() {
        cards.add(new Card(Card.Value.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        assertTrue(!pile.addCards(cards));
        assertEquals(PileTypeWaffeGame2.NULL, pile.getType());
    }

    @Test
    public void emptyPileSuitIsValid() {
        cards.add(new Card(Card.Value.TEN, Card.Suit.DIAMONDS));
        checkAdd(PileTypeWaffeGame2.DIAMONDS);
    }

    @Test
    public void emptyPileSuitIsValidWithJokers() {
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.DIAMONDS);
    }

    @Test
    public void emptyPileStraightIsValid() {
        cards.add(new Card(Card.Value.THREE, Card.Suit.HEARTS));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
    }

    @Test
    public void emptyPileStraightIsValidWrapAround() {
        cards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
    }

    @Test
    public void emptyPileStraightIsValidWithJokersOnEnd() {
        cards.add(new Card(Card.Value.THREE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
    }

    @Test
    public void emptyPileStraightIsValidWithJokersInMiddle() {
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.SPADES));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
    }

    @Test
    public void emptyPileStraightIsValidWithJokersInMiddleWrapAround() {
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
    }

    @Test
    public void emptyPileStraightIsValidWithThreeJokersInMiddle() {
        cards.add(new Card(Card.Value.FOUR, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
    }

    @Test
    public void emptyPileStraightIsValidWithJokersEverywhereWrapAround() {
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
    }

    @Test
    public void emptyPileStraightWithTooManyCards() {
        cards.add(new Card(Card.Value.THREE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.CLUBS));
        Card card = new Card(Card.Value.QUEEN, Card.Suit.DIAMONDS);
        cards.add(card);
        assertTrue(!pile.addCards(cards));
        assertEquals(PileTypeWaffeGame2.NULL, pile.getType());
        cards.remove(card);
        assertTrue(pile.addCards(cards));
        assertEquals(PileTypeWaffeGame2.STRAIGHT, pile.getType());
    }
    
    @Test
    public void emptyPileOverStraight() {
        cards.add(new Card(Card.Value.THREE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.KING, Card.Suit.DIAMONDS));
        assertTrue(pile.addCards(cards));
        assertEquals(PileTypeWaffeGame2.LONGSTRAIGHT, pile.getType());
    }

    
    @Test
    public void emptyPilePerfectStraight() {
        cards.add(new Card(Card.Value.THREE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        assertTrue(pile.addCards(cards));
        assertEquals(PileTypeWaffeGame2.PERFECTSTRAIGHT, pile.getType());
    }

    
    @Test
    public void emptyPilePairsAreValid() {
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        checkAdd(PileTypeWaffeGame2.PAIRS);
    }

    @Test
    public void emptyPilePairsAreValidWithJoker() {
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.PAIRS);
    }

    @Test
    public void emptyPilePairsAreValidWithJokers() {
        cards.clear();
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.PAIRS);
    }

    @Test
    public void emptyPileTriplesAreValid() {
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        checkAdd(PileTypeWaffeGame2.TRIPLES);
    }

    @Test
    public void emptyPileTriplesAreValidWithJoker() {
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.TRIPLES);
    }

    @Test
    public void emptyPileTriplesAreValidWithJokers() {
        cards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.TRIPLES);
    }

    @Test
    public void emptyPileQuadruplesAreValid() {
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        checkAdd(PileTypeWaffeGame2.QUADRUPLES);
    }

    @Test
    public void emptyPileQuadruplesAreValidWithJokers() {
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.QUADRUPLES);
    }

    @Test
    public void evolutionTest1() {
        cards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        checkAdd(PileTypeWaffeGame2.STRAIGHT);
        cards.clear();
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.PAIRS);
        cards.clear();
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        checkAdd(PileTypeWaffeGame2.TRIPLES);
        cards.clear();
        cards.add(new Card(Card.Value.KING, Card.Suit.HEARTS));
        checkAdd(PileTypeWaffeGame2.PAIRS);
        cards.clear();
        cards.add(new Card(Card.Value.TWO, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.SPADES));
        checkAdd(PileTypeWaffeGame2.QUADRUPLES);
    }
}
