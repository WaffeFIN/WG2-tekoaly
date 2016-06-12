/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.Collection;
import java.util.List;
import waffegame2.card.Card;

/**
 * An interface for all minimax trees used by AIs
 *
 * @author Walter
 */
public abstract class MinimaxTree {

    public MinimaxNode root;

    /**
     * Creates a minimax tree using alpha-beta pruning
     * @param cards
     * @param opponentsCards
     * @param pileCards
     */
    abstract public void generateTree(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards);

    abstract public List<Card> getBestMove(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards);
    
    abstract public int estimateScore(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards);
}
