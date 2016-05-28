/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner.pileRules;

import waffegame2.card.Card;
import java.util.List;
import waffegame2.cardOwner.PileType;

/**
 *
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public interface PileRule {

    public PileType checkType(List<Card> list);

    public PileType updateType(PileType currentType, List<Card> list);
}
