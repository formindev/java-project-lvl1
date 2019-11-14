package games;

import org.apache.commons.math3.util.MathArrays;

public class CardUtils {
  public static final int PARS_TOTAL_COUNT = Par.values().length;
  public static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

  enum Suit {
    SPADES,
    HEARTS,
    CLUBS,
    DIAMONDS
  }

  enum Par {
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE
  }

  static int[] getShaffledCards() {
    int[] cards = new int[CARDS_TOTAL_COUNT];

    for (int i = 0; i < cards.length; i++) {
      cards[i] = i;
    }

    MathArrays.shuffle(cards);
    return cards;
  }

  public static String getString(final int cardNumber) {
    return getPar(cardNumber) + " " + getSuit(cardNumber);
  }

  public static Suit getSuit(final int cardNumber) {
    return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
  }

  public static Par getPar(final int cardNumber) {
    return Par.values()[cardNumber % PARS_TOTAL_COUNT];
  }
}
