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

    TeamColor player_active;
    ChessBoard player_board;

    ChessPosition player_inactive_last_move_position;

    public ChessGame() {
        player_active = TeamColor.WHITE;
        player_board = new ChessBoard();
        player_board.resetBoard();

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return player_active;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        player_active = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Set<ChessMove> moves_output = new HashSet<ChessMove>();

        // If no piece there, return null
        if (player_board.getPiece(startPosition) == null)
            return null;

        ChessPiece piece_moving = player_board.getPiece(startPosition);

        // Get the moves possible, without taking check into consideration
        Set<ChessMove> moves_input = (Set<ChessMove>) player_board.getPiece(startPosition).pieceMoves(player_board, startPosition);
        if(moves_input == null)
        {
            return null;
        }

        ChessBoard player_board_imaginary = player_board.get_board();

        // Check every move
        for (ChessMove move : moves_input)
        {
            System.out.println("Checking if " + move.getStartPosition() + " can move to " + move.getEndPosition());

            // This imaginary board will be the board if it completes the specific move
            player_board_imaginary = player_board.get_board();
            //ChessBoard player_board_imaginary = player_board;

            // Kill the previous piece and add the new piece in its place
            player_board_imaginary.piece_remove(move.getEndPosition());
            player_board_imaginary.addPiece(move.getEndPosition(), player_board_imaginary.getPiece(startPosition));

            // remove the piece from start position
            player_board_imaginary.piece_remove(startPosition);


            if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                // It is in check, so it won't be added
                System.out.println("It is in check, so it won't be added");
            } else {
                System.out.println("It is not in check, so it will be added");
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
                    if (player_board.getPiece(piece_moving_row, 4) == null)
                    {
                        if (player_board.getPiece(piece_moving_row, 3) == null)
                        {
                            if (player_board.getPiece(piece_moving_row, 2) == null)
                            {
                                if (player_board.getPiece(piece_moving_row, 1) != null)
                                {
                                    if (player_board.getPiece(piece_moving_row, 1).getPieceType() == ChessPiece.PieceType.ROOK)
                                    {
                                        if (player_board.getPiece(piece_moving_row, 1).get_piece_moved() == 0)
                                        {
                                            // This imaginary board will be the board if it completes the specific move
                                            player_board_imaginary = player_board.get_board();

                                            // Move the king left by 1
                                            player_board_imaginary.addPiece(piece_moving_row, 4, piece_moving);
                                            player_board_imaginary.piece_remove(piece_moving_row, 5);
                                            if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                                                // It is in check, so it won't be added
                                                System.out.println("It is in check, so it won't be added");
                                            } else {
                                                // This imaginary board will be the board if it completes the specific move
                                                player_board_imaginary = player_board.get_board();

                                                // Move the king left by 2
                                                player_board_imaginary.addPiece(piece_moving_row, 3, piece_moving);
                                                player_board_imaginary.piece_remove(piece_moving_row, 5);
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
                    if (player_board.getPiece(piece_moving_row, 6) == null)
                    {
                        if (player_board.getPiece(piece_moving_row, 7) == null)
                        {
                            if (player_board.getPiece(piece_moving_row, 8) != null)
                            {
                                if (player_board.getPiece(piece_moving_row, 8).getPieceType() == ChessPiece.PieceType.ROOK)
                                {
                                    if (player_board.getPiece(piece_moving_row, 8).get_piece_moved() == 0)
                                    {
                                        // This imaginary board will be the board if it completes the specific move
                                        player_board_imaginary = player_board.get_board();

                                        // Move the king right by 1
                                        player_board_imaginary.addPiece(piece_moving_row, 6, piece_moving);
                                        player_board_imaginary.piece_remove(piece_moving_row, 5);
                                        if (isInCheck(piece_moving.getTeamColor(), player_board_imaginary)) {
                                            // It is in check, so it won't be added
                                            System.out.println("It is in check, so it won't be added");
                                        } else {
                                            // This imaginary board will be the board if it completes the specific move
                                            player_board_imaginary = player_board.get_board();

                                            // Move the king left by 2
                                            player_board_imaginary.addPiece(piece_moving_row, 7, piece_moving);
                                            player_board_imaginary.piece_remove(piece_moving_row, 5);
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
            // Check is last moved piece exists
            // Check is last moved piece exists
            if (player_inactive_last_move_position != null)
            {
                if (player_board.getPiece(player_inactive_last_move_position) != null)
                {
                    if (player_board.getPiece(player_inactive_last_move_position).get_piece_moved() == 1)
                    {
                        // Check if inactive pawn only moved one time
                        if (player_board.getPiece(player_inactive_last_move_position).get_piece_moved() == 1)
                        {
                            // Check to see if they are now on the same row (required for EnPassant
                            if (player_inactive_last_move_position.getRow() == piece_moving_row)
                            {
                                // If one row over from the other pawn
                                if (piece_moving_col == player_inactive_last_move_position.getColumn() + 1 || piece_moving_col == player_inactive_last_move_position.getColumn() - 1)
                                {
                                    // Because I have only moved the enemy pawn once, if it is on row 4 or 5, it must have jumped up
                                    if (piece_moving_row == 4)
                                    {
                                        moves_output.add(new ChessMove(startPosition, new ChessPosition(piece_moving_row, player_inactive_last_move_position.getColumn()), null));
                                    } else if (piece_moving_row == 5)
                                    {
                                        moves_output.add(new ChessMove(startPosition, new ChessPosition(piece_moving_row, player_inactive_last_move_position.getColumn()), null));
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
        ChessPosition position_start = move.getStartPosition();
        ChessPosition position_end = move.getEndPosition();
        Set<ChessMove> positions_possible = (Set<ChessMove>) validMoves(position_start);

        if(player_board.getPiece(position_start) == null)
            throw new InvalidMoveException("No Piece there");

        if(player_board.getPiece(move.getStartPosition()).getTeamColor() != player_active)
            throw new InvalidMoveException("Wrong Turn!!!");

        //System.out.println("Checking if " + startPosition + " can move to " + end_position);
        // Check every move
        if(positions_possible == null)
        {
            // The list is empty
            throw new InvalidMoveException("Can't move to " + move.getEndPosition());
        }
        for (ChessMove move_checking : positions_possible)
        {
            //System.out.println("Checking move: " + move_checking);
            // See if move is the same
            if (move_checking.equals(move)) {
                // If EnPassant, kill the Enemy Pawn
                // This has to be done first, so I can check to see if a pawn moved diagonal into a blank space

                // Only EnPassant if Pawn
                if(player_board.getPiece(position_start).getPieceType() == ChessPiece.PieceType.PAWN)
                {
                    // Check if Pawn moved diagonal
                    if (position_end.getColumn() == position_start.getColumn() + 1 || position_end.getColumn() == position_start.getColumn() - 1 )
                    {
                        // If the pawn moved diagonal without killing anything
                        if (player_board.getPiece(position_end) == null)
                        {
                            // Kill the pawn!!!
                            // The dead pawn will be on the starting row of the moving pawn and the ending column of the same pawn
                            player_board.piece_remove(position_start.getRow(), position_end.getColumn());
                        }
                    }
                }


                // Kill the previous piece and add the new piece in its place
                player_board.piece_remove(position_end);

                if(move.getPromotionPiece() == null) {
                    player_board.addPiece(position_end, player_board.getPiece(position_start));
                } else {
                    player_board.addPiece(position_end, new ChessPiece(player_active, move.getPromotionPiece()));
                }


                // remove the piece from start position
                player_board.piece_remove(position_start);

                // Mark the moved piece as having moved
                player_board.getPiece(position_end).tick_piece_moved();

                // If Castling, move the Rook as well
                // Check if King
                if(player_board.getPiece(position_end).getPieceType() == ChessPiece.PieceType.KING)
                {
                    // If King in starting location
                    if (position_start.getColumn() == 5)
                    {
                        // If castling left
                        if(position_end.getColumn() == 3)
                        {
                            // Remove the Rook and add the Rook to the new place
                            player_board.piece_remove(position_end.getRow(),1);
                            player_board.addPiece(position_end.getRow(), 4, new ChessPiece(player_board.getPiece(position_end).getTeamColor(), ChessPiece.PieceType.ROOK));

                            // Mark the moved piece as having moved
                            player_board.getPiece(position_end.getRow(), 4).tick_piece_moved();
                        }
                        // If castling right
                        if(position_end.getColumn() == 7)
                        {
                            // Remove the Rook and add the Rook to the new place
                            player_board.piece_remove(position_end.getRow(),8);
                            player_board.addPiece(position_end.getRow(), 6, new ChessPiece(player_board.getPiece(position_end).getTeamColor(), ChessPiece.PieceType.ROOK));

                            // Mark the moved piece as having moved
                            player_board.getPiece(position_end.getRow(), 6).tick_piece_moved();
                        }
                    }
                }





                // piece is moved, so we're done here
                System.out.println("Piece has been moved");

                player_inactive_last_move_position = position_end;

                // Change to new color turn
                if (player_active == TeamColor.WHITE) {
                    player_active = TeamColor.BLACK;
                } else {
                    player_active = TeamColor.WHITE;
                }

                System.out.println(player_board);
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
        return isInCheck(teamColor, player_board);
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
                if(player_board.getPiece(r,c) != null) {
                    if(player_board.getPiece(r,c).getTeamColor() == teamColor)
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
                if(player_board.getPiece(r,c) != null) {
                    if(player_board.getPiece(r,c).getTeamColor() == teamColor)
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
        player_board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return player_board;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "player_board=" + player_board +
                ", player_active=" + player_active +
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
        return player_active == chessGame.player_active && Objects.equals(player_board, chessGame.player_board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player_active, player_board);
    }
}
