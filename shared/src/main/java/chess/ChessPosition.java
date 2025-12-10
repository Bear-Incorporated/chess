package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    int position_row;
    int position_column;

    public ChessPosition(int row, int col) {
        position_row = row;
        position_column = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return position_row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return position_column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessPosition that)) {
            return false;
        }
        return position_row == that.position_row && position_column == that.position_column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position_row, position_column);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "position_row=" + position_row +
                ", position_column=" + position_column +
                '}';
    }

    public String toStringShort() {
        Character columnChar;
        if (position_column == 1)
        {
            columnChar = 'A';
        } else if (position_column == 2)
        {
            columnChar = 'B';
        } else if (position_column == 3)
        {
            columnChar = 'C';
        } else if (position_column == 4)
        {
            columnChar = 'D';
        } else if (position_column == 5)
        {
            columnChar = 'E';
        } else if (position_column == 6)
        {
            columnChar = 'F';
        } else if (position_column == 7)
        {
            columnChar = 'G';
        } else if (position_column == 8)
        {
            columnChar = 'H';
        } else
        {
            columnChar = '?';
        }

        return columnChar.toString() + Integer.toString(position_row);
    }
}
