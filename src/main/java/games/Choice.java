package games;

import java.io.IOException;

public class Choice {
  private static final String LINE_SEPARATOR = System.lineSeparator();

  public static void main(final String... args) throws IOException {
    System.out.println("Выберите игру:\n1 - \"однорукий бандит\", 2 - \"пьяница\", 3 - \"очко\"");
    switch (getCharacterFromUser()) {
      case '1': Slot.main();
              break;
      case '2': Drunkard.main();
              break;
      case '3': BlackJack.main();
              break;
      default: System.out.println("Нет игры с таким номером!");
    }
  }

  private static char getCharacterFromUser() throws IOException {
    byte[] input = new byte[1 + getSeparatorLength()];
    if (System.in.read(input) != input.length)
      throw new RuntimeException("Пользователь ввёл недостаточное кол-во символов");
    return (char) input[0];
  }

  private static int getSeparatorLength() {
    final String osName = System.getProperty("os.name");
    if (osName.toUpperCase().contains("WIN"))
      return LINE_SEPARATOR.length() - 1;
    else
      return LINE_SEPARATOR.length();
  }
}
