package life;

public class Model {
    private final Universe universe;
    private final int generation;

    public Model(Universe universe) {
        this(universe, 1);
    }

    public Model(Universe universe, int generation) {
        this.universe = universe;
        this.generation = generation;
    }

    public Model nextGeneration() {
        return new Model(universe.nextGeneration(), generation + 1);
    }

    public Universe getUniverse() {
        return universe;
    }

    public int getGeneration() {
        return generation;
    }

    @Override
    public String toString() {
        return "Generation #" + generation +
                "\nAlive: " + universe.getAliveCount() +
                "\n\n" + universe;
    }
}
