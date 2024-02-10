package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    ChessPosition position_start;
    ChessPosition position_end;
    ChessPiece.PieceType promotion_piece;
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        position_start = startPosition;
        position_end = endPosition;
        promotion_piece = promotionPiece;
    }

    // new version of add chess move, which allows me to create using ints instead of positions
    public ChessMove(int start_row, int start_col, int end_row, int end_col,
                     ChessPiece.PieceType promotionPiece) {
        position_start = new ChessPosition(start_row, start_col);
        position_end = new ChessPosition(end_row, end_col);
        promotion_piece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return position_start;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return position_end;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotion_piece;
    }


    @Override
    public String toString() {
        return "Move{" +
                "from " + position_start +
                "to " + position_end +
                ", p=" + promotion_piece +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMove chessMove)) return false;
        return Objects.equals(position_start, chessMove.position_start) && Objects.equals(position_end, chessMove.position_end) && promotion_piece == chessMove.promotion_piece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position_start, position_end, promotion_piece);
    }


}
