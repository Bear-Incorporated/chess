package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
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


//        if (board == null)
//            return null;
//        if (board.getPiece(myPosition) == null) {
//            return null;
//        }


        Set<ChessMove> return_moves = new HashSet<ChessMove>();
        return_moves.clear();

        ChessPiece piece_moving = board.getPiece(myPosition);
//        if (piece_moving == null)
//        {
//            return null;
//        }
        ChessGame.TeamColor piece_color = piece_moving.getTeamColor();
        PieceType piece_type = piece_moving.getPieceType();
        int piece_row = myPosition.getRow();
        int piece_col = myPosition.getColumn();
        int offset_row;
        int offset_col;
        int temp_row;
        int temp_col;
        final int OFF_BOARD = 10;

        if (piece_type == PieceType.BISHOP)
        {
            // check up-right
            offset_row = 1;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check up-left
            offset_row = 1;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down-right
            offset_row = -1;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down-left
            offset_row = -1;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
        }
        else if (piece_type == PieceType.KING)
        {
            // check up-right
            offset_row = 1;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check up-left
            offset_row = 1;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down-right
            offset_row = -1;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down-left
            offset_row = -1;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check up
            offset_row = 1;
            offset_col = 0;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check left
            offset_row = 0;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check right
            offset_row = 0;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down
            offset_row = -1;
            offset_col = 0;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
        }
        else if (piece_type == PieceType.KNIGHT)
        {
            // check up-right
            offset_row = 2;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check
            offset_row = 1;
            offset_col = 2;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check
            offset_row = -1;
            offset_col = 2;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check
            offset_row = -2;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check
            offset_row = -1;
            offset_col = -2;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check
            offset_row = -2;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check right
            offset_row = 1;
            offset_col = -2;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down
            offset_row = 2;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
        }
        else if (piece_type == PieceType.PAWN)
        {
            if (piece_color == ChessGame.TeamColor.WHITE)
            {
                offset_row = 1;
            } else {
                offset_row = -1;
            }

            // check right to kill
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    // Nothing
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    // If not at edge of board
                    if (temp_row >= 2 && temp_row <= 7) {
                        // Keep going buddy.
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    } else {
                        // PROMOTED!!!!!
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.ROOK));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.KNIGHT));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.BISHOP));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.QUEEN));
                    }
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check left to kill
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    // Nothing
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    // If not at edge of board
                    if (temp_row >= 2 && temp_row <= 7) {
                        // Keep going buddy.
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    } else {
                        // PROMOTED!!!!!
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.ROOK));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.KNIGHT));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.BISHOP));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.QUEEN));
                    }
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check forward
            offset_col = 0;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 ) {
                if (board.getPiece(temp_row, temp_col) == null) {
                    // If not at edge of board
                    if (temp_row >= 2 && temp_row <= 7) {
                        // Keep going buddy.
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    } else {
                        // PROMOTED!!!!!
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.ROOK));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.KNIGHT));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.BISHOP));
                        return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), ChessPiece.PieceType.QUEEN));
                    }

                    // Check if on starting row
                    // If in starting place for White
                    if (piece_row == 2 && piece_color == ChessGame.TeamColor.WHITE) {
                        temp_row = piece_row + offset_row + offset_row;
                        if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8) {
                            if (board.getPiece(temp_row, temp_col) == null) {
                                return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                            }
                        }
                    }

                    // Check if on starting row
                    // If in starting place for Black
                    if (piece_row == 7 && piece_color == ChessGame.TeamColor.BLACK) {
                        temp_row = piece_row + offset_row + offset_row;
                        if (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8) {
                            if (board.getPiece(temp_row, temp_col) == null) {
                                return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                            }
                        }
                    }
                }
            }
        }
        if (piece_type == PieceType.QUEEN)
        {
            // check up-right
            offset_row = 1;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check up-left
            offset_row = 1;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down-right
            offset_row = -1;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down-left
            offset_row = -1;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check up
            offset_row = 1;
            offset_col = 0;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check left
            offset_row = 0;
            offset_col = -1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check right
            offset_row = 0;
            offset_col = 1;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
            // check down
            offset_row = -1;
            offset_col = 0;
            temp_row = piece_row + offset_row;
            temp_col = piece_col + offset_col;
            while (temp_row >= 1 && temp_row <= 8 && temp_col >= 1 && temp_col <= 8 )
            {
                if (board.getPiece(temp_row,temp_col) == null)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    temp_row = temp_row + offset_row;
                    temp_col = temp_col + offset_col;
                } else if (board.getPiece(temp_row,temp_col).piece_color != piece_color)
                {
                    return_moves.add(new ChessMove(new ChessPosition(piece_row, piece_col), new ChessPosition(temp_row, temp_col), null));
                    // Escape the loop
                    temp_row = OFF_BOARD;
                } else {
                    // Escape the loop
                    temp_row = OFF_BOARD;
                }
            }
        }
        else if (piece_type == PieceType.ROOK)
        {

        }



















        return return_moves;

        // throw new RuntimeException("Not implemented");

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessPiece that)) {
            return false;
        }
        return piece_type == that.piece_type && piece_color == that.piece_color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece_type, piece_color);
    }
}
