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
public class DummyLogic extends GameLogic {

    public DummyLogic(UI ui) {
        super(ui);
    }

    @Override
    public GameRules getRules() {
        return new DummyRules(); //:D
    }

    @Override
    public void init() {
        return; //:D
    }

    @Override
    public void setup() {
        return; //:D
    }

    @Override
    public void playGame() {
        return; //:D
    }

    @Override
    public String getGameName() {
        return "game";
    }
}
