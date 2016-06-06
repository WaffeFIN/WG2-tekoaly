/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.logic;

import waffegame2.ui.UI;

/**
 * The interface for all game rules
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public interface GameRules {

    public int getMinPlayers();

    public int getMaxPlayers();

    /**
     * Sets all the rules using the UI.
     * @param ui the UI to be used when selecting all rules.
     */
    public void setAllOptions(UI ui);
}
