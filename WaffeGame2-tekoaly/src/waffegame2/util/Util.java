/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

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

}
