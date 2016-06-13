/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;

/**
 *
 * @author Walter
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
        MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2);
        List<Card> myCards;
        List<Card> othersCards;

//        myCards = new ArrayList();
//        myCards.add(new Card(Card.Value.QUEEN, Card.Suit.SPADES));
//        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
//        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
//        myCards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
//        othersCards = new ArrayList();
//        othersCards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
//        othersCards.add(new Card(Card.Value.NINE, Card.Suit.HEARTS));
//        othersCards.add(new Card(Card.Value.FOUR, Card.Suit.DIAMONDS));
        /*
         Win:
         sQ
         s10
         -
         h9 | d4
         d8   c5
         -    -
         h8   dh8
         -
         c5
         */
        myCards = new ArrayList();
        myCards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.TEN, Card.Suit.HEARTS));
        myCards.add(new Card(Card.Value.TEN, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        myCards.add(new Card(Card.Value.SEVEN, Card.Suit.CLUBS));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        othersCards = new ArrayList();
        othersCards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        othersCards.add(new Card(Card.Value.NINE, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.SIX, Card.Suit.DIAMONDS));
        othersCards.add(new Card(Card.Value.FOUR, Card.Suit.SPADES));

        System.out.println(tree.estimateScore(myCards, othersCards, null));
        System.out.println("-------------");
        tree.generateTree(myCards, othersCards, null);
        printTree(tree.root);
        System.out.println(tree.getBestMove(myCards, othersCards, null));
    }

    private static void printTree(MinimaxNode root) {
        String spaces = "";
        for (int i = 0; i < root.depth; i++) {
            spaces += ' ';
        }
        System.out.println(spaces + root.value);
        for (MinimaxNode child : root.children) {
            if (child.depth > root.depth) {
                printTree(child);
            }
        }
    }

}
