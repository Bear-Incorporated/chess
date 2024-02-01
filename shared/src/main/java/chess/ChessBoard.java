package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    ChessPiece[][] chess_board;
    public ChessBoard() {
        chess_board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (position.getRow()<1 || position.getRow() >8 || position.getColumn() <1 || position.getColumn() >8)
            return;
        if(chess_board[position.getRow()-1][position.getColumn()-1] == null)
        {

            chess_board[position.getRow()-1][position.getColumn()-1] = piece;

        }
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (position.getRow()<1 || position.getRow() >8 || position.getColumn() <1 || position.getColumn() >8)
            return null;
        if(chess_board[position.getRow()-1][position.getColumn()-1] == null)
        {
            return null;
        }
        return chess_board[position.getRow()-1][position.getColumn()-1];
        //throw new RuntimeException("Not implemented");
    }

    //Same as previous question, but using ints for the row and column, for easier use with functions.
    public ChessPiece getPiece(int row, int col) {
        ChessPosition position_temp = new ChessPosition(row, col);
        return getPiece(position_temp);
    }




    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.equals(chess_board, that.chess_board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(chess_board);
    }
}
