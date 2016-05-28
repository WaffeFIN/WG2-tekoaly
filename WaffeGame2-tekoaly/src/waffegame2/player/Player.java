/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.ArrayList;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.Hand;

/**
 * A player of the game/program. The CardSelector will determine who has control
 * over the Player's actions.
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public class Player {

    private String name;
    private final List<Hand> hands;
    private CardSelector selector;

    public Player(String name, CardSelector selector) {
        this.name = name;
        this.hands = new ArrayList();
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hands.get(0);
    }

    public void addHand(Hand hand) {
        hands.add(hand);
    }

    /**
     * Gets the (index + 1)th hand
     *
     * @param index
     * @return the Hand in the specified index
     */
    public Hand getHand(int index) {
        if (index < hands.size()) {
            return hands.get(index);
        } else {
            return null;
        }
    }

    public void setHand(int index, Hand hand) {
        hands.set(index, hand);
    }

    public List<Hand> getHands() {
        return hands;
    }

    /**
     * @return the sum of all the cards in all player hands
     */
    public int cardAmount() {
        int cardAmount = 0;
        for (Hand hand : hands) {
            cardAmount += hand.cardAmount();
        }
        return cardAmount;
    }

    /**
     * Asks the players CardSelector to select a list of cards.
     * @return a list of cards
     */
    public List<Card> selectCards() {
        return selector.selectCards();
    }

    /**
     * Makes the game wait until the CardSelector continues
     */
    public void waitToContinue() {
        selector.waitToContinue();
    }

    @Override
    public String toString() {
        return getName();
    }

}
