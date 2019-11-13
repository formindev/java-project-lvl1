package games;

import org.apache.commons.math3.util.MathArrays;

public class Drunkard {

  private static final int PARS_TOTAL_COUNT = Par.values().length;
  private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;
  private static final int FIRST_PLAYER = 0;
  private static final int SECOND_PLAYER = 1;

  private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT + 1];
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
    initArrays(cards,cards.length / 2);

    int iteration = 0;

    while (!playerCardsIsEmpty(FIRST_PLAYER) && !playerCardsIsEmpty(SECOND_PLAYER)) {
      iteration += 1;

      final int firstPlayerCard = getPlayerCard(FIRST_PLAYER, playerCardTails[FIRST_PLAYER]);
      final int secondPlayerCard = getPlayerCard(SECOND_PLAYER, playerCardTails[SECOND_PLAYER]);

      System.out.format("Итерация №%d Игрок №1 карта: %s; игрок №2 карта: %s. %n",
          iteration, getString(firstPlayerCard), getString(secondPlayerCard));

      final int firstPlayerPar =  firstPlayerCard % PARS_TOTAL_COUNT;
      final int secondPlayerPar =  secondPlayerCard % PARS_TOTAL_COUNT;

      if (firstPlayerPar > secondPlayerPar || (firstPlayerPar == 0 && secondPlayerPar == 8)) {
        addCardsToPlayer(FIRST_PLAYER, firstPlayerCard, secondPlayerCard);
        System.out.format("Выиграл %d игрок! %n", FIRST_PLAYER + 1);
      } else if (firstPlayerPar == secondPlayerPar) {
        addCardsToPlayer(-1, firstPlayerCard, secondPlayerCard);
        System.out.println("Спор - каждый остаётся при своих!");
      } else {
        addCardsToPlayer(SECOND_PLAYER, firstPlayerCard, secondPlayerCard);
        System.out.format("Выиграл %d игрок! %n", SECOND_PLAYER + 1);
      }

      playerCardTails[FIRST_PLAYER] = incrementIndex(playerCardTails[FIRST_PLAYER]);
      playerCardTails[SECOND_PLAYER] = incrementIndex(playerCardTails[SECOND_PLAYER]);

      getStringCountCards();

      final int winner = getWinner();

      if (winner != -1) {
        System.out.format("Выиграл %d игрок! Количество произведённых итераций: %d.",
            winner + 1, iteration);
      }
    }
  }

  private static void getStringCountCards() {
    int firstPlayerCountCards;
    int secondPlayerCountCards;
    if (playerCardHeads[FIRST_PLAYER] > playerCardTails[FIRST_PLAYER]) {
      firstPlayerCountCards = playerCardHeads[FIRST_PLAYER] - playerCardTails[FIRST_PLAYER];
    } else {
      if (!playerCardsIsEmpty(FIRST_PLAYER)) {
        firstPlayerCountCards = playerCardHeads[FIRST_PLAYER]
            + CARDS_TOTAL_COUNT + 1 - playerCardTails[FIRST_PLAYER];
      } else {
        firstPlayerCountCards = 0;
      }
    }

    if (playerCardHeads[SECOND_PLAYER] > playerCardTails[SECOND_PLAYER]) {
      secondPlayerCountCards = playerCardHeads[SECOND_PLAYER] - playerCardTails[SECOND_PLAYER];
    } else {
      if (!playerCardsIsEmpty(SECOND_PLAYER)) {
        secondPlayerCountCards = playerCardHeads[SECOND_PLAYER]
            + CARDS_TOTAL_COUNT + 1 - playerCardTails[SECOND_PLAYER];
      } else {
        secondPlayerCountCards = 0;
      }
    }

    System.out.format("У игрока №1 %d карт, у игрока №2 %d карт%n", firstPlayerCountCards,
        secondPlayerCountCards);
  }

  //Check players cards
  private static int getWinner() {
    if (playerCardsIsEmpty(FIRST_PLAYER)) {
      return SECOND_PLAYER;
    }

    if (playerCardsIsEmpty(SECOND_PLAYER)) {
      return FIRST_PLAYER;
    }

    return -1;
  }

  private static int getPlayerCard(final int playerIndex, final int cardIndex) {
    return playersCards[playerIndex][cardIndex];
  }

  private static void addCardsToPlayer(final int playerIndex, final int firstPlayerCard,
                                       final int secondPlayerCard) {
    if (playerIndex != -1) {
      playerCardHeads[playerIndex] = incrementIndex(playerCardHeads[playerIndex]);
      playersCards[playerIndex][playerCardHeads[playerIndex]] = firstPlayerCard;
      playerCardHeads[playerIndex] = incrementIndex(playerCardHeads[playerIndex]);
      playersCards[playerIndex][playerCardHeads[playerIndex]] = secondPlayerCard;
    } else {
      playerCardHeads[FIRST_PLAYER] = incrementIndex(playerCardHeads[FIRST_PLAYER]);
      playersCards[FIRST_PLAYER][playerCardHeads[FIRST_PLAYER]] = firstPlayerCard;
      playerCardHeads[SECOND_PLAYER] = incrementIndex(playerCardHeads[SECOND_PLAYER]);
      playersCards[SECOND_PLAYER][playerCardHeads[SECOND_PLAYER]] = secondPlayerCard;
    }
  }

  private static String getString(final int cardNumber) {
    return getPar(cardNumber) + " " + getSuit(cardNumber);
  }

  private static Suit getSuit(final int cardNumber) {
    return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
  }

  private static Par getPar(final int cardNumber) {
    return Par.values()[cardNumber % PARS_TOTAL_COUNT];
  }

  private static void initArrays(final int[] cards, final int middleArray) {
    for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
      if (i < middleArray) {
        playersCards[FIRST_PLAYER][i] = cards[i];
      } else {
        playersCards[SECOND_PLAYER][i - middleArray] = cards[i];
      }
    }

    playerCardHeads[FIRST_PLAYER] = middleArray;
    playerCardHeads[SECOND_PLAYER] = middleArray;
    playerCardTails[FIRST_PLAYER] = 0;
    playerCardTails[SECOND_PLAYER] = 0;
  }

  private static int incrementIndex(int i) {
    return (i + 1) % (CARDS_TOTAL_COUNT + 1);
  }

  private static boolean playerCardsIsEmpty(final int playerIndex) {
    final int tail = playerCardTails[playerIndex];
    final int head = playerCardHeads[playerIndex];

    return tail == head;
  }
}
