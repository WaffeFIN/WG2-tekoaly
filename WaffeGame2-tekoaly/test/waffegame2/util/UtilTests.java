/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import waffegame2.card.Card;
import waffegame2.player.ai.MinimaxNode;
import waffegame2.player.ai.MinimaxNodeValueComparator;

/**
 *
 * @author Walter
 */
public class UtilTests {

    public UtilTests() {
    }

    @Test
    public void testSquare() {
        assertEquals(1, Util.sqr(1), 0.01);
        assertEquals(7, Util.sqr(2.646), 0.01);
    }

    @Test
    public void testIsInteger() {
        assertEquals(true, Util.isInteger("7"));
        assertEquals(true, Util.isInteger("-17"));
        assertEquals(false, Util.isInteger("-17asd"));
        assertEquals(false, Util.isInteger("0.3"));
    }

    @Test
    public void nodeSortTestDesc() {
        int N = 1000;

        List<MinimaxNode> descList = new WaffeList(N);
        List<MinimaxNode> descListControl = new WaffeList(N);
        Set<Card> dummy = new WaffeSet();
        
        for (int i = N - 1; i >= 0; i--) {
            MinimaxNode node = new MinimaxNode(i, 0, dummy, dummy, dummy);
            descList.add(node);
            descListControl.add(node);
        }

        Comparator<MinimaxNode> valueComp = new MinimaxNodeValueComparator();

        Util.sortNodes(descList, valueComp);
        Collections.sort(descListControl, valueComp);

        for (int i = 0; i < N; i++) {
            assertEquals(descListControl.get(i), descList.get(i));
        }
    }

    @Test
    public void nodeSortTestRand() {
        int N = 1000;

        Random r = new Random();
        List<MinimaxNode> randList = new WaffeList(N);
        List<MinimaxNode> randListControl = new WaffeList(N);
        Set<Card> dummy = new WaffeSet();
        
        for (int i = N - 1; i >= 0; i--) {
            MinimaxNode node = new MinimaxNode(i, 0, dummy, dummy, dummy);
            randList.add(node);
            randListControl.add(node);
        }
        Collections.shuffle(randList);
        Collections.shuffle(randListControl);

        Comparator<MinimaxNode> valueComp = new MinimaxNodeValueComparator();

        Util.sortNodes(randList, valueComp);
        Collections.sort(randListControl, valueComp);

        for (int i = 0; i < N; i++) {
            assertEquals(randListControl.get(i), randList.get(i));
        }
    }
}
