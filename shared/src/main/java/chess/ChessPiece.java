package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    PieceType piece_type;
    ChessGame.TeamColor piece_color;


    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
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
        if (board == null)
            return null;
        if (board.getPiece(myPosition) == null) {
            return null;
        }
        //ChessMove temp_for_stupid_collection = new ChessMove(1, 1, 1, 1, ChessPiece.PieceType.KING);
        //List<ChessMove> moves_output = new LinkedList<ChessMove>();
        Set<ChessMove> moves_output = new HashSet<ChessMove>();
        moves_output.clear();
        //moves_output.clear();
        //ChessMove[] moves_output;
        //moves_output = new ChessMove[64];



        PieceType piece_type_move = board.getPiece(myPosition).getPieceType();
        ChessGame.TeamColor piece_color_move = board.getPiece(myPosition).getTeamColor();

        if (piece_type_move == PieceType.KING){
            throw new RuntimeException("Not implemented");
        } else if (piece_type_move == PieceType.QUEEN){
            throw new RuntimeException("Not implemented");

        } else if (piece_type_move == PieceType.BISHOP){
            int row_start =  myPosition.getRow();
            int col_start =  myPosition.getColumn();
            int row_current, col_current;
            //Check up right
            row_current = row_start + 1;
            col_current = col_start + 1;
            while (row_current <= 8 && col_current <= 8){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current++;
                    col_current++;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 10;
                } else {
                    row_current = 10;
                }
            }
            //Check down right
            row_current = row_start - 1;
            col_current = col_start + 1;
            while (row_current >= 1 && col_current <= 8){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current--;
                    col_current++;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 0;
                } else {
                    row_current = 0;
                }
            }
            //Check down left
            row_current = row_start - 1;
            col_current = col_start - 1;
            while (row_current >= 1 && col_current >= 1){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current--;
                    col_current--;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 0;
                } else {
                    row_current = 0;
                }
            }
            //Check up left
            row_current = row_start + 1;
            col_current = col_start - 1;
            while (row_current <= 8 && col_current >= 1){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current++;
                    col_current--;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 10;
                } else {
                    row_current = 10;
                }
            }


        } else if (piece_type_move == PieceType.KNIGHT){
            throw new RuntimeException("Not implemented");

        } else if (piece_type_move == PieceType.ROOK){
            int row_start =  myPosition.getRow();
            int col_start =  myPosition.getColumn();
            int row_current, col_current;
            //Check up
            row_current = row_start + 1;
            col_current = col_start ;
            while (row_current <= 8 && col_current <= 8){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current++;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 10;
                } else {
                    row_current = 10;
                }
            }
            //Check down
            row_current = row_start - 1;
            col_current = col_start;
            while (row_current >= 1 && col_current <= 8){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current--;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 0;
                } else {
                    row_current = 0;
                }
            }
            //Check left
            row_current = row_start;
            col_current = col_start - 1;
            while (row_current >= 1 && col_current >= 1){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    col_current--;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 0;
                } else {
                    row_current = 0;
                }
            }
            //Check right
            row_current = row_start;
            col_current = col_start + 1;
            while (row_current >= 1 && col_current <= 8){
                if (board.getPiece(row_current, col_current) == null){
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    col_current++;
                    //throw new RuntimeException(new ChessMove(row_start, col_start, row_current, col_current, piece_type).toString());
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                    row_current = 0;
                } else {
                    row_current = 0;
                }
            }

        } else if (piece_type_move == PieceType.PAWN){
            throw new RuntimeException("Not implemented");

        }


        return moves_output;


        //throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return piece_type == that.piece_type && piece_color == that.piece_color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece_type, piece_color);
    }
}
