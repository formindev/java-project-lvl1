package games;

import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

public class Drunkard {

  private static final int PARS_TOTAL_COUNT = Par.values().length;
  private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

  private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];
  private static int[] playerCardTails = new int[2];
  private static int[] playerCardHeads = new int[2];

  private enum Suit {
    SPADES,
    HEARTS,
    CLUBS,
    DIAMONDS
  }

  private enum Par {
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

  public static void main(final String... args) {
    int[] cards = new int[CARDS_TOTAL_COUNT];

    for (int i = 0; i < cards.length; i++) {
      cards[i] = i;
    }

    MathArrays.shuffle(cards);
    copyArrays(cards,cards.length / 2);

  }

  private static Suit getSuit(final int cardNumber) {
    return Suit.values()[cardNumber / 9];
  }

  private static Par getPar(final int cardNumber) {
    return Par.values()[cardNumber % 9];
  }

  private static void copyArrays(final int[] cards, final int middleArray) {
    for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
      if (i < middleArray)
        playersCards[0][i] = cards[i];
      else
        playersCards[1][i - middleArray] = cards[i];
    }
  }
}
