package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private int ro;
    private int co;
    public ChessPosition(int row, int col) {
        if (row<1 || row >8 || col <1 || col >8)
            throw new RuntimeException("Not on the Board");
        ro = row;
        co = col;

    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return ro;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left column
     */
    public int getColumn() {

        return co;
        //throw new RuntimeException("Not implemented");
    }


    @Override
    public String toString() {
        return "{" +
                "c" + co +
                " r" + ro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPosition that)) return false;
        return ro == that.ro && co == that.co;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ro, co);
    }

}
