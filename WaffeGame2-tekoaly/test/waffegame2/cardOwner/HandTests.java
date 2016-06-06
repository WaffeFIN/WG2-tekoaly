/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import waffegame2.card.Card;

/**
 *
 * @author Walter
 */
public class HandTests {

    private Hand hand;

    public HandTests() {
    }

    @Before
    public void setUp() {
        hand = new Hand(10);
    }

    @Test
    public void testDefaultHandSize() {
        assertEquals(0, hand.cardAmount(), 0);
    }

    @Test
    public void testDefaultHandAccessibility() {
        assertEquals(Hand.Accessibility.PRIVATE, hand.getAccessibility());
    }

    @Test
    public void testHandAccessibility() {
    }

    @Test
    public void testHandMaxSize1() {
        List<Card> cards = new ArrayList();
        for (int i = 0; i < 9; i++) {
            cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        }
        hand.addCards(cards);
        assertEquals(true, hand.addCard(new Card(Card.Value.TWO, Card.Suit.DIAMONDS)));
        assertEquals(false, hand.addCard(new Card(Card.Value.THREE, Card.Suit.DIAMONDS)));
    }

    @Test
    public void testHandMaxSize2() {
        List<Card> cards = new ArrayList();
        for (int i = 0; i < 11; i++) {
            cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        }
        assertEquals(false, hand.addCards(cards));
        assertEquals(true, hand.addCard(new Card(Card.Value.TWO, Card.Suit.DIAMONDS)));
    }

    @Test
    public void testHandMaxSize3() {
        List<Card> cards = new ArrayList();
        for (int i = 0; i < 11; i++) {
            cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        }
        CardOwner owner = new Pack();
        assertTrue(owner.addCards(cards));
        int amount = owner.cardAmount();
        assertEquals(false, owner.transferCards(hand));
        assertEquals(true, owner.cardAmount() == amount);
        owner.removeCard(owner.getCard());
        assertEquals(true, owner.transferCards(hand));
        assertEquals(false, owner.cardAmount() == amount);
    }

    @Test
    public void testHandAddCards() {
        Hand otherHand = new Hand(0);
        List<Card> cards = new ArrayList();
        for (int i = 0; i < 50; i++) {
            cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        }
        assertEquals(true, otherHand.addCards(cards));
        assertEquals(true, otherHand.addCard(new Card(Card.Value.TWO, Card.Suit.DIAMONDS)));
    }

    @Test
    public void testHandSorting() {
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.KING, Card.Suit.DIAMONDS));
        hand.addCards(cards);
        hand.sort();
        assertEquals(true, hand.toString().contains("Joker\n"
                + "Ace of Spades\n"
                + "2 of Clubs\n"
                + "Queen of Hearts\n"
                + "King of Diamonds"));
    }
}
