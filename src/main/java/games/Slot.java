package games;

public class Slot {

  public static void main(final String... args) {
    int bank = 100;
    int bet = 10;
    int winBet = 1000;
    int reel1 = 0;
    int reel2 = 0;
    int reel3 = 0;
    int reelSize = 6;

    while (bank > 0) {
      System.out.format("У Вас - %d$, ставка - %d$ %n", bank, bet);
      System.out.println("Крутим барабаны! Розыгрыш принёс следующие результаты:");
      reel1 = (reel1 + (int) Math.round(Math.random() * 100)) % reelSize;
      reel2 = (reel2 + (int) Math.round(Math.random() * 100)) % reelSize;
      reel3 = (reel3 + (int) Math.round(Math.random() * 100)) % reelSize;

      System.out.format("первый барабан - %d, второй - %d, третий - %d %n", reel1, reel2, reel3);

      if (reel1 == reel2 && reel1 == reel3) {
        bank = bank + winBet;
        System.out.format("Выигрыш: %d$, ", winBet);
      }
      else {
        bank = bank - bet;
        System.out.format("Проигрыш: %d$, ", bet);
      }
      System.out.format("ваш капитал теперь составляет: %d$%n", bank);
    }
    System.out.println("Игра окончена!");
  }
}