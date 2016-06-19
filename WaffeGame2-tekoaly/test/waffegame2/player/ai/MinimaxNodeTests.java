/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.HashSet;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import waffegame2.card.Card;

/**
 *
 * @author Walter
 */
public class MinimaxNodeTests {

    private MinimaxNode node;
    private Card dummyCard;

    public MinimaxNodeTests() {
    }

    @Before
    public void setUp() {
        node = new MinimaxNode();
        dummyCard = new Card(Card.Value.JOKER, Card.Suit.JOKER);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void isLeafNodeTest() {
        node.successors.clear();
        assertTrue("", !node.successors.isEmpty() ^ node.isLeafNode());
        node.successors.add(new MinimaxNode());
        assertTrue("", !node.successors.isEmpty() ^ node.isLeafNode());
    }

    @Test
    public void isMinNodeTest() {
        node.depth = 0;
        assertTrue("", node.isMinNode() ^ node.depth % 2 == 0);
        node.depth++;
        assertTrue("", node.isMinNode() ^ node.depth % 2 == 0);
        node.depth++;
        assertTrue("", node.isMinNode() ^ node.depth % 2 == 0);
    }

    @Test
    public void getPlayingCardsTest() {
        HashSet<Card> set1 = new HashSet();
        HashSet<Card> set2 = new HashSet();
        set1.add(dummyCard);

        node.maxCards = set1;
        node.minCards = set2;

        node.depth = 0;
        assertEquals("", set1, node.getNodePlayingCards());
        node.depth++;
        assertEquals("", set2, node.getNodePlayingCards());
        node.depth++;
        assertEquals("", set1, node.getNodePlayingCards());
    }

    @Test
    public void getOpponentsCardsTest() {
        HashSet<Card> set1 = new HashSet();
        HashSet<Card> set2 = new HashSet();
        set1.add(dummyCard);

        node.maxCards = set1;
        node.minCards = set2;

        node.depth = 0;
        assertEquals("", set2, node.getNodeOpponentsCards());
        node.depth++;
        assertEquals("", set1, node.getNodeOpponentsCards());
        node.depth++;
        assertEquals("", set2, node.getNodeOpponentsCards());
    }

    @Test
    public void equalsTest() {
        MinimaxNode other = new MinimaxNode();
        
        HashSet<Card> set1 = new HashSet();
        HashSet<Card> set2 = new HashSet();
        HashSet<Card> set3 = new HashSet();
        HashSet<Card> set4 = new HashSet();
        HashSet<Card> set5 = new HashSet();
        HashSet<Card> set6 = new HashSet();

        node.maxCards = set1;
        node.minCards = set2;
        node.pileCards = set3;
        other.maxCards = set4;
        other.minCards = set5;
        other.pileCards = set6;
        
        node.value = 100;
        
        assertEquals("", node, other);
        
        set1.add(dummyCard);
        assertTrue("", !node.equals(other));
        set4.add(dummyCard);
        assertTrue("", node.equals(other));
        set2.add(dummyCard);
        assertTrue("", !node.equals(other));
        set5.add(dummyCard);
        assertTrue("", node.equals(other));
        set3.add(dummyCard);
        assertTrue("", !node.equals(other));
        set6.add(dummyCard);
        assertTrue("", node.equals(other));
        node.depth++;
        assertTrue("", !node.equals(other));
        other.depth++;
        assertTrue("", node.equals(other));
    }
}
