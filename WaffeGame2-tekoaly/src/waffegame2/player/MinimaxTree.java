/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.List;
import waffegame2.card.Card;

/**
 * An interface for all minimax trees used by AIs
 * @author Walter
 */
public abstract class MinimaxTree {

    public MinimaxNode root;

    /**
     * Finds a card combination that guarantees a win.
     *
     * @return a list containing all cards to be played on specific turns.
     */
    abstract public List<List<Card>> findWinningLine();
}
