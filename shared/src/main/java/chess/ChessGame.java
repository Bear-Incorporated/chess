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

    ChessBoard chess_board;
    TeamColor turn_color;

    public ChessGame() {
        chess_board = new ChessBoard();
        chess_board.resetBoard();
        turn_color = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn_color;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn_color = team;
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
        if (chess_board.getPiece(startPosition) == null)
            return null;

        ChessPiece piece_moving = chess_board.getPiece(startPosition);

        // Check the color of the piece in question
        if(piece_moving.getTeamColor() != turn_color)
            return null;

        // Get the moves possible, without taking check into consideration
        Set<ChessMove> moves_input = (Set<ChessMove>) chess_board.getPiece(startPosition).pieceMoves(chess_board, startPosition);

        // Check every move
        for (ChessMove move : moves_input)
        {
            System.out.println("Checking if " + move.getStartPosition() + " can move to " + move.getEndPosition());

            // This imaginary board will be the board if it completes the specific move
            ChessBoard chess_board_imaginary = chess_board.copy_board();


            //ChessBoard chess_board_imaginary = chess_board;

            // Kill the previous piece and add the new piece in its place
            chess_board_imaginary.remove_piece(move.getEndPosition());
            chess_board_imaginary.addPiece(move.getEndPosition(), chess_board_imaginary.getPiece(startPosition));

            // remove the piece from start position
            chess_board_imaginary.remove_piece(startPosition);


            if (isInCheck(piece_moving.getTeamColor(), chess_board_imaginary)) {
                // It is in check, so it won't be added
                System.out.println("It is in check, so it won't be added");
            } else {
                System.out.println("It is not in check, so it will be added");
                moves_output.add(move);
            }


        }

        return moves_output;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition end_position = move.getEndPosition();
        Set<ChessMove> moves_possible = (Set<ChessMove>) validMoves(startPosition);

        System.out.println("Checking if " + startPosition + " can move to " + end_position);
        // Check every move
        for (ChessMove move_checking : moves_possible)
        {
            System.out.println("Checking move: " + move_checking);
            // See if move is the same
            if (move_checking.equals(move)) {
                // Kill the previous piece and add the new piece in its place
                System.out.println("Removing piece @" + end_position);
                chess_board.remove_piece(end_position);
                System.out.println(chess_board);
                System.out.println("Add piece @" + end_position);
                chess_board.addPiece(end_position, chess_board.getPiece(startPosition));
                System.out.println(chess_board);

                // remove the piece from start position
                System.out.println("Removing piece @" + startPosition);
                chess_board.remove_piece(startPosition);
                System.out.println(chess_board);

                // piece is moved, so we're done here
                System.out.println("Piece has been moved");

                // Change to new color turn
                if (turn_color == TeamColor.WHITE) {
                    turn_color = TeamColor.BLACK;
                } else {
                    turn_color = TeamColor.WHITE;
                }

                System.out.println(chess_board);
                return;
            }
        }
        System.out.println("Invalid move, did not find it on the list");
        // It wasn't there, throw exception
        // throw new InvalidMoveException("Can't move to " + move.getEndPosition());
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor, chess_board);
    }

    /**
     * Check if something is in check using any board
     *
     * @param teamColor which team to check for check
     * @param check_board which board to check on
     * @return True if the specified team is in check
     *
     */
    public boolean isInCheck(TeamColor teamColor, ChessBoard check_board) {
        for(int r=1; r<=8; r++)
        {
            for(int c=1; c<=8; c++)
            {
                // Check if piece there
                if (check_board.getPiece(r,c) != null)
                {
                    // Check to see if it is the king of the correct color
                    ChessPiece piece_king = check_board.getPiece(r,c);
                    ChessPosition piece_king_position = new ChessPosition(r,c);
                    //System.out.println("Found the KING @" + piece_king_position);
                    if (piece_king.getTeamColor() == teamColor && piece_king.getPieceType() == ChessPiece.PieceType.KING)
                    {
                        // Check every spot on the board to see if it can kill the king
                        for(int rr=1; rr<=8; rr++)
                        {
                            for(int cc=1; cc<=8; cc++)
                            {
                                if (check_board.getPiece(rr,cc) != null)
                                {
                                    // Check to see if it is the opposite color
                                    ChessPiece piece_other = check_board.getPiece(rr,cc);
                                    ChessPosition piece_other_position = new ChessPosition(rr,cc);
                                    //System.out.println("Checking Piece @" + piece_other_position);
                                    if (piece_other.getTeamColor() != piece_king.getTeamColor())
                                    {
                                        //System.out.println("Different Team!!!");
                                        Set<ChessMove> piece_other_moves = new HashSet<ChessMove>();
                                        piece_other_moves = (Set<ChessMove>) piece_other.pieceMoves(check_board, piece_other_position);

                                        //System.out.println("Found moves");
                                        //System.out.println("King @ " + piece_king_position);
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
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        chess_board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chess_board;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "chess_board=" + chess_board +
                ", turn_color=" + turn_color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame chessGame)) return false;
        return Objects.equals(chess_board, chessGame.chess_board) && turn_color == chessGame.turn_color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chess_board, turn_color);
    }
}
