/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.Hand;
import waffegame2.player.Player;

/**
 * Utility methods
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-09
 */
public class Util {
    /**
     * Returns whether a String is in an integer form.
     *
     * @param str the string to check
     * @return true if str is an integer
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the square of the input double n.
     *
     * @param n
     * @return n*n
     */
    public static double sqr(double n) {
        return n * n;
    }

    
    /**
     * Note that jokers are included in index 0!
     */
    public static List<Card>[] getValueListArray(Collection<Card> cards) {
        List<Card>[] rv = new List[Card.Value.max() + 1];
        for (int i = 0; i < Card.Value.max() + 1; i++) {
            rv[i] = new ArrayList();
        }
        for (Card card : cards) {
            rv[card.getValue().toInt()].add(card);
        }
        return rv; //:D
    }
    
    
    /**
     * Note that jokers are included in index 0!
     */
    public static List<Card>[] getSuitListArray(Collection<Card> cards) {
        List<Card>[] rv = new List[Card.Suit.max() + 1];
        for (int i = 0; i < Card.Suit.max() + 1; i++) {
            rv[i] = new ArrayList();
        }
        for (Card card : cards) {
            rv[card.getSuit().toInt()].add(card);
        }
        return rv; //:D
    }
}
