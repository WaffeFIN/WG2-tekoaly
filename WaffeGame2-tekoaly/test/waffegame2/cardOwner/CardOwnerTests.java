/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import waffegame2.card.Card;

/**
 *
 * @author Walter
 */
public class CardOwnerTests {
    
    private CardOwner owner;
    private CardOwner pack;
    
    public CardOwnerTests() {
    }
    
    @Before
    public void setUp() {
        owner = new Pack();
        pack = new Pack(2, 0);
    }
    
    @Test
    public void testCardOwnerRemoveCards() {
        List<Card> cards = pack.getCards(10);
        assertEquals(true, pack.removeCards(cards));
        cards.clear();
        cards = pack.getCards(3);
        Card card = new Card(Card.Value.NINE, Card.Suit.CLUBS);
        cards.add(card);
        assertEquals(false, pack.removeCards(cards));
        cards.remove(card);
        assertEquals(true, pack.removeCards(cards));
    }
    
    @Test
    public void testTransferCards1() {
        int amount = pack.cardAmount();
        assertEquals(true, pack.transferCard(owner));
        assertEquals(1, owner.cardAmount());
        assertEquals(true, pack.transferCards(owner));
        assertEquals(amount, owner.cardAmount());
    }
    
    @Test
    public void testTransferCards2() {
        Card card = new Card(Card.Value.NINE, Card.Suit.CLUBS);
        assertEquals(false, pack.transferCard(owner, card));
        List<Card> cards = pack.getCards(10);
        cards.add(card);
        assertEquals(false, pack.transferCards(owner, cards));
        cards.clear();
        
        card = pack.getCard(13);
        cards.add(card);
        assertEquals(true, pack.transferCard(owner, card));
        card = pack.getCard();
        cards.add(card);
        assertEquals(true, pack.transferCard(owner, card));
        assertEquals(true, owner.transferCards(pack, cards));
        cards.clear();
        
        Hand hand = new Hand(1);
        card = pack.getCard();
        assertEquals(true, pack.transferCard(hand, card));
        card = pack.getCard();
        assertEquals(false, pack.transferCard(hand, card));
        assertEquals(true, pack.cards.contains(card));
    }
    
    @Test
    public void listCards() {
        Card card = new Card(Card.Value.NINE, Card.Suit.CLUBS);
        owner.addCard(card);
        assertEquals(true, owner.listCards().contains(card.toString()));
        owner.removeCard(card);
        assertEquals(true, owner.listCards().contains("<none>"));
    }
}
