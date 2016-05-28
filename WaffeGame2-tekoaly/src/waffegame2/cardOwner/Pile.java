/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import waffegame2.cardOwner.pileRules.PileRule;
import waffegame2.card.Card;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import waffegame2.card.CardSuitComparator;

/**
 * A CardOwner which can be considered the "centre of the playing field". When
 * adding cards, it checks whether they follow the pile's rules.
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public class Pile extends CardOwner {

    private PileType type;
    private PileRule rule;

    public Pile(PileRule rule) {
        this.type = null;
        this.rule = rule;
    }

    @Override
    public boolean addCard(Card card) {
        ArrayList<Card> list = new ArrayList();
        list.add(card);
        return (addCards(list));
    }

    @Override
    public boolean addCards(Collection<Card> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        List<Card> backup = new ArrayList(cards);
        cards.addAll(collection);
        PileType newType = rule.checkType(cards);
        if (newType == PileTypeWaffeGame2.NULL) {
            cards = backup;
            return false;
        }
        type = newType;
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        type = rule.checkType(cards);
    }

    /**
     * @return the PileType of the Pile.
     */
    public PileType getType() {
        return type;
    }

    @Override
    public String getName() {
        if (type == null) {
            return "Pile";
        } else {
            return "Pile: " + type.toString();
        }
    }

    /**
     * Sorts the cards from lowest value to highest
     */
    public void sort() {
        Collections.sort(cards, new CardSuitComparator());
    }
}
