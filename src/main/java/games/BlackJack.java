package games;

import org.slf4j.Logger;

import java.io.IOException;

public class BlackJack {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);

  private static final int PLAYER = 0;
  private static final int CPU = 1;
  private static final int BET = 10;

  private static int[] cards;
  private static int cursor;

  private static int[][] playersCards;
  private static int[] playersCursors;

  private static final int MAX_VALUE = 21;
  private static final int MAX_CPU_VALUE = 17;
  private static final int MAX_CARDS_COUNT = 8;

  private static int[] playersMoney = {100, 100};

  public static void main(final String... args) throws IOException {
    while (playersMoney[PLAYER] > 0 && playersMoney[CPU] > 0) {
      initRound();

      getPlayerCards();

      getCpuCards();

      getWinner();
    }

    if (playersMoney[PLAYER] > 0)
      log.info("Вы выиграли! Поздравляем!");
    else
      log.info("Вы проиграли. Соболезнуем...");
  }

  private static void getWinner() {
    final int sumPlayer = getFinalSum(PLAYER);
    final int sumCpu = getFinalSum(CPU);
    log.info("Сумма ваших очков - {}, компьютера - {}", sumPlayer, sumCpu);

    if (sumCpu > sumPlayer) {
      getPaid(CPU);
    } else
    if (sumCpu < sumPlayer) {
      getPaid(PLAYER);
    } else {
      log.info("Ничья - каждый остается при своих!");
    }
  }

  private static void getCpuCards() {
    while (sum(CPU) < MAX_CPU_VALUE) {
      log.info("Компьютеру выпала карта {}", CardUtils.getString(addCardToPlayer(CPU)));
    }
  }

  private static void getPlayerCards() throws IOException {
    int iteration = 1;
    while (sum(PLAYER) < MAX_VALUE - 1) {
      if (confirm(iteration)) {
        log.info("Вам выпала карта {}", CardUtils.getString(addCardToPlayer(PLAYER)));
        iteration++;
      } else {
        break;
      }
    }
  }

  private static void getPaid(final int playerIndex) {
    if (playerIndex == CPU) {
      playersMoney[PLAYER] -= BET;
      playersMoney[CPU] += BET;
      log.info("Вы проиграли раунд! Теряете {}$", BET);
    } else {
      playersMoney[PLAYER] += BET;
      playersMoney[CPU] -= BET;
      log.info("Вы выиграли раунд! Получаете {}$", BET);
    }
  }

  private static int sum(final int player) {
    int result = 0;
    for (int i = 0; i < playersCursors[player]; i++) {
      result += value(playersCards[player][i]);
    }

    return result;
  }

  private static int getFinalSum(final int player) {
    final int result = sum(player);
    if (result > MAX_VALUE) {
      return 0;
    }
    return result;
  }

  private static boolean confirm(final int iteration) throws IOException {
    if (iteration > 2) {
      log.info("Берем еще? \"Y\" - Да, \"N\" - нет (Чтобы выйти из игры, нажмите Ctrl + C)");
      switch (Choice.getCharacterFromUser()) {
        case 'Y':
        case 'y': return true;
        default: return false;
      }
    }
    return true;
  }

  private static int addCardToPlayer(final int playerIndex) {
    playersCards[playerIndex][playersCursors[playerIndex]] = cards[cursor];
    playersCursors[playerIndex]++;
    cursor++;
    return cards[cursor - 1];
  }

  private static void initRound() {
    log.info("У Вас {}$, у компьютера - {}$. Начинаем новый раунд!", playersMoney[PLAYER], playersMoney[CPU]);
    cards = CardUtils.getShuffledCards();
    playersCards = new int[2][MAX_CARDS_COUNT];
    playersCursors = new int[]{0, 0};
    cursor = 0;
  }

  private static int value(final int card) {
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
