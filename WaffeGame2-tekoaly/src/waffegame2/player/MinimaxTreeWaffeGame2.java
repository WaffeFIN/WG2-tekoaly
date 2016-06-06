/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.PileType;
import waffegame2.cardOwner.PileTypeWaffeGame2;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;
import waffegame2.util.Util;

/**
 * The minimax tree data structure used by the AI to score endgame positions.
 *
 * @author Walter
 */
public class MinimaxTreeWaffeGame2 extends MinimaxTree {

    private final PileRuleWaffeGame2 prwg2;
    private int calculations;
    private final int calculationLimit = 10000;

    /**
     *
     * @param prwg2 the PileRule entity used to determine valid configurations
     */
    public MinimaxTreeWaffeGame2(PileRuleWaffeGame2 prwg2) {
        this.prwg2 = prwg2;

    }

    /**
     * Creates a new minimax tree data structure used by the AI to score endgame
     * positions.
     *
     * @param cards The card collection associated to you
     * @param opponentsCards The card collection associated to the opponent
     * @param pile The card collection associated to the pile. If left as null
     * the pile is considered empty
     */
    @Override
    public void generateTree(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pile) {
        calculations = 0;
        HashSet<Card> pileCards;
        if (pile == null) {
            pileCards = new HashSet();
        } else {
            pileCards = new HashSet(pile);
        }
        root = new MinimaxNode(0, 0, null, new HashSet(cards), new HashSet(opponentsCards), pileCards);
        root.value = minimax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * The main minimax algorithm
     *
     * @param node the current node
     * @param a alpha value
     * @param b beta value
     * @return the score of the node
     */
    private int minimax(MinimaxNode node, int a, int b) {
        calculations++;

        int winScore = checkForWin(node);
        if (winScore != 0) {
            return winScore;
        }
        if (node.depth * calculations > calculationLimit) {
            return scoreEstimate(node);
        } else {
            int alpha = a;
            int beta = b;
            List<MinimaxNode> successors = createSuccessors(node);
            if (node.isMinNode()) {
                for (MinimaxNode successor : successors) {
                    successor.value = minimax(successor, alpha, beta);
                    updateParentValue(successor);
                    beta = Math.min(beta, successor.value);
                    if (beta <= alpha) {
                        return alpha;
                    }
                }
                return beta;
            } else {
                for (MinimaxNode successor : successors) {
                    successor.value = minimax(successor, alpha, beta);
                    updateParentValue(successor);
                    alpha = Math.max(alpha, successor.value);
                    if (alpha >= beta) {
                        return beta;
                    }
                }
                return alpha;
            }
        }
    }

    private void updateParentValue(MinimaxNode n) {
        MinimaxNode p = n.parent;
        if (p.value == 0 || (p.isMinNode() && p.value > n.value) || (!p.isMinNode() && p.value < n.value)) {
            p.value = n.value;
            p.bestChild = n;
        }
    }

    /**
     * Finds the best move for the input position.
     *
     * @param cards the cards of who goes first
     * @param opponentsCards the cards of the responder
     * @param pileCards the cards in the pile
     * @return A list containing the best move to be played by whoever holds the
     * 'cards'
     */
    @Override
    public List<Card> getBestMove(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards) {
        MinimaxNode node = findNode(root, new HashSet(cards), new HashSet(opponentsCards), new HashSet(pileCards));
        if (node == null) {
            generateTree(cards, opponentsCards, pileCards);
        } else {
            root = node;
        }
        if (root.bestChild.pileCards.isEmpty()) {
            return new ArrayList();
        } else {
            HashSet<Card> play = new HashSet(root.bestChild.pileCards);
            play.removeAll(root.pileCards);
            return new ArrayList(play);
        }
    }

    /**
     * Finds the node with the inputted game state
     */
    private MinimaxNode findNode(MinimaxNode node, HashSet<Card> maxCards, HashSet<Card> minCards, HashSet<Card> pileCards) {
        //find move by navigating tree:

        //if move isn't found, generateTree
        return null; //:D
    }

    /**
     * Creates a list of every single (or most of the) playable combination
     *
     * @param node the parent node
     */
    private List<MinimaxNode> createSuccessors(MinimaxNode node) {
        HashSet<Card> cards;
        if (node.isMinNode()) {
            cards = node.minCards;
        } else {
            cards = node.maxCards;
        }
        PileType pileType = prwg2.checkType(node.pileCards);

        HashSet<Card> pileCards = node.pileCards;
        List<MinimaxNode> rv = new ArrayList();

        switch (pileType.toInt()) {
            case -1:
            case 0:
                if (cards.size() > 2) {
                    addAllStraights(rv, cards, true);
                    addAllSuitCombos(rv, cards, 0);
                    addAllGroups(rv, cards);
                }
                addAllSingles(rv, cards);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                addAllConnectedStraights(rv, cards, pileCards, pileType.toInt());
                checkAllGroupings(rv, cards, pileCards);
                addAllSuitCombos(rv, cards, pileType.toInt());
                break;
            case 5:
                continueStraight(rv, cards, pileCards);
                checkAllGroupings(rv, cards, pileCards);
                break;
            case 6:
                checkGrouping(rv, cards, pileCards, 3);
            case 7:
                checkGrouping(rv, cards, pileCards, 4);
            case 8:
                continueGroups(rv, cards, pileCards, pileType.toInt() - 4);
                break;
            case 9:
                addAllStraights(rv, cards, false);
                addAllSingles(rv, cards);
                break;
            case 10:
                continueLongStraight(rv, cards, pileCards);
                break;
        }

//      if you cannot hit anything
        if (rv.isEmpty()) {
            rv.add(createNewSuccessor(node, null));
        }

        return rv;
    }

    private void addAllStraights(List<MinimaxNode> rv, Collection<Card> cards, boolean ignoreSameSuit) {
        //jokers excluded
    }

    private void addAllConnectedStraights(List<MinimaxNode> rv, Collection<Card> cards, Collection<Card> pileCards, int ignoreSuit) {
        //jokers included
    }

    private void addAllGroups(List<MinimaxNode> rv, Collection<Card> cards) {
        for (int i = 2; i <= 4; i++) {
            continueGroups(rv, cards, null, i);
        }
    }

    private void checkAllGroupings(List<MinimaxNode> rv, Collection<Card> cards, Collection<Card> pileCards) {
        for (int i = 2; i <= 4; i++) {
            if (!checkGrouping(rv, cards, pileCards, i)) {
                break;
            }
        }
    }

    private void addAllSuitCombos(List<MinimaxNode> rv, Collection<Card> cards, int suit) {

    }

    private void addAllSingles(List<MinimaxNode> rv, Collection<Card> cards) {

    }

    private void continueStraight(List<MinimaxNode> rv, Collection<Card> cards, Collection<Card> pileCards) {

    }

    private void continueLongStraight(List<MinimaxNode> rv, Collection<Card> cards, Collection<Card> pileCards) {

    }

    private boolean checkGrouping(List<MinimaxNode> rv, Collection<Card> cards, Collection<Card> pileCards, int groupSize) {
        //if pairing, pair and continueGroups
        return false; //:D
    }

    private void continueGroups(List<MinimaxNode> rv, Collection<Card> cards, Collection<Card> pileCards, int groupSize) {

    }

    private MinimaxNode createNewSuccessor(MinimaxNode parent, Collection<Card> play) {
        HashSet<Card> maxCards = new HashSet(parent.maxCards);
        HashSet<Card> minCards = new HashSet(parent.minCards);
        HashSet<Card> pileCards;

        if (play == null) {
            pileCards = new HashSet();
        } else {
            pileCards = new HashSet(parent.pileCards);
            if (parent.isMinNode()) {
                transferToPile(play, minCards, pileCards);
            } else {
                transferToPile(play, maxCards, pileCards);
            }
        }

        return new MinimaxNode(0, parent.depth + 1, parent, maxCards, minCards, pileCards);
    }

    private PileType checkPlayValidity(Collection<Card> play, HashSet<Card> pile, PileType pileType, int mode) {
        if (pileType.toInt() > mode) {
            return PileTypeWaffeGame2.NULL;
        }
        play.addAll(pile);

        return (prwg2.updateType(pileType, play));
    }

    private void transferToPile(Collection<Card> play, HashSet<Card> cards, HashSet<Card> pileCards) {
        for (Card card : play) {
            if (cards.remove(card)) {
                pileCards.add(card);
            } else {
                System.out.println("Problem while creating successor: played a card which didn't belong to the hand");
            }
        }
    }

    /**
     * Estimates the score judging by hand size
     */
    private int scoreEstimate(MinimaxNode node) {
        HashSet<Card> cards;
        HashSet<Card> oCards;
        if (node.isMinNode()) {
            cards = node.minCards;
            oCards = node.maxCards;
        } else {
            cards = node.maxCards;
            oCards = node.minCards;
        }
        double sgn;
        if (node.isMinNode()) {
            sgn = -1.0;
        } else {
            sgn = 1.0;
        }
        int cardsPoints = cards.size() + 3 * countJokers(cards);
        int oCardsPoints = oCards.size() + 3 * countJokers(oCards);
        PileType oType = prwg2.checkType(oCards);
        if (oCardsPoints == 4 * oCards.size()) {
            if (node.isMinNode()) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        } else if (oCards.size() == 1) {
            return 7 * cards.size() - 9;
        } else if (oType == PileTypeWaffeGame2.CLUBS || oType == PileTypeWaffeGame2.DIAMONDS || oType == PileTypeWaffeGame2.HEARTS || oType == PileTypeWaffeGame2.SPADES) {
            return (int) (sgn * 7 * ((double) cardsPoints / (oCardsPoints - 0.5) - 1.0));
        } else {
            return (int) (sgn * 24 * (1.0 / Util.sqr(oCardsPoints - 0.5) - 1.0 / Util.sqr(cardsPoints)));
        }
    }

    /**
     * Returns Integer.MIN_VALUE if a min node has won, or Integer.MAX_VALUE if
     * a max node has won
     */
    private int checkForWin(MinimaxNode node) {
        HashSet<Card> play = new HashSet(node.pileCards);
        if (node.isMinNode()) {
            play.addAll(node.minCards);
        } else {
            play.addAll(node.maxCards);
        }
        if (prwg2.checkType(play) != PileTypeWaffeGame2.NULL) {//win condition
            if (node.isMinNode()) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }
        return 0;
    }

    private int countJokers(HashSet<Card> cards) {
        int rv = 0;
        rv = cards.stream().filter((card) -> (card.isJoker())).map((_item) -> 1).reduce(rv, Integer::sum);
        return rv;
    }
}
