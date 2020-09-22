package life;

import java.util.Arrays;
import java.util.Random;

public class Universe {
    private FieldState[][] fields;

    private Universe(FieldState[][] fields) {
        this.fields = fields;
    }

    public static Universe generate(int size) {
        final Random random = new Random();
        final FieldState[][] fields = new FieldState[size][];
        for (int i = 0; i < size; i++) {
            final FieldState[] row = new FieldState[size];
            Arrays.setAll(row, j -> random.nextBoolean() ? FieldState.ALIVE : FieldState.DEAD);
            fields[i] = row;
        }
        return new Universe(fields);
    }

    public Universe nextGeneration() {
        FieldState[][] nextGenerationFields = new FieldState[fields.length][fields[0].length];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                nextGenerationFields[i][j] = getNextState(i, j);
            }
        }
        return new Universe(nextGenerationFields);
    }

    private FieldState getNextState(int row, int column) {
        int aliveCount = 0;
        final int rowsCount = fields.length;
        final int columnCount = fields[0].length;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i == row && j == column) {
                    continue;
                }
                final FieldState field = fields[(i + rowsCount) % rowsCount][(j + columnCount) % columnCount];
                if (field == FieldState.ALIVE && ++aliveCount > 3) {
                    return FieldState.DEAD;
                }
            }
        }
        final int threshold = fields[row][column] == FieldState.ALIVE ? 2 : 3;
        return aliveCount < threshold ? FieldState.DEAD : FieldState.ALIVE;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(fields.length * (fields.length + 1));
        for (FieldState[] row : fields) {
            for (FieldState field : row) {
                builder.append(field.getSymbol());
            }
            builder.append('\n');
        }
        builder.setLength(Math.max(builder.length() - 1, 0));
        return builder.toString();
    }

    public int getAliveCount() {
        int aliveCount = 0;
        for (FieldState[] row : fields) {
            for (FieldState field : row) {
                if (field == FieldState.ALIVE) {
                    aliveCount++;
                }
            }
        }
        return aliveCount;
    }

    public int getSize() {
        return fields.length;
    }

    public FieldState[][] getFields() {
        return fields;
    }
}
