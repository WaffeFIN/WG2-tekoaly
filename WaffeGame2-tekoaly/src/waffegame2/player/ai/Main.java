/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.List;
import waffegame2.card.Card;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;

/**
 *
 * @author Walter
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        estimationTestSetSmall();
//        estimationTestSetOutnumbered();
        estimationTestSetBig();

        PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
        MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2);
        List<Card> myCards = new ArrayList();
        List<Card> othersCards = new ArrayList();
        
//        this is very difficult to calculate
//        addCardSet1(myCards);
//        addCardSet3(myCards);
//        addCardSet5(othersCards);
//        addCardSet6(othersCards);
        
        System.out.println("-------------");
        tree.generateTree(myCards, othersCards, null);

        System.out.println("-------------");
//        printTree(tree.root);
        System.out.println("Score: " + tree.root.value);
        System.out.println("-------------");
        printMoveSeq(tree.root);
    }

    private static void printTree(MinimaxNode root) {
        String spaces = "";
        for (int i = 0; i < root.depth; i++) {
            spaces += ' ';
        }
        if (root.value == 0) {
            System.out.println(spaces + "N/V");
        } else {
            System.out.println(spaces + root.value);
        }
        for (MinimaxNode child : root.children) {
            if (child.depth == root.depth + 1) {
                printTree(child);
            } else {
                System.out.println(spaces + " Duplicate");
            }
        }
    }

    private static void printMoveSeq(MinimaxNode root) {
        if (root.isMinNode()) {
            System.out.print("+:");
        } else {
            System.out.print("-:");
        }
        for (Card card : root.pileCards) {
            System.out.print(card.shortString() + ":");
        }
        System.out.println("");
        if (root.bestChild != null) {
            printMoveSeq(root.bestChild);
        } else {
            System.out.print(".:");
            for (Card card : root.getNodePlayingCards()) {
                System.out.print(card.shortString() + ":");
            }
            System.out.println("");
        }
    }

    private static void addCardSet(int set, List<Card> cards) {
        switch (set) {
            case 2:
                addCardSet2(cards);
                break;
            case 3:
                addCardSet3(cards);
                break;
            case 4:
                addCardSet4(cards);
                break;
            case 5:
                addCardSet5(cards);
                break;
            case 6:
                addCardSet6(cards);
                break;
            default:
                addCardSet1(cards);
                break;
        }
    }

    private static void addCardSet1(List<Card> cards) {//Two suits
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.NINE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.CLUBS));
    }

    private static void addCardSet2(List<Card> cards) {//Dominant suit
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.DIAMONDS));
    }

    private static void addCardSet3(List<Card> cards) {//Almost Pairs
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.SIX, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.SIX, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.THREE, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.THREE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.SPADES));
    }

    private static void addCardSet4(List<Card> cards) {//Almost straight
        cards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.KING, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.HEARTS));
    }

    private static void addCardSet5(List<Card> cards) {//Almost pairs
        cards.add(new Card(Card.Value.KING, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.THREE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.THREE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.HEARTS));
    }

    private static void addCardSet6(List<Card> cards) {//Almost straight
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.KING, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.JACK, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.TEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.SIX, Card.Suit.DIAMONDS));
    }

    private static void estimationTestSetSmall() {
        int correctness = 0;
        int count = 0;
        int error = 0;
        int errorCount = 0;

        PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
        for (int i = 1; i < 7; i++) {
            List<Card> myCards = new ArrayList();
            addCardSet(i, myCards);
            for (int j = 1; j < 7; j++) {
                if (i != j) {
                    System.out.println("Test: Set #" + i + " vs Set #" + j);
                    MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2);

                    List<Card> othersCards = new ArrayList();
                    addCardSet(j, othersCards);

                    int estimate = tree.estimateScore(myCards, othersCards, null);

                    System.out.println("-------------");
                    tree.generateTree(myCards, othersCards, null);
                    System.out.println("-------------");
                    System.out.println("Estimate: " + estimate);
                    System.out.println("Score: " + tree.root.value);
                    System.out.println("-------------");

                    correctness += estimate * Math.signum(tree.root.value);
                    count++;

                    if (estimate * Math.signum(tree.root.value) < 0) {
                        error += Math.abs(estimate);
                        errorCount++;
                    }
                }
            }
        }
        System.out.println("\n\nCorrectness: " + (double) correctness / count);
        System.out.println("\n\nAverage error: " + (double) error / Math.max(1, errorCount));
        System.out.println("Errors: " + errorCount);
    }

    private static void estimationTestSetOutnumbered() {
        int correctness = 0;
        int count = 0;
        int error = 0;
        int errorCount = 0;

        PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
        for (int i = 1; i < 7; i++) {
            List<Card> myCards = new ArrayList();
            addCardSet(i, myCards);
            for (int j = 1; j < 6; j++) {
                for (int m = j + 1; m < 7; m++) {
                    if (i != j && i != m) {

                        System.out.println("Test: Set #" + i + " vs Sets #" + j + " & #" + m);
                        MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2);

                        List<Card> othersCards = new ArrayList();
                        addCardSet(j, othersCards);
                        addCardSet(m, othersCards);

                        int estimate = tree.estimateScore(myCards, othersCards, null);

                        System.out.println("-------------");
                        tree.generateTree(myCards, othersCards, null);
                        System.out.println("-------------");
                        System.out.println("Estimate: " + estimate);
                        System.out.println("Score: " + tree.root.value);
                        System.out.println("-------------");

                        correctness += estimate * Math.signum(tree.root.value);
                        count++;

                        if (estimate * Math.signum(tree.root.value) < 0) {
                            error += Math.abs(estimate);
                            errorCount++;
                        }
                    }
                }
            }
        }
        System.out.println("\n\nCorrectness: " + (double) correctness / count);
        System.out.println("\n\nAverage error: " + (double) error / Math.max(1, errorCount));
        System.out.println("Errors: " + errorCount);
    }

    private static void estimationTestSetBig() {
        int correctness = 0;
        int count = 0;
        int error = 0;
        int errorCount = 0;

        PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
        for (int i = 1; i < 7; i++) {
            for (int k = i + 1; k < 7; k++) {
                List<Card> myCards = new ArrayList();
                addCardSet(i, myCards);
                addCardSet(k, myCards);
                for (int j = 1; j < 6; j++) {
                    for (int m = j + 1; m < 7; m++) {
                        if (i != j && i != m && k != j && k != m) {

                            System.out.println("Test: Sets #" + i + " & #" + k + " vs Sets #" + j + " & #" + m);
                            MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2);

                            List<Card> othersCards = new ArrayList();
                            addCardSet(j, othersCards);
                            addCardSet(m, othersCards);

                            int estimate = tree.estimateScore(myCards, othersCards, null);

                            System.out.println("-------------");
                            tree.generateTree(myCards, othersCards, null);
                            System.out.println("-------------");
                            System.out.println("Estimate: " + estimate);
                            System.out.println("Score: " + tree.root.value);
                            System.out.println("-------------");

                            correctness += estimate * Math.signum(tree.root.value);
                            count++;

                            if (estimate * Math.signum(tree.root.value) < 0) {
                                error += Math.abs(estimate);
                                errorCount++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("\n\nCorrectness: " + (double) correctness / count);
        System.out.println("\n\nAverage error: " + (double) error / Math.max(1, errorCount));
        System.out.println("Errors: " + errorCount);
    }
}
