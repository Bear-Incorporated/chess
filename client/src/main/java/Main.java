import chess.ChessGame;
import chess.ChessPiece;
import ui.*;
public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        System.out.println("Options:");
        System.out.println("\"help\" or \"h\" - Displays text informing the user what actions they can take.");
        System.out.println("\"quit\" or \"q\" - Exits the program.");
        System.out.println("\"login <USERNAME> <PASSWORD>\" or \"l <USERNAME> <PASSWORD>\" - Logs in.");
        System.out.println("\"register <USERNAME> <PASSWORD> <EMAIL>\" or \"r <USERNAME> <PASSWORD> <EMAIL>\" - Registers a new user.");





        new Repl().run();
        //new Repl(serverUrl).run();
    }




}