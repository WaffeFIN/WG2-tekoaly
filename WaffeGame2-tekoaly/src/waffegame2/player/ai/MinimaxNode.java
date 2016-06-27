/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import waffegame2.card.Card;

/**
 * The nodes used by MinimaxTrees
 *
 * @author Walter
 */
public class MinimaxNode {

    int value;
    int depth;

    Set<Card> maxCards;
    Set<Card> minCards;
    Set<Card> pileCards;

    List<MinimaxNode> successors;
    MinimaxNode bestSuccessor;

    public MinimaxNode(int value, int depth, Set<Card> maxCards, Set<Card> minCards, Set<Card> pileCards) {
        this.value = value;
        this.depth = depth;
        this.maxCards = maxCards;
        this.minCards = minCards;
        this.pileCards = pileCards;
        this.successors = new ArrayList();
    }

    public MinimaxNode() {
        this(0, 0, null, null, null);
    }

    public boolean isMinNode() {
        return (depth % 2 == 1);
    }

    public boolean isLeafNode() {
        return successors.isEmpty(); //:D
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
}
