/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.Comparator;

/**
 *
 * @author Walter
 */
public class MinimaxNodeValueComparator implements Comparator<MinimaxNode> {

    @Override
    public int compare(MinimaxNode o1, MinimaxNode o2) {
        return Math.abs(o2.value) - Math.abs(o1.value);
    }

}
