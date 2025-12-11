package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor playerActive;
    ChessBoard playerBoard;

    ChessPosition playerInactiveLastMovePosition;

    public ChessGame() {
        playerActive = TeamColor.WHITE;
        playerBoard = new ChessBoard();
        playerBoard.resetBoard();

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return playerActive;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        playerActive = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK,
        NONE
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // If game is over, return null
        if (playerActive == null)
        {
            return null;
        }


        Set<ChessMove> movesOutput = new HashSet<ChessMove>();

        // If no piece there, return null
        if (playerBoard.getPiece(startPosition) == null)
        {
            return null;
        }


        ChessPiece pieceMoving = playerBoard.getPiece(startPosition);

        // Get the moves possible, without taking check into consideration
        Set<ChessMove> movesInput = (Set<ChessMove>) playerBoard.getPiece(startPosition).pieceMoves(playerBoard, startPosition);
        if(movesInput == null)
        {
            return null;
        }

        ChessBoard playerBoardImaginary = playerBoard.getBoard();

        // Check every move
        for (ChessMove move : movesInput)
        {
            // System.out.println("Checking if " + move.getStartPosition() + " can move to " + move.getEndPosition());

            // This imaginary board will be the board if it completes the specific move
            playerBoardImaginary = playerBoard.getBoard();
            //ChessBoard playerBoardImaginary = player_board;

            // Kill the previous piece and add the new piece in its place
            playerBoardImaginary.pieceRemove(move.getEndPosition());
            playerBoardImaginary.addPiece(move.getEndPosition(), playerBoardImaginary.getPiece(startPosition));

            // remove the piece from start position
            playerBoardImaginary.pieceRemove(startPosition);


            if (isInCheck(pieceMoving.getTeamColor(), playerBoardImaginary)) {
                // It is in check, so it won't be added
                // System.out.println("It is in check, so it won't be added");
            } else {
                // System.out.println("It is not in check, so it will be added");
                movesOutput.add(move);
            }


        }


        // Castling Test
        movesOutput.addAll(validMovesHelperCheckCastling(startPosition));

        // EnPassant Test
        // Check if piece moving is a Pawn
        // Check is last moved piece exists
        movesOutput.addAll(validMovesHelperCheckEnPassant(startPosition));


        return movesOutput;


    }

    private Collection<ChessMove> validMovesHelperCheckCastling(ChessPosition startPosition) {

        Set<ChessMove> movesOutput = new HashSet<ChessMove>();
        ChessBoard playerBoardImaginary = playerBoard.getBoard();

        ChessPiece pieceMoving = playerBoard.getPiece(startPosition);

        // Castling Test
        int pieceMovingRow = startPosition.getRow();
        int pieceMovingCol = startPosition.getColumn();

        // Check if King
        // Check if moved already

        if (pieceMoving.getPieceType() != ChessPiece.PieceType.KING)
        {
            return movesOutput;
        }

        if (pieceMoving.getPieceMoved() != 0)
        {
            return movesOutput;
        }

        // Check if King is in Check
        if (isInCheck(pieceMoving.getTeamColor()))
        {
            return movesOutput;
        }

        // Check if Rook is in position

        // Look left, on Queen's side
        if (playerBoard.getPiece(pieceMovingRow, 4) == null && playerBoard.getPiece(pieceMovingRow, 3) == null &&
                playerBoard.getPiece(pieceMovingRow, 2) == null && playerBoard.getPiece(pieceMovingRow, 1) != null)
        {

            if (playerBoard.getPiece(pieceMovingRow, 1).getPieceType() == ChessPiece.PieceType.ROOK &&
                    playerBoard.getPiece(pieceMovingRow, 1).getPieceMoved() == 0)
            {

                // This imaginary board will be the board if it completes the specific move
                playerBoardImaginary = playerBoard.getBoard();

                // Move the king left by 1
                playerBoardImaginary.addPiece(pieceMovingRow, 4, pieceMoving);
                playerBoardImaginary.pieceRemove(pieceMovingRow, 5);
                if (isInCheck(pieceMoving.getTeamColor(), playerBoardImaginary)) {
                    // It is in check, so it won't be added
                    System.out.println("It is in check, so it won't be added");
                } else {
                    // This imaginary board will be the board if it completes the specific move
                    playerBoardImaginary = playerBoard.getBoard();

                    // Move the king left by 2
                    playerBoardImaginary.addPiece(pieceMovingRow, 3, pieceMoving);
                    playerBoardImaginary.pieceRemove(pieceMovingRow, 5);
                    if (isInCheck(pieceMoving.getTeamColor(), playerBoardImaginary)) {
                        // It is in check, so it won't be added
                        System.out.println("It is in check, so it won't be added");
                    } else {
                        System.out.println("It is not in check, so it will be added");
                        movesOutput.add(new ChessMove(startPosition, new ChessPosition(pieceMovingRow, 3), null));
                    }
                }
            }
        }


        // Look right, on King's side
        if (playerBoard.getPiece(pieceMovingRow, 6) == null && playerBoard.getPiece(pieceMovingRow, 7) == null &&
                playerBoard.getPiece(pieceMovingRow, 8) != null)
        {

            if (playerBoard.getPiece(pieceMovingRow, 8).getPieceType() == ChessPiece.PieceType.ROOK &&
                    playerBoard.getPiece(pieceMovingRow, 8).getPieceMoved() == 0)
            {

                // This imaginary board will be the board if it completes the specific move
                playerBoardImaginary = playerBoard.getBoard();

                // Move the king right by 1
                playerBoardImaginary.addPiece(pieceMovingRow, 6, pieceMoving);
                playerBoardImaginary.pieceRemove(pieceMovingRow, 5);
                if (isInCheck(pieceMoving.getTeamColor(), playerBoardImaginary)) {
                    // It is in check, so it won't be added
                    System.out.println("It is in check, so it won't be added");
                } else {
                    // This imaginary board will be the board if it completes the specific move
                    playerBoardImaginary = playerBoard.getBoard();

                    // Move the king left by 2
                    playerBoardImaginary.addPiece(pieceMovingRow, 7, pieceMoving);
                    playerBoardImaginary.pieceRemove(pieceMovingRow, 5);
                    if (isInCheck(pieceMoving.getTeamColor(), playerBoardImaginary)) {
                        // It is in check, so it won't be added
                        System.out.println("It is in check, so it won't be added");
                    } else {
                        System.out.println("It is not in check, so it will be added");
                        movesOutput.add(new ChessMove(startPosition, new ChessPosition(pieceMovingRow, 7), null));
                    }

                }

            }
        }




        return movesOutput;
    }

    private Collection<ChessMove> validMovesHelperCheckEnPassant(ChessPosition startPosition) {

        Set<ChessMove> movesOutput = new HashSet<ChessMove>();

        int pieceMovingRow = startPosition.getRow();
        int pieceMovingCol = startPosition.getColumn();

        ChessPiece pieceMoving = playerBoard.getPiece(startPosition);

        // EnPassant Test
        // Check if piece moving is a Pawn
        // Check is last moved piece exists
        if(pieceMoving.getPieceType() == ChessPiece.PieceType.PAWN && playerInactiveLastMovePosition != null)
        {
            //System.out.println("EnPassant Test 1 Passed");


            //System.out.println("EnPassant Test 2 Passed");
            // Check is last moved piece exists
            if (playerBoard.getPiece(playerInactiveLastMovePosition) == null)
            {
                return movesOutput;
            }

            //System.out.println("EnPassant Test 3 Passed");
            //System.out.println("EnPassant Test 4 Passed");
            // Check if inactive pawn only moved one time
            if (playerBoard.getPiece(playerInactiveLastMovePosition).getPieceMoved() != 1)
            {
                return movesOutput;
            }

            //System.out.println("EnPassant Test 5 Passed");
            // Check to see if they are now on the same row (required for EnPassant
            if (playerInactiveLastMovePosition.getRow() != pieceMovingRow)
            {
                return movesOutput;
            }


            //System.out.println("EnPassant Test 6 Passed");
            // If one row over from the other pawn
            if (pieceMovingCol == playerInactiveLastMovePosition.getColumn() + 1 || pieceMovingCol ==
                    playerInactiveLastMovePosition.getColumn() - 1)
            {
                //System.out.println("EnPassant Test 7 Passed");
                // Because I have only moved the enemy pawn once, if it is on row 4 or 5, it must have jumped up
                if (pieceMovingRow == 4)
                {
                    //System.out.println("EnPassant Test 8 Passed");
                    movesOutput.add(new ChessMove(startPosition, new ChessPosition(3,
                            playerInactiveLastMovePosition.getColumn()), null));
                } else if (pieceMovingRow == 5)
                {
                    //System.out.println("EnPassant Test 9 Passed");
                    movesOutput.add(new ChessMove(startPosition, new ChessPosition(6,
                            playerInactiveLastMovePosition.getColumn()), null));
                }
            }





        }
        return movesOutput;
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        System.out.println("In ChessGame.makeMove()");

        // If game is over, return null
        if (playerActive == null || playerActive == TeamColor.NONE)
        {
            System.out.println("Game is already over.");
            throw new InvalidMoveException("Game is already over.");
        }



        ChessPosition positionStart = move.getStartPosition();
        ChessPosition positionEnd = move.getEndPosition();
        Set<ChessMove> positionsPossible = (Set<ChessMove>) validMoves(positionStart);

        if (playerBoard.getPiece(positionStart) == null)
        {
            System.out.println("No Piece there");
            throw new InvalidMoveException("No Piece there");
        }

        System.out.println("Player active is " + playerActive);
        System.out.println("Player of piece there is " + playerBoard.getPiece(move.getStartPosition()).getTeamColor());


        if(playerBoard.getPiece(move.getStartPosition()).getTeamColor() != playerActive)
        {
            System.out.println("Wrong Turn!!!");
            throw new InvalidMoveException("Wrong Turn!!!");
        }


        //System.out.println("Checking if " + startPosition + " can move to " + end_position);
        // Check every move
        if(positionsPossible == null)
        {
            // The list is empty
            throw new InvalidMoveException("Can't move to " + move.getEndPosition());
        }
        for (ChessMove moveChecking : positionsPossible)
        {
            //System.out.println("Checking move: " + moveChecking);
            // See if move is the same
            if (moveChecking.equals(move)) {
                // If EnPassant, kill the Enemy Pawn
                // This has to be done first, so I can check to see if a pawn moved diagonal into a blank space

                // Only EnPassant if Pawn
                // Check if Pawn moved diagonal
                // If the pawn moved diagonal without killing anything
                if(playerBoard.getPiece(positionStart).getPieceType() == ChessPiece.PieceType.PAWN &&
                        (positionEnd.getColumn() == positionStart.getColumn() + 1 || positionEnd.getColumn() == positionStart.getColumn() - 1 ) &&
                        playerBoard.getPiece(positionEnd) == null)
                {


                    // Kill the pawn!!!
                    // The dead pawn will be on the starting row of the moving pawn and the ending column of the same pawn
                    playerBoard.pieceRemove(positionStart.getRow(), positionEnd.getColumn());
                }



                // Kill the previous piece and add the new piece in its place
                playerBoard.pieceRemove(positionEnd);

                if(move.getPromotionPiece() == null) {
                    playerBoard.addPiece(positionEnd, playerBoard.getPiece(positionStart));
                } else {
                    playerBoard.addPiece(positionEnd, new ChessPiece(playerActive, move.getPromotionPiece()));
                }


                // remove the piece from start position
                playerBoard.pieceRemove(positionStart);

                // Mark the moved piece as having moved
                playerBoard.getPiece(positionEnd).tickPieceMoved();

                // If Castling, move the Rook as well
                // Check if King
                // If King in starting location
                if(playerBoard.getPiece(positionEnd).getPieceType() == ChessPiece.PieceType.KING && positionStart.getColumn() == 5)
                {


                    // If castling left
                    if(positionEnd.getColumn() == 3)
                    {
                        // Remove the Rook and add the Rook to the new place
                        playerBoard.pieceRemove(positionEnd.getRow(),1);
                        playerBoard.addPiece(positionEnd.getRow(), 4, new ChessPiece(playerBoard.getPiece(positionEnd).getTeamColor(),
                                ChessPiece.PieceType.ROOK));

                        // Mark the moved piece as having moved
                        playerBoard.getPiece(positionEnd.getRow(), 4).tickPieceMoved();
                    }
                    // If castling right
                    if(positionEnd.getColumn() == 7)
                    {
                        // Remove the Rook and add the Rook to the new place
                        playerBoard.pieceRemove(positionEnd.getRow(),8);
                        playerBoard.addPiece(positionEnd.getRow(), 6, new ChessPiece(playerBoard.getPiece(positionEnd).getTeamColor(),
                                ChessPiece.PieceType.ROOK));

                        // Mark the moved piece as having moved
                        playerBoard.getPiece(positionEnd.getRow(), 6).tickPieceMoved();
                    }

                }





                // piece is moved, so we're done here
                System.out.println("Piece has been moved");

                playerInactiveLastMovePosition = positionEnd;

                // Change to new color turn
                if (playerActive == TeamColor.WHITE) {
                    playerActive = TeamColor.BLACK;
                } else if (playerActive == TeamColor.BLACK) {
                    playerActive = TeamColor.WHITE;
                }

                System.out.println(playerBoard);
                return;
            }
        }
        System.out.println("Invalid move, did not find it on the list");
        // It wasn't there, throw exception
        throw new InvalidMoveException("Can't move to " + move.getEndPosition());

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor, playerBoard);
    }

    /**
     * Determines if the given team is in check given any board
     * Needed for validMoves() to work correctly, so I can check any board
     *
     * @param teamColor which team to check for check
     * @param playerBoardChecking which board to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor, ChessBoard playerBoardChecking) {
        // check every row
        for(int r=1; r<=8; r++) {
            for (int c = 1; c <= 8; c++) {
                // Check if piece there
                if (playerBoardChecking.getPiece(r, c) != null)
                {
                    // Check to see if it is the king of the correct color
                    ChessPiece pieceChecking = playerBoardChecking.getPiece(r,c);

                    //System.out.println("Found the KING @" + pieceCheckingPosition);

                    if (pieceChecking.getTeamColor() == teamColor && pieceChecking.getPieceType() == ChessPiece.PieceType.KING)
                    {
                        // Check every spot on the board to see if it can kill the king
                        return isInChecKHelperCanKillKing(playerBoardChecking, r, c);
                    }
                }

            }
        }
        return false;
    }


    private boolean isInChecKHelperCanKillKing(ChessBoard playerBoardChecking, int r, int c) {
        ChessPiece pieceChecking = playerBoardChecking.getPiece(r,c);

        // Check every spot on the board to see if it can kill the king
        for(int rr=1; rr<=8; rr++)
        {
            for(int cc=1; cc<=8; cc++)
            {
                if (playerBoardChecking.getPiece(rr,cc) == null)
                {
                    continue;
                }

                // Check to see if it is the opposite color
                ChessPiece pieceOther = playerBoardChecking.getPiece(rr,cc);
                ChessPosition pieceOtherPosition = new ChessPosition(rr,cc);
                //System.out.println("Checking Piece @" + pieceOtherPosition);
                // Check to see if they are not teammates
                if (pieceOther.getTeamColor() == pieceChecking.getTeamColor())
                {
                    continue;
                }

                //System.out.println("Different Team!!!");
                Set<ChessMove> pieceOtherMoves = new HashSet<ChessMove>();
                pieceOtherMoves = (Set<ChessMove>) pieceOther.pieceMoves(playerBoardChecking, pieceOtherPosition);

                //System.out.println("Found moves");
                //System.out.println("King @ " + pieceCheckingPosition);
                if(pieceOtherMoves == null)
                {
                    continue;
                }

                for (ChessMove move : pieceOtherMoves)
                {
                    //System.out.println(m.getEndPosition());
                    if(move.getEndPosition().equals(new ChessPosition(r,c)))
                    {
                        return true;
                    }
                }



            }
        }
        return false;
    }





    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // Check if it is in check
        if (!isInCheck(teamColor))
        {
            return false;
        }
        // Check if any moves break you out of check
        for(int r=1; r<=8; r++)
        {
            for(int c=1; c<=8; c++)
            {
                if(playerBoard.getPiece(r,c) != null)
                {
                    if(playerBoard.getPiece(r,c).getTeamColor() == teamColor &&
                            validMoves(new ChessPosition(r,c)) != null &&
                            !validMoves(new ChessPosition(r, c)).isEmpty())
                    {
                        return false;
                    }
                }
            }
        }




        // Couldn't find any escapes
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor))
        {
            return false;
        }
        for(int r=1; r<=8; r++)
        {
            for(int c=1; c<=8; c++)
            {
                if(playerBoard.getPiece(r,c) != null) {
                    if(playerBoard.getPiece(r,c).getTeamColor() == teamColor &&
                            validMoves(new ChessPosition(r,c)) != null && !validMoves(new ChessPosition(r, c)).isEmpty())
                    {
                        // Escape found!!!
                        return false;
                    }
                }
            }
        }
        // Couldn't find any escapes
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        playerBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return playerBoard;
    }

    /**
     * Mark the game as over by marking the Active player as null
     *
     * @return nothing
     */
    public void gameOver()
    {
        playerActive = TeamColor.NONE;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "playerBoard=" + playerBoard +
                ", playerActive=" + playerActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessGame chessGame)) {
            return false;
        }
        return playerActive == chessGame.playerActive && Objects.equals(playerBoard, chessGame.playerBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerActive, playerBoard);
    }
}
