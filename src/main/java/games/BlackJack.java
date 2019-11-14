package games;

public class BlackJack {
  private static final int PLAYER = 0;
  private static final int CPU = 1;

  private static int[] cards;
  private static int cursor;

  private static int[][] playersCards;
  private static int[] playersCursors;

  private static final int MAX_VALUE = 21;
  private static final int MAX_CARDS_COUNT = 8;

  private static int[] playersMoney = {100, 100};

  public static void main(final String... args) {
    initRound();
  }

  private static void initRound() {
    System.out.format("%nУ Вас %d$, у компьютера - %d$. Начинаем новый раунд!%n",
                      playersMoney[PLAYER], playersMoney[CPU]);
    cards = CardUtils.getShaffledCards();
    playersCards = new int[2][MAX_CARDS_COUNT];
    playersCursors = new int[]{0, 0};
    cursor = 0;
  }

  private static int value(int card) {
    switch (CardUtils.getPar(card)) {
      case JACK: return 2;
      case QUEEN: return 3;
      case KING: return 4;
      case SIX: return 6;
      case SEVEN: return 7;
      case EIGHT: return 8;
      case NINE: return 9;
      case TEN: return 10;
      case ACE:
      default: return 11;
    }
  }
}
