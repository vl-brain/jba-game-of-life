package life;

import java.util.Arrays;
import java.util.Random;

public class Game
{
	private final FieldState[][] fields;

	private Game(FieldState[][] fields)
	{
		this.fields = fields;
	}

	public static Game generate(int size, int seed)
	{
		final Random random = new Random(seed);
		final FieldState[][] fields = new FieldState[size][];
		for (int i = 0; i < size; i++)
		{
			final FieldState[] row = new FieldState[size];
			Arrays.setAll(row, j -> random.nextBoolean() ? FieldState.ALIVE : FieldState.DEAD);
			fields[i] = row;
		}
		return new Game(fields);
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder(fields.length * (fields.length + 1));
		for (FieldState[] row : fields)
		{
			for (FieldState field : row)
			{
				builder.append(field.getSymbol());
			}
			builder.append('\n');
		}
		builder.setLength(Math.max(builder.length() - 1, 0));
		return builder.toString();
	}
}
