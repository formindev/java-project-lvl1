package games;

import java.io.IOException;

public class Choice {
  public static void main(final String... args) throws IOException {
    System.out.println("Выберите игру:\n1 - \"однорукий бандит\", 2 - \"пьяница\"");
    switch (System.in.read()) {
      case '1': Slot.main();
              break;
      case '2': Drunkard.main();
              break;
      default: System.out.println("Нет игры с таким номером!");
    }
  }
}
