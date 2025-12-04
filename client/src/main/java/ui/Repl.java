package ui;

import websocket.HttpTalker;

import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;


public class Repl {
    private String visitorName = null;
    // private final ServerFacade server;
    // private final WebSocketFacade ws;
    private State state = State.SIGNEDOUT;
    private HttpTalker client = new HttpTalker();

    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 8080;

    // private Chess_Service service = new Chess_Service();


    public Repl() {
        // server = new ServerFacade(serverUrl);
        // ws = new WebSocketFacade(serverUrl, this);
        client = new HttpTalker();
    }

    public Repl(String serverUrl) {
        // server = new ServerFacade(serverUrl);
        // ws = new WebSocketFacade(serverUrl, this);
    }

    public void run() {
        System.out.println(LOGO + " Welcome to chess! Sign in to start.");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }


//    public void notify(Notification notification) {
//        System.out.println(SET_TEXT_COLOR_RED + notification.message());
//        printPrompt();
//    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_GREEN);
    }


    public String eval(String input) throws Exception
    {

        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        String input1 = (tokens.length > 1) ? tokens[1] : "";
        System.out.print(input1 + "\n");
        String input2 = (tokens.length > 2) ? tokens[2] : "";
        System.out.print(input2 + "\n");
        String input3 = (tokens.length > 3) ? tokens[3] : "";
        System.out.print(input3 + "\n");


        return switch (cmd) {
            // case "signin" -> signIn(params);
            // case "rescue" -> rescuePet(params);
            case "list" -> listGames();
            case "l" -> listGames();
            case "login" -> login(input1, input2);
            case "register" -> register(input1, input2, input3);
            case "r" -> register(input1, input2, input3);
            // case "adopt" -> adoptPet(params);
            // case "adoptall" -> adoptAllPets();
            case "quit" -> "quit";
            case "q" -> "quit";
            case "help" -> help();
            case "h" -> help();
            default -> help();
        };


    }

//
//    """
//    Options:
//
//    \"register <USERNAME> <PASSWORD> <EMAIL>\" or \"r <USERNAME> <PASSWORD> <EMAIL>\" - Registers a new user.
//
//    \"logout\" or \"o\" - Logs you out.
//    \"create <NAME>\" or \"c <NAME>\" - creates a new game.
//    \"join <ID> [WHITE|BLACK]\" or \"j <ID> [WHITE|BLACK]\" - Join specified game.
//    \"view <ID>\" or \"v <ID>\" - View specified game.
//    """;




//    public String signIn(String... params) {
//        if (params.length >= 1) {
//            state = State.SIGNEDIN;
//            visitorName = String.join("-", params);
//            ws.enterPetShop(visitorName);
//            return String.format("You signed in as %s.", visitorName);
//        }
//    }

//    public String rescuePet(String... params) {
//        assertSignedIn();
//        if (params.length >= 2) {
//            String name = params[0];
//            PetType type = PetType.valueOf(params[1].toUpperCase());
//            var pet = new Pet(0, name, type);
//            pet = server.addPet(pet);
//            return String.format("You rescued %s. Assigned ID: %d", pet.name(), pet.id());
//        }
//    }


    public String listGames() throws Exception
    {
        // assertSignedIn();
        client.get("/game");
        System.out.print(client.toString() + "\n");
        // Run Function
        //Game_Response_List output = service.Game_List(new Game_Request_List());

        // return output.toString();
        return "Done";
    }

    public String login(String username, String password) throws Exception
    {
        // assertSignedIn();
        System.out.print("username = " + username + "\n");
        System.out.print("password = " + password + "\n");
        client.postSession(username, password);
        System.out.print(client.toString() + "\n");
        // Run Function
        //Game_Response_List output = service.Game_List(new Game_Request_List());

        // return output.toString();
        return "Done";
    }

    public String register(String username, String password, String email) throws Exception
    {
        // assertSignedIn();
        System.out.print("username = " + username + "\n");
        System.out.print("password = " + password + "\n");
        client.postUser(username, password, email);
        System.out.print(client.toString() + "\n");
        // Run Function
        //Game_Response_List output = service.Game_List(new Game_Request_List());

        // return output.toString();
        return "Done";
    }

//    public String adoptPet(String... params) {
//        assertSignedIn();
//        if (params.length == 1) {
//            try {
//                int id = Integer.parseInt(params[0]);
//                Pet pet = getPet(id);
//                if (pet != null) {
//                    server.deletePet(id);
//                    return String.format("%s says %s", pet.name(), pet.sound());
//                }
//            } catch (NumberFormatException ignored) {
//            }
//        }
//
//    }

//    public String adoptAllPets() {
//        assertSignedIn();
//        var buffer = new StringBuilder();
//        for (Pet pet : server.listPets()) {
//            buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
//        }
//
//        server.deleteAllPets();
//        return buffer.toString();
//    }

//    public String signOut() {
//        assertSignedIn();
//        ws.leavePetShop(visitorName);
//        state = State.SIGNEDOUT;
//        return String.format("%s left the shop", visitorName);
//    }

//    private Pet getPet(int id) {
//        for (Pet pet : server.listPets()) {
//            if (pet.id() == id) {
//                return pet;
//            }
//        }
//        return null;
//    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    Options:
                    \"help\" or \"h\" - Displays text informing the user what actions they can take.
                    \"quit\" or \"q\" - Exits the program.
                    \"login <USERNAME> <PASSWORD>\" - Logs in.
                    \"register <USERNAME> <PASSWORD> <EMAIL>\" or \"r <USERNAME> <PASSWORD> <EMAIL>\" - Registers a new user.
                    """;
        }
        else if (state == State.SIGNEDIN) {
            return """
                    Options:
                    \"help\" or \"h\" - Displays text informing the user what actions they can take.
                    \"logout\" or \"o\" - Logs you out.
                    \"create <NAME>\" or \"c <NAME>\" - creates a new game.
                    \"list\" or \"l\" - Lists all the game.
                    \"join <ID> [WHITE|BLACK]\" or \"j <ID> [WHITE|BLACK]\" - Join specified game.
                    \"view <ID>\" or \"v <ID>\" - View specified game.
                    """;
        }
        else if (state == State.INGAME) {
            return """
                    Options:
                    \"move <LOCAL_START> <LOCAL_END>\" or \"<LOCAL_START> <LOCAL_END>\" - Moves a piece.
                    """;
        }

        return """
                - list
                - adopt <pet id>
                - rescue <name> <CAT|DOG|FROG|FISH>
                - adoptAll
                - signOut
                - quit
                """;
    }

    private void assertSignedIn() {
        if (state == State.SIGNEDOUT) {

        }
    }
}
