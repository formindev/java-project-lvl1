package games;

import org.slf4j.Logger;

public class Slot {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);

  private static final int BET = 10;
  private static final int WIN_BET = 1000;

  public static void main(final String... args) {
    int bank = 100;
    int reel1 = 0;
    int reel2 = 0;
    int reel3 = 0;
    int reelSize = 6;

    while (bank > 0) {
      log.info("У Вас - {}$, ставка - {}$", bank, BET);
      log.info("Крутим барабаны! Розыгрыш принёс следующие результаты:");

      reel1 = (reel1 + (int) Math.round(Math.random() * 100)) % reelSize;
      reel2 = (reel2 + (int) Math.round(Math.random() * 100)) % reelSize;
      reel3 = (reel3 + (int) Math.round(Math.random() * 100)) % reelSize;

      log.info("первый барабан - {}, второй - {}, третий - {}", reel1, reel2, reel3);

      if (reel1 == reel2 && reel1 == reel3) {
        bank += WIN_BET;
        log.info("Выигрыш: {}$, ", WIN_BET);
      }
      else {
        bank -= BET;
        log.info("Проигрыш: {}$, ", BET);
      }
      log.info("Ваш капитал теперь составляет: {}$", bank);
    }
    log.info("Игра окончена!");
  }
}