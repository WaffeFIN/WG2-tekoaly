/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import waffegame2.card.Card;

/**
 *
 * @author Walter
 */
public class CardCollectionTests {

    private CardCollection cardCollection;
    private CardOwner owner1;
    private CardOwner owner2;

    public CardCollectionTests() {
    }

    @Before
    public void setUp() {
        cardCollection = new CardCollection();
        owner1 = new Pack(1, 1);
        owner2 = new Pack();

        owner1.transferCards(owner2, owner1.getCards(14));
    }

    @Test
    public void testAddCards() {
        cardCollection.addCardsFrom(owner1, owner1.getCards(3));
        assertEquals(3, cardCollection.cardAmount());

        cardCollection.addCardFrom(owner2, owner2.getCard());
        assertEquals(4, cardCollection.cardAmount());
    }

    @Test
    public void testAddCards2() {
        cardCollection.addCardsFrom(owner1, owner1.getCards(3));
        cardCollection.addCardsFrom(owner1, owner1.getCards(3));
        assertEquals(6, cardCollection.cardAmount());

        cardCollection.addCardFrom(owner2, owner2.getCard());
        cardCollection.addCardFrom(owner2, owner2.getCard());
        assertEquals(8, cardCollection.cardAmount());
    }

    @Test
    public void testCardMap() {
        cardCollection.addCardsFrom(owner1, owner1.getCards(3));
        assertEquals(1, cardCollection.getCardMap().size());
    }

    @Test
    public void testRemoveCards() {
        List<Card> cards = owner1.getCards(3);
        cardCollection.addCardsFrom(owner1, cards);
        Card card = owner2.getCard();
        cardCollection.addCardFrom(owner2, card);
        cards.add(card);
        assertEquals(false, cardCollection.removeCards(owner1, cards));
        cards.remove(card);
        assertEquals(true, cardCollection.removeCards(owner1, cards));
        assertEquals(1, cardCollection.cardAmount());
        assertEquals(true, cardCollection.removeCard(owner2, card));
        assertEquals(0, cardCollection.cardAmount());
    }

    @Test
    public void testRemoveCards2() {
        Card card = owner1.getCard();
        assertEquals(false, cardCollection.removeCard(owner1, card));
    }

    @Test
    public void testTransferCards() {
        CardOwner target = new Hand(8);
        cardCollection.addCardsFrom(owner1, owner1.getCards(6));
        cardCollection.addCardFrom(owner2, owner2.getCard());
        int owner1Amount = owner1.cardAmount();
        int owner2Amount = owner2.cardAmount();
        assertEquals(true, cardCollection.transferCards(target));
        assertEquals(owner1Amount - 6, owner1.cardAmount());
        assertEquals(owner2Amount - 1, owner2.cardAmount());
        assertEquals(0, cardCollection.cardAmount());

        cardCollection.addCardFrom(owner1, owner1.getCard());
        cardCollection.addCardFrom(owner2, owner2.getCard());
        owner1Amount = owner1.cardAmount();
        owner2Amount = owner2.cardAmount();
        assertEquals(false, cardCollection.transferCards(target));
        assertEquals(owner1Amount, owner1.cardAmount());
        assertEquals(owner2Amount, owner2.cardAmount());
        assertEquals(2, cardCollection.cardAmount());
    }

    @Test
    public void testGetCards() {
        cardCollection.addCardsFrom(owner1, owner1.getCards(6));
        Card card = owner2.getCard();
        cardCollection.addCardFrom(owner2, card);
        assertEquals(card, cardCollection.getCards(owner2).get(0));
    }

    @Test
    public void testGetCards2() {
        assertEquals(0, cardCollection.getCards(owner2).size());
    }
}
