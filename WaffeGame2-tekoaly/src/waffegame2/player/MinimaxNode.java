/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import waffegame2.card.Card;

/**
 * The nodes used by MinimaxTrees
 *
 * @author Walter
 */
public class MinimaxNode {

    int value;
    int depth;
    MinimaxNode parent;
    MinimaxNode bestChild;

    HashSet<Card> maxCards;
    HashSet<Card> minCards;
    HashSet<Card> pileCards;

    List<MinimaxNode> children;

    public MinimaxNode(int value, int depth, MinimaxNode parent, HashSet<Card> maxCards, HashSet<Card> minCards, HashSet<Card> pileCards) {
        this.value = value;
        this.depth = depth;
        this.parent = parent;
        this.maxCards = maxCards;
        this.minCards = minCards;
        this.pileCards = pileCards;
        this.children = new ArrayList();
    }

    public boolean isMinNode() {
        return (depth % 2 == 1);
    }
}
