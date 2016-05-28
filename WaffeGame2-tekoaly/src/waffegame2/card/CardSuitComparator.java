package waffegame2.card;

/**
 * Used to compare and sort cards. Compares only the cards' values
 *
 * @author Walter Grönholm
 * @version 1.0
 * @since 2016-01-02
 */
import java.util.Comparator;

public class CardSuitComparator implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        if (o1.getSuit().toInt() == o2.getSuit().toInt()) {
            return (o1.getValue().toInt()) - (o2.getValue().toInt());
        } else {
            return (o1.getSuit().toInt()) - (o2.getSuit().toInt());
        }
    }
}
