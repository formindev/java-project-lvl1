package games;

import java.io.IOException;

public class BlackJack {
  private static final int PLAYER = 0;
  private static final int CPU = 1;
  private static final int BET = 10;

  private static int[] cards;
  private static int cursor;

  private static int[][] playersCards;
  private static int[] playersCursors;

  private static final int MAX_VALUE = 21;
  private static final int MAX_CARDS_COUNT = 8;

  private static int[] playersMoney = {100, 100};

  public static void main(final String... args) throws IOException {
    while (playersMoney[PLAYER] > 0 && playersMoney[CPU] > 0) {
      initRound();

      System.out.format("Вам выпала карта %s%n", CardUtils.getString(addCardToPlayer(PLAYER)));
      System.out.format("Вам выпала карта %s%n", CardUtils.getString(addCardToPlayer(PLAYER)));
      while (sum(PLAYER) < 20) {
        if (confirm("Берем еще?")) {
          System.out.format("Вам выпала карта %s%n", CardUtils.getString(addCardToPlayer(PLAYER)));
        } else {
          break;
        }
      }

      System.out.format("Компьютеру выпала карта %s%n", CardUtils.getString(addCardToPlayer(CPU)));
      System.out.format("Компьютеру выпала карта %s%n", CardUtils.getString(addCardToPlayer(CPU)));
      while (sum(CPU) < 17) {
          System.out.format("Компьютеру выпала карта %s%n", CardUtils.getString(addCardToPlayer(CPU)));
      }
      final int sumPlayer = getFinalSum(PLAYER);
      final int sumCpu = getFinalSum(CPU);
      System.out.format("Сумма ваших очков - %d, компьютера - %d%n", sumPlayer, sumCpu);

      if (sumCpu > sumPlayer) {
        playersMoney[PLAYER] -= BET;
        playersMoney[CPU] += BET;
        System.out.format("Вы проиграли раунд! Теряете %d$%n", BET);
      } else
        if (sumCpu < sumPlayer) {
          playersMoney[PLAYER] += BET;
          playersMoney[CPU] -= BET;
          System.out.format("Вы выиграли раунд! Получаете %d$%n", BET);
        } else
           System.out.println("Ничья - каждый остается при своих!");
    }

    if (playersMoney[PLAYER] > 0)
      System.out.println("Вы выиграли! Поздравляем!");
    else
      System.out.println("Вы проиграли. Соболезнуем...");
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

  private static boolean confirm(String message) throws IOException {
    System.out.println(message + " \"Y\" - Да, {любой другой символ} - "
                       + "нет (Чтобы выйти из игры, нажмите Ctrl + C)");
    switch (Choice.getCharacterFromUser()) {
      case 'Y':
      case 'y': return true;
      default: return false;
    }
  }

  private static int addCardToPlayer(final int player) {
    playersCards[player][playersCursors[player]] = cards[cursor];
    playersCursors[player] += 1;
    cursor += 1;
    return cards[cursor - 1];
  }

  private static void initRound() {
    System.out.format("У Вас %d$, у компьютера - %d$. Начинаем новый раунд!%n",
                      playersMoney[PLAYER], playersMoney[CPU]);
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
