package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    ChessPosition positionStart;
    ChessPosition positionEnd;
    ChessPiece.PieceType promoted;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        positionStart = startPosition;
        positionEnd = endPosition;
        promoted = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return positionStart;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return positionEnd;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessMove move)) {
            return false;
        }
        return Objects.equals(positionStart, move.positionStart) && Objects.equals(positionEnd, move.positionEnd) && promoted == move.promoted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionStart, positionEnd, promoted);
    }

    @Override
    public String toString() {
        return "ChessMove{" +
                "positionStart=" + positionStart +
                ", positionEnd=" + positionEnd +
                ", promoted=" + promoted +
                '}';
    }
}
