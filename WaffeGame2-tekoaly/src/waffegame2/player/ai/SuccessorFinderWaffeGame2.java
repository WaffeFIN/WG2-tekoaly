/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.PileType;
import waffegame2.cardOwner.PileTypeWaffeGame2;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;

/**
 *
 * @author Walter
 */
public class SuccessorFinderWaffeGame2 {

    private static final int maxSuccessors = 80; //this can be relatively high because of clone checking

    /**
     * Creates a list of every single (or most of the) playable combination
     *
     * @param node the parent node
     * @param prwg2
     * @return
     * @throws java.lang.Exception If the pileCards are of invalid type
     */
    public static List<MinimaxNode> createSuccessors(MinimaxNode node, PileRuleWaffeGame2 prwg2) throws Exception {
        Collection<Card> pileCards = node.pileCards;
        PileType pileType = prwg2.checkType(node.pileCards);

        if (pileCards.size() > 0 && pileType == PileTypeWaffeGame2.NULL) {
            throw new Exception("Invalid piletype in successor! Size: " + pileCards.size());
        }

        Collection<Card> cards = node.getNodePlayingCards();
        List<MinimaxNode> rv = new ArrayList();

        List<Card>[] cardsValues = getValueListArray(cards);
        List<Card>[] pileValues = getValueListArray(pileCards);

        switch (pileType.toInt()) {
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
                checkGrouping(node, rv, cards, cardsValues, pileCards, pileValues, 2);
                addAllSuitCombos(node, rv, cards, pileType.toInt());
                break;
            case 5:
                checkGrouping(node, rv, cards, cardsValues, pileCards, pileValues, 2);
            case 10:
                continueStraight(node, rv, cards, cardsValues, pileCards, pileValues);
                break;
            case 6:
            case 7:
                checkGrouping(node, rv, cards, cardsValues, pileCards, pileValues, pileType.toInt() - 3);
            case 8:
                continueGroups(node, rv, cards, cardsValues, pileCards, pileValues, pileType.toInt() - 4);
                straightTransform(node, rv, cards, cardsValues, pileCards, pileValues, pileType.toInt() - 5);
                break;
            case 9:
                addAllStraights(node, rv, cards, cardsValues);
                addAllSingles(node, rv, cards);
                break;
        }

        if (rv.size() > maxSuccessors) {
            return rv.subList(0, maxSuccessors - 1);
        } else {
            if (rv.isEmpty()) {
                rv.add(createNewSuccessor(node, null));
            }
            return rv;
        }
    }

    /**
     * Note that jokers are included in index 0!
     */
    private static List<Card>[] getValueListArray(Collection<Card> cards) {
        List<Card>[] rv = new List[Card.Value.max() + 1];
        for (int i = 0; i < Card.Value.max() + 1; i++) {
            rv[i] = new ArrayList();
        }
        for (Card card : cards) {
            rv[card.getValue().toInt()].add(card);
        }
        return rv; //:D
    }

    private static void addAllStraights(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues) {
        for (int i = 1; i <= 13; i++) {
            if (cardsValues[i].size() > 0) {
                continueStraightDir(node, rv, cards, cardsValues, 1, i % 13 + 1, true);
            }
        }
    }

    private static void straightTransform(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues, int cycles) {
        //jokers included
        //use the pilerule method:
        /*
         find all holes' card collection
         */
        if (cards.size() + pileCards.size() < 13 * cycles) {
            return;
        }
        List<Collection<Card>> holes;
        boolean foundBrokenHole = false;
        //two broken holes -> return;
        //check transform node thing from grouping
    }

    private static void continueStraight(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues) {
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
        //DOESN'T TAKE COUNT POSSIBILITIES OF CONTINUING BOTH ENDS!
        continueStraightDir(node, rv, cards, cardsValues, -1, downValue, false);
        continueStraightDir(node, rv, cards, cardsValues, 1, upValue, false);
    }

    private static void continueStraightDir(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, int d, int start, boolean ignoreOneSized) {
        Collection<Card> play = new HashSet();
        int cycle = 0;
        int n = start;
        if (ignoreOneSized) {
            List<Card> list = cardsValues[start];
            if (list.size() > cycle) {
                play.add(list.get(cycle));
            } else {
                return;
            }
            n++;
        }
        for (; true; n += d) {
            int i = (n + 51) % 13 + 1;
            List<Card> list = cardsValues[i];
            if (list.size() > cycle) {
                play.add(list.get(cycle));
                rv.add(createNewSuccessor(node, play));
            } else {
                return;
            }
            if (i == (start + 51 - d) % 13 + 1) {
                cycle++;
            }
        }
    }

    private static void addAllGroups(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues) {
        for (int i = 2; i <= 4; i++) {
            continueGroups(node, rv, cards, cardsValues, pileCards, pileValues, i);
        }
    }

    private static void checkGrouping(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues, int groupSize) {
        MinimaxNode transformNode;
        Collection<Card> transformCards = new ArrayList();

        //Make transform node
        for (int j = 1; j < pileValues.length; j++) {
            if (!pileValues[j].isEmpty()) {
                if (cardsValues[j].isEmpty()) {
                    return;
                } else {
                    transformCards.add(cardsValues[j].get(0)); //this choise could be improved
                }
            }
        }
        if (transformCards.isEmpty()) {
            return;
        }
        transformNode = createNewSuccessor(node, transformCards);
        //decrease node depth to the original node's value
        transformNode.depth--;

        //calculating continuations
        Collection<Card> newCards = transformNode.getNodePlayingCards();
        Collection<Card> newPileCards = transformNode.pileCards;

        List<Card>[] newCardsValues = getValueListArray(newCards);
        List<Card>[] newPileCardsValues = getValueListArray(newPileCards);

        continueGroups(transformNode, rv, newCards, newCardsValues, newPileCards, newPileCardsValues, groupSize);
        checkGrouping(transformNode, rv, newCards, newCardsValues, newPileCards, newPileCardsValues, groupSize + 1);
        //returns depth value to normal
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
        if (1 << cardsOfSuit.size() > maxSuccessors / 2) {
            for (int i = 0; i < cardsOfSuit.size(); i++) {
                Collection<Card> play = new HashSet();
                for (int j = i; j < cardsOfSuit.size(); j++) {
                    play.add(cardsOfSuit.get(j));
                }
                rv.add(createNewSuccessor(node, play));
            }
        } else {
            List<Collection<Card>> plays = new ArrayList();
            addSuitPermutation(plays, cardsOfSuit, 0);
            for (Collection<Card> play : plays) {
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
        parent.children.add(rv);
        return rv;
    }

    private static void transferCards(Collection<Card> play, Collection<Card> from, Collection<Card> to) {
        for (Card card : play) {
            if (from.remove(card)) {
                to.add(card);
            } else {
                System.out.println("PROBLEM TRANSFERRING CARDS");
            }
        }
    }
}
