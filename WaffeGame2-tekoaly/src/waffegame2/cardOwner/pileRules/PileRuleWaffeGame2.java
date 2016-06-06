package waffegame2.cardOwner.pileRules;

import waffegame2.cardOwner.PileTypeWaffeGame2;
import waffegame2.card.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import waffegame2.cardOwner.PileType;

/**
 * The logic of the pile for WaffeGame2. Implements PileRule
 *
 * @author Walter Grönholm
 * @version 1.1
 * @since 2016-05-28
 */
public class PileRuleWaffeGame2 implements PileRule {

    private List<Card> njCards;
    private int[] groups;
    private int jokers;
    private int maxGroup;

    @Override
    public PileType nullType() {
        return PileTypeWaffeGame2.NULL; //:D
    }

    @Override
    public PileType checkType(Collection<Card> c) {
        initCards(c);
        if (njCards.isEmpty()) {
            if (jokers == 0) {
                return PileTypeWaffeGame2.NULL;
            } else {
                return PileTypeWaffeGame2.OPEN;
            }
        }
        PileTypeWaffeGame2 s;
        s = getSuitType();
        if (s != PileTypeWaffeGame2.NULL) {
            return s;
        }
        s = getStraightType();
        if (s != PileTypeWaffeGame2.NULL) {
            return s;
        }
        s = getGroupType();
        return s;
    }

    @Override
    public PileType updateType(PileType currentType, Collection<Card> c) {
        initCards(c);
        PileTypeWaffeGame2 s;
        switch (currentType.toInt()) {
            case -1:
            case 0:
                if (njCards.isEmpty()) {
                    if (jokers == 0) {
                        return PileTypeWaffeGame2.NULL;
                    } else {
                        return PileTypeWaffeGame2.OPEN;
                    }
                }
            case 1:
            case 2:
            case 3:
            case 4:
                s = getSuitType();
                if (s != PileTypeWaffeGame2.NULL) {
                    return s;
                }
            case 5:
            case 6:
            case 7:
            case 8:
                s = getGroupType();
                if (s != PileTypeWaffeGame2.NULL) {
                    return s;
                }
            case 9:
            case 10:
                s = getStraightType();
                return s;
            default:
                return PileTypeWaffeGame2.NULL;
        }
    }

    private void initCards(Collection<Card> c) {
        njCards = new ArrayList();
        jokers = 0;
        groups = new int[Card.Value.max()];
        maxGroup = 0;

        int i;
        for (Card card : c) {
            if (card.isJoker()) {
                jokers++;
            } else {
                njCards.add(card);
                i = card.getValue().toInt() - 1;
                groups[i]++;
                if (groups[i] > maxGroup) {
                    maxGroup = groups[i];
                }
            }
        }

        Collections.sort(njCards, new CardValueComparator());
    }

    private PileTypeWaffeGame2 getSuitType() {
        if (njCards.isEmpty()) {
            return PileTypeWaffeGame2.OPEN;
        }

        Card.Suit suit = njCards.get(0).getSuit();
        for (Card card : njCards) {
            if (card.getSuit() != suit) {
                return PileTypeWaffeGame2.NULL;
            }
        }
        switch (suit) {
            case CLUBS:
                return PileTypeWaffeGame2.CLUBS;
            case DIAMONDS:
                return PileTypeWaffeGame2.DIAMONDS;
            case HEARTS:
                return PileTypeWaffeGame2.HEARTS;
            default:
                return PileTypeWaffeGame2.SPADES;
        }
    }

    private PileTypeWaffeGame2 getStraightType() {
        if (isPerfectStraight()) {
            return PileTypeWaffeGame2.PERFECTSTRAIGHT;
        }
        if (jokers >= getMinJokersNeededForStraight()) {
            if (maxGroup == 1) {
                return PileTypeWaffeGame2.STRAIGHT;
            }
            return PileTypeWaffeGame2.LONGSTRAIGHT;
        }
        return PileTypeWaffeGame2.NULL;
    }

    private boolean isPerfectStraight() {
        boolean perfectStraight = true;

        int jokersNeeded = 0;
        for (int i = 0; i < groups.length; i++) {
            int group = groups[i];
            if (group != maxGroup) {
                jokersNeeded += maxGroup - group;
                if (jokers < jokersNeeded) {
                    perfectStraight = false;
                    break;
                }
            }
        }

        return (perfectStraight);
    }

    private int getMinJokersNeededForStraight() {
        int jokersNeeded = 0;

        int largestHole = 0;
        int currentHole = 0;
        int holeSum = 0;
        boolean skipFirstHole = true;

        for (int i = 0; true; i++) {
            int group = groups[i % groups.length];
            if (skipFirstHole) {
                if (group == maxGroup) {
                    skipFirstHole = false;
                }
            } else {
                if (group < maxGroup) {
                    if (group < maxGroup - 1) {
                        jokersNeeded += (maxGroup - 1) - group;
                    }
                    currentHole++;
                } else {
                    if (currentHole > largestHole) {
                        largestHole = currentHole;
                    }
                    holeSum += currentHole;
                    currentHole = 0;
                    if (i >= groups.length) {
                        break;
                    }
                }
            }
        }
        return (jokersNeeded + holeSum - largestHole);
    }

    private PileTypeWaffeGame2 getGroupType() {
        int n = 2;
        if (maxGroup > n) {
            n = maxGroup;
        }
        for (; n <= 4; n++) {
            if ((njCards.size() + jokers) % n == 0) {
                int jokersNeeded = 0;
                for (int i = 0; i < groups.length; i++) {
                    int group = groups[i];
                    if (group > 0) {
                        jokersNeeded += n - group;
                    }
                }
                if (jokers >= jokersNeeded && (jokers - jokersNeeded) % n == 0) {
                    switch (n) {
                        case 2:
                            return PileTypeWaffeGame2.PAIRS;
                        case 3:
                            return PileTypeWaffeGame2.TRIPLES;
                        default:
                            return PileTypeWaffeGame2.QUADRUPLES;
                    }
                }
            }
        }
        return PileTypeWaffeGame2.NULL;
    }

}
