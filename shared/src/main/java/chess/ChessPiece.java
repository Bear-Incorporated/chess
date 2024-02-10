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
        int row_start =  myPosition.getRow();
        int col_start =  myPosition.getColumn();

        if (piece_type_move == PieceType.KING){
            int row_current, col_current;
            //Check up right
            row_current = row_start + 1;
            col_current = col_start + 1;
            if (row_current <= 8 && col_current <= 8){
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
            if (row_current >= 1 && col_current <= 8){
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
            if (row_current >= 1 && col_current >= 1){
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
            if (row_current <= 8 && col_current >= 1){
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
            //Check up
            row_current = row_start + 1;
            col_current = col_start ;
            if (row_current <= 8 && col_current <= 8){
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
            if (row_current >= 1 && col_current <= 8){
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
            if (row_current >= 1 && col_current >= 1){
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
            if (row_current >= 1 && col_current <= 8){
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



        } else if (piece_type_move == PieceType.QUEEN){
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

        } else if (piece_type_move == PieceType.BISHOP){
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
            int row_current, col_current;

            // Check Down-left
            row_current = row_start - 1;
            col_current = col_start - 2;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }
            row_current = row_start - 2;
            col_current = col_start - 1;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }

            // Check Down-right
            row_current = row_start - 1;
            col_current = col_start + 2;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }
            row_current = row_start - 2;
            col_current = col_start + 1;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }

            // Check Up-left
            row_current = row_start + 1;
            col_current = col_start - 2;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }
            row_current = row_start + 2;
            col_current = col_start - 1;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }

            // Check Up-right
            row_current = row_start + 1;
            col_current = col_start + 2;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }
            row_current = row_start + 2;
            col_current = col_start + 1;
            if (row_current >= 1 && row_current <= 8 && col_current >= 1 && col_current <= 8) {
                if (board.getPiece(row_current, col_current) == null) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                } else if (board.getPiece(row_current, col_current).piece_color != piece_color_move) {
                    moves_output.add(new ChessMove(row_start, col_start, row_current, col_current, null));
                }
            }






            //throw new RuntimeException("Not implemented");

        } else if (piece_type_move == PieceType.ROOK){
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
            int promote = 0;
            //System.out.println(row_start);
            //System.out.println(col_start);
            if (row_start < 8 && piece_color_move == ChessGame.TeamColor.WHITE) {
                if (row_start == 7)
                    promote = 1;
                if (board.getPiece(row_start +1, col_start) == null) {
                    if (promote == 0) {
                        moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start, null));
                    } else {
                        moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start, PieceType.BISHOP));
                        moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start, PieceType.KNIGHT));
                        moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start, PieceType.QUEEN));
                        moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start, PieceType.ROOK));
                    }
                    if (row_start == 2 && board.getPiece(4, col_start) == null) {
                        moves_output.add(new ChessMove(row_start, col_start, row_start+2, col_start, null));
                    }
                }
                if (col_start > 1 && board.getPiece(row_start +1, col_start-1) != null) {
                    if (board.getPiece(row_start +1, col_start-1).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        if (promote == 0) {
                            moves_output.add(new ChessMove(row_start, col_start, row_start +1, col_start-1, null));
                        } else {
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start-1, PieceType.BISHOP));
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start-1, PieceType.KNIGHT));
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start-1, PieceType.QUEEN));
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start-1, PieceType.ROOK));
                        }
                    }
                }
                if (col_start < 8 && board.getPiece(row_start +1, col_start+1) != null) {
                    if (board.getPiece(row_start +1, col_start+1).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        if (promote == 0) {
                            moves_output.add(new ChessMove(row_start, col_start, row_start + 1, col_start + 1, null));
                        } else {
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start+1, PieceType.BISHOP));
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start+1, PieceType.KNIGHT));
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start+1, PieceType.QUEEN));
                            moves_output.add(new ChessMove(row_start, col_start, row_start+1, col_start+1, PieceType.ROOK));
                        }
                    }
                }
            } else if (row_start > 1 && piece_color_move == ChessGame.TeamColor.BLACK) {
                if (row_start == 2)
                    promote = 1;
                if (board.getPiece(row_start -1, col_start) == null) {
                    if (promote == 0) {
                        moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start, null));
                    } else {
                        moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start, PieceType.BISHOP));
                        moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start, PieceType.KNIGHT));
                        moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start, PieceType.QUEEN));
                        moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start, PieceType.ROOK));
                    }
                    if (row_start == 7 && board.getPiece(5, col_start) == null) {
                        moves_output.add(new ChessMove(row_start, col_start, row_start-2, col_start, null));
                    }
                }
                if (col_start > 1 && board.getPiece(row_start -1, col_start-1) != null) {
                    if (board.getPiece(row_start -1, col_start-1).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        if (promote == 0) {
                            moves_output.add(new ChessMove(row_start, col_start, row_start -1, col_start-1, null));
                        } else {
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start-1, PieceType.BISHOP));
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start-1, PieceType.KNIGHT));
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start-1, PieceType.QUEEN));
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start-1, PieceType.ROOK));
                        }
                    }
                }
                if (col_start < 8 && board.getPiece(row_start -1, col_start+1) != null) {
                    if (board.getPiece(row_start -1, col_start+1).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        if (promote == 0) {
                            moves_output.add(new ChessMove(row_start, col_start, row_start -1, col_start+1, null));
                        } else {
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start+1, PieceType.BISHOP));
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start+1, PieceType.KNIGHT));
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start+1, PieceType.QUEEN));
                            moves_output.add(new ChessMove(row_start, col_start, row_start-1, col_start+1, PieceType.ROOK));
                        }
                    }
                }
            }



        }


        return moves_output;


        //throw new RuntimeException("Not implemented");
    }


    @Override
    public String toString() {
        if(piece_color == ChessGame.TeamColor.WHITE)
        {
            if(piece_type == PieceType.BISHOP)
                return "B";
            if(piece_type == PieceType.KING)
                return "K";
            if(piece_type == PieceType.KNIGHT)
                return "N";
            if(piece_type == PieceType.PAWN)
                return "P";
            if(piece_type == PieceType.QUEEN)
                return "Q";
            if(piece_type == PieceType.ROOK)
                return "R";
        } else {
            if(piece_type == PieceType.BISHOP)
                return "b";
            if(piece_type == PieceType.KING)
                return "k";
            if(piece_type == PieceType.KNIGHT)
                return "n";
            if(piece_type == PieceType.PAWN)
                return "p";
            if(piece_type == PieceType.QUEEN)
                return "q";
            if(piece_type == PieceType.ROOK)
                return "r";
        }
        return " ";
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
