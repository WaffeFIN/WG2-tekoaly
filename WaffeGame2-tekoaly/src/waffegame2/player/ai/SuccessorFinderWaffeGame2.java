/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import waffegame2.card.Card;
import waffegame2.cardOwner.PileTypeWaffeGame2;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;
import waffegame2.util.Util;

/**
 * Calculates all possible successors to MinimaxNode.
 *
 * @author Walter
 */
public class SuccessorFinderWaffeGame2 {

    /**
     * Branching factor. This can be relatively high because of sibling checking
     */
    private static final int maxSuccessors = 200;

    /**
     * Creates a list of every single (or most of the) playable combination
     *
     * @param node the parent node
     * @param pileType
     * @return
     */
    public static List<MinimaxNode> createSuccessors(MinimaxNode node, int pileType) {
        Collection<Card> pileCards = node.pileCards;

        if (pileCards.size() > 0 && pileType == PileTypeWaffeGame2.NULL.toInt()) {
            Logger.getLogger(MinimaxTreeWaffeGame2.class.getName()).log(Level.SEVERE, null, new Exception("Invalid piletype in node!"));
            return null;
        }

        Collection<Card> cards = node.getNodePlayingCards();
        List<MinimaxNode> rv = new ArrayList();

        List<Card>[] cardsValues = Util.getValueListArray(cards);
        List<Card>[] pileValues = Util.getValueListArray(pileCards);

        switch (pileType) {
            case -1:
            case 0:
                if (cards.size() >= 2) {
                    addAllStraights(node, rv, cards, cardsValues);
                    addAllGroups(node, rv, cards, cardsValues, pileCards, pileValues);
                }
                addAllSuitCombos(node, rv, cards, 0);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                straightTransform(node, rv, cards, cardsValues, pileCards, pileValues, 0);
                groupTransform(node, rv, cards, cardsValues, pileCards, pileValues, 2);
                addAllSuitCombos(node, rv, cards, pileType);
                break;
            case 5:
                groupTransform(node, rv, cards, cardsValues, pileCards, pileValues, 2);
            case 10:
                continueStraight(node, rv, cards, cardsValues, pileCards, pileValues);
                break;
            case 6:
            case 7:
                groupTransform(node, rv, cards, cardsValues, pileCards, pileValues, pileType - 3);
            case 8:
                continueGroups(node, rv, cards, cardsValues, pileCards, pileValues, pileType - 4);
                straightTransform(node, rv, cards, cardsValues, pileCards, pileValues, pileType - 5);
                break;
            case 9:
                addAllStraights(node, rv, cards, cardsValues);
                addAllSingles(node, rv, cards);
                break;
        }

        if (rv.size() > maxSuccessors) {
            Collections.shuffle(rv);
            rv = rv.subList(0, maxSuccessors);
            Collections.sort(rv, new MinimaxNodePlayComparator());
            return rv;
        } else {
            if (rv.isEmpty()) {
                rv.add(createNewSuccessor(node, null));
            } else {
                Collections.sort(rv, new MinimaxNodePlayComparator());
            }
            return rv;
        }
    }

    private static void addAllStraights(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues) {
        for (int i = 1; i <= 13; i++) {
            continueStraightDir(node, rv, cards, cardsValues, 1, i % 13 + 1, true);
        }
    }

    private static void straightTransform(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cV, Collection<Card> pileCards, List<Card>[] pileValues, int cycles) {
        if (cards.size() + pileCards.size() < 13 * cycles) {
            return;
        }
        List<Collection<Card>> holes = new ArrayList();
        List<Card>[] cardsValues = Util.getValueListArray(cards);

        holes.add(new HashSet());
        boolean foundBrokenHole = false;
        boolean skipCurrentHole = true;
        for (int n = 1; true; n++) {
            int i = (n + (pileValues.length - 1) * 4 - 1) % (pileValues.length - 1) + 1;
            List<Card> pileList = pileValues[i];
            if (skipCurrentHole) {
                if (pileList.size() <= cycles) {
                    continue;
                }
                skipCurrentHole = false;
            }
            if (pileList.size() <= cycles) {
                List<Card> cardsList = cardsValues[i];
                if (cardsList.size() <= cycles) {
                    if (foundBrokenHole) {
                        return;
                    } else {
                        holes.get(holes.size() - 1).clear();
                        foundBrokenHole = true;
                        skipCurrentHole = true;
                    }
                } else {
                    for (int j = 0; j <= cycles - pileList.size(); j++) {
                        holes.get(holes.size() - 1).add(cardsList.remove(0));//ERROR <========================
                    }
                }
            } else {
                if (pileList.size() > cycles + 1) {
                    return; //should never happen
                }
                if (n >= pileValues.length) {
                    break;
                }
                if (!holes.get(holes.size() - 1).isEmpty()) {
                    holes.add(new HashSet());
                }
            }
        }

        if (holes.get(holes.size() - 1).isEmpty()) {
            holes.remove(holes.size() - 1);
        }
        if (holes.isEmpty()) {
            if (cycles == 0) { //since there is only one broken hole, the pile must be a straight
                continueStraight(node, rv, cards, cV, pileCards, pileValues);
            }
            return;
        }
        if (foundBrokenHole) {
            Collection<Card> transformCards = new HashSet();
            for (Collection<Card> hole : holes) {
                transformCards.addAll(hole);
            }
            straightTransform2(node, rv, transformCards);
        } else {
            List<Collection<Card>> transformCardsList = new ArrayList();
            for (int i = 0; i < holes.size(); i++) {
                transformCardsList.add(new HashSet());
            }
            transformCardsList.add(new HashSet());
            for (int i = 0; i < holes.size(); i++) {
                Collection<Card> hole = holes.get(i);
                for (int j = 0; j < transformCardsList.size(); j++) {
                    if (i != j) {
                        transformCardsList.get(j).addAll(hole);
                    }
                }
            }
            for (Collection<Card> transformCards : transformCardsList) {
                straightTransform2(node, rv, transformCards);
            }
        }
    }

    private static void straightTransform2(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> transformCards) {
        MinimaxNode transformNode = createNewSuccessor(node, transformCards);
        rv.add(transformNode);

        int type = new PileRuleWaffeGame2().checkType(transformNode.pileCards).toInt();
        //if the pile is anything other than a straight, ignore. Groups handled later
        if (type == 5 || type == 10) {
            transformNode.depth--;
            //decrease node depth to the original node's value before continuing
            continueStraight(transformNode, rv,
                    transformNode.getNodePlayingCards(),
                    Util.getValueListArray(transformNode.getNodePlayingCards()),
                    transformNode.pileCards,
                    Util.getValueListArray(transformNode.pileCards));
            transformNode.depth++;
        }
    }

    private static void continueStraight(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues) {
        //perfect straights
        if (pileCards.size() % 13 == 0) {
            addAllStraights(node, rv, cards, cardsValues);
            return;
        }

        //find both ends, check cards for values
        int lookForCount = 1 + pileCards.size() / 13;

        int downValue = -1;
        int upValue = -1;
        boolean skipFirst = true;

        for (int i = 0; i < 2 * pileValues.length; i++) {
            int n = i % (pileValues.length - 1) + 1;
            if (skipFirst) {
                if (pileValues[n].size() == lookForCount) {
                    continue;
                }
                skipFirst = false;
            }
            if (pileValues[n].size() == lookForCount) {
                if (downValue == -1) {
                    if (n == 1) {
                        downValue = 13;
                    } else {
                        downValue = n - 1;
                    }
                }
            } else {
                if (downValue > -1) {
                    upValue = n;
                    break;
                }
            }
        }
        if (downValue == -1 || upValue == -1) {
            return; //should never happen
        }

        continueStraightDir(node, rv, cards, cardsValues, -1, downValue, false);
        continueStraightDir(node, rv, cards, cardsValues, 1, upValue, false);
    }

    private static void continueStraightDir(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, int d, int start, boolean ignoreOneSized) {
        Collection<Card> play = new HashSet();
        int cycle = 0;
        int n = start;
        for (; true; n += d) {
            int i = (n + (cardsValues.length - 1) * 4 - 1) % (cardsValues.length - 1) + 1;
            List<Card> list = cardsValues[i];
            if (list.size() > cycle) {
                play.add(list.get(cycle));
                if (ignoreOneSized) {
                    ignoreOneSized = false;
                } else {
                    rv.add(createNewSuccessor(node, play));
                }
            } else {
                return;
            }
            if (i == (start + (cardsValues.length - 1) * 4 - 1 - d) % (cardsValues.length - 1) + 1) {
                cycle++;
            }
        }
    }

    private static void addAllGroups(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues) {
        for (int i = 2; i <= 4; i++) {
            continueGroups(node, rv, cards, cardsValues, pileCards, pileValues, i);
        }
    }

    private static void groupTransform(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues, int groupSize) {
        Collection<Card> transformCards = new ArrayList();

        //Make transform node
        for (int j = 1; j < pileValues.length; j++) {
            if (pileValues[j].size() == groupSize - 1) {
                if (cardsValues[j].isEmpty()) {
                    return;
                } else {
                    transformCards.add(cardsValues[j].get(0)); //this choise could be improved
                }
            } else if (pileValues[j].size() > groupSize - 1) {
                return;
            }
        }
        if (transformCards.isEmpty()) {
            return;
        }

        MinimaxNode transformNode = createNewSuccessor(node, transformCards);

        rv.add(transformNode);
        //decrease node depth to the original node's value
        transformNode.depth--;
        continueGroups(transformNode, rv,
                transformNode.getNodePlayingCards(),
                Util.getValueListArray(transformNode.getNodePlayingCards()),
                transformNode.pileCards,
                Util.getValueListArray(transformNode.pileCards), groupSize);
        if (groupSize < 4) {
            groupTransform(transformNode, rv,
                    transformNode.getNodePlayingCards(),
                    Util.getValueListArray(transformNode.getNodePlayingCards()),
                    transformNode.pileCards,
                    Util.getValueListArray(transformNode.pileCards), groupSize + 1);
        }
        transformNode.depth++;
        //note that this method leaves children to the original node with same depth value.
    }

    private static void continueGroups(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues, int groupSize) {
        for (int i = 1; i < cardsValues.length; i++) {
            int cardsCountValue = cardsValues[i].size();
            int pileCountValue = pileValues[i].size();
            if (pileCountValue == 0 && cardsCountValue == groupSize) {
                Collection<Card> play = new HashSet();
                play.addAll(cardsValues[i]);
                rv.add(createNewSuccessor(node, play));
            }
        }
    }

    private static void addAllSuitCombos(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, int suit) {
        if (suit == 0) {
            List<Card>[] suits = new List[4];
            for (int i = 0; i < suits.length; i++) {
                suits[i] = new ArrayList();
            }
            for (Card card : cards) {
                if (!card.isJoker()) {
                    suits[card.getSuit().toInt() - 1].add(card);
                }
            }
            for (int i = 0; i < suits.length; i++) {
                addSuitPermutations(node, rv, suits[i]);
            }
        } else {
            List<Card> cardsOfSuit = new ArrayList();
            for (Card card : cards) {
                if (card.getSuit().toInt() == suit) {
                    cardsOfSuit.add(card);
                }
            }
            addSuitPermutations(node, rv, cardsOfSuit);
        }
    }

    private static void addSuitPermutations(MinimaxNode node, List<MinimaxNode> rv, List<Card> cardsOfSuit) {
        if (cardsOfSuit.isEmpty()) {
            return;
        }
        if (cardsOfSuit.size() <= 4 || 1 << (cardsOfSuit.size() + 1) < maxSuccessors - rv.size()) { //full check, 2^N
            List<Collection<Card>> plays = new ArrayList();
            addSuitPermutation(plays, cardsOfSuit, 0);
            for (Collection<Card> play : plays) {
                rv.add(createNewSuccessor(node, play));
            }
        } else { //fast check, N^2
            for (int i = 0; i < cardsOfSuit.size(); i++) {
                Collection<Card> play = new HashSet();
                for (int j = i; j < cardsOfSuit.size(); j++) {
                    play.add(cardsOfSuit.get(j));
                }
                rv.add(createNewSuccessor(node, play));
            }
        }
    }

    private static void addSuitPermutation(List<Collection<Card>> plays, List<Card> cardsOfSuit, int i) {
        if (i < cardsOfSuit.size()) {
            int size = plays.size();
            for (int j = 0; j < size; j++) {
                Collection<Card> newPlay = new HashSet(plays.get(j));
                newPlay.add(cardsOfSuit.get(i));
                plays.add(newPlay);
            }
            Collection<Card> newPlay = new HashSet();
            newPlay.add(cardsOfSuit.get(i));
            plays.add(newPlay);
            addSuitPermutation(plays, cardsOfSuit, i + 1);
        }
    }

    private static void addAllSingles(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards) {
        for (Card card : cards) {
            Collection<Card> play = new HashSet();
            play.add(card);
            rv.add(createNewSuccessor(node, play));
        }
    }

    private static MinimaxNode createNewSuccessor(MinimaxNode parent, Collection<Card> play) {
        HashSet<Card> maxCards = parent.maxCards;
        HashSet<Card> minCards = parent.minCards;
        HashSet<Card> pileCards;

        if (play == null) {
            pileCards = new HashSet();
        } else {
            pileCards = new HashSet(parent.pileCards);
            if (parent.isMinNode()) {
                minCards = new HashSet(parent.minCards);
                transferCards(play, minCards, pileCards);
            } else {
                maxCards = new HashSet(parent.maxCards);
                transferCards(play, maxCards, pileCards);
            }
        }
        MinimaxNode rv = new MinimaxNode(0, parent.depth + 1, maxCards, minCards, pileCards);
        return rv;
    }

    private static boolean transferCards(Collection<Card> play, Collection<Card> from, Collection<Card> to) {
        for (Card card : play) {
            if (!from.remove(card)) {
                Logger.getLogger(MinimaxTreeWaffeGame2.class.getName()).log(Level.SEVERE, null, new Exception("Tried transferring card that didn't belong to Collection!"));
                return false;
            } else {
                if (!to.add(card)) {
                    Logger.getLogger(MinimaxTreeWaffeGame2.class.getName()).log(Level.SEVERE, null, new Exception("Tried adding a card to a Collection which already contains it!"));
                    from.add(card);
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkSum(Collection<Card> cards, List<Card>[] cardsValues) {
        int sum = 0;
        for (int i = 0; i < cardsValues.length; i++) {
            sum += cardsValues[i].size();
        }
        return (sum != cards.size());
    }
}
