/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import waffegame2.card.Card;
import waffegame2.player.ai.MinimaxNode;

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
     * Returns an array of card lists, each containing cards from only one
     * value. Note that jokers are included in index 0!
     */
    public static List<Card>[] getValueListArray(Collection<Card> cards) {
        List<Card>[] rv = new List[Card.Value.max() + 1];
        for (int i = 0; i < rv.length; i++) {
            rv[i] = new ArrayList();
        }
        for (Card card : cards) {
            rv[card.getValue().toInt()].add(card);
        }
        return rv; //:D
    }

    /**
     * Returns an array of card lists, each containing cards from only one suit.
     * Note that jokers are included in index 0!
     */
    public static List<Card>[] getSuitListArray(Collection<Card> cards) {
        List<Card>[] rv = new List[Card.Suit.max() + 1];
        for (int i = 0; i < rv.length; i++) {
            rv[i] = new ArrayList();
        }
        for (Card card : cards) {
            rv[card.getSuit().toInt()].add(card);
        }
        return rv; //:D
    }

    public static void shuffleNodes(List<MinimaxNode> list) {
        Random r = new Random();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.set(i, list.set(r.nextInt(size), list.get(i)));
        }
    }

    /**
     * Sorts MinimaxNodes in a list using a comparator. Uses the merge sort
     * algorithm.
     *
     * @param list
     * @param c
     */
    public static void sortNodes(List<MinimaxNode> list, Comparator<MinimaxNode> c) {
        MinimaxNode[] array = toArray(list);
        mergeSort(array, new MinimaxNode[array.length], c, 0, list.size() - 1);

        for (int i = 0; i < array.length; i++) {
            list.set(i, array[i]);
        }
    }

    /**
     * Replacement for list.toArray, which returns an Object[]
     *
     * @return MinimaxNode array from list
     */
    private static MinimaxNode[] toArray(List<MinimaxNode> list) {
        MinimaxNode[] rv = new MinimaxNode[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rv[i] = list.get(i);
        }
        return rv;
    }

    /**
     * Merge sort division
     */
    private static void mergeSort(MinimaxNode[] array, MinimaxNode[] buffer, Comparator<MinimaxNode> comp, int l, int r) {
        if (l < r) {
            int c = (l + r) / 2;
            mergeSort(array, buffer, comp, l, c);
            mergeSort(array, buffer, comp, c + 1, r);
            merge(array, buffer, comp, l, c + 1, r);
        }
    }

    /**
     * Comparison and merge
     */
    private static void merge(MinimaxNode[] array, MinimaxNode[] buffer, Comparator<MinimaxNode> comp, int l1, int r1, int r2) {

        int l2 = r1 - 1;
        int index = l1;
        int size = r2 - l1 + 1;

        while (l1 <= l2 && r1 <= r2) {
            if (comp.compare(array[l1], array[r1]) <= 0) {
                buffer[index++] = array[l1++];
            } else {
                buffer[index++] = array[r1++];
            }
        }

        //Copy rest
        while (l1 <= l2) {
            buffer[index++] = array[l1++];
        }

        while (r1 <= r2) {
            buffer[index++] = array[r1++];
        }

        for (int i = 0; i < size; i++, r2--) {
            array[r2] = buffer[r2];
        }
    }
}
