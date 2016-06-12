package waffegame2.player.ai;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package waffegame2.player;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import waffegame2.card.Card;
//import waffegame2.cardOwner.PileType;
//import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;
//
///**
// * The minimax tree data structure used by the AI to determine winning endgame
// * positions.
// *
// * @author Walter
// */
//public class MinimaxTreeWaffeGame2Old extends MinimaxTree {
//
//    private final PileRuleWaffeGame2 prwg2;
//
//    private final List<Card> c1;
//    private final List<Card> c2;
//    private final Map<Card, Integer> ic1;
//    private final Map<Card, Integer> ic2;
//
//    public MinimaxTreeWaffeGame2Old(PileRuleWaffeGame2 prwg2, List<Card> c1, List<Card> c2) {
//        this.prwg2 = prwg2;
//        this.c1 = c1;
//        this.c2 = c2;
//        this.ic1 = inverseMap(c1);
//        this.ic2 = inverseMap(c1);
//    }
//
//    private Map<Card, Integer> inverseMap(List<Card> cards) {
//        Map<Card, Integer> rv = new HashMap();
//        for (int i = 0; i < cards.size(); i++) {
//            rv.put(cards.get(i), i);
//        }
//        return rv;
//    }
//
//    @Override
//    public List<List<Card>> generateTree() {
//        long initialState = (1 << (c1.size() + c2.size())) - 1;
//        root = new MinimaxNode(0, initialState);
//
//        recursiveTreeGeneration(root, 0l);
//
//        return null;
//    }
//
//    
//    private void recursiveTreeGeneration(MinimaxNode node, long tableState) {
//        long cardsState = node.state;
//
//        //check for win in node, if c1 won, value = 100-depth, if c2 won, value = depth-100
//        //send value to parent. Return;
//        long s1 = (cardsState >>> c2.size()) << c2.size();
//        long s2 = (cardsState & ~s1);
//        if (s1 == 0l) {
//            node.value = 100 - node.depth;
//            updateParentNodeValue(node);
//            return;
//        }
//        if (s2 == 0l) {
//            node.value = node.depth - 100;
//            updateParentNodeValue(node);
//            return;
//        }
//
//        //first parent node state -> list of playable cards
//        //tableState -> list of cards -> pileType?
//        List<Card> playableCards;
//        List<Card> opponentsCards;
//        if (node.depth % 2 == 0) {
//            playableCards = getCards(s1);
//            opponentsCards = getCards(s2);
//        } else {
//            playableCards = getCards(s2);
//            opponentsCards = getCards(s1);
//        }
//        List<Card> tableCards;
//        if (tableState == 0l) {
//            tableCards = new ArrayList();
//        } else {
//            tableCards = getCards(tableState);
//        }
//
//        //check all playable combinations depending on table state
//        //create child nodes for each (combination -> long)
//        //recursively create next nodes for each child
//        node.value = createChildNodes(node.depth, playableCards, opponentsCards, tableCards);
//        //when all children have been checked, send value to parent.parent
//        updateParentNodeValue(node);
//    }
//
//    private void updateParentNodeValue(MinimaxNode node) {
//        if (node.parent.depth % 2 == 0 ^ node.value <= node.parent.value) {
//            node.parent.value = node.value;
//            node.parent.bestChild = node;
//        }
//    }
//
//    private List<Card> getCards(long state) {
//        List<Card> rv = new ArrayList(c1.size() + c2.size());
//        for (int i = 0; i < c1.size() + c2.size(); i++) {
//            //check LSB
//            if ((state & 1) == 0) {
//                if (i < c2.size()) {
//                    rv.add(c2.get(i));
//                } else {
//                    rv.add(c1.get(i - c2.size()));
//                }
//            }
//            state = state >>> 1;
//        }
//        return rv;
//    }
//
////    private class SuitCombination {
////        Card.Suit suit;
////        Set<Card> cards;
////        boolean enabled;
////
////        public Combination(Set<Card> cards) {
////            this.cards = cards;
////            this.enabled = true;
////        }
////    }
//    private int createChildNodes(int depth, List<Card> playableCards, List<Card> opponentsCards, List<Card> tableCards) {
//        List<List<Card>> C;
//
//        //combinations can increase! if both hands' combinations are calculated, they can only decrease!
//        //Suits can be treated separately
//        //When trying striaght or group, prioritize isolated cards
//        //
//        //check all playable combinations depending on table state
//        if (tableCards.isEmpty()) {
//            //isolate smallest 2 suits
//            //get biggest C with them
//            //afterwards:
//            //Piletype order, largest C first
//        } else {
//            //jokers should be avoided
//            PileType type = prwg2.checkType(new ArrayList(tableCards));
//            //Piletype order, largest C first
//        }
//
//        for (List<Card> cards : C) {
//            recursiveTreeGeneration(new MinimaxNode(depth, cardsState), tableState);
//        }
//
//        return 1;
//    }
//
//    private void createNode(MinimaxNode parent, long state) {
//        MinimaxNode node = new MinimaxNode(parent.depth++, state);
//        node.parent = parent;
//    }
//}
