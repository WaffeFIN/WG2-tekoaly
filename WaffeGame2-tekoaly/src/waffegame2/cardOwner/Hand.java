/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import waffegame2.card.CardSuitComparator;
import waffegame2.card.Card;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;
import waffegame2.player.Player;

/**
 * A CardOwner that is owned by a player, therefore has an accessibility level.
 * Can be limited to be a certain size.
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public class Hand extends CardOwner {

    public enum Accessibility {

        HIDDEN, //Can't be used until type has been changed
        PRIVATE, //Visible and usable only by the owner
        VISIBLE, //Publicly visible, but only usable by the owner
        PUBLIC; //Visible and usable by everyone

        /**
         * @return whether the accessibility level permits global usage
         */
        public boolean isUsableByAnyone() {
            switch (this) {
                case PUBLIC:
                    return true;
                default:
                    return false;
            }
        }

        /**
         * @return whether the accessibility level permits personal usage
         */
        public boolean isUsableByOwner() {
            switch (this) {
                case HIDDEN:
                    return false;
                default:
                    return true;
            }
        }

        /**
         * @return whether the accessibility level makes the hand publicly
         * visible
         */
        public boolean isVisibleToEveryone() {
            switch (this) {
                case VISIBLE:
                case PUBLIC:
                    return true;
                default:
                    return false;
            }
        }

        /**
         * @return whether the accessibility level makes the hand visible to the
         * owner, that is, not hidden.
         */
        public boolean isVisibleToOwner() {
            switch (this) {
                case HIDDEN:
                    return false;
                default:
                    return true;
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case HIDDEN:
                    return "Hidden";
                case PRIVATE:
                    return "Private";
                case VISIBLE:
                    return "Visible";
                default:
                    return "Public";
            }
        }
        
        
    }

    private Player player;
    private int maxCardAmount;
    private Accessibility acc;

    public Hand(int maxCardAmount) {
        this(null, "", maxCardAmount, Accessibility.PRIVATE);
    }

    public Hand(Player player, String name, int maxCardAmount) {
        this(player, name, maxCardAmount, Accessibility.PRIVATE);
    }

    public Hand(Player player, String name, int maxCardAmount, Accessibility acc) {
        super();
        this.player = player;
        this.maxCardAmount = maxCardAmount;
        this.acc = acc;
    }

    @Override
    public String getName() {
        return getPlayer().getName() + "'s " + getAccessibility().toString() + " hand";
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean addCard(Card card) {
        if (maxCardAmount <= 0 || cardAmount() < maxCardAmount) {
            return super.addCard(card);
        }
        return false;
    }

    @Override
    public boolean addCards(Collection<Card> collection) {
        if (maxCardAmount <= 0 || cardAmount() + collection.size() - 1 < maxCardAmount) {
            return super.addCards(collection);
        }
        return false;
    }

    /**
     * @return the accessibility level of the hand-object.
     */
    public Accessibility getAccessibility() {
        return acc;
    }

    /**
     * Sets the accessibility level of the hand-object.
     *
     * @param acc the Accessibility level
     */
    public void setAccessibility(Accessibility acc) {
        this.acc = acc;
    }

    /**
     * Sorts the cards from lowest value to highest
     */
    public void sort() {
        Collections.sort(cards, new CardSuitComparator());
    }
}
