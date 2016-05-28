/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.tekoaly;

import java.util.ArrayList;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.*;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;
import waffegame2.player.MinimaxTreeWaffeGame2;

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
        myCards = new ArrayList();
        myCards.add(new Card(Card.Value.QUEEN, Card.Suit.SPADES));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.HEARTS));
        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        myCards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        othersCards = new ArrayList();
        othersCards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
        othersCards.add(new Card(Card.Value.NINE, Card.Suit.HEARTS));
        othersCards.add(new Card(Card.Value.FOUR, Card.Suit.DIAMONDS));
        printListList(getWinningLine(true));

    }

    public static List<List<Card>> getWinningLine(boolean forMe) {
        MinimaxTreeWaffeGame2 tree;
        if (forMe) {
            tree = new MinimaxTreeWaffeGame2(prwg2, myCards, othersCards);
        } else {
            tree = new MinimaxTreeWaffeGame2(prwg2, othersCards, myCards);
        }
        return tree.findWinningLine();
    }

    public static List<List<Card>> recursion(boolean t, List<Card> round, PileType currentType) {
        if (myCards.isEmpty()) {
            return new ArrayList();//return non null
        }
        if (othersCards.isEmpty()) {
            return null;
        }

        List<List<Card>> C;
        if (t) {
            C = getAllCombinations(myCards, currentType);
        } else {
            C = getAllCombinations(othersCards, currentType);
        }
        boolean couldHit = false;
        for (List<Card> c : C) {
            round.addAll(c);
            PileType newType = prwg2.checkType(round);
            if (newType != PileTypeWaffeGame2.NULL) {
                couldHit = true;
                myCards.removeAll(c);
                List<List<Card>> rv = recursion(!t, round, newType);
                if (rv == null ^ t) {
                    if (rv != null) {
                        rv.add(c);
                    }
                    return rv;
                }
                myCards.addAll(c);
            }
            round.removeAll(c);
        }
        if (!couldHit) {
            return recursion(!t, new ArrayList(), PileTypeWaffeGame2.NULL);
        }
        if (t) { // can hit but nothing is good
            return null;
        } else {
            return new ArrayList(); //NOT NULL
        }
    }
    /*
     get combinations for both, sort by C size
     ..try all starting combinations (removes all other combinations containing any of the combination)
     ....try all responses
     ....if a response leads to immediate failure, record why and do not check same type of C
     ....min-max: c1 returns 1 if any C is 1, -1 otherwise. c2 return -1 if any C is -1, 1 otherwise.
     ..if no response is good return null if it's your turn, else return the built on returned combination
        
     null = -1
     */
    /*
     fun minimax(n: node, d: int, min: int, max: int): int =
     if leaf(n) or depth=0 return evaluate(n)
     if n is a max node
     v := min
     for each child of n
     v' := minimax (child,d-1,v,max)
     if v' > v, v:= v'
     if v > max return max
     return v
     if n is a min node
     v := max
     for each child of n
     v' := minimax (child,d-1,min,v)
     if v' < v, v:= v'
     if v < min return min
     return v
     */

    public static List<List<Card>> getAllCombinations(List<Card> cards, PileType pt) {
        List<List<Card>> rv = new ArrayList();
        for (Card card : cards) {
            ArrayList<Card> c = new ArrayList(1);
            c.add(card);
            rv.add(c);
        }
        return rv;
    }

    /*
     OLD CODE
        
     if (myCards.isEmpty()){
     return null;//return non null
     }
     if (othersCards.isEmpty()){
     return null;
     }
        
     List<List<Card>> C;
     if (t) {
     C = getAllCombinations(myCards, currentType);
     } else {
     C = getAllCombinations(othersCards, currentType);
     }
     boolean couldHit = false;
     for (List<Card> c : C) {
     round.addAll(c);
     PileType newType = prwg2.checkType(round);
     if (newType != PileTypeWaffeGame2.NULL) {
     couldHit = true;
     myCards.removeAll(c);
     List<List<Card>> rv = recursion(!t, round, newType);
     if (rv == null ^ t) {
     return rv;
     }
     myCards.addAll(c);
     }
     round.removeAll(c);
     }
     if (!couldHit) {
     List<List<Card>> rv = recursion(!t, new ArrayList(), PileTypeWaffeGame2.NULL);
     rv.add(round);
     return rv;
     }
     if (t) {
     return null;
     } else {
     return null; //NOT NULL
     }*/
    private static void printListList(List<List<Card>> winningLine) {
        for (List<Card> line : winningLine) {
            for (Card c : line) {
                System.out.print(c + "  ");
            }
            System.out.println("");
        }
    }
}
