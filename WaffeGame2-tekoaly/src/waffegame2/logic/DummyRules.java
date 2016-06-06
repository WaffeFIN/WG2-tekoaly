/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.logic;

import waffegame2.ui.UI;

/**
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public class DummyRules implements GameRules {

    @Override
    public void setAllOptions(UI ui) {
    }

    @Override
    public int getMinPlayers() {
        return 2;
    }

    @Override
    public int getMaxPlayers() {
        return 2;
    }

}
