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

    NULL, OPEN, CLUBS, DIAMONDS, HEARTS, SPADES, STRAIGHT, PERFECTSTRAIGHT, PAIRS, TRIPLES, QUADRUPLES, LONGSTRAIGHT;

    @Override
    public String toString() {
        switch (this) {
            case NULL:
                return "Open";
            case OPEN: //only jokers
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
