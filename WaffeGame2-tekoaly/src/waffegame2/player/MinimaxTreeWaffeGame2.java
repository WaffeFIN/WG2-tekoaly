/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import waffegame2.card.Card;
import waffegame2.cardOwner.PileType;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;

/**
 *
 * @author Walter
 */
public class MinimaxTreeWaffeGame2 {

    private final PileRuleWaffeGame2 prwg2;

    private final List<Card> c1;
    private final List<Card> c2;

    MinimaxNode root;

    public MinimaxTreeWaffeGame2(PileRuleWaffeGame2 prwg2, List<Card> c1, List<Card> c2) {
        this.prwg2 = prwg2;
        this.c1 = c1;
        this.c2 = c2;
    }

    /**
     * Finds a card combination that guarantees a win.
     *
     * @return
     */
    public List<List<Card>> findWinningLine() {
        long initialState = (1 << (c1.size() + c2.size())) - 1;
        root = new MinimaxNode(0, initialState);

        recursiveTreeGeneration(root, 0l);

        return null;
    }

    private void recursiveTreeGeneration(MinimaxNode node, long tableState) {
        long cardsState = node.state;

        //check for win in node, if c1 won, value = 100-depth, if c2 won, value = depth-100
        //send value to parent. Return;
        long s1 = (cardsState >>> c2.size()) << c2.size();
        long s2 = (cardsState & ~s1);
        if (s1 == 0l) {
            node.value = 100 - node.depth;
            updateParentNodeValue(node);
            return;
        }
        if (s2 == 0l) {
            node.value = node.depth - 100;
            updateParentNodeValue(node);
            return;
        }

        //first parent node state -> list of playable cards
        //tableState -> list of cards -> pileType?
        Set<Card> playableCards;
        Set<Card> opponentsCards;
        if (node.depth % 2 == 0) {
            playableCards = getCards(s1);
            opponentsCards = getCards(s2);
        } else {
            playableCards = getCards(s2);
            opponentsCards = getCards(s1);
        }
        Set<Card> tableCards;
        if (tableState == 0l) {
            tableCards = new HashSet();
        } else {
            tableCards = getCards(tableState);
        }

        //check all playable combinations depending on table state
        //create child nodes for each (combination -> long)
        //recursively create next nodes for each child
        node.value = createChildNodes(node.depth, playableCards, opponentsCards, tableCards);
        //when all children have been checked, send value to parent.parent
        updateParentNodeValue(node);
    }

    private void updateParentNodeValue(MinimaxNode node) {
        if (node.parent.depth % 2 == 0 ^ node.value <= node.parent.value) {
            node.parent.value = node.value;
            node.parent.bestChild = node;
        }
    }

    private Set<Card> getCards(long state) {
        Set<Card> rv = new HashSet(c1.size() + c2.size());
        for (int i = 0; i < c1.size() + c2.size(); i++) {
            //check LSB
            if ((state & 1) == 0) {
                if (i < c2.size()) {
                    rv.add(c2.get(i));
                } else {
                    rv.add(c1.get(i - c2.size()));
                }
            }
            state = state >>> 1;
        }
        return rv;
    }

    private int createChildNodes(int depth, Set<Card> playableCards, Set<Card> opponentsCards, Set<Card> tableCards) {
        //check all playable combinations depending on table state
        //COMBINATION CHECK HAS TO BE EFFICIENT
        PileType type = prwg2.checkType(new ArrayList(tableCards));
        
        for (Card card : playableCards) {
            recursiveTreeGeneration(new MinimaxNode(depth, cardsState), tableState);
        }
        //info:
        //int[] -> {clubs, diamonds, hearts, spades, pairs, triples, quads}
        //IN WHAT ORDER DO WE ADD COMBINATIONS TO BE CHECKED?
        //if there's an empty table, check if you can place all cards
        //if the table is suits, add a suit if the opponent has one. If the opponent doesn't have one add all of suit.
        //if the table is (small) straight check start and end.
        //if the straight <= pCards size check for pairs
        //add suits two cards larger than opponents suits to C
        //add each card alone to C
        //create child nodes for each (combination -> long)
        //recursively create next nodes for each child
        
        return 1;
    }

    private long getState(Set<Card> cards) {
        long state = 0l;
        for (int i = c1.size() + c2.size() - 1; i >= 0; i--) {
            if (i >= c2.size()) {
                if (cards.contains(c1.get(i - c2.size()))) {
                    state++;
                }
            } else {
                if (cards.contains(c2.get(i))) {
                    state++;
                }
            }
            state = state << 1;
        }
        return state;
    }

    private void createNode(MinimaxNode parent, long state) {
        MinimaxNode node = new MinimaxNode(parent.depth++, state);
        node.parent = parent;
    }

}
