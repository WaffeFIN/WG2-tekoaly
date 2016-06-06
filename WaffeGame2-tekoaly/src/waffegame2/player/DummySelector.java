/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.ArrayList;
import java.util.List;
import waffegame2.card.Card;

/**
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public class DummySelector extends CardSelector {

    @Override
    public void waitToContinue() {
        return; //:D
    }

    @Override
    public List<Card> selectCards() {
        return new ArrayList();//:D
    }

}
