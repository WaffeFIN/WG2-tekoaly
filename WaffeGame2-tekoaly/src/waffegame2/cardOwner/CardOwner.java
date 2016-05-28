/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import waffegame2.card.Card;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An abstract class for all classes who own cards.
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public abstract class CardOwner {

    protected List<Card> cards;

    public CardOwner() {
        this.cards = new ArrayList();
    }

    /**
     * Adds the card to the owner if possible
     *
     * @param card The card to be added
     *
     * @return true if the addition was successful
     */
    public boolean addCard(Card card) {
        return cards.add(card);
    }

    /**
     * Adds the cards to the owner if possible
     *
     * @param collection The cards to be added
     *
     * @return true if the addition was successful
     */
    public boolean addCards(Collection<Card> collection) {
        return cards.addAll(collection);
    }

    /**
     * Removes the card from the owner
     *
     * @param card The cards to be removed
     *
     * @return true if the owner contained the specified card
     */
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    /**
     * Removes the cards from the owner only if the owner contains all the
     * specified cards
     *
     * @param collection The cards to be removed
     *
     * @return true if the owner contained all the specified cards
     */
    public boolean removeCards(Collection<Card> collection) {
        if (cards.containsAll(collection)) {
            cards.removeAll(collection);
            return true;
        }
        return false;
    }

    public void clear() {
        cards.clear();
    }

    /**
     * Transfers a card to the target.
     *
     * @param target
     *
     * @return true if the target is able to receive the card.
     */
    public boolean transferCard(CardOwner target) {
        return transferCard(target, getCard());
    }

    /**
     * Transfers all cards to the target.
     *
     * @param target
     *
     * @return true if the target is able to receive the cards.
     */
    public boolean transferCards(CardOwner target) {
        return transferCards(target, getCards());
    }

    /**
     * Transfers a specified card to the target.
     *
     * @param card The card to be transferred
     * @param target
     *
     * @return true if the CardOwner owns the card and the target is able to
     * receive the card.
     */
    public boolean transferCard(CardOwner target, Card card) {
        if (!removeCard(card)) {
            return false;
        }
        if (target.addCard(card)) {
            return true;
        } else {
            addCard(card);
            return false;
        }
    }

    /**
     * Transfers specified collection of cards to the target.
     *
     * @param collection The cards to be transferred
     * @param target
     *
     * @return true if the CardOwner owns all the cards and the target is able
     * to receive the cards.
     */
    public boolean transferCards(CardOwner target, Collection<Card> collection) {
        if (!removeCards(collection)) {
            return false;
        }
        if (target.addCards(collection)) {
            return true;
        } else {
            addCards(collection);
            return false;
        }
    }

    public Card getCard() {
        return getCard(0);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * Returns a sublist of the CardOwner's cards with the specified amount of
     * cards. Doesn't remove the cards from the owner
     *
     * @param amount
     *
     * @return List of cards
     */
    public List<Card> getCards(int amount) {
        List<Card> list = new ArrayList(cards.subList(0, amount));
        return list;
    }

    public List<Card> getCards() {
        List<Card> list = new ArrayList(cards);
        return list;
    }

    public int cardAmount() {
        return cards.size();
    }

    abstract public String getName();
    
    /**
     * Returns a String containing a list of all the cards
     *
     * @return A string containing a list of cards
     */
    public String listCards() {
        String str = "";
        if (cardAmount() == 0) {
            return "<none>";
        }
        for (Card card : cards) {
            str += card.toString() + "\n";
        }
        return str;
    }

    @Override
    public String toString() {
        return getName() + ":\n" + listCards();
    }
}
