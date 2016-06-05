/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.cardOwner;

/**
 * Enum for the type of the pile.
 *
 * @author Walter Grönholm
 * @version 1.1
 * @since 2016-05-28
 */
public enum PileTypeWaffeGame2 implements PileType {

    /**
     * Null type, used for invalid pile configurations.
     */
    NULL(-1),
    /**
     * Open, only jokers have been placed and the type of the pile is still
     * undetermined.
     */
    OPEN(0),
    /**
     * All cards are of clubs.
     */
    CLUBS(1),
    /**
     * All cards are of diamonds.
     */
    DIAMONDS(2),
    /**
     * All cards are of hearts.
     */
    HEARTS(3),
    /**
     * All cards are of spades.
     */
    SPADES(4),
    /**
     * All cards are in a straight. The straight doesn't overlap itself.
     */
    STRAIGHT(5),
    /**
     * All cards are in pairs.
     */
    PAIRS(6),
    /**
     * All cards are in triples.
     */
    TRIPLES(7),
    /**
     * All cards are in quadruples.
     */
    QUADRUPLES(8),
    /**
     * All cards are in a perfect straight. A perfect straight contains a number
     * of cards divisible by 13.
     */
    PERFECTSTRAIGHT(9),
    /**
     * All cards are in an overlapping straight. This is the final type a pile
     * can achieve.
     */
    LONGSTRAIGHT(10);

    private final int type;

    private PileTypeWaffeGame2(int type) {
        this.type = type;
    }

    @Override
    public int toInt() {
        return type;
    }

    @Override
    public String toString() {
        switch (this) {
            case NULL:
                return "Open";
            case OPEN: 
                return "Open";
            case CLUBS:
                return "Clubs";
            case DIAMONDS:
                return "Diamonds";
            case HEARTS:
                return "Hearts";
            case SPADES:
                return "Spades";
            case STRAIGHT:
                return "Straight";
            case PERFECTSTRAIGHT:
                return "Perfect Straight";
            case PAIRS:
                return "Pairs";
            case TRIPLES:
                return "Triples";
            case QUADRUPLES:
                return "Quadruples";
            case LONGSTRAIGHT:
                return "Long Straight";
            default:
                return "Other";
        }
    }
}
