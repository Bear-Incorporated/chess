package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    int positionRow;
    int positionColumn;

    public ChessPosition(int row, int col) {
        positionRow = row;
        positionColumn = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return positionRow;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return positionColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessPosition that)) {
            return false;
        }
        return positionRow == that.positionRow && positionColumn == that.positionColumn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionRow, positionColumn);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "positionRow=" + positionRow +
                ", positionColumn=" + positionColumn +
                '}';
    }

    public String toStringShort() {
        Character columnChar;
        if (positionColumn == 1)
        {
            columnChar = 'A';
        } else if (positionColumn == 2)
        {
            columnChar = 'B';
        } else if (positionColumn == 3)
        {
            columnChar = 'C';
        } else if (positionColumn == 4)
        {
            columnChar = 'D';
        } else if (positionColumn == 5)
        {
            columnChar = 'E';
        } else if (positionColumn == 6)
        {
            columnChar = 'F';
        } else if (positionColumn == 7)
        {
            columnChar = 'G';
        } else if (positionColumn == 8)
        {
            columnChar = 'H';
        } else
        {
            columnChar = '?';
        }

        return columnChar.toString() + Integer.toString(positionRow);
    }
}
