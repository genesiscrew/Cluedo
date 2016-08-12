package cluedo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import cluedo.Player.Character;
import cluedo.Room.roomName;
import cluedo.Weapon.Weapons;

public class Controller {
    /**
     * Get integer from System.in
     */
    private static int inputNumber(String msg) {
        System.out.print(msg + " ");
        while (1 == 1) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                String v = input.readLine();
                return Integer.parseInt(v);
            } catch (IOException  | NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }
    }

    /**
     * input a string
     *
     * @param msg
     * @return
     */
    private static String inputString(String msg) {
        System.out.print(msg + " ");
        while (1 == 1) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            try {
                return input.readLine();
            } catch (IOException e) {
                System.out.println("I/O Error ... please try again!");
            }
        }
    }

    /**
     * helper method to enable player selection
     * @return
     */
private static String characterList() throws IOException {

    System.out.println("1: MissScarlet");
    System.out.println("2: ColonelMustard");
    System.out.println("3: MrsWhite");
    System.out.println("4: TheReverendGreen");
    System.out.println("5: MrsPeacock");
    System.out.println("6: ProfessorPlum");
    int selection = 0;
 // convert the integer input into a character name
    String token = null;
    try {

        selection = inputNumber("Please select one of the characters above: ");
        if (selection < 1 || selection > 6 ) {
        	System.out.println("Please select a number between 1 to 6!");
        	//recursively call method untill proper selection is made
        	token = characterList();
        	return token;
        }
        ;
    }
    catch (Exception e) {
System.out.println("Invalid Input");

    }

    for (Player.Character r:Character.values()){
        if (r.getNumVal() == selection) {
            token =  r.name();

        }
    }


    return token;

}
/**
 * helper method to enable weapon selection
 * @return
 * @throws IOException
 */
private static String weaponList() throws IOException {

    System.out.println("1: Candlestick");
    System.out.println("2: Dagger");
    System.out.println("3: Lead Pipe");
    System.out.println("4: Revolver");
    System.out.println("5: Rope");
    System.out.println("6: Spanner");
    int selection = 0;
    String token = null;
    try {
        selection = inputNumber("Please select one of the weapons above: ");
        if (selection < 1 || selection > 6 ) {
        	System.out.println("Please select a number between 1 to 6!");
        	//recursively call method untill proper selection is made
        	token = characterList();
        	return token;
        };
    }
    catch (Exception e) {
System.out.println("Invalid Input");

    }
    // convert the integer input into a character name

    for (int i = 0; i < Weapons.values().length; i++){
        if ( i == (selection-1)) {
            token =  Weapons.values()[i].toString();


        }
    }


    return token;

}
/**
 * helper method to enable room selection
 * @return
 * @throws IOException
 */

private static String roomList() throws IOException {


    System.out.println("1: Ball Room");
    System.out.println("2: Kitchen");
    System.out.println("3: Billiard Room");
    System.out.println("4: Dining Room");
    System.out.println("5: Lounge");
    System.out.println("6: Study");
    System.out.println("7: Hall");
    System.out.println("8: Library");
    System.out.println("9: Conservatory");

    int selection = 0;
    String token = null;
    try {
        selection = inputNumber("Please select one of the rooms above: ");
        if (selection < 1 || selection > 9 ) {
        	System.out.println("Please select a number between 1 to 9!");
        	//recursively call method untill proper selection is made
        	token = characterList();
        	return token;
        };
    }
    catch (Exception e) {
System.out.println("Invalid Input");

    }
    // convert the integer input into a character name

    for (int i = 0; i < Room.roomName.values().length; i++){
        if ( i == (selection-1)) {
            token =  Room.roomName.values()[i].toString();



        }
    }


    return token;

}
    /**
     * input plays into the game object
     *
     * @param nplayers
     * @param game
     * @return
     * @throws IOException
     */
    private static ArrayList<Player> inputPlayers(int nplayers, Game game) throws IOException {
        // set up the tokens
        ArrayList<Player> players = null;
        try {
        ArrayList<Player.Character> tokens = new ArrayList<Player.Character>();
        for (Player.Character t : Player.Character.values()) {
            tokens.add(t);

        }
        Square[] startPos = game.getBoard().getStartingLocations();
        players = new ArrayList<Player>();

        for (int i = 0; i != nplayers; ++i) {
            String name = inputString("Player #" + (i+1) + " name?");
            String tokenName = characterList();
            Player.Character token = Player.Character.valueOf(tokenName);
            while (!tokens.contains(token)) {
                // loop until player selects an unchosen character
                System.out.print("Character already selected. please try again");
                System.out.println();
                System.out.println();
                tokenName = characterList();
                token = Player.Character.valueOf(tokenName);
            }
            // we get the required starting location based on character chosen
            int[] xy = game.getTokenStartLocation(token);
            PlayerSquare start = new PlayerSquare(token.getNumVal(), xy[0], xy[1]);
            tokens.remove(token);
            game.addPlayer(new Player(name, token, start));

        }
        }
        catch (Exception e) {


        }
        return players;
    }

    /**
     * this method is responsible for starting and running game
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        Game game = new Game();
        Board bb = null;
        ;
        try {

            bb = game.createBoardFromFile(System.getProperty("user.dir") + "/src/board1",
                    System.getProperty("user.dir") + "/src/doors.txt", System.getProperty("user.dir") + "/src/tunnels");
        } catch (IOException e) {

            e.printStackTrace();
        }

        // Print banner ;)


System.out.println("CCCCCCCCCCCCCLLLLLLLLLLL            UUUUUUUU     UUUUUUUUEEEEEEEEEEEEEEEEEEEEEEDDDDDDDDDDDDD             OOOOOOOOO");
System.out.println("CCC::::::::::::CL:::::::::L            U::::::U     U::::::UE::::::::::::::::::::ED::::::::::::DDD        OO:::::::::OO");
System.out.println("CC:::::::::::::::CL:::::::::L            U::::::U     U::::::UE::::::::::::::::::::ED:::::::::::::::DD    OO:::::::::::::OO");
System.out.println("C:::::CCCCCCCC::::CLL:::::::LL            UU:::::U     U:::::UUEE::::::EEEEEEEEE::::EDDD:::::DDDDD:::::D  O:::::::OOO:::::::O");
System.out.println("C:::::C       CCCCCC  L:::::L               U:::::U     U:::::U   E:::::E       EEEEEE  D:::::D    D:::::D O::::::O   O::::::O");
System.out.println("C:::::C                L:::::L               U:::::D     D:::::U   E:::::E               D:::::D     D:::::DO:::::O     O:::::O");
System.out.println("C:::::C                L:::::L               U:::::D     D:::::U   E::::::EEEEEEEEEE     D:::::D     D:::::DO:::::O     O:::::O");
System.out.println("C:::::C                L:::::L               U:::::D     D:::::U   E:::::::::::::::E     D:::::D     D:::::DO:::::O     O:::::O");
System.out.println("C:::::C                L:::::L               U:::::D     D:::::U   E:::::::::::::::E     D:::::D     D:::::DO:::::O     O:::::O");
System.out.println("C:::::C                L:::::L               U:::::D     D:::::U   E::::::EEEEEEEEEE     D:::::D     D:::::DO:::::O     O:::::O");
System.out.println("C:::::C                L:::::L               U:::::D     D:::::U   E:::::E               D:::::D     D:::::DO:::::O     O:::::O");
System.out.println("C:::::C       CCCCCC  L:::::L         LLLLLLU::::::U   U::::::U   E:::::E       EEEEEE  D:::::D    D:::::D O::::::O   O::::::O");
System.out.println("C:::::CCCCCCCC::::CLL:::::::LLLLLLLLL:::::LU:::::::UUU:::::::U EE::::::EEEEEEEE:::::EDDD:::::DDDDD:::::D  O:::::::OOO:::::::O");
System.out.println("CC:::::::::::::::CL::::::::::::::::::::::L UU:::::::::::::UU  E::::::::::::::::::::ED:::::::::::::::DD    OO:::::::::::::OO");
System.out.println("CCC::::::::::::CL::::::::::::::::::::::L   UU:::::::::UU    E::::::::::::::::::::ED::::::::::::DDD        OO:::::::::OO");
System.out.println("CCCCCCCCCCCCCLLLLLLLLLLLLLLLLLLLLLLLL     UUUUUUUUU      EEEEEEEEEEEEEEEEEEEEEEDDDDDDDDDDDDD             OOOOOOOOO");
System.out.println("2016 All Rights Persevered");
System.out.println("By Hamid Abubakr");

        // input player info and position players on the board
        int nplayers1 = 0;
        while (nplayers1 < 3) {
        nplayers1 = inputNumber("How many players? Minimum of 3");
        }
        ArrayList<Player> players = inputPlayers(nplayers1, game);
        // game.updatePlayersonBoard();
        // Draw Board
        game.drawBoard();
        // create deck of cards for the game to play with

        // shuffle the deck of cards
        Collections.shuffle(game.getDeck().cards);

        // store three cards from deck into center of Board
        game.selectSecretCards();

        // deal the rest of the cards evenly to the players, while storing extra
        // cards that have not been dealt
        game.dealCards();

        // now start the game, and let the controller interact with user and
        // game model
        Random rand = new Random();
        // run the game till we have winner
        while (game.getStatus()) {
            for (Player p : game.getPlayers()) {
                if (p.InGame) {
                    int diceRoll = rand.nextInt(12);
                    if (diceRoll < 2) {

                        diceRoll = 2;
                    }
                    boolean moved = false;
                    // here we interact with the player to move him/her on the
                    // board
                    // is player in a room? if not , he can make an accusation
                    // or start moving
                    if (!p.inRoom) {
                        // first we ask if user wants to accuse
                        makeAccusation(p, game);
                        // is user still in game?
                        if (p.InGame) {
                            movePlayer(p, diceRoll, game);
                            moved = true;
                        }
                    }
                    // if player in room, and did not suggest, make him suggest
                    if (p.inRoom() && !p.getSuggest() && p.InGame) {
                        // ask user if he would like to make suggestion
                        String ask = inputString(p.getName() + ", would you like to make a suggestion? Enter Y or N");
                        if (ask.equalsIgnoreCase("Y")) {
                            ArrayList<String> suggestionList = makeSuggestion(p, game);
                            // if the user suggested, present(if any) the found
                            // suggestions
                            if (p.getSuggest()) {
                                presentSuggestions(p, suggestionList);
                                // now we offer him to make accusation
                                makeAccusation(p, game);
                            }
                            // after he suggests, ask if he wants to
                            // player does not want to suggest, so we just move
                            // him
                        } else {
                            if (!moved) {
                                movePlayer(p, diceRoll, game);
                            }
                        }
                        // turn has finished, so we reset all weapon squares
                        game.removeWeaponsfromRoom();
                        p.setSuggest(false);
                    }

                }
            }

        }

    }

    /**
     * method responsible for making accusations
     *
     * @param p
     * @param game
     */
    private static void makeAccusation(Player p, Game game) {
        String acuse = inputString(p.getName() + ", would you like to make an accusation? Enter Y or N");
        if (acuse.equalsIgnoreCase("Y")) {
            // we make accusation
            System.out.println("Please select a suspect");
            String suspect = null;
            String weapon = null;
            String room = null;
            try {
             suspect = characterList();
             weapon = weaponList();
             room = roomList();
            }
            catch (Exception e) {
                System.out.println("Invalid Input");

            }

            // next , we check with game whether accusations are valid
            boolean accusation = game.checkAccusation(suspect, weapon, room);
            if (accusation) {
                System.out.println();
                System.out.println();
                System.out.println("*******************************************************************************************");
                System.out.println("*******************************************************************************************");
                System.out.println("*******************************************************************************************");
                System.out.println("Congratulations, " +p.getName() + ",  your accusations were right!! You have won the game!!");
                System.out.println("*******************************************************************************************");
                System.out.println("********************************************************************************************");
                System.out.println("*******************************************************************************************");


            } else {
                // player has failed accusation, he will now be spectator
                p.InGame = false;
                System.out.println("Your accusations were wrong, " + p.getName() + ", you are out of the game");

            }
        }

    }

    /**
     * helper method that presents suggestions to the user
     *
     * @param p
     * @param suggestionList
     */
    private static void presentSuggestions(Player p, ArrayList<String> suggestionList) {
        if (suggestionList == null || suggestionList.size() < 1) {
            System.out.println();
            System.out.println("We found no matching suggestions with any other players!!");
        } else {
            // display what we found with other players
            System.out.println();
            System.out.println(p.getName() + ", we found the following suggestions with other players");
            for (String found : suggestionList) {
                System.out.println(found);
            }

        }

    }

    /**
     * this method is responsible for interacting with the user about his
     * suggestions and confirms with other players whether they have matching
     * cards. if they do a list is returned by this method which contains all
     * matched suggestion cards with other players
     *
     * @param p
     * @param game
     * @return
     */
    private static ArrayList<String> makeSuggestion(Player p, Game game) {
        ArrayList<String> totSuggestions = new ArrayList<String>();
        String suspectName = null;
        Player suspect = null;
        String weapon = null;

        p.setSuggest(true);
        try {
        System.out.println(p.getName() + ", its  time for you to give us some suggestions!");
        //suspectName = inputString(p.getName() + ", please enter the Character name of your suspect");
        suspectName = characterList();
        //token = Player.Character.valueOf(tokenName);
         suspect = game.getPlayerfromCharacter(suspectName);
         weapon = weaponList();
        }
        catch (Exception e) {
            System.out.println("Invalid Input");

        }
        String roomName = null;
        if (p.getlastSquare() instanceof Room) {

            Room room = (Room) p.getlastSquare();
            roomName = room.getFullName();

        }

        // first we bring the player and the weapon to the same room
        game.movesuggestedPlayertoRoom(suspect, p.getlastSquare().getName());
        game.movesuggestedWeapontoRoom(weapon, roomName);
        game.updatePlayersonBoard();
        game.drawBoard();
        // now we check with each user whether they have one or many
        // of suggested cards

        for (Player e : game.getPlayers()) {
            int count = 1;
            if (!e.equals(p)) {
                //System.out.println("It is now " + e.getName() + "'s turn to play");
                // we store what we find from each player into here
                ArrayList<String> suggestions = game.checkSuggestion(e, suspectName, weapon, roomName);
                if (suggestions.size() > 0) {

                    for (String suggestion : suggestions) {
                        System.out.println(count + ":  " + suggestion);
                        count++;
                    }

                    int suggest = inputNumber(e.getName()
                            + ", select the number associated with matching card that you would like to reveal:");

                    totSuggestions.add(suggestions.get(suggest - 1));
                } else {

                    System.out.println(e.getName() + " has no matching suggestions");
                }

            }

        }
        return totSuggestions;
    }

    /**
     * this is a key method, responsible for interacting with user into where
     * they want to go, interacts with player to determine whether movement is
     * valid,
     *
     * @param player
     * @param diceRoll
     * @param game
     */
    public static void movePlayer(Player player, int diceRoll, Game game) {
        boolean ended = false;
        while (diceRoll > 0 && !ended) {
            System.out.println();
            System.out.println("***************");
            System.out.println("W for up");
            System.out.println("S for Down");
            System.out.println("D for right");
            System.out.println("A for left");
            System.out.println("or E to END turn");
            System.out.println("********************");
            System.out.println();
            System.out.println("You have " + diceRoll + " moves left");
            String direction = inputString(player.getName() + " ,please enter a direction:");

            try {
                // if user enters E, we end turn
                if (!direction.equalsIgnoreCase("e")) {

                    Position oldPosition = new Position(player.getLocation().getX(), player.getLocation().getY());
                    Position newPosition = game.updatePosition(oldPosition, direction);
                    Square oldSquare = player.getlastSquare();
                    Square newSquare = game.getBoard().squareAt(newPosition);
                    // check of player move is valid
                    if (player.isValidMove(newPosition, oldPosition, game.getBoard())) {

                        // code that deals with movement that leads to room
                        // entrance event
                        if (!player.inRoom()) {

                            diceRoll--;

                            if (oldSquare instanceof Door && newSquare instanceof Room) {
                                // confirmed, user entered room
                                diceRoll = 0;

                                System.out.println(player.getName() + ", you have entered the "
                                        + ((Room) newSquare).getFullName());
                                player.inRoom = true;

                            }

                        }
                        // code that deals with movement that leads to room exit event
                        if (player.inRoom() && oldSquare instanceof Room && newSquare instanceof Door) {

                            System.out.println(
                                    player.getName() + ", you have exited the " + ((Room) oldSquare).getFullName());
                            player.inRoom = false;
                        }
                        boolean tunneled = false;

                        // code that deals with tunnel movement
                        if (newSquare instanceof Tunnel) {

                            Tunnel tunnel = (Tunnel) newSquare;
                            String roomtoMove = String.valueOf(tunnel.getAscroom());
                            System.out.println("i am in tunnel that leads to " + roomtoMove);
                            tunneled = game.movesuggestedPlayertoTunnel(player, roomtoMove);


                        }

                        // move is valid, update the Players position
                        // move player into the new position in the board,
                        // assuming he has not already tunneled out!
                        // then we update all board positions and draw.
                        if (!tunneled) {

                            game.moveUser(newPosition, oldPosition, player);
                        }
                        // finally we update the board and draw the board

                        game.updatePlayersonBoard();
                        //

                        game.drawBoard();
                        // check if player is in room. if they are then stop
                        // their
                        // movement and exit this method

                    } // player made invalid move
                    else {
                        System.out.println("Invalid direction, please try again");
                    }
                    // }
                } else {
                    System.out.println("Turn for " + player.getName() + " has ended");
                    player.resetVisitedSquares();
                    ended = true;

                }
            } catch (Exception e) {
                System.out.println("Invalid Input");

            }

        }
        System.out.println("Moves for " + player.getName() + " have finished.");
        player.resetVisitedSquares();
    }

}
