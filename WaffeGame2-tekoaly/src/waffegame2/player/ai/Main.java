/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import waffegame2.card.Card;
import waffegame2.card.CardValueComparator;
import waffegame2.cardOwner.PileTypeWaffeGame2;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;
import waffegame2.util.Util;

/**
 *
 * @author Walter
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int softLimit = 4000000;
        final PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
//
//        List<Card> myCards = new ArrayList();
//        myCards.add(new Card(Card.Value.QUEEN, Card.Suit.HEARTS));
//        myCards.add(new Card(Card.Value.JACK, Card.Suit.SPADES));
//        myCards.add(new Card(Card.Value.SIX, Card.Suit.HEARTS));
//        myCards.add(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
//        myCards.add(new Card(Card.Value.FOUR, Card.Suit.DIAMONDS));
//        myCards.add(new Card(Card.Value.EIGHT, Card.Suit.CLUBS));
//        myCards.add(new Card(Card.Value.TWO, Card.Suit.DIAMONDS));
//        List<Card> othersCards = new ArrayList();
//        othersCards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
//        othersCards.add(new Card(Card.Value.EIGHT, Card.Suit.SPADES));
//        othersCards.add(new Card(Card.Value.SEVEN, Card.Suit.SPADES));
//
//        MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2, softLimit);
//        tree.generateTree(myCards, othersCards, null);
//        
//        printMoveSeq(tree.root);

//        estimationTestSetBig(softLimit);
        if (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Welcome to the WaffeGame2.3 endgame AI demo. AI version: 1.0a\n\nAuthor: Walter Grönholm\n\n");
            loop:
            while (true) {
                switch (menu(sc)) {
                    case -1:
                        break;
                    case -2:
                        break loop;
                    case 0:
                        printHelp(sc);
                        break;
                    case 1:
                        analyse(sc, prwg2, softLimit);
                        break;
                    case 2:
                        play(sc, prwg2, softLimit, true);
                        break;
                    case 3:
                        play(sc, prwg2, softLimit, false);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static int menu(Scanner sc) {
        System.out.println("");
        System.out.println("\t\tMenu");
        System.out.println("[1]\tCalculate winning line (Analysis)");
        System.out.println("[2]\tPlay vs computer");
        System.out.println("[3]\tSpectate computer vs computer");
        System.out.println("[0]\tHelp");
        System.out.println("[X]\tExit");
        System.out.println("");
        String cmd = sc.nextLine();
        if (Util.isInteger(cmd)) {
            return Integer.parseInt(cmd);
        } else {
            cmd = cmd.toUpperCase();
            if (cmd.contains("X")) {
                return -2;
            }
            return -1;
        }
    }

    private static void printHelp(Scanner sc) {
        System.out.println("\n\t\tHelp");
        System.out.println("Enter cards using the following format:");
        System.out.println("The digit or first letter of the value, followed by the first letter of the suit");
        System.out.println("For instance, if you want to input the Queen of Diamonds, write 'QD' without the apostrophes");
        System.out.println("Ten of clubs would be 'TC'");
        System.out.println("Four of hearts would be '4H'");
        System.out.println("Ace of spades would be 'AS'");
        System.out.println("\nAdditionally, in analysis you can enter different card sets by typing 'S#' where # is any digit from 1 to 6");
        System.out.println("The card sets are as follows:");
        for (int i = 1; i <= 6; i++) {
            System.out.print(" Set #" + i + ":\t");
            printCards(getCardSet(i));
        }
        System.out.println("\nAlso random cards can be added from the pack using 'R#' where # is any number from 1 to 30");
        System.out.println("\nNote that you can't enter more than 31 cards at a time to be analysed.");
        enterToContinue(sc);
    }

    private static void play(Scanner sc, PileRuleWaffeGame2 prwg2, int softLimit, boolean control) {
        
        return; //:D
    }

    private static void analyse(Scanner sc, PileRuleWaffeGame2 prwg2, int softLimit) {
        List<Card> myCards;
        List<Card> othersCards;
        List<Card> pileCards;

        System.out.println("Enter your cards separated by spaces \n(read the Help if needed, enter empty line to cancel): ");
        while (true) {
            String str = sc.nextLine();
            if (str.isEmpty()) {
                return;
            }
            myCards = getCardsFromStrings(str);
            if (myCards != null) {
                break;
            }
            System.out.println("Invalid input, try again");
        }
        System.out.println("Enter your opponent's cards separated by spaces: ");
        while (true) {
            othersCards = getCardsFromStrings(sc.nextLine());
            if (othersCards != null) {
                break;
            }
            System.out.println("Invalid input, try again");
        }

        MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2, softLimit);
        System.out.println("Calculating...");
        tree.generateTree(myCards, othersCards, null);
        if (tree.root == null) {
            System.out.println("Analysis failed!");
            return;
        }
        System.out.println("Done! Press <Enter> to show the results");
        sc.nextLine();
        System.out.print("Your cards:\n\t");
        printCards(myCards);
        System.out.print("Opponent's cards:\n\t");
        printCards(othersCards);
        System.out.println("");
        printMoveSeq(tree.root);
        if (tree.root.value == Integer.MAX_VALUE) {
            System.out.println("\nScore: Win");
        } else if (tree.root.value == Integer.MIN_VALUE) {
            System.out.println("\nScore: Loss");
        } else {
            System.out.println("\nScore: " + tree.root.value);
        }
        enterToContinue(sc);
    }

    private static List<Card> getCardsFromStrings(String str) {
        if (str.isEmpty()) {
            return new ArrayList();
        }
        str = str.toUpperCase();
        String[] strs = str.split(" ");
        List<Card> cards = new ArrayList();
        for (String s : strs) {
            if (s.isEmpty()) {
                continue;
            }
            Collection<Card> c = getCardsFromString(s);
            if (c == null) {
                return null;
            }
            cards.addAll(c);
        }
        return cards; //:D
    }

    private static Collection<Card> getCardsFromString(String s) {
        char[] chrs = s.toCharArray();
        int value;
        int suit;
        switch (chrs[0]) {
            case 'R': //random
                s = s.substring(1);
                if (Util.isInteger(s)) {
                    int n = Integer.parseInt(s);
                    Random r = new Random();
                    Collection<Card> subList = new ArrayList();
                    for (int i = 0; i < n; i++) {
                        subList.add(new Card(
                                Card.Value.values()[1 + r.nextInt(13)],
                                Card.Suit.values()[1 + r.nextInt(4)]));
                    }
                    return subList;
                }
                return null;
            case 'S': //set
                if (Util.isInteger("" + chrs[1])) {
                    suit = chrs[1] - 48;
                    Collection<Card> set = getCardSet(suit);
                    return set;
                } else {
                    return null;
                }
            case 'A':
                value = 1;
                break;
            case 'T':
                value = 10;
                break;
            case 'J':
                value = 11;
                break;
            case 'Q':
                value = 12;
                break;
            case 'K':
                value = 13;
                break;
            default:
                if (!Util.isInteger("" + chrs[0])) {
                    return null;
                }
                value = chrs[0] - 48;
        }
        switch (chrs[1]) {
            case 'D':
                suit = 2;
                break;
            case 'H':
                suit = 3;
                break;
            case 'S':
                suit = 4;
                break;
            default:
                suit = 1;
        }
        Collection<Card> rv = new ArrayList();
        rv.add(new Card(Card.Value.values()[value], Card.Suit.values()[suit]));
        return rv; //:D
    }

    private static void printCards(Collection<Card> cards) {
        for (Card card : cards) {
            System.out.print(card.shortString() + " ");
        }
        System.out.println("");
    }

    private static void printMoveSeq(MinimaxNode node) {
        printMovePrefix(!node.isMinNode());
        printCards(node.pileCards);
        if (node.bestSuccessor != null) {
            printMoveSeq(node.bestSuccessor);
        } else {
            if (node.value == Integer.MAX_VALUE || node.value == Integer.MIN_VALUE) {
                printMovePrefix(node.isMinNode());
                List<Card> list = new ArrayList(node.pileCards);
                list.addAll(node.getNodePlayingCards());
                printCards(list);
            } else {
                System.out.println("//END OF ANALYSIS DEPTH");
            }
        }
    }

    private static void printMovePrefix(boolean minNode) {
        if (minNode) {
            System.out.print("Opp: ");
        } else {
            System.out.print("You: ");
        }
    }

    private static List<Card> getCardSet(int set) {
        switch (set) {
            case 2:
                return cardSet2();
            case 3:
                return cardSet3();
            case 4:
                return cardSet4();
            case 5:
                return cardSet5();
            case 6:
                return cardSet6();
            default:
                return cardSet1();
        }
    }

    private static List<Card> cardSet1() {//Two suits
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.NINE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.CLUBS));
        return cards;
    }

    private static List<Card> cardSet2() {//Dominant suit
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.DIAMONDS));
        return cards;
    }

    private static List<Card> cardSet3() {//Almost Pairs
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.SIX, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.SIX, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.THREE, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.THREE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.SPADES));
        return cards;
    }

    private static List<Card> cardSet4() {//Almost straight
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.ACE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.KING, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.NINE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.HEARTS));
        return cards;
    }

    private static List<Card> cardSet5() {//Almost pairs
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.KING, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.KING, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.THREE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.THREE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.TWO, Card.Suit.HEARTS));
        return cards;
    }

    private static List<Card> cardSet6() {//Almost straight
        List<Card> cards = new ArrayList();
        cards.add(new Card(Card.Value.ACE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.KING, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.QUEEN, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.JACK, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.TEN, Card.Suit.HEARTS));
        cards.add(new Card(Card.Value.EIGHT, Card.Suit.DIAMONDS));
        cards.add(new Card(Card.Value.SEVEN, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.SIX, Card.Suit.DIAMONDS));
        return cards;
    }

    private static void estimationTestSetBig(int limit) {
        int correctness = 0;
        int count = 0;
        int error = 0;
        int errorCount = 0;
        int wins = 0;
        int losses = 0;

        PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
        for (int i = 1; i < 6; i++) {
            for (int k = i + 1; k < 7; k++) {
                List<Card> myCards = new ArrayList();
                myCards.addAll(getCardSet(i));
                myCards.addAll(getCardSet(k));
                for (int j = 1; j < 6; j++) {
                    for (int m = j + 1; m < 7; m++) {
                        if (i != j && i != m && k != j && k != m) {

                            System.out.println("Test: Sets #" + i + " & #" + k + " vs Sets #" + j + " & #" + m);
                            MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2, limit);

                            List<Card> othersCards = new ArrayList();
                            othersCards.addAll(getCardSet(j));
                            othersCards.addAll(getCardSet(m));

                            int estimate = tree.estimateScore(myCards, othersCards, null);

                            System.out.println("-------------");
                            tree.generateTree(myCards, othersCards, null);
                            System.out.println("Estimate: " + estimate);
                            System.out.println("-------------");
                            System.out.println(tree);
                            System.out.println("-------------");

                            correctness += estimate * Math.signum(tree.root.value);
                            count++;

                            if (estimate * Math.signum(tree.root.value) < 0) {
                                error += Math.abs(estimate);
                                errorCount++;
                            }

                            if (tree.root.value == Integer.MAX_VALUE) {
                                wins++;
                            } else if (tree.root.value == Integer.MIN_VALUE) {
                                losses++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("\n\nEstimation correctness: " + (double) correctness / count);
        System.out.println("Average error: " + (double) error / Math.max(1, errorCount));
        System.out.println("Errors: " + errorCount);
        System.out.println("\nWins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Undetermined: " + (count - wins - losses));
        System.out.println("");
    }

    private static void estimationTestRandom(int limit, int n, int c1size, int c2size, boolean print) {
        if (c1size + c2size > 52) {
            System.out.println("Too many cards to deal: " + (c1size + c2size));
            return;
        }
        int count = 0;
        int wins = 0;
        int losses = 0;
        PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();

        for (int i = 0; i < n; i++) {
            List<Card> pack = new ArrayList();
            for (Card.Suit suit : Card.Suit.values()) {
                if (suit != Card.Suit.JOKER) {
                    for (Card.Value value : Card.Value.values()) {
                        if (value != Card.Value.JOKER) {
                            pack.add(new Card(value, suit));
                        }
                    }
                }
            }
            Collections.shuffle(pack);

            List<Card> myCards = pack.subList(0, c1size);
            List<Card> othersCards = pack.subList(c1size, c1size + c2size);

            MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2, limit);

            Collections.sort(myCards, new CardValueComparator());
            Collections.sort(othersCards, new CardValueComparator());

            int estimate = tree.estimateScore(myCards, othersCards, null);

            if (print) {
                System.out.print("\nRandom test #" + (i + 1) + ": |");
                for (Card card : myCards) {
                    System.out.print(card.shortString() + "|");
                }
                System.out.print(" vs |");
                for (Card card : othersCards) {
                    System.out.print(card.shortString() + "|");
                }
                System.out.println("");
            }

            tree.generateTree(myCards, othersCards, null);

            count++;
            if (tree.root.value == Integer.MAX_VALUE) {
                wins++;
            } else if (tree.root.value == Integer.MIN_VALUE) {
                losses++;
            } else {
                if (print) {
                    System.out.print("\t\tEstimated score: " + tree.root.value);
                }
            }
        }
        System.out.println("\nWins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.println("Undetermined: " + (count - wins - losses));
        System.out.println("");
    }

    private static List<Card> getPack() {
        List<Card> pack = new ArrayList();
        for (Card.Suit suit : Card.Suit.values()) {
            if (suit != Card.Suit.JOKER) {
                for (Card.Value value : Card.Value.values()) {
                    if (value != Card.Value.JOKER) {
                        pack.add(new Card(value, suit));
                    }
                }
            }
        }
        Collections.shuffle(pack);
        return pack;
    }

    private static void enterToContinue(Scanner sc) {
        System.out.println("\nPress <Enter> to continue");
        sc.nextLine();
    }

}
