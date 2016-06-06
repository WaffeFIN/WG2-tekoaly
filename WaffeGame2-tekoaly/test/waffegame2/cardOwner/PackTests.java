/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import waffegame2.card.Card;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Walter
 */
public class PackTests {

    private Pack pack;

    public PackTests() {
    }

    @Before
    public void setUp() {
        pack = new Pack(1, 3);
    }

    @Test
    public void testPacktoString() {
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.JOKER, Card.Suit.JOKER));
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.KING, Card.Suit.DIAMONDS));
        pack.addCards(cards);
        assertEquals(true, pack.toString().contains("Ace of Spades"));
        assertEquals(true, pack.toString().contains("Ace of Hearts"));
        assertEquals(true, pack.toString().contains("Joker"));
        assertEquals(true, pack.toString().contains("2 of Clubs"));
        assertEquals(true, pack.toString().contains("King of Diamonds"));
    }

    @Test
    public void testPackSize1() {
        assertEquals(pack.cardAmount(), 1 * (52 + 3));
        Pack otherPack = new Pack(3, 4);
        assertEquals(otherPack.cardAmount(), 3 * (52 + 4));
    }

    @Test
    public void testPackSize2() {
        pack = new Pack();
        assertEquals(pack.cardAmount(), 0);
    }

    @Test
    public void testPackJokers() {
        int jokers = 0;
        for (Card card : pack.getCards()) {
            if (card.isJoker()) {
                jokers++;
            }
        }
        assertEquals(1 * 3, jokers);
        jokers = 0;
        Pack otherPack = new Pack(3, 4);
        for (Card card : otherPack.getCards()) {
            if (card.isJoker()) {
                jokers++;
            }
        }
        assertEquals(3 * 4, jokers);
    }
}
