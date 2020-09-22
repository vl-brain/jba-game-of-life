package life;

public class Main {
    public static void main(String[] args) {
        final Model model = new Model(Universe.generate(20));
        final GameOfLife view = new GameOfLife();
        new Controller(model, view);
    }
}
