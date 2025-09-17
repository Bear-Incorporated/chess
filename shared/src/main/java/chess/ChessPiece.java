package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    PieceType piece_type;
    ChessGame.TeamColor piece_color;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        piece_type = type;
        piece_color = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return piece_color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return piece_type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // throw new RuntimeException("Not implemented");

        if (board == null)
            return null;
        if (board.getPiece(myPosition) == null) {
            return null;
        }


        Set<ChessMove> return_moves = new HashSet<ChessMove>();
        return_moves.clear();

        ChessPiece piece_moving = board.getPiece(myPosition);
        ChessGame.TeamColor piece_color = piece_moving.getTeamColor();



























    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
