package games;

import org.slf4j.Logger;

public class Drunkard {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

  private static final int NO_PLAYER = -1;
  private static final int FIRST_PLAYER = 0;
  private static final int SECOND_PLAYER = 1;

  private static int[][] playersCards = new int[2][CardUtils.CARDS_TOTAL_COUNT + 1];
  private static int[] playerCardTails = new int[2];
  private static int[] playerCardHeads = new int[2];

  public static void main(final String... args) {
    int iteration = 0;

    dealCards();

    while (!playerCardsIsEmpty(FIRST_PLAYER) && !playerCardsIsEmpty(SECOND_PLAYER)) {
      getPlayersCardsString(++iteration);

      playRound();

      getNextCards();

      getCountsCardsString();

      getWinner(iteration);
    }
  }

  private static void playRound() {
    final int firstPlayerParIndex  = getParIndex(FIRST_PLAYER);
    final int secondPlayerParIndex =  getParIndex(SECOND_PLAYER);

    if (firstPlayerParIndex  > secondPlayerParIndex
        || firstPlayerParIndex  == 0 && secondPlayerParIndex  == 8) {
      addCardsToPlayer(FIRST_PLAYER);
    } else if (firstPlayerParIndex  == secondPlayerParIndex) {
      addCardsToPlayer(NO_PLAYER);
    } else {
      addCardsToPlayer(SECOND_PLAYER);
    }
  }

  private static void getNextCards() {
    for (int i = FIRST_PLAYER; i <= SECOND_PLAYER; i++) {
      playerCardTails[i] = incrementIndex(playerCardTails[i]);
    }
  }

  private static int getParIndex(final int playerIndex) {
    final int playerCard = getPlayerCard(playerIndex);
    return CardUtils.getPar(playerCard).ordinal();
  }

  private static void getPlayersCardsString(final int iteration) {
    final int firstPlayerCard = getPlayerCard(FIRST_PLAYER);
    final int secondPlayerCard = getPlayerCard(SECOND_PLAYER);

    log.info("Итерация №{} Игрок №1 карта: {}; игрок №2 карта: {}.",
        iteration, CardUtils.getString(firstPlayerCard), CardUtils.getString(secondPlayerCard));
  }

  private static void getCountsCardsString() {
    final int firstPlayerCountCards = getCountCards(FIRST_PLAYER);
    final int secondPlayerCountCards = getCountCards(SECOND_PLAYER);

    log.info("У игрока №1 {} карт, у игрока №2 {} карт", firstPlayerCountCards,
        secondPlayerCountCards);
  }

  private static int getCountCards(final int playerIndex) {
    final int result;

    if (playerCardHeads[playerIndex] > playerCardTails[playerIndex]) {
      result = playerCardHeads[playerIndex] - playerCardTails[playerIndex];
    } else {
      if (!playerCardsIsEmpty(playerIndex)) {
        result = playerCardHeads[playerIndex]
            + CardUtils.CARDS_TOTAL_COUNT + 1 - playerCardTails[playerIndex];
      } else {
        result = 0;
      }
    }
    return result;
  }

  //Check players cards
  private static void getWinner(final int iteration) {
    if (playerCardsIsEmpty(FIRST_PLAYER)) {
      log.info("Выиграл второй игрок! Количество произведённых итераций: {}.", iteration);
    }

    if (playerCardsIsEmpty(SECOND_PLAYER)) {
      log.info("Выиграл первый игрок! Количество произведённых итераций: {}.", iteration);
    }
  }

  private static int getPlayerCard(final int playerIndex) {
    final int cardIndex = playerCardTails[playerIndex];
    return playersCards[playerIndex][cardIndex];
  }

  private static void addCardsToPlayer(final int playerIndex) {
    if (playerIndex != NO_PLAYER) {
      for (int i = FIRST_PLAYER; i <= SECOND_PLAYER; i++) {
        playerCardHeads[playerIndex] = incrementIndex(playerCardHeads[playerIndex]);
        playersCards[playerIndex][playerCardHeads[playerIndex]] = getPlayerCard(i);
      }
      log.info("Выиграл {} игрок!", playerIndex + 1);
    } else {
      for (int i = FIRST_PLAYER; i <= SECOND_PLAYER; i++) {
        playerCardHeads[i] = incrementIndex(playerCardHeads[i]);
        playersCards[i][playerCardHeads[i]] = getPlayerCard(i);
      }
      log.info("Спор - каждый остаётся при своих!");
    }
  }

  private static void dealCards() {
    final int[] cards = CardUtils.getShuffledCards();
    final int middleArray = cards.length / 2;

    for (int i = 0; i < cards.length; i++) {
      if (i < middleArray) {
        playersCards[FIRST_PLAYER][i] = cards[i];
      } else {
        playersCards[SECOND_PLAYER][i - middleArray] = cards[i];
      }
    }
    setPlayersCardsPoints(middleArray);
  }

  private static void setPlayersCardsPoints(final int middleArray) {
    for (int i = FIRST_PLAYER; i <= SECOND_PLAYER; i++) {
      playerCardHeads[i] = middleArray;
      playerCardTails[i] = 0;
    }
  }

  private static int incrementIndex(final int i) {
    return (i + 1) % (CardUtils.CARDS_TOTAL_COUNT + 1);
  }

  private static boolean playerCardsIsEmpty(final int playerIndex) {
    final int tail = playerCardTails[playerIndex];
    final int head = playerCardHeads[playerIndex];

    return tail == head;
  }
}
