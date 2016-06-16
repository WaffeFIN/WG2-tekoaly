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
    private int nodeMapDuplicates;
    private int nodeClones;
    private final int calculationLimit = 1000000;

    private final Map<Long, MinimaxNode> nodeMap;
    private final Map<Card, Long> stateConverter;
    private int pileCardStateShifter;

    /**
     *
     * @param prwg2 the PileRule entity used to determine valid configurations
     */
    public MinimaxTreeWaffeGame2(PileRuleWaffeGame2 prwg2) {
        this.prwg2 = prwg2;
        this.nodeMap = new HashMap();
        this.stateConverter = new HashMap(64);
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
        if (maxCards.size() + minCards.size() > 32) {
            try {
                throw new Exception("Too many cards for tree generation!");
            } catch (Exception ex) {
                Logger.getLogger(MinimaxTreeWaffeGame2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        HashSet<Card> pileCards;
        if (pile == null) {
            pileCards = new HashSet();
        } else {
            pileCards = new HashSet(pile);
        }
        
        calculations = 0;
        nodeClones = 0;
        nodeMapDuplicates = 0;
        nodeMap.clear();
        
        initStateConverter(maxCards, minCards, pileCards);

        System.out.println("Generating tree...");

        root = new MinimaxNode(0, 0, new HashSet(maxCards), new HashSet(minCards), pileCards);
        root.value = minimax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);

        System.out.print("DONE\n\n");
        System.out.println("calcs: " + calculations);
        System.out.println("nodes: " + (calculations - nodeClones - nodeMapDuplicates));
        System.out.println("clones: " + nodeClones);
        System.out.println("dupes: " + nodeMapDuplicates);
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
        if (calculations % (calculationLimit / 500) == 0) {
            if (calculations % (calculationLimit / 100) == 0) {
                System.out.print("|");
            } else {
                System.out.print(".");
            }
        }
        calculations++;

        if (checkForWin(node)) {
            if (node.isMinNode()) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        int d = checkMapForDuplicateNode(node);
        if (d == 2) {
            return node.value;
        }
        if (node.depth * calculations > calculationLimit) {
            return estimateScore(node);
        } else {
            List<MinimaxNode> successors = null;
            if (d == 1) {
                successors = node.children;
            } else {
                try {
                    successors = SuccessorFinderWaffeGame2.createSuccessors(node, prwg2);
                } catch (Exception ex) {
                    Logger.getLogger(MinimaxTreeWaffeGame2.class.getName()).log(Level.SEVERE, "\nCalculations: " + calculations + "\nNode: " + node.hashCode(), ex);
                }
            }
            int alpha = a;
            int beta = b;

            if (node.isMinNode()) {
                for (MinimaxNode successor : successors) {
                    successor.value = minimax(successor, alpha, beta);
                    if (successor.value <= beta) {
                        beta = successor.value;
                        node.bestChild = successor;
                    }
                    if (beta <= alpha) {
                        node.bestChild = successor;
                        return alpha;
                    }
                }
                return beta;
            } else {
                for (MinimaxNode successor : successors) {
                    successor.value = minimax(successor, alpha, beta);
                    if (successor.value >= alpha) {
                        alpha = successor.value;
                        node.bestChild = successor;
                    }
                    if (alpha >= beta) {
                        node.bestChild = successor;
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
        HashSet<Card> play = new HashSet(node.pileCards);
        if (node.isMinNode()) {
            play.addAll(node.minCards);
        } else {
            play.addAll(node.maxCards);
        }
        return (prwg2.checkType(play) != PileTypeWaffeGame2.NULL);
    }

    private int checkMapForDuplicateNode(MinimaxNode node) {
        long state = convertStateToLong(node);
        MinimaxNode duplicate = nodeMap.get(state);
        if (duplicate != null) {
            if (node.equals(duplicate)) {
                node.value = duplicate.value;
                node.children = duplicate.children;
                node.bestChild = duplicate.bestChild;
                nodeClones++;
                return 2;
            } else {
                if (!duplicate.isLeafNode()) { //leaf nodes lack children
                    if (node.isMinNode()) {
                        for (MinimaxNode child : duplicate.children) {
                            node.children.add(new MinimaxNode(0, node.depth + 1, node.maxCards, child.minCards, child.pileCards));
                        }
                    } else {
                        for (MinimaxNode child : duplicate.children) {
                            node.children.add(new MinimaxNode(0, node.depth + 1, child.maxCards, node.minCards, child.pileCards));
                        }
                    }
                    nodeMapDuplicates++;
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
     * @return A list containing the best move to be played by whoever holds the
     * 'cards'
     */
    @Override
    public List<Card> getBestMove(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards) {
        if (pileCards == null) {
            pileCards = new HashSet();
        }
        MinimaxNode node = findNode(cards, opponentsCards, pileCards);
        if (node == null) {
            generateTree(cards, opponentsCards, pileCards);
        } else {
            root = node;
//            continueMinimax(root);
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
    private MinimaxNode findNode(Collection<Card> cards1, Collection<Card> cards2, Collection<Card> pileCards) {
        //find node in nodeMap
        MinimaxNode node1 = nodeMap.get(convertStateToLong(cards1, pileCards));
        MinimaxNode node2 = nodeMap.get(convertStateToLong(cards2, pileCards));
        MinimaxNode node;
        if (node1 == null && node2 == null) {
            return null;
        }
        if (node1 == null) {
            if (node2.getNodeOpponentsCards().equals(cards1)) {
                node = node2;
            } else {
                return null;
            }
        } else {
            if (node1.getNodeOpponentsCards().equals(cards2)) {
                node = node1;
            } else {
                return null;
            }
        }
        return node;
    }

    /**
     * YET TO BE IMPLEMENTED
     *
     * @param node
     */
    private void continueMinimax(MinimaxNode node) {
        //fix parent values! recursively?
        if (node.children.isEmpty()) {
            if (node.value != Integer.MAX_VALUE && node.value != Integer.MIN_VALUE) {
                node.value = minimax(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
        } else {
            Collections.sort(node.children);
            for (MinimaxNode child : node.children) {
                continueMinimax(child);
            }
        }
    }

    private int estimateScore(MinimaxNode node) {
        HashSet<Card> cards;
        HashSet<Card> oCards;
        if (node.isMinNode()) {
            cards = node.minCards;
            oCards = node.maxCards;
        } else {
            cards = node.maxCards;
            oCards = node.minCards;
        }

        if (node.isMinNode()) {
            return -estimateScore(cards, oCards, node.pileCards);
        } else {
            return estimateScore(cards, oCards, node.pileCards);
        }
    }

    /**
     * Estimates the score judging by hand size and pile type. Update this!
     *
     * @param cards
     * @param opponentsCards
     * @param pileCards
     * @return
     */
    @Override
    public int estimateScore(Collection<Card> cards, Collection<Card> opponentsCards, Collection<Card> pileCards) {
        if (pileCards == null) {
            pileCards = new HashSet();
        }

        double K = cards.size() + 3 * countJokers(cards);
        double k = opponentsCards.size() + 3 * countJokers(opponentsCards);
        PileType oType = prwg2.checkType(opponentsCards);

        if (k < 2) {
            k = 2;
        }

        double P = getHittingProbability(cards.size(), pileCards.size(), prwg2.checkType(pileCards));
        double A;
        if (oType == PileTypeWaffeGame2.NULL) {
            A = 2.0 * P - 1.0;
        } else { //if you can't hit you lose
            if (P < 0.0001) {
                P = 0.0001;
            }
            A = 3.0 - 2.0 / P;
        }
        return noZeroRounding(100 / (Math.sqrt(k * K)) * (K - k + A));
    }

    private int countJokers(Collection<Card> cards) {
        int rv = 0;
        rv = cards.stream().filter((card) -> (card.isJoker())).map((_item) -> 1).reduce(rv, Integer::sum);
        return rv;
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
        }
        return 1.0;
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

    private int noZeroRounding(double d) {
        if (d == 0) {
            return -1;
        } else if (d > 0) {
            return (int) Math.ceil(d);
        } else {
            return (int) Math.floor(Math.max(-10, d));
        }
    }
}
