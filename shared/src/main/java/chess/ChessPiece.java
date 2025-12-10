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

    PieceType pieceType;
    ChessGame.TeamColor pieceColor;
    Integer pieceMoved;


    public ChessPiece(ChessGame.TeamColor pieceColorNew, ChessPiece.PieceType type) {
        pieceType = type;
        pieceColor = pieceColorNew;
        pieceMoved = 0;
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
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
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


        Set<ChessMove> returnMoves = new HashSet<ChessMove>();
        returnMoves.clear();

        ChessPiece pieceMoving = board.getPiece(myPosition);
//        if (pieceMoving == null)
//        {
//            return null;
//        }
        ChessGame.TeamColor pieceColor = pieceMoving.getTeamColor();
        PieceType pieceType = pieceMoving.getPieceType();
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        int offsetRow;
        int offsetCol;
        int tempRow;
        int tempCol;
        final int OFF_BOARD = 10;

        if (pieceType == PieceType.BISHOP)
        {
            // check up-right
            offsetRow = 1;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check up-left
            offsetRow = 1;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down-right
            offsetRow = -1;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down-left
            offsetRow = -1;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
        }
        else if (pieceType == PieceType.KING)
        {
            // check up-right
            offsetRow = 1;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check up-left
            offsetRow = 1;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down-right
            offsetRow = -1;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down-left
            offsetRow = -1;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check up
            offsetRow = 1;
            offsetCol = 0;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check left
            offsetRow = 0;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check right
            offsetRow = 0;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down
            offsetRow = -1;
            offsetCol = 0;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
        }
        else if (pieceType == PieceType.KNIGHT)
        {
            // check up-right
            offsetRow = 2;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check
            offsetRow = 1;
            offsetCol = 2;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check
            offsetRow = -1;
            offsetCol = 2;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check
            offsetRow = -2;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check
            offsetRow = -1;
            offsetCol = -2;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check
            offsetRow = -2;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check right
            offsetRow = 1;
            offsetCol = -2;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down
            offsetRow = 2;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
        }
        else if (pieceType == PieceType.PAWN)
        {
            if (pieceColor == ChessGame.TeamColor.WHITE)
            {
                offsetRow = 1;
            } else {
                offsetRow = -1;
            }

            // check right to kill
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    // Nothing
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    // If not at edge of board
                    if (tempRow >= 2 && tempRow <= 7) {
                        // Keep going buddy.
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    } else {
                        // PROMOTED!!!!!
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.ROOK));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.KNIGHT));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.BISHOP));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.QUEEN));
                    }
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check left to kill
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    // Nothing
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    // If not at edge of board
                    if (tempRow >= 2 && tempRow <= 7) {
                        // Keep going buddy.
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    } else {
                        // PROMOTED!!!!!
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.ROOK));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.KNIGHT));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.BISHOP));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.QUEEN));
                    }
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check forward
            offsetCol = 0;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 ) {
                if (board.getPiece(tempRow, tempCol) == null) {
                    // If not at edge of board
                    if (tempRow >= 2 && tempRow <= 7) {
                        // Keep going buddy.
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    } else {
                        // PROMOTED!!!!!
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.ROOK));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.KNIGHT));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.BISHOP));
                        returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), ChessPiece.PieceType.QUEEN));
                    }

                    // Check if on starting row
                    // If in starting place for White
                    if (pieceRow == 2 && pieceColor == ChessGame.TeamColor.WHITE) {
                        tempRow = pieceRow + offsetRow + offsetRow;
                        if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8) {
                            if (board.getPiece(tempRow, tempCol) == null) {
                                returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                            }
                        }
                    }

                    // Check if on starting row
                    // If in starting place for Black
                    if (pieceRow == 7 && pieceColor == ChessGame.TeamColor.BLACK) {
                        tempRow = pieceRow + offsetRow + offsetRow;
                        if (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8) {
                            if (board.getPiece(tempRow, tempCol) == null) {
                                returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                            }
                        }
                    }
                }
            }
        }
        if (pieceType == PieceType.QUEEN)
        {
            // check up-right
            offsetRow = 1;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check up-left
            offsetRow = 1;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down-right
            offsetRow = -1;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down-left
            offsetRow = -1;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check up
            offsetRow = 1;
            offsetCol = 0;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check left
            offsetRow = 0;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check right
            offsetRow = 0;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down
            offsetRow = -1;
            offsetCol = 0;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
        }
        else if (pieceType == PieceType.ROOK)
        {
// check up
            offsetRow = 1;
            offsetCol = 0;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check left
            offsetRow = 0;
            offsetCol = -1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check right
            offsetRow = 0;
            offsetCol = 1;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
            // check down
            offsetRow = -1;
            offsetCol = 0;
            tempRow = pieceRow + offsetRow;
            tempCol = pieceCol + offsetCol;
            while (tempRow >= 1 && tempRow <= 8 && tempCol >= 1 && tempCol <= 8 )
            {
                if (board.getPiece(tempRow, tempCol) == null)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    tempRow = tempRow + offsetRow;
                    tempCol = tempCol + offsetCol;
                } else if (board.getPiece(tempRow, tempCol).pieceColor != pieceColor)
                {
                    returnMoves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol), new ChessPosition(tempRow, tempCol), null));
                    // Escape the loop
                    tempRow = OFF_BOARD;
                } else {
                    // Escape the loop
                    tempRow = OFF_BOARD;
                }
            }
        }

        return returnMoves;

        // throw new RuntimeException("Not implemented");

    }

    public int getPieceMoved()
    {
        return pieceMoved;

    }

    public void tickPieceMoved()
    {
        pieceMoved = pieceMoved + 1;
        return;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessPiece that)) {
            return false;
        }
        return pieceType == that.pieceType && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, pieceColor);
    }
}
