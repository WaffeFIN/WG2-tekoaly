/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int nodeMapTwin;
    private int nodeClones;
    private int calculationLimit;

    private final Map<Long, MinimaxNode> nodeMap;
    private final Map<Card, Long> stateConverter;
    private int pileCardStateShifter;

    /**
     *
     * @param prwg2 the PileRule entity used to determine valid configurations
     * @param calculationLimit use to limit the minimax-tree size. Branch is
     * stopped when depth * calculations > calculationLimit. Node amount is
     * usually less than 0.25 * limit
     */
    public MinimaxTreeWaffeGame2(PileRuleWaffeGame2 prwg2, int calculationLimit) {
        this.prwg2 = prwg2;
        this.nodeMap = new HashMap();
        this.stateConverter = new HashMap(64);
        this.calculationLimit = calculationLimit;
    }

    /**
     * Creates a new minimax tree data structure used by the AI to score endgame
     * positions.
     *
     * @param maxCards The card collection associated to you
     * @param minCards The card collection associated to the opponent
     * @param pile The card collection associated to the pile. If left as null
     * the pile is considered empty
     */
    @Override
    public void generateTree(Collection<Card> maxCards, Collection<Card> minCards, Collection<Card> pile) {
        if (maxCards.size() + minCards.size() >= 32) {
            Logger.getLogger(MinimaxTreeWaffeGame2.class.getName()).log(Level.SEVERE, null, new Exception("Too many cards for tree generation!"));
        }
        HashSet<Card> pileCards;
        if (pile == null) {
            pileCards = new HashSet();
        } else {
            pileCards = new HashSet(pile);
        }

        calculations = 0;
        nodeClones = 0;
        nodeMapTwin = 0;
        nodeMap.clear();

        initStateConverter(maxCards, minCards, pileCards);

        System.out.println("Generating tree...");

        root = new MinimaxNode(0, 0, new HashSet(maxCards), new HashSet(minCards), pileCards);
        root.value = minimax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private void initStateConverter(Collection<Card> maxCards, Collection<Card> minCards, Collection<Card> pile) {
        stateConverter.clear();
        pileCardStateShifter = maxCards.size() + minCards.size();
        int i = 0;
        for (Card card : maxCards) {
            stateConverter.put(card, 1l << i);
            i++;
        }
        for (Card card : minCards) {
            stateConverter.put(card, 1l << i);
            i++;
        }
        for (Card card : pile) {
            stateConverter.put(card, 1l << i);
            i++;
        }
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

        if (checkForWin(node)) {
            if (node.isMinNode()) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        int d = checkMapForSimilarNode(node);
        if (d == 2) {
            return node.value;
        }
        if (node.depth * calculations > calculationLimit) {
            return estimateScore(node);
        } else {
            if (d == 0) {
                node.successors = SuccessorFinderWaffeGame2.createSuccessors(node, prwg2.checkType(node.pileCards).toInt());
            }
            int alpha = a;
            int beta = b;

            if (node.isMinNode()) {
                for (MinimaxNode successor : node.successors) {
                    successor.value = minimax(successor, alpha, beta);
                    if (successor.value <= beta) {
                        beta = successor.value;
                        node.bestSuccessor = successor;
                    }
                    if (beta <= alpha) {
                        node.bestSuccessor = successor;
                        return alpha;
                    }
                }
                return beta;
            } else {
                for (MinimaxNode successor : node.successors) {
                    successor.value = minimax(successor, alpha, beta);
                    if (successor.value >= alpha) {
                        alpha = successor.value;
                        node.bestSuccessor = successor;
                    }
                    if (alpha >= beta) {
                        node.bestSuccessor = successor;
                        return beta;
                    }
                }
                return alpha;
            }
        }
    }

    /**
     * Checks if the node's playing cards can be added to the pile
     */
    private boolean checkForWin(MinimaxNode node) {
        if (node.getNodePlayingCards().isEmpty()) {
            return true;
        }
        HashSet<Card> play = new HashSet(node.pileCards);
        if (node.isMinNode()) {
            play.addAll(node.minCards);
        } else {
            play.addAll(node.maxCards);
        }
        return (prwg2.checkType(play) != PileTypeWaffeGame2.NULL);
    }

    private int checkMapForSimilarNode(MinimaxNode node) {
        long state = convertStateToLong(node);
        MinimaxNode sibling = nodeMap.get(state);
        if (sibling != null) {
            if (node.equals(sibling)) {
                node.value = sibling.value;
                node.successors = sibling.successors;
                node.bestSuccessor = sibling.bestSuccessor;
                nodeClones++;
                return 2;
            } else {
                //we know the playing cards and pile cards match
                //but opponents' cards must differ
                if (!sibling.isLeafNode()) { //leaf nodes lack children
                    if (node.isMinNode()) {
                        for (MinimaxNode nibling : sibling.successors) {
                            if (nibling.isMinNode()) {
                                continue;
                            }
                            Long l = convertStateToLong(node.maxCards, nibling.pileCards);
                            if (nodeMap.containsKey(l)) {
                                node.successors.add(nodeMap.get(l));
                            } else {
                                node.successors.add(new MinimaxNode(0, node.depth + 1, node.maxCards, nibling.minCards, nibling.pileCards));
                            }
                        }
                    } else {
                        for (MinimaxNode nibling : sibling.successors) {
                            if (!nibling.isMinNode()) {
                                continue;
                            }
                            Long l = convertStateToLong(node.minCards, nibling.pileCards);
                            if (nodeMap.containsKey(l)) {
                                node.successors.add(nodeMap.get(l));
                            } else {
                                node.successors.add(new MinimaxNode(0, node.depth + 1, nibling.maxCards, node.minCards, nibling.pileCards));
                            }
                        }
                    }
                    nodeMapTwin++;
                    return 1;
                }
            }
        }
        nodeMap.put(state, node);
        return 0;
    }

    private long convertStateToLong(MinimaxNode node) {
        return convertStateToLong(node.getNodePlayingCards(), node.pileCards);
    }

    private long convertStateToLong(Collection<Card> cards, Collection<Card> pile) {
        long rv = 0l;
        for (Card card : cards) {
            if (!stateConverter.containsKey(card)) {
                return -1l;
            }
            rv += stateConverter.get(card);
        }
        for (Card card : pile) {
            if (!stateConverter.containsKey(card)) {
                return -1l;
            }
            long state = stateConverter.get(card);
            if (state >= 1l << pileCardStateShifter) {
                rv += state;
            } else {
                rv += state << pileCardStateShifter;
            }
        }
        return rv;
    }

    /**
     * Finds the best move for the input position.
     *
     * @param cards the cards of who goes first
     * @param opponentsCards the cards of the responder
     * @param pileCards the cards in the pile
     * @param advanceBranch if set to true, the tree branch is advanced and
     * re-evaluated
     * @return A list containing the best move to be played by whoever holds the
     * 'cards'
     */
    @Override
    public List<Card> findBestMove(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards, boolean advanceBranch) {
        if (pileCards == null) {
            pileCards = new HashSet();
        }
        MinimaxNode node = findNode(cards, opponentsCards, pileCards);
        if (node == null) {
            generateTree(cards, opponentsCards, pileCards);
            node = root;
        } else {
            if (advanceBranch && node.value != Integer.MAX_VALUE && node.value != Integer.MIN_VALUE) {
                continueMinimax(node);
            }
        }
        if (node.bestSuccessor.pileCards.isEmpty()) {
            return new ArrayList();
        } else {
            HashSet<Card> play = new HashSet(node.bestSuccessor.pileCards);
            play.removeAll(node.pileCards);
            return new ArrayList(play);
        }
    }

    /**
     * Finds the node with the inputted game state
     */
    private MinimaxNode findNode(Collection<Card> cards1, Collection<Card> cards2, Collection<Card> pileCards) {
        //find node in nodeMap
        MinimaxNode node1 = nodeMap.get(convertStateToLong(cards1, pileCards));
        MinimaxNode node2 = nodeMap.get(convertStateToLong(cards2, pileCards));
        MinimaxNode node;
        if (node1 == null && node2 == null) {
            return null;
        }
        if (node1 == null) {
            if (equalSets(node2.getNodeOpponentsCards(), cards1)) {
                node = node2;
            } else {
                return null;
            }
        } else {
            if (equalSets(node1.getNodeOpponentsCards(), cards2)) {
                node = node1;
            } else {
                return null;
            }
        }
        return node;
    }

    private boolean equalSets(Collection<Card> c1, Collection<Card> c2) {
        return (c1.containsAll(c2) && c2.containsAll(c1)); //:D
    }

    /**
     * YET TO BE IMPLEMENTED
     *
     * @param node
     */
    private void continueMinimax(MinimaxNode node) {
        //fix parent values! recursively?
        if (node.successors.isEmpty()) {
            if (node.value != Integer.MAX_VALUE && node.value != Integer.MIN_VALUE) {
                node.value = minimax(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
        } else {
            Collections.sort(node.successors, new MinimaxNodeComparator());
            for (MinimaxNode child : node.successors) {
                continueMinimax(child);
            }
        }
    }

    private int estimateScore(MinimaxNode node) {
        if (node.isMinNode()) {
            return -estimateScore(node.getNodePlayingCards(), node.getNodeOpponentsCards(), node.pileCards);
        } else {
            return estimateScore(node.getNodePlayingCards(), node.getNodeOpponentsCards(), node.pileCards);
        }
    }

    /**
     * Estimates the score judging by hand size and pile type. Update this!
     *
     * @param cards
     * @param oCards opponent's cards
     * @param pileCards
     * @return
     */
    @Override
    public int estimateScore(Collection<Card> cards, Collection<Card> oCards, Collection<Card> pileCards) {
        if (cards.size() == 1) {
            if (prwg2.checkType(oCards) != PileTypeWaffeGame2.NULL) {
                return Integer.MIN_VALUE;
            }
        }

        if (pileCards == null) {
            pileCards = new HashSet();
        }

        int firstMoveAdvantage = 400;

        List<Card>[] cardsValues = Util.getValueListArray(cards);
        List<Card>[] cardsSuits = Util.getSuitListArray(cards);
        List<Card>[] oCardsValues = Util.getValueListArray(oCards);
        List<Card>[] oCardsSuits = Util.getSuitListArray(oCards);

        double Ac = getAttackScore(cards, cardsSuits, cardsValues);
        double Dc = getDefenceScore(cards, cardsSuits, cardsValues);
        double Ao = getAttackScore(oCards, oCardsSuits, oCardsValues);
        double Do = getDefenceScore(oCards, oCardsSuits, oCardsValues);

        double score;
        double P = getHittingProbability(cards.size(), pileCards.size(), prwg2.checkType(pileCards));

        if (prwg2.checkType(oCards) == PileTypeWaffeGame2.NULL) {
            P = 2.0 * P - 1.0;
            score = (P + 1) * (Ac - Do) + (1 - P) * (Dc - Ao);
        } else {
            if (P < 0.0001) {
                P = 0.0001;
            }
            P = 3.0 - 2.0 / P;
            if (P < -1) {
                score = (P - 1) * Ao;
            } else {
                score = (P + 1) * (Ac - Do) - (1 - P) * Ao;
            }
        }
        return noZeroRounding(score + firstMoveAdvantage);
    }

    private double getHittingProbability(int K, int p, PileType type) {
        switch (type.toInt()) {
            case 1:
            case 2:
            case 3:
            case 4:
                return 1.0 - Math.pow(((39 + Math.min(p, 45.5 - Util.sqr(p) / 2)) / 52), K);
            case 5:
            case 10:
                return 1.0 - Math.pow(11 / 13, K);
            case 6:
            case 7:
            case 8:
                return 1.0 - (nPr(13, K / (type.toInt() - 5)) / Math.pow(13, K / (type.toInt() - 5))); //approximated (badly)
            default:
                return 1.0;
        }
    }

    private double nPr(int n, int r) {
        if (r > n) {
            return 0;
        }
        double rv = 1;
        for (int i = n; i > n - r; i--) {
            rv *= i;
        }
        return rv;
    }

    private double getAttackScore(Collection<Card> cards, List<Card>[] cardsSuits, List<Card>[] cardsValues) {
        if (cards.size() == 1) {
            return 1000;
        }

        double rv = 0;
        int jokers = cardsSuits[0].size();

        //add suits
        for (int i = 1; i < cardsSuits.length; i++) {
            if (cardsSuits[i].size() > 1) {
                rv += Util.sqr(cardsSuits[i].size() + jokers);
            }
        }

        //add straights
        boolean ignoreFirstStraight = true;
        int straightLength = 0;
        int straightCardAmount = 0;
        for (int n = 1; true; n++) {
            int i = (n + (cardsValues.length - 1) * 4 - 1) % (cardsValues.length - 1) + 1;
            List<Card> list = cardsValues[i];
            if (ignoreFirstStraight) {
                if (!list.isEmpty() && n < cardsValues.length) {
                    continue;
                }
                ignoreFirstStraight = false;
            }
            if (!list.isEmpty()) {
                straightCardAmount += list.size();
                straightLength++;
                if (straightLength >= 13) {
                    rv += Util.sqr(straightCardAmount + jokers);
                    break;
                }
            } else if (straightLength > 0) {
                if (straightLength > 1) {
                    rv += Util.sqr(straightCardAmount + jokers);
                }
                if (n >= cardsValues.length) {
                    break;
                }
                straightCardAmount = 0;
                straightLength = 0;
            }

        }

        //add groups
        int pairCards = 0;
        int tripleCards = 0;
        int quadCards = 0;
        for (int i = 1; i < cardsValues.length; i++) {
            switch (cardsValues[i].size()) {
                case 4:
                    quadCards += cardsValues[i].size();
                case 3:
                    tripleCards += cardsValues[i].size();
                case 2:
                    pairCards += cardsValues[i].size();
            }
        }
        rv += Util.sqr(pairCards) + Util.sqr(tripleCards) + Util.sqr(quadCards);

        if (jokers >= cards.size()) {
            return 1 << 16; //should never happen
        }
        return rv * (1000.0 / Util.sqr(cards.size()));
    }

    private double getDefenceScore(Collection<Card> cards, List<Card>[] cardsSuits, List<Card>[] cardsValues) {
        if (!cardsSuits[0].isEmpty()) {
            return 2078; //if you got jokers, return max value
        }

        int uniqueValues = 13;

        for (int i = 1; i < cardsValues.length; i++) {
            if (cardsValues[i].isEmpty()) {
                uniqueValues--;
            }
        }

        double suitSum = 0;

        for (int i = 1; i < cardsSuits.length; i++) {
            suitSum += Math.sqrt(cardsSuits[i].size());
        }

        return ((uniqueValues + 1) / 13.0 * (suitSum / Math.sqrt(cards.size()) - 1) + Util.sqr(uniqueValues / 13.0)) * 1000.0;
    }

    private int noZeroRounding(double d) {
        if (d == 0) {
            return -1;
        } else if (d > 0) {
            return (int) Math.ceil(d);
        } else {
            return (int) Math.floor(d);
        }
    }

    @Override
    public String toString() {
        return "Checks: " + calculations + "\nNodes:  " + nodeMap.size() + "\nTwins:  " + nodeMapTwin + "\nClones: " + nodeClones + "\n\nScore:  " + root.value;
    }
}
