package life;

public enum FieldState {
    ALIVE('O'),
    DEAD(' ');
    private final char symbol;

    FieldState(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
