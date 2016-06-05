/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner.pileRules;

import java.util.Collection;
import waffegame2.card.Card;
import waffegame2.cardOwner.PileType;

/**
 *
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public interface PileRule {

    public PileType checkType(Collection<Card> c);

    public PileType updateType(PileType currentType, Collection<Card> c);
}
