/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.player.ai;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import waffegame2.card.Card;
import waffegame2.card.CardValueComparator;
import waffegame2.cardOwner.PileTypeWaffeGame2;
import waffegame2.cardOwner.pileRules.PileRuleWaffeGame2;
import waffegame2.util.Util;
import waffegame2.util.WaffeList;
import waffegame2.util.WaffeMap;

/**
 *
 * @author Walter
 */
public class Main {

    /**
     * Default soft limit
     */
    static final int DEFAULT_LIMIT = 4000000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int softLimit = DEFAULT_LIMIT;
        int thinkingTime = 1600;
        final PileRuleWaffeGame2 prwg2 = new PileRuleWaffeGame2();
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
                    case 8:
                        thinkingTime = changeThinking(thinkingTime, sc);
                        break;
                    case 9:
                        softLimit = changeLimit(softLimit, sc);
                        break;
                    case 1:
                        analyse(sc, prwg2, softLimit);
                        break;
                    case 2:
                        initPlay(sc, prwg2, softLimit, thinkingTime, true);
                        break;
                    case 3:
                        initPlay(sc, prwg2, softLimit, thinkingTime, false);
                        break;
                    case 4:
                        estimationTestSetBig(softLimit);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Main menu
     * @return Action number
     */
    private static int menu(Scanner sc) {
        System.out.println("");
        System.out.println("\t\tMenu");
        System.out.println("[1]\tCalculate winning line (Analysis)");
        System.out.println("[2]\tPlay vs computer");
        System.out.println("[3]\tSpectate computer vs computer");
        System.out.println("[4]\tBig test");
        System.out.println("[8]\tChange computer thinking time");
        System.out.println("[9]\tChange soft limit");
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

    /**
     * Prints out the help text
     */
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
        System.out.println("\nWhen playing against the computer, select your cards by enter the corresponding numbers separeted with spaces");
        System.out.println("For instance '1 2 10 4'");
        System.out.println("Remember, if you are able to hit, you MUST!");
        System.out.println("Also, if you are unable to hit, you can enter an empoty string. The program will also automatically pass for you if you try to enter anything else");
        System.out.println("\nIn case of OutOfMemoryErrors, try lowering the soft limit from the settings");
        enterToContinue(sc);
    }

    /**
     * Initialization before playing. Get card amounts
     */
    private static void initPlay(Scanner sc, PileRuleWaffeGame2 prwg2, int softLimit, int think, boolean control) {
        int c1;
        int c2;
        if (control) {
            switch (getInitPlay1(sc)) {
                case 0:
                    c1 = 15;
                    c2 = 10;
                    break;
                case 1:
                    c1 = 10;
                    c2 = 2;
                    break;
                case 2:
                    c1 = 8;
                    c2 = 3;
                    break;
                case 4:
                    c1 = 8;
                    c2 = 6;
                    break;
                case 5:
                    c1 = 10;
                    c2 = 8;
                    break;
                case 3:
                    c1 = 6;
                    c2 = 4;
                    break;
                default:
                    return;
            }
        } else {
            switch (getInitPlay2(sc)) {
                case 0:
                    c1 = 15;
                    c2 = 16;
                    break;
                case 1:
                    c1 = 7;
                    c2 = 8;
                    break;
                case 2:
                    c1 = 9;
                    c2 = 10;
                    break;
                case 4:
                    c1 = 13;
                    c2 = 14;
                    break;
                case 5:
                    c1 = 14;
                    c2 = 15;
                    break;
                case 3:
                    c1 = 11;
                    c2 = 12;
                    break;
                default:
                    return;
            }
        }
        List<Card> pack = getPack();
        List<Card> cards = pack.subList(0, c1);
        List<Card> oCards = pack.subList(c1, c1 + c2);
        play(sc, cards, oCards, prwg2, softLimit, think, control);
    }

    /**
     * Method contains everything used for versus play.
     */
    private static void play(Scanner sc, List<Card> cards, List<Card> oCards, PileRuleWaffeGame2 prwg2, int softLimit, int think, boolean control) {
        MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2, softLimit);
        
        cards = new WaffeList(cards);
        oCards = new WaffeList(oCards);
        List<Card> pile = new WaffeList();
        for (int turn = 0; true; turn++) {
            if (turn > 31 * 2) {
                System.out.println("Something has gone wrong...");
                break;
            }
            List<Card> playable;
            List<Card> others;
            if (turn % 2 == 0) {
                playable = cards;
                others = oCards;
            } else {
                playable = oCards;
                others = cards;
            }
            printGameState(turn, playable, others, pile);
            Collection<Card> play;
            if (turn % 2 == 0 && control) {
                while (true) {
                    play = chooseCards(sc, playable);
                    if (play == null || play.isEmpty()) {
                        if (SuccessorFinderWaffeGame2.isAbleToHit(playable, pile, prwg2.checkType(pile).toInt())) {
                            System.out.println("You must hit if you are able to! Try transforming the pile");
                            continue;
                        } else {
                            break;
                        }
                    }
                    List<Card> test = new WaffeList(pile);
                    test.addAll(play);
                    if (prwg2.checkType(test) == PileTypeWaffeGame2.NULL) {
                        if (SuccessorFinderWaffeGame2.isAbleToHit(playable, pile, prwg2.checkType(pile).toInt())) {
                            System.out.println("Invalid selection");
                            continue;
                        } else {
                            System.out.println("Invalid selection, but it turns out you cannot hit anyway! Passing your turn");
                            play = null;
                            break;
                        }
                    }
                    break;
                }
            } else {
                System.out.println("Thinking...");
                try {
                    if (think > 0) {
                        Thread.sleep(think);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                play = tree.findBestMove(playable, others, pile);
            }
            
            if (play == null || play.isEmpty()) {
                pile.clear();
            } else {
                playable.removeAll(play);
                pile.addAll(play);
            }
            
            if (playable.isEmpty()) {
                if (control) {
                    if (turn % 2 == 0) {
                        System.out.println("\nYou won! Congratulations!\n");
                    } else {
                        System.out.println("\nYou lost! Too bad...\n");
                    }
                } else {
                    System.out.println("\nComputer number " + (turn % 2 + 1) + " wins!\n");
                }
                enterToContinue(sc);
                break;
            }
        }
    }

    /**
     * Shows all game information to the user
     */
    private static void printGameState(int turn, List<Card> playable, List<Card> others, List<Card> pile) {
        System.out.println("\n\n\n");
        System.out.println("\t" + (turn + 1) + ":\t" + "Player " + (turn % 2 + 1) + ", it's your turn\n");
        System.out.println("Opponent's cards: ");
        printCards(others);
        System.out.println("Your cards: ");
        printCards(playable);
        if (pile.isEmpty()) {
            System.out.println("<Empty table> \n");
        } else {
            System.out.println("Cards on table: ");
            printCards(pile);
        }
        System.out.println("");
    }

    /**
     * Asks the user to select cards
     * @param playable Cards from which you can choose
     * @return a possibly non-valid card selection
     */
    private static Collection<Card> chooseCards(Scanner sc, List<Card> playable) {
        Collections.sort(playable, new CardValueComparator());
        System.out.println("Choose your cards! Separate each selection with a space. Input empty string if you have to pass");
        int i = 1;
        for (Card card : playable) {
            System.out.println("[" + i + "]: " + card);
            i++;
        }
        selectionLoop:
        while (true) {
            Collection<Card> play = new WaffeList();
            String str = sc.nextLine();
            if (str.isEmpty()) {
                return null;
            }
            for (String s : str.split(" ")) {
                if (Util.isInteger(s)) {
                    int n = Integer.parseInt(s) - 1;
                    if (n >= playable.size() || n < 0) {
                        System.out.println("Number " + (n + 1) + " is out of bounds! Try again");
                        continue selectionLoop;
                    }
                    play.add(playable.get(n));
                } else if (!s.isEmpty()) {
                    System.out.println("Invalid input, '" + s + "'! Try again");
                    continue selectionLoop;
                }
            }
            return play;
        }
    }

    /**
     * Analyzes a position and prints out the winning/losing line
     */
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
        if (myCards.size() + othersCards.size() >= 32) {
            System.out.println("Too many cards entered! You can only enter a total of 31 cards at a time");
            return;
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

    /**
     * A help-method for analysis. Takes the user input and returns a list of cards
     */
    private static List<Card> getCardsFromStrings(String str) {
        if (str.isEmpty()) {
            return new WaffeList();
        }
        str = str.toUpperCase();
        String[] strs = str.split(" ");
        List<Card> cards = new WaffeList();
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

    /**
     * A help-method used by getCardsFromStrings. Takes the isolated string and returns a collection of cards
     */
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
                    Collection<Card> subList = new WaffeList();
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
        Collection<Card> rv = new WaffeList();
        rv.add(new Card(Card.Value.values()[value], Card.Suit.values()[suit]));
        return rv; //:D
    }

    /**
     * Print the cards using their shortString
     */
    private static void printCards(Collection<Card> cards) {
        for (Card card : cards) {
            System.out.print(card.shortString() + " ");
        }
        System.out.println("");
    }

    /**
     * Prints out the best line from a node
     * @param node 
     */
    private static void printMoveSeq(MinimaxNode node) {
        printMovePrefix(!node.isMinNode());
        printCards(node.pileCards);
        if (node.bestSuccessor != null) {
            printMoveSeq(node.bestSuccessor);
        } else {
            if (node.value == Integer.MAX_VALUE || node.value == Integer.MIN_VALUE) {
                printMovePrefix(node.isMinNode());
                List<Card> list = new WaffeList(node.pileCards);
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

    /**
     * Returns a list of 6-8 cards
     */
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
        List<Card> cards = new WaffeList();
        cards.add(new Card(Card.Value.ACE, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.JACK, Card.Suit.CLUBS));
        cards.add(new Card(Card.Value.TEN, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.NINE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.FIVE, Card.Suit.SPADES));
        cards.add(new Card(Card.Value.FOUR, Card.Suit.CLUBS));
        return cards;
    }

    private static List<Card> cardSet2() {//Dominant suit
        List<Card> cards = new WaffeList();
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
        List<Card> cards = new WaffeList();
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
        List<Card> cards = new WaffeList();
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
        List<Card> cards = new WaffeList();
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
        List<Card> cards = new WaffeList();
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

    /**
     * A standard test choosing all possible combinations of 2 v 2 card sets.
     */
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
                List<Card> myCards = new WaffeList();
                myCards.addAll(getCardSet(i));
                myCards.addAll(getCardSet(k));
                for (int j = 1; j < 6; j++) {
                    for (int m = j + 1; m < 7; m++) {
                        if (i != j && i != m && k != j && k != m) {

                            System.out.println("Test: Sets #" + i + " & #" + k + " vs Sets #" + j + " & #" + m);
                            MinimaxTreeWaffeGame2 tree = new MinimaxTreeWaffeGame2(prwg2, limit);

                            List<Card> othersCards = new WaffeList();
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

    /**
     * Generates random c1size v c2size games and analyzes them.
     * @param n the amount of tests to be done
     * @param c1size card amount of starting hand
     * @param c2size card amount of responding hand
     * @param print if true, prints out each test output. If false, only the win/loss details are printed out after all the tests.
     */
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
            List<Card> pack = new WaffeList();
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

    /**
     * Generates a shuffled pack of 52 cards
     */
    private static List<Card> getPack() {
        List<Card> pack = new WaffeList();
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

    /**
     * Used to change AI thinking time
     */
    private static int changeThinking(int thinkingTime, Scanner sc) {
        System.out.println("Enter the thinking time in milliseconds (currently: " + thinkingTime + ")");

        while (true) {
            String str = sc.nextLine();
            if (str.isEmpty()) {
                return thinkingTime;
            }
            if (!Util.isInteger(str)) {
                System.out.println("Please enter a number. Enter an empty string to cancel");
                continue;
            }
            int n = Integer.parseInt(str);
            if (n < 0) {
                n = 0;
            } else if (n > 10000) {
                System.out.println("You really want to wait that long for each move?");
                enterToContinue(sc);
            }
            return n;
        }
    }

    /**
     * Used to change the soft limit
     */
    private static int changeLimit(int softLimit, Scanner sc) {
        if (softLimit == DEFAULT_LIMIT) {
            System.out.println("Enter the soft limit (currently: " + softLimit + ")");
        } else {
            System.out.println("Enter the soft limit (currently: " + softLimit + ", default: " + DEFAULT_LIMIT + ")");
        }
        while (true) {
            String str = sc.nextLine();
            if (str.isEmpty()) {
                return softLimit;
            }
            if (!Util.isInteger(str)) {
                System.out.println("Please enter a number. Enter an empty string to cancel");
                continue;
            }
            int n = Integer.parseInt(str);
            if (n < 1000) {
                n = 1000;
            } else if (n > 9999999) {
                System.out.println("Warning! The limit you entered might make the program run out of memory!");
                enterToContinue(sc);
            }
            return n;
        }
    }

    /**
     * A help-method which prints out the choices for a Human v Computer game
     */
    private static int getInitPlay1(Scanner sc) {
        System.out.println("Enter your preferred difficulty! Enter empty string to cancel"
                + "\n[1] Very easy \t10 v 2"
                + "\n[2] Easy      \t8 v 3"
                + "\n[3] Normal    \t6 v 4"
                + "\n[4] Hard      \t8 v 6"
                + "\n[5] Very Hard \t10 v 8"
                + "\n[0] Super Hard\t15 v 10");
        while (true) {
            String cmd = sc.nextLine();
            if (cmd.isEmpty()) {
                return -1;
            }
            if (Util.isInteger(cmd)) {
                return Integer.parseInt(cmd);
            }
        }
    }

    /**
     * A help-method which prints out the choices for a Computer v Computer game
     */
    private static int getInitPlay2(Scanner sc) {
        System.out.println("Enter game length! Enter empty string to cancel"
                + "\n[1] Very Short \t7 v 8"
                + "\n[2] Short      \t9 v 10"
                + "\n[3] Medium     \t11 v 12"
                + "\n[4] Long       \t13 v 14"
                + "\n[5] Very Long  \t14 v 15"
                + "\n[0] Maximum    \t15 v 16");
        while (true) {
            String cmd = sc.nextLine();
            if (cmd.isEmpty()) {
                return -1;
            }
            if (Util.isInteger(cmd)) {
                return Integer.parseInt(cmd);
            }
        }
    }
}
