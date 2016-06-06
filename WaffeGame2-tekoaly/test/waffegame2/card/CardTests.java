package waffegame2.card;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Walter
 */
public class CardTests {

    public CardTests() {
    }

    @Test
    public void isJokerWorks() {
        Card card = new Card(Card.Value.JOKER, Card.Suit.JOKER);
        assertEquals(true, card.isJoker());
    }

    @Test
    public void tryingToCreateInvalidCard() {
        try {
            Card card = new Card(Card.Value.ACE, Card.Suit.JOKER);
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        try {
            Card card = new Card(Card.Value.JOKER, Card.Suit.SPADES);
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testCardToString() {
        Card card = new Card(Card.Value.TEN, Card.Suit.CLUBS);
        assertEquals("10 of Clubs", card.toString());

        card = new Card(Card.Value.KING, Card.Suit.HEARTS);
        assertEquals("King of Hearts", card.toString());

        card = new Card(Card.Value.ACE, Card.Suit.SPADES);
        assertEquals("Ace of Spades", card.toString());

        card = new Card(Card.Value.QUEEN, Card.Suit.DIAMONDS);
        assertEquals("Queen of Diamonds", card.toString());

        card = new Card(Card.Value.JOKER, Card.Suit.JOKER);
        assertEquals("Joker", card.toString());
    }

}
