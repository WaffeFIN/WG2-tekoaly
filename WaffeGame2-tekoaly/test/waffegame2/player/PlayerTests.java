package waffegame2.player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import waffegame2.card.Card;
import waffegame2.cardOwner.Hand;

/**
 *
 * @author Walter
 */
public class PlayerTests {

    private Player player;

    public PlayerTests() {
    }

    @Before
    public void setUp() {
        player = new Player("Name", new DummySelector());

        Hand hand1 = new Hand(player, player.getName() + "'s Hand", 23);
        Hand hand2 = new Hand(player, player.getName() + "'s Hand numero dos", 123);
        hand1.addCard(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        hand1.addCard(new Card(Card.Value.KING, Card.Suit.HEARTS));
        hand1.addCard(new Card(Card.Value.QUEEN, Card.Suit.HEARTS));
        hand2.addCard(new Card(Card.Value.JACK, Card.Suit.HEARTS));
        hand2.addCard(new Card(Card.Value.TEN, Card.Suit.HEARTS));
        player.addHand(hand1);
        player.addHand(hand2);
    }

    @Test
    public void testCardAmount() {
        assertEquals(5, player.cardAmount());
    }

    @Test
    public void testCardSelection() {
        assertEquals(0, player.selectCards().size());
    }
}
