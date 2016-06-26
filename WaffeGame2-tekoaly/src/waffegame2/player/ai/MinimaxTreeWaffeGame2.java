/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.Collection;
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
    private int nodes;
    private int nodeTwins;
    private int nodeClones;
    private final int softCalculationLimit;

    private final Map<Long, MinimaxNode> nodeMap;
    private final Map<Card, Long> stateConverter;
    private int pileCardStateShifter;

    /**
     *
     * @param prwg2 the PileRule entity used to determine valid configurations
     * @param softCalculationLimit use to limit the minimax-tree size. Branch is
     * stopped when depth * calculations > calculationLimit. Node amount is
     * usually less than 0.3 * limit
     */
    public MinimaxTreeWaffeGame2(PileRuleWaffeGame2 prwg2, int softCalculationLimit) {
        this.prwg2 = prwg2;
        this.nodeMap = new HashMap(softCalculationLimit / 100);
        this.stateConverter = new HashMap(64);
        this.softCalculationLimit = softCalculationLimit;
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
            return;
        }

        HashSet<Card> pileCards;
        if (pile == null || pile.isEmpty()) {
            pileCards = new HashSet();
        } else {
            if (prwg2.checkType(pile) == PileTypeWaffeGame2.NULL) {
                Logger.getLogger(MinimaxTreeWaffeGame2.class.getName()).log(Level.SEVERE, null, new Exception("Invalid piletype"));
                return;
            }
            pileCards = new HashSet(pile);
        }

        calculations = 0;
        nodes = 1;
        nodeClones = 0;
        nodeTwins = 0;
        nodeMap.clear();
        initStateConverter(maxCards, minCards, pileCards);

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

        if (addPreExistingSuccessors(node)) {
            return node.value;
        }
        if (node.depth * calculations > softCalculationLimit) {
            if (node.maxCards.size() > 2 && node.minCards.size() > 2) {
                return estimateScore(node);
            }
        }
        if (node.successors.isEmpty()) {
            node.successors = SuccessorFinderWaffeGame2.createSuccessors(node, prwg2.checkType(node.pileCards).toInt());
            nodes += node.successors.size();
        }

        int alpha = a;
        int beta = b;

        if (node.isMinNode()) {
            for (MinimaxNode successor : node.successors) {
                if (successor.value == 0) {
                    successor.value = minimax(successor, alpha, beta);
                    if (nodeMap.get(convertStateToLong(successor)) != successor) {
                        successor.successors.clear();
                    }
                }
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
                if (successor.value == 0) {
                    successor.value = minimax(successor, alpha, beta);
                    if (nodeMap.get(convertStateToLong(successor)) != successor) {
                        successor.successors.clear();
                    }
                }
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

    /**
     * Checks if the node's playing cards can be added to the pile
     */
    private boolean checkForWin(MinimaxNode node) {
        if (node.getNodePlayingCards().isEmpty()) {
            return true;
        }
        HashSet<Card> play = new HashSet(node.pileCards);
        play.addAll(node.getNodePlayingCards());

        return (prwg2.checkType(play) != PileTypeWaffeGame2.NULL);
    }

    private boolean addPreExistingSuccessors(MinimaxNode node) {
        long state = convertStateToLong(node);
        MinimaxNode sibling = nodeMap.get(state);
        if (sibling != null) {
            if (node.equals(sibling)) {
                node.value = sibling.value;
                node.successors = sibling.successors;
                node.bestSuccessor = sibling.bestSuccessor;
                nodeClones++;
                return true;
            } else {
                //we know the playing cards and pile cards match
                //but opponents' cards must differ
                if (!sibling.isLeafNode()) { //leaf nodes lack children
//                    node.successors.clear();
                    if (node.isMinNode()) {
                        for (MinimaxNode nibling : sibling.successors) {
                            if (nibling.isMinNode()) {
                                continue;
                            }
                            Long l = convertStateToLong(node.maxCards, nibling.pileCards);
                            if (nodeMap.containsKey(l)) {
                                MinimaxNode child = nodeMap.get(l);
                                if (equalSets(child.minCards, nibling.minCards)) {
                                    node.successors.add(child);
                                    continue;
                                }
                            }
                            node.successors.add(new MinimaxNode(0, node.depth + 1, node.maxCards, nibling.minCards, nibling.pileCards));
                            nodes++;
                        }
                    } else {
                        for (MinimaxNode nibling : sibling.successors) {
                            if (!nibling.isMinNode()) {
                                continue;
                            }
                            Long l = convertStateToLong(node.minCards, nibling.pileCards);
                            if (nodeMap.containsKey(l)) {
                                MinimaxNode child = nodeMap.get(l);
                                if (equalSets(child.maxCards, nibling.maxCards)) {
                                    node.successors.add(child);
                                    continue;
                                }
                            }
                            node.successors.add(new MinimaxNode(0, node.depth + 1, nibling.maxCards, node.minCards, nibling.pileCards));
                            nodes++;
                        }
                    }
                    nodeTwins++;
                    return false;
                }
            }
        }
        nodeMap.put(state, node);
        return false;
    }

    private boolean equalSets(Collection<Card> c1, Collection<Card> c2) {
        if (c1.size() != c2.size()) {
            return false;
        }
        return (c1.containsAll(c2) && c2.containsAll(c1)); //:D
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
     * @return A list containing the best move to be played by whoever holds the
     * 'cards'
     */
    @Override
    public List<Card> findBestMove(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards) {
        if (pileCards == null) {
            pileCards = new HashSet();
        }
        MinimaxNode node = findNode(cards, opponentsCards, pileCards);
        if (node == null) {
            generateTree(cards, opponentsCards, pileCards);
            node = root;
        } else {
            if (false && node.value != Integer.MAX_VALUE && node.value != Integer.MIN_VALUE) {
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

        if (node1 == null && node2 == null) {
            return null;
        }
        if (node1 == null) {
            if (equalSets(node2.getNodeOpponentsCards(), cards1)) {
                return node2;
            }
        } else {
            if (equalSets(node1.getNodeOpponentsCards(), cards2)) {
                return node1;
            }
        }
        return null;
    }

    /**
     * YET TO BE IMPLEMENTED
     *
     * @param node
     */
    private void continueMinimax(MinimaxNode node) {
        //fix parent values! recursively?
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
            if (P <= -1) {
                score = (P - 1) * Ao;
            } else {
                score = (P - 1) * Ao + (P + 1) * (Ac - Do);
            }
        }
        return noZeroRounding(100.0 / (cards.size() + oCards.size() + 2) * (score + firstMoveAdvantage));
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
        int jokers = cardsSuits[0].size();

        if (jokers == cards.size()) {
            return 1000000; //should never happen
        }

        double rv = 0;
        rv += addGroupAttackScore(cardsValues);
        rv += addStraightAttackScore(cardsValues, jokers);
        rv += addSuitAttackScore(cardsSuits, jokers);

        return rv * (1000.0 / Util.sqr(cards.size()));
    }

    private double addGroupAttackScore(List<Card>[] cardsValues) {
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
        return Util.sqr(pairCards) + Util.sqr(tripleCards) + Util.sqr(quadCards);
    }

    private double addStraightAttackScore(List<Card>[] cardsValues, int jokers) {
        int rv = 0;
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
                if (straightLength > 1 || jokers > 0) {
                    rv += Util.sqr(straightCardAmount + jokers);
                }
                if (n >= cardsValues.length) {
                    break;
                }
                straightCardAmount = 0;
                straightLength = 0;
            }
        }
        return rv;
    }

    private double addSuitAttackScore(List<Card>[] cardsSuits, int jokers) {
        double rv = 0;
        for (int i = 1; i < cardsSuits.length; i++) {
            if (cardsSuits[i].size() > 1) {
                rv += Util.sqr(cardsSuits[i].size() + jokers);
            }
        }
        return rv;
    }

    private double getDefenceScore(Collection<Card> cards, List<Card>[] cardsSuits, List<Card>[] cardsValues) {
        int jokers = cardsSuits[0].size();
        int uniqueValues = 13;
        for (int i = 1; i < cardsValues.length; i++) {
            if (cardsValues[i].isEmpty()) {
                uniqueValues--;
            }
        }
        uniqueValues += 2 * jokers;
        if (uniqueValues > 13) {
            uniqueValues = 13;
        }

        double suitSum = 0.0;

        for (int i = 1; i < cardsSuits.length; i++) {
            suitSum += Math.sqrt(cardsSuits[i].size() + (double) jokers / 4.0);
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
        return "Checks: " + calculations + "\nNodes in map: " + nodeMap.size() + "\nNodes:  " + nodes + "\nTwins:  " + nodeTwins + "\nClones: " + nodeClones + "\n\nScore:  " + root.value;
    }
}
