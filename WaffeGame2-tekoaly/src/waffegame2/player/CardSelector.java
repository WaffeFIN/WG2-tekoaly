/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.List;
import waffegame2.card.Card;
import waffegame2.logic.Table;

/**
 * Interface for a card selector e.g. the UI or the AI
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public abstract class CardSelector {
    
    protected Table table;

    /**
     * Wait for the player to continue.
     */
    abstract public void waitToContinue();
    
    /**
     * Get a list of cards to be played.
     * 
     * @return a list of Cards
     */
    abstract public List<Card> selectCards();
}
