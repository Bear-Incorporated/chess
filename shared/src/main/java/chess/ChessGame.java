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

    ChessPosition player_inactive_last_move_position;

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
            return null;

        Set<ChessMove> moves_output = new HashSet<ChessMove>();

        // If no piece there, return null
        if (playerBoard.getPiece(startPosition) == null)
            return null;

        ChessPiece piece_moving = playerBoard.getPiece(startPosition);

        // Get the moves possible, without taking check into consideration
        Set<ChessMove> moves_input = (Set<ChessMove>) playerBoard.getPiece(startPosition).pieceMoves(playerBoard, startPosition);
        if(moves_input == null)
        {
            return null;
        }

        ChessBoard player_board_imaginary = playerBoard.getBoard();

        // Check every move
        for (ChessMove move : moves_input)
        {
            // System.out.println("Checking if " + move.getStartPosition() + " can move to " + move.getEndPosition());

            // This imaginary board will be the board if it completes the specific move
            player_board_imaginary = playerBoard.getBoard();
            //ChessBoard player_board_imaginary = player_board;

            // Kill the previous piece and add the new piece in its place
            player_board_imaginary.pieceRemove(move.getEndPosition());
            player_board_imaginary.addPiece(move.getEndPosition(), player_board_imaginary.getPiece(startPosition));

            // remove the piece from start position
            player_board_imaginary.pieceRemove(startPosition);


            if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                // It is in check, so it won't be added
                // System.out.println("It is in check, so it won't be added");
            } else {
                // System.out.println("It is not in check, so it will be added");
                moves_output.add(move);
            }


        }


        // Castling Test
        int piece_moving_row = startPosition.getRow();
        int piece_moving_col = startPosition.getColumn();

        // Check if King
        if (piece_moving.getPieceType() == ChessPiece.PieceType.KING)
        {
            // Check if moved already
            if (piece_moving.get_piece_moved() == 0)
            {
                // Check if King is in Check
                if (isInCheck(piece_moving.getTeamColor()) == false)
                {


                    // Check if Rook is in position

                    // Look left, on Queen's side
                    if (playerBoard.getPiece(piece_moving_row, 4) == null)
                    {
                        if (playerBoard.getPiece(piece_moving_row, 3) == null)
                        {
                            if (playerBoard.getPiece(piece_moving_row, 2) == null)
                            {
                                if (playerBoard.getPiece(piece_moving_row, 1) != null)
                                {
                                    if (playerBoard.getPiece(piece_moving_row, 1).getPieceType() == ChessPiece.PieceType.ROOK)
                                    {
                                        if (playerBoard.getPiece(piece_moving_row, 1).get_piece_moved() == 0)
                                        {
                                            // This imaginary board will be the board if it completes the specific move
                                            player_board_imaginary = playerBoard.getBoard();

                                            // Move the king left by 1
                                            player_board_imaginary.addPiece(piece_moving_row, 4, piece_moving);
                                            player_board_imaginary.pieceRemove(piece_moving_row, 5);
                                            if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                                                // It is in check, so it won't be added
                                                System.out.println("It is in check, so it won't be added");
                                            } else {
                                                // This imaginary board will be the board if it completes the specific move
                                                player_board_imaginary = playerBoard.getBoard();

                                                // Move the king left by 2
                                                player_board_imaginary.addPiece(piece_moving_row, 3, piece_moving);
                                                player_board_imaginary.pieceRemove(piece_moving_row, 5);
                                                if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                                                    // It is in check, so it won't be added
                                                    System.out.println("It is in check, so it won't be added");
                                                } else {
                                                    System.out.println("It is not in check, so it will be added");
                                                    moves_output.add(new ChessMove(startPosition, new ChessPosition(piece_moving_row, 3), null));
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Look right, on King's side
                    if (playerBoard.getPiece(piece_moving_row, 6) == null)
                    {
                        if (playerBoard.getPiece(piece_moving_row, 7) == null)
                        {
                            if (playerBoard.getPiece(piece_moving_row, 8) != null)
                            {
                                if (playerBoard.getPiece(piece_moving_row, 8).getPieceType() == ChessPiece.PieceType.ROOK)
                                {
                                    if (playerBoard.getPiece(piece_moving_row, 8).get_piece_moved() == 0)
                                    {
                                        // This imaginary board will be the board if it completes the specific move
                                        player_board_imaginary = playerBoard.getBoard();

                                        // Move the king right by 1
                                        player_board_imaginary.addPiece(piece_moving_row, 6, piece_moving);
                                        player_board_imaginary.pieceRemove(piece_moving_row, 5);
                                        if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                                            // It is in check, so it won't be added
                                            System.out.println("It is in check, so it won't be added");
                                        } else {
                                            // This imaginary board will be the board if it completes the specific move
                                            player_board_imaginary = playerBoard.getBoard();

                                            // Move the king left by 2
                                            player_board_imaginary.addPiece(piece_moving_row, 7, piece_moving);
                                            player_board_imaginary.pieceRemove(piece_moving_row, 5);
                                            if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                                                // It is in check, so it won't be added
                                                System.out.println("It is in check, so it won't be added");
                                            } else {
                                                System.out.println("It is not in check, so it will be added");
                                                moves_output.add(new ChessMove(startPosition, new ChessPosition(piece_moving_row, 7), null));
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }

        // EnPassant Test
        // Check if piece moving is a Pawn
        if(piece_moving.getPieceType() ==  ChessPiece.PieceType.PAWN)
        {
            //System.out.println("EnPassant Test 1 Passed");
            // Check is last moved piece exists
            if (player_inactive_last_move_position != null)
            {
                //System.out.println("EnPassant Test 2 Passed");
                // Check is last moved piece exists
                if (playerBoard.getPiece(player_inactive_last_move_position) != null)
                {
                    //System.out.println("EnPassant Test 3 Passed");
                    if (playerBoard.getPiece(player_inactive_last_move_position).get_piece_moved() == 1)
                    {
                        //System.out.println("EnPassant Test 4 Passed");
                        // Check if inactive pawn only moved one time
                        if (playerBoard.getPiece(player_inactive_last_move_position).get_piece_moved() == 1)
                        {
                            //System.out.println("EnPassant Test 5 Passed");
                            // Check to see if they are now on the same row (required for EnPassant
                            if (player_inactive_last_move_position.getRow() == piece_moving_row)
                            {
                                //System.out.println("EnPassant Test 6 Passed");
                                // If one row over from the other pawn
                                if (piece_moving_col == player_inactive_last_move_position.getColumn() + 1 || piece_moving_col == player_inactive_last_move_position.getColumn() - 1)
                                {
                                    //System.out.println("EnPassant Test 7 Passed");
                                    // Because I have only moved the enemy pawn once, if it is on row 4 or 5, it must have jumped up
                                    if (piece_moving_row == 4)
                                    {
                                        //System.out.println("EnPassant Test 8 Passed");
                                        moves_output.add(new ChessMove(startPosition, new ChessPosition(3, player_inactive_last_move_position.getColumn()), null));
                                    } else if (piece_moving_row == 5)
                                    {
                                        //System.out.println("EnPassant Test 9 Passed");
                                        moves_output.add(new ChessMove(startPosition, new ChessPosition(6, player_inactive_last_move_position.getColumn()), null));
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }


        return moves_output;


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
        for (ChessMove move_checking : positionsPossible)
        {
            //System.out.println("Checking move: " + move_checking);
            // See if move is the same
            if (move_checking.equals(move)) {
                // If EnPassant, kill the Enemy Pawn
                // This has to be done first, so I can check to see if a pawn moved diagonal into a blank space

                // Only EnPassant if Pawn
                if(playerBoard.getPiece(positionStart).getPieceType() == ChessPiece.PieceType.PAWN)
                {
                    // Check if Pawn moved diagonal
                    if (positionEnd.getColumn() == positionStart.getColumn() + 1 || positionEnd.getColumn() == positionStart.getColumn() - 1 )
                    {
                        // If the pawn moved diagonal without killing anything
                        if (playerBoard.getPiece(positionEnd) == null)
                        {
                            // Kill the pawn!!!
                            // The dead pawn will be on the starting row of the moving pawn and the ending column of the same pawn
                            playerBoard.pieceRemove(positionStart.getRow(), positionEnd.getColumn());
                        }
                    }
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
                playerBoard.getPiece(positionEnd).tick_piece_moved();

                // If Castling, move the Rook as well
                // Check if King
                if(playerBoard.getPiece(positionEnd).getPieceType() == ChessPiece.PieceType.KING)
                {
                    // If King in starting location
                    if (positionStart.getColumn() == 5)
                    {
                        // If castling left
                        if(positionEnd.getColumn() == 3)
                        {
                            // Remove the Rook and add the Rook to the new place
                            playerBoard.pieceRemove(positionEnd.getRow(),1);
                            playerBoard.addPiece(positionEnd.getRow(), 4, new ChessPiece(playerBoard.getPiece(positionEnd).getTeamColor(), ChessPiece.PieceType.ROOK));

                            // Mark the moved piece as having moved
                            playerBoard.getPiece(positionEnd.getRow(), 4).tick_piece_moved();
                        }
                        // If castling right
                        if(positionEnd.getColumn() == 7)
                        {
                            // Remove the Rook and add the Rook to the new place
                            playerBoard.pieceRemove(positionEnd.getRow(),8);
                            playerBoard.addPiece(positionEnd.getRow(), 6, new ChessPiece(playerBoard.getPiece(positionEnd).getTeamColor(), ChessPiece.PieceType.ROOK));

                            // Mark the moved piece as having moved
                            playerBoard.getPiece(positionEnd.getRow(), 6).tick_piece_moved();
                        }
                    }
                }





                // piece is moved, so we're done here
                System.out.println("Piece has been moved");

                player_inactive_last_move_position = positionEnd;

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
     * @param player_board_checking which board to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor, ChessBoard player_board_checking) {
        // check every row
        for(int r=1; r<=8; r++) {
            for (int c = 1; c <= 8; c++) {
                // Check if piece there
                if (player_board_checking.getPiece(r, c) != null)
                {
                    // Check to see if it is the king of the correct color
                    ChessPiece piece_king = player_board_checking.getPiece(r,c);
                    ChessPosition piece_king_position = new ChessPosition(r,c);
                    //System.out.println("Found the KING @" + piece_king_position);
                    if (piece_king.getTeamColor() == teamColor && piece_king.getPieceType() == ChessPiece.PieceType.KING)
                    {
                        // Check every spot on the board to see if it can kill the king
                        for(int rr=1; rr<=8; rr++)
                        {
                            for(int cc=1; cc<=8; cc++)
                            {
                                if (player_board_checking.getPiece(rr,cc) != null)
                                {
                                    // Check to see if it is the opposite color
                                    ChessPiece piece_other = player_board_checking.getPiece(rr,cc);
                                    ChessPosition piece_other_position = new ChessPosition(rr,cc);
                                    //System.out.println("Checking Piece @" + piece_other_position);
                                    // Check to see if they are not teammates
                                    if (piece_other.getTeamColor() != piece_king.getTeamColor())
                                    {
                                        //System.out.println("Different Team!!!");
                                        Set<ChessMove> piece_other_moves = new HashSet<ChessMove>();
                                        piece_other_moves = (Set<ChessMove>) piece_other.pieceMoves(player_board_checking, piece_other_position);

                                        //System.out.println("Found moves");
                                        //System.out.println("King @ " + piece_king_position);
                                        if(piece_other_moves != null)
                                        {
                                            for (ChessMove move : piece_other_moves)
                                            {
                                                //System.out.println(m.getEndPosition());
                                                if(move.getEndPosition().equals(piece_king_position))
                                                {
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
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
            return false;
        // Check if any moves break you out of check
        for(int r=1; r<=8; r++)
        {
            for(int c=1; c<=8; c++)
            {
                if(playerBoard.getPiece(r,c) != null) {
                    if(playerBoard.getPiece(r,c).getTeamColor() == teamColor)
                    {
                        if (validMoves(new ChessPosition(r,c)) != null)
                        {
                            if (!validMoves(new ChessPosition(r, c)).isEmpty())
                            {
                                // Escape found!!!
                                return false;
                            }
                        }
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
                    if(playerBoard.getPiece(r,c).getTeamColor() == teamColor)
                    {
                        if (validMoves(new ChessPosition(r,c)) != null)
                        {
                            if (!validMoves(new ChessPosition(r, c)).isEmpty())
                            {
                                // Escape found!!!
                                return false;
                            }
                        }
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
                "player_board=" + playerBoard +
                ", player_active=" + playerActive +
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
