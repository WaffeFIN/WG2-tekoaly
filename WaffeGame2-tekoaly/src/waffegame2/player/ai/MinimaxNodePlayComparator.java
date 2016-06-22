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
public class MinimaxNodePlayComparator implements Comparator<MinimaxNode> {

    @Override
    public int compare(MinimaxNode o1, MinimaxNode o2) {
        return o2.pileCards.size() - o1.pileCards.size();
    }

}
