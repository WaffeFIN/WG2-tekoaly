/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waffegame2.card;

/**
 * A card containing a suit and value.
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
public class Card {

    /**
     * Enum for suits, including joker.
     *
     * @author Walter Grönholm
     * @version 1.0
     * @since 2016-01-02
     */
    public enum Suit {

        JOKER(0), CLUBS(1), DIAMONDS(2), HEARTS(3), SPADES(4);

        private final int suit;

        private Suit(int suit) {
            this.suit = suit;
        }

        public int toInt() {
            return suit;
        }

        /**
         * The method searches through all Suits and returns the highest integer
         * value found (which is 4, the Spades)
         *
         * @return the highest integer value
         */
        public static int max() {
            return 4;
        }

        @Override
        public String toString() {
            switch (this) {
                case JOKER:
                    return "Joker";
                case HEARTS:
                    return "Hearts";
                case DIAMONDS:
                    return "Diamonds";
                case CLUBS:
                    return "Clubs";
                case SPADES:
                    return "Spades";
                default:
                    return "Other";
            }
        }
    }

    /**
     * Enum for values, including joker.
     *
     * @author Walter Grönholm
     * @version 1.0
     * @since 2016-01-02
     */
    public enum Value {

        JOKER(0), ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
        SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

        private final int value;

        private Value(int value) {
            this.value = value;
        }

        public int toInt() {
            return value;
        }

        /**
         * The method searches through all Values and returns the highest
         * integer value found (which is 13, the King)
         *
         * @return the highest integer value
         */
        public static int max() {
            return 13;
        }

        @Override
        public String toString() {
            switch (value) {
                case 0:
                    return "Joker";
                case 1:
                    return "Ace";
                case 11:
                    return "Jack";
                case 12:
                    return "Queen";
                case 13:
                    return "King";
                default:
                    return "" + toInt();
            }
        }
    }

    private final Value value;
    private final Suit suit;

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;

        if (value == Value.JOKER ^ suit == Suit.JOKER) {
            throw new IllegalArgumentException("Tried creating a Joker card with non-matching suit and value");
        }
    }

    public Value getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isJoker() {
        return (getSuit() == Suit.JOKER);
    }

    @Override
    public String toString() {
        if (isJoker()) {
            return suit.toString();
        } else {
            return value + " of " + suit;
        }
    }

    public String shortString() {
        if (isJoker()) {
            return "" + suit.toString().charAt(0);
        } else {
            char c = 0;
            switch (value) {
                case ACE:
                    c = 'A';
                    break;
                case TEN:
                    c = 'T';
                    break;
                case JACK:
                    c = 'J';
                    break;
                case QUEEN:
                    c = 'Q';
                    break;
                case KING:
                    c = 'K';
                    break;
                default:
                    c = (char) (value.toInt() + 48);
            }
            return "" + suit.toString().charAt(0) + c;
        }
    }

}
