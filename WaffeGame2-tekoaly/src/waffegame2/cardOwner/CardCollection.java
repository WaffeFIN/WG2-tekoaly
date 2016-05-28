/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import waffegame2.card.Card;

/**
 * A collection of cards from different owners. This class is used as an
 * assisting class when a selection from multiple CardOwners' cards is made. A
 * CardCollection doesn't own cards, but help in transferring them between
 * multiple owners.
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public class CardCollection {

    private Map<CardOwner, List<Card>> cardMap;

    public CardCollection() {
        this.cardMap = new HashMap();
    }

    public Map<CardOwner, List<Card>> getCardMap() {
        return cardMap;
    }

    /**
     * Adds the card to a map with the specified owner. The card is not
     * transfered
     *
     * @param owner CardOwner of the card
     * @param card the card to be added
     */
    public void addCardFrom(CardOwner owner, Card card) {
        if (cardMap.containsKey(owner)) {
            List<Card> cards = cardMap.get(owner);
            cards.add(card);
        } else {
            List<Card> cards = new ArrayList();
            cards.add(card);
            cardMap.put(owner, cards);
        }
    }

    /**
     * Adds the cards to a map with the specified owner. The cards are not
     * transfered
     *
     * @param owner CardOwner of the card
     * @param collection the cards to be added
     */
    public void addCardsFrom(CardOwner owner, Collection<Card> collection) {
        if (cardMap.containsKey(owner)) {
            List<Card> cards = cardMap.get(owner);
            cards.addAll(collection);
        } else {
            List<Card> cards = new ArrayList();
            cards.addAll(collection);
            cardMap.put(owner, cards);
        }
    }

    /**
     * Removes the card from the map with the specified owner.
     *
     * @param owner CardOwner of the card
     * @param card the card to be removed
     *
     * @return true if the map contains the specified card with the specified
     * owner
     */
    public boolean removeCard(CardOwner owner, Card card) {
        if (cardMap.containsKey(owner)) {
            List<Card> cards = cardMap.get(owner);
            return cards.remove(card);
        }
        return false;
    }

    /**
     * Removes the cards from the map with the specified owner.
     *
     * @param owner CardOwner of the card
     * @param collection the cards to be removed
     *
     * @return true if the map contains the specified cards with the specified
     * owner
     */
    public boolean removeCards(CardOwner owner, Collection<Card> collection) {
        if (cardMap.containsKey(owner)) {
            List<Card> cards = cardMap.get(owner);
            if (cards.containsAll(collection)) {
                return cards.removeAll(collection);
            }
        }
        return false;
    }

    /**
     * Returns all cards that have been added with the specified owner to the
     * CardCollection.
     *
     * @param owner CardOwner of the card
     *
     * @return List of cards that had been added to the CardCollection with the
     * specified owner
     */
    public List<Card> getCards(CardOwner owner) {
        if (cardMap.containsKey(owner)) {
            return cardMap.get(owner);
        } else {
            return new ArrayList();
        }
    }

    /**
     * Returns all cards that have been added to the CardCollection.
     *
     * @return Collection of cards that had been added to the CardCollection
     */
    public Collection<Card> getCards() {
        Collection<Card> cards = new ArrayList();
        for (List<Card> list : cardMap.values()) {
            cards.addAll(list);
        }
        return cards;
    }

    /**
     * Transfers all cards to the target CardOwner. If the addition of all cards
     * is successful, the cards are removed from their previous CardOwner.
     *
     * @param target The transfer target
     *
     * @return true if the target does accept the cards.
     *
     * @throws IllegalStateException if the CardCollection contains any cards
     * with false owner
     */
    public boolean transferCards(CardOwner target) {
        if (target.addCards(getCards())) {
            for (CardOwner owner : cardMap.keySet()) {
                if (!owner.removeCards(getCards(owner))) {
                    throw new IllegalStateException("Temporary CardCollection object contains cards with false owner");
                }
            }
            cardMap.clear();
            return true;
        }
        return false;
    }

    public boolean contains(Card card) {
        for (List<Card> list : cardMap.values()) {
            if (list.contains(card)) {
                return true;
            }
        }
        return false;
    }

    public int cardAmount() {
        return getCards().size();
    }
}
