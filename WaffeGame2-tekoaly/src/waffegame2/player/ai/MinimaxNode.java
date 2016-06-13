/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import waffegame2.card.Card;

/**
 * The nodes used by MinimaxTrees
 *
 * @author Walter
 */
public class MinimaxNode implements Comparable<MinimaxNode> {

    int value;
    int depth;

    HashSet<Card> maxCards;
    HashSet<Card> minCards;
    HashSet<Card> pileCards;

    List<MinimaxNode> children;
    MinimaxNode bestChild;

    public MinimaxNode(int value, int depth, HashSet<Card> maxCards, HashSet<Card> minCards, HashSet<Card> pileCards) {
        this.value = value;
        this.depth = depth;
        this.maxCards = maxCards;
        this.minCards = minCards;
        this.pileCards = pileCards;
        this.children = new ArrayList();
    }

    public boolean isMinNode() {
        return (depth % 2 == 1);
    }

    public Collection<Card> getNodePlayingCards() {
        if (isMinNode()) {
            return minCards;
        } else {
            return maxCards;
        }
    }

    public Collection<Card> getNodeOpponentsCards() {
        if (!isMinNode()) {
            return minCards;
        } else {
            return maxCards;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MinimaxNode o = (MinimaxNode) obj;
        return ((this.depth - o.depth) % 2 == 0 && this.maxCards.equals(o.maxCards) && this.minCards.equals(o.minCards) && this.pileCards.equals(o.pileCards));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.depth % 2);
        hash = 67 * hash + Objects.hashCode(this.maxCards);
        hash = 67 * hash + Objects.hashCode(this.minCards);
        hash = 67 * hash + Objects.hashCode(this.pileCards);
        return hash;
    }

    @Override
    public int compareTo(MinimaxNode o) {
        if (Math.abs(value) > Math.abs(o.value)) {
            return -1;
        } else if (Math.abs(value) > Math.abs(o.value)) {
            return 1;
        } else {
            return 0;
        }
    }
}