/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.tekoaly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.*;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;
import waffegame2.player.ai.MinimaxTreeWaffeGame2;

/**
 *
 * @author Walter
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
    static List<Card> myCards;
    static List<Card> othersCards;

    public static void main(String[] args) {
        MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2);
        myCards = new ArrayList();
        myCards.add(new Card(Card.Value.QUEEN, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        othersCards = new ArrayList();
        othersCards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.NINE, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.FOUR, Card.Suit.DIAMONDS));
        
        tree.generateTree(myCards, othersCards, null);
        //printListList(getWinningLine(true));

    }

}
