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
public class MinimaxNodeSuccessorFinderWaffeGame2 {

    private static final int maxSuccessors = 32;

    /**
     * Creates a list of every single (or most of the) playable combination
     *
     * @param node the parent node
     */
    public static List<MinimaxNode> createSuccessors(MinimaxNode node, PileRuleWaffeGame2 prwg2) {
        Collection<Card> cards = node.getNodePlayingCards();

        Collection<Card> pileCards = node.pileCards;
        PileType pileType = prwg2.checkType(node.pileCards);

        if (pileCards.size() > 0 && pileType == PileTypeWaffeGame2.NULL) {
            System.out.println("Invalid piletype in successor!");
        }

        List<Card>[] cardsValues = getValueListArray(cards);
        List<Card>[] pileValues = getValueListArray(pileCards);

        List<MinimaxNode> rv = new ArrayList();

        switch (pileType.toInt()) {
            case -1:
            case 0:
                if (cards.size() >= 2) {
                    addAllStraights(node, rv, cards, cardsValues, true);
                    addAllGroups(node, rv, cards, cardsValues, pileCards, pileValues);
                    addAllSuitCombos(node, rv, cards, 0);
                }
                addAllSingles(node, rv, cards);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                addAllConnectedStraights(node, rv, cards, cardsValues, pileCards, pileValues, pileType.toInt());
                checkAllGroupings(node, rv, cards, cardsValues, pileCards, pileValues);
                addAllSuitCombos(node, rv, cards, pileType.toInt());
                break;
            case 5:
                checkAllGroupings(node, rv, cards, cardsValues, pileCards, pileValues);
            case 10:
                continueStraight(node, rv, cards, cardsValues, pileCards, pileValues);
                break;
            case 6:
                checkGrouping(node, rv, cards, cardsValues, pileCards, pileValues, 3);
            case 7:
                checkGrouping(node, rv, cards, cardsValues, pileCards, pileValues, 4);
            case 8:
                continueGroups(node, rv, cards, cardsValues, pileCards, pileValues, pileType.toInt() - 4);
                break;
            case 9:
                addAllStraights(node, rv, cards, cardsValues, false);
                addAllSingles(node, rv, cards);
                break;
        }

        if (rv.size() >= maxSuccessors) {
            return rv.subList(0, maxSuccessors - 1);
        } else {
//      if you cannot hit anything
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

    private static void addAllStraights(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, boolean ignoreSameSuit) {
        for (int i = 1; i <= 13; i++) {
            if (cardsValues[i].size() > 0) {
                List<MinimaxNode> bufferList = new ArrayList();
                continueStraightDir(node, bufferList, cards, cardsValues, 1, (i + 1) % 13);
                //for each in bufferList add one from cardsValues[i]
                //add to rv
            }
        }
    }

    private static void addAllConnectedStraights(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues, int ignoreSuit) {
        //jokers included
        //use the 
    }

    private static void addAllGroups(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues) {
        for (int i = 2; i <= 4; i++) {
            continueGroups(node, rv, cards, cardsValues, pileCards, pileValues, i);
        }
    }

    private static void checkAllGroupings(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues) {
        for (int i = 2; i <= 4; i++) {
            if (!checkGrouping(node, rv, cards, cardsValues, pileCards, pileValues, i)) {
                break;
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
                addAllSuitPermutations(node, rv, suits[i]);
            }
        } else {
            List<Card> cardsOfSuit = new ArrayList();
            for (Card card : cards) {
                if (card.getSuit().toInt() == suit) {
                    cardsOfSuit.add(card);
                }
            }
            addAllSuitPermutations(node, rv, cardsOfSuit);
        }
    }

    private static void addAllSuitPermutations(MinimaxNode node, List<MinimaxNode> rv, List<Card> cardsOfSuit) {
        //this comes last, so add all until rv.size > max

    }

    private static void addAllSingles(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards) {

        for (Card card : cards) {
            Collection<Card> play = new HashSet();
            play.add(card);
            rv.add(createNewSuccessor(node, play));
        }
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
        continueStraightDir(node, rv, cards, cardsValues, -1, downValue);
        continueStraightDir(node, rv, cards, cardsValues, 1, upValue);
    }

    private static void continueStraightDir(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, int d, int start) {

    }

    private static boolean checkGrouping(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues, int groupSize) {
        Collection<Card> transformCards = groupTransform(node, cards, cardsValues, pileCards, pileValues);
        if (transformCards != null) {
            Collection<Card> newCards = new HashSet(cards);
            Collection<Card> newPileCards = new HashSet(pileCards);
            transferCards(transformCards, newCards, newPileCards);

            List<Card>[] newCardsValues = getValueListArray(newCards);
            List<Card>[] newPileCardsValues = getValueListArray(newPileCards);

            checkGrouping(node, rv, newCards, newCardsValues, newPileCards, newPileCardsValues, groupSize);
            continueGroups(node, rv, newCards, newCardsValues, newPileCards, newPileCardsValues, groupSize);
            return true;
        }
        return false;
    }

    private static Collection<Card> groupTransform(MinimaxNode node, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues) {
        Collection<Card> transformCards = new ArrayList();
        for (int j = 1; j < pileValues.length; j++) {
            if (!pileValues[j].isEmpty()) {
                if (!cardsValues[j].isEmpty()) {
                    transformCards.add(cardsValues[j].get(0)); //this could be improved
                } else {
                    return null;
                }
            }
        }
        if (transformCards.isEmpty()) {
            return null;
        } else {
            createNewSuccessor(node, transformCards);
            return transformCards;
        }
    }

    private static void continueGroups(MinimaxNode node, List<MinimaxNode> rv, Collection<Card> cards, List<Card>[] cardsValues, Collection<Card> pileCards, List<Card>[] pileValues, int groupSize) {
        for (int i = 1; i < cardsValues.length; i++) {
            int cardsCountValue = cardsValues[i].size();
            int pileCountValue = pileValues[i].size();
            if (pileCountValue == 0 && cardsCountValue >= groupSize) {
                //possible value to add, add to list for permutation checking
            }
        }
    }

    private static MinimaxNode createNewSuccessor(MinimaxNode parent, Collection<Card> play) {
        HashSet<Card> maxCards = new HashSet(parent.maxCards);
        HashSet<Card> minCards = new HashSet(parent.minCards);
        HashSet<Card> pileCards;

        if (play == null) {
            pileCards = new HashSet();
        } else {
            pileCards = new HashSet(parent.pileCards);
            if (parent.isMinNode()) {
                transferCards(play, minCards, pileCards);
            } else {
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
                System.out.println("Problem while transferring cards: played a card which didn't belong to the hand");
            }
        }
    }
}
