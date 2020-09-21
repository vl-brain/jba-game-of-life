package life;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Game game = Game.generate(scanner.nextInt(), scanner.nextInt());
        System.out.println(game);
    }
}
