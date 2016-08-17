package cluedo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import cluedo.Room.roomName;
import cluedo.Weapon.Weapons;





/**
 * @author Hamid Abubakr
 * ID 300312017
 *
 */
public class Game {

	private Board board;
	private ArrayList<Player> players;
	private Deck deck;
	private ArrayList<Card> secret;
	private ArrayList<Card> remainingCards;
	private boolean gameStatus = false;
	private Map<String,Square> previousPlayerLocations;

	public Game(){
        // every new game instance start with a new deck of cards
		try {
			deck = new Deck();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		secret = new ArrayList<Card>();
		players = new ArrayList<Player>();
		remainingCards = new ArrayList<Card>();
		 previousPlayerLocations = new HashMap<String,Square>();

	}

	/**
	 * Get the current game board.
	 *
	 * @return
	 */
	public Board getBoard() {
		return board;
	}
/**
 * return the deck of cards
 * @return
 */
	public Deck getDeck() {
		return this.deck;
	}

	public Player getPlayer(String name) {
		for (Player p: this.players){
			if (p.getName().equals(name)) {

				return p;
			}

		}
		return null;
	}

	public void setPreviousPlayerLocations(String player, Square square){

		if (!this.previousPlayerLocations.containsKey(player)) {

			previousPlayerLocations.put(player, square);
		}



	}

public Square getPreviousPlayerLocations(String player){




		return previousPlayerLocations.get(player);
	}




/**
 * this is a key method. it takes in a textfile containing board logic, and returns board object. it also takes in a door and tunnel text files, to assign door and
 * tunnel object to their respective rooms
 * @param filename
 * @return
 * @throws IOException
 */
		public  Board createBoardFromFile(String filename, String filename2, String filename3) throws IOException {
			//read the door location data
			FileReader fr1 = new FileReader(filename2);
			BufferedReader br1 = new BufferedReader(fr1);
			ArrayList<Character> doors = new ArrayList<Character>();
			int width1 = -1;
			String line1;
			int counter1 = 0;
			line1 = br1.readLine();
			for(int x=0;x!=line1.length();++x) {
				char c = line1.charAt(x);
				doors.add(c);
			}
			//read the tunnel location data
			FileReader fr2 = new FileReader(filename3);
			BufferedReader br2 = new BufferedReader(fr2);
			ArrayList<Character> tunnels = new ArrayList<Character>();
			int width2 = -1;
			String line2;
			int counter2 = 0;
			line2 = br2.readLine();
			for(int x=0;x!=line2.length();++x) {
				char c = line2.charAt(x);
				tunnels.add(c);
			}

			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			ArrayList<String> lines = new ArrayList<String>();
			int width = -1;
			String line;
			int counter = 0;
			while((!(line = br.readLine()).isEmpty())) {
				counter++;
				//System.out.println(counter);
				lines.add(line);

				// now sanity check

				if(width == -1) {
					width = line.length();
				} else if(width != line.length()) {
					throw new IllegalArgumentException("Input file \"" + filename + "\" is malformed; line " + lines.size() + " incorrect width.");
				}
			}
			int doorCount = 0;
			int tunnelCount = 0;

			Board board = new Board(width,lines.size());
			for(int y=0;y!=lines.size();++y) {
				line = lines.get(y);

				for(int x=0;x!=width;++x) {
					char c = line.charAt(x);
					switch (c) {
						case '-' :
							board.addEmpty(y, x);
							break;
						case 'B' :
							board.addRoom("|B",y,x);
							break;
						case 'K' :
							board.addRoom("|K",y,x);
							break;
						case 'C' :
							board.addRoom("|C",y,x);
							break;
						case 'E' :
							board.addRoom("|E",y,x);
							break;
						case 'W' :
							board.addRoom("|W",y,x);
							break;
						case 'L' :
							board.addRoom("|L",y,x);
							break;
						case 'O' :
							board.addRoom("|O",y,x);
							break;
						case 'H' :
							board.addRoom("|H",y,x);
							break;
						case 'Y' :
							board.addRoom("|Y",y,x);
							break;
						case 'S' :
							board.addStart(y,x);
							break;
						case '*' :
							board.addBlock(y,x);
							break;
						case 'D' :
							board.addDoor(y,x, doors.get(doorCount));
							doorCount++;
							break;
						case 'T' :
							board.addTunnel(y,x, tunnels.get(tunnelCount));
							tunnelCount++;
							break;
						case 'F' :
							board.addCenter(y,x);
							break;



					}


				}


			}


            this.board = board;
			return board;
		}


/**
 * this method contains the logic that sets the players on the board based on chosen character
 * @param players
 * @param board
 */
		public void updatePlayersonBoard() {
			for (Player p: this.players){
				this.getBoard().getSquares()[p.getLocation().getX()][p.getLocation().getY()] = p.getLocation();
			}


		}
/**
 * this method contains the logic to ensure characters are located in
 * their correct position when game is initialised
 * @param character
 * @return
 */
		public int[] getTokenStartLocation(Player.Character character){
			for (Square s:this.getBoard().getStartingLocations()) {
				if (character.equals(Player.Character.MissScarlet)) {

					int[] xy = {19,25};
					return xy;
				}

				else if (character.equals(Player.Character.MrsWhite)) {

					int[] xy = {0,10};
					return xy;
				}

				else if (character.equals(Player.Character.TheReverendGreen)) {

					int[] xy = {0,15};
					return xy;
				}
				else if (character.equals(Player.Character.MrsPeacock)) {

					int[] xy = {6,25};
					return xy;
				}
				else if (character.equals(Player.Character.ColonelMustard)) {

					int[] xy = {17,1};
					return xy;
				}
				else if (character.equals(Player.Character.ProfessorPlum)) {

					int[] xy = {24,8};
					return xy;
				}


			}


			return null;
		}
/**
 * this method contains the logic to generate a random card from the deck of cards in the game, note that when
 * card is handed to use it is removed from the deck
 * @return
 */
		public Card getRandomCard(){
		Random rand = new Random();
		int index = 0;
		index = rand.nextInt(this.deck.cards.size());
		Card chosen = this.deck.cards.get(index);
		//remove selected card from deck

		//return random card
		return chosen;

		}
		/**
		 * selects three secret cards and store them in list
		 */

		public void selectSecretCards(){
			System.out.println("the secret deck size is: " + this.deck.cards.size());
			Card weapon = this.getRandomCard();
			while (!weapon.getype().equals("Weapon")) {

				weapon = this.getRandomCard();

			}
			this.deck.remove(weapon);
			Card room = this.getRandomCard();
			while (!room.getype().equals("Room")) {

				room = this.getRandomCard();

			}
			this.deck.remove(room);
			Card suspect = this.getRandomCard();
			while (!suspect.getype().equals("Character")) {

				suspect = this.getRandomCard();

			}
			this.deck.remove(suspect);
				secret.add(weapon);
				secret.add(room);
				secret.add(suspect);
				System.out.println(" the secret deck size is: " + this.deck.cards.size());

		}
		public ArrayList<Card> getSecretCards(){

         return secret;
		}
  /**
   * deal the cards to all the players
   */
		public void dealCards() {
			int playerNum = this.players.size();
			int cardsAmount = this.deck.cards.size();
			System.out.println(" the deck size are: " + deck.cards.size());


			while ((cardsAmount%playerNum) != 0) {
				Card throwAway = this.getRandomCard();
				remainingCards.add(throwAway);
				this.deck.remove(throwAway);
				cardsAmount = this.deck.cards.size();
			}

			while(this.deck.cards.size() > 0) {
			for (Player p: this.players) {
				Card card = this.getRandomCard();
				p.GiveCard(card);
				this.deck.remove(card);
			}
			}
			System.out.println(" the reminaining cards are: " + remainingCards.size());

		}
		/**
		 * return a new position based on input direction
		 * @param direction
		 * @return
		 */
		public Position updatePosition(Position oldPosition, String direction) {
			if (direction.equalsIgnoreCase("S")) {
				return new Position(oldPosition.row()+1,oldPosition.column());
			}
			else if (direction.equalsIgnoreCase("W")) {
				return new Position(oldPosition.row()-1,oldPosition.column());
			}
			else if (direction.equalsIgnoreCase("D")) {
				return new Position(oldPosition.row(),oldPosition.column()+1);
			}
			else if (direction.equalsIgnoreCase("A")) {
				return new Position(oldPosition.row(),oldPosition.column()-1);
			}
			return null;
		}

		public boolean getStatus() {
			// TODO Auto-generated method stub
			return this.gameStatus;
		}
/**
 * draw the simple 2D text board
 */
		public void drawBoard() {


			for (Square[] s: this.getBoard().getSquares()) {
				for (Square a:s) {
					System.out.printf(a.getName());

				}
				System.out.println();

			}

		}
/**
 * initialize a player on the board, store his Start location into his previously visited squares,
 * @param player
 */
		public void addPlayer(Player player) {
			//add player to game
			this.players.add(player);
			//retrieve the default square type user will ibe iniitialized upon
			Square previous = this.getBoard().getSquares()[player.getLocation().getX()][player.getLocation().getY()];
			//System.out.println(previous.getName());
			// add the square to the list of visited squares
			player.updateVisitedSquares(previous);
			this.getBoard().getSquares()[player.getLocation().getX()][player.getLocation().getY()] = player.getLocation();


		}

public ArrayList<Player> getPlayers() {
	// TODO Auto-generated method stub
	return this.players;
}

public void moveUser(Position newPosition, Position oldPosition, Player player) {

	// setting the value of old position to
	this.getBoard().getSquares()[oldPosition.row()][oldPosition.column()] = player.getlastSquare();
	this.getBoard().getSquares()[oldPosition.row()][oldPosition.column()].setStatus(false);
	//then we save the type of new square the user is visting
    Square newSquare = this.getBoard().getSquares()[newPosition.row()][newPosition.column()];
    //we update the users square information
	this.getPlayer(player.getName()).getLocation().setX(newPosition.row());
	this.getPlayer(player.getName()).getLocation().setY(newPosition.column());
	//next we add the old the previous squqre to users history
	player.updateVisitedSquares(newSquare);

}
/**
 * check witch each player whether they have one or many of suggested cards, if
 * they do, then a message is returned from ther player(s)to the originator of message
 * @param suspect
 * @param weapon
 * @param roomName
 */
public ArrayList<String> checkSuggestion(Player player, String suspect, String weapon, String roomName) {
	ArrayList<String> suggestions = new ArrayList<String>();
	for (Card h:player.getHand()) {
		if (h.getName().equals(suspect)) {
			suggestions.add(suspect);

		}
		if (h.getName().equals(weapon)) {
			suggestions.add(weapon);

		}
		if (h.getName().equals(roomName)) {
			suggestions.add(roomName);

		}

	}
	return suggestions;

}
/**
 * this method should physically move a player to suggested room
 * @param name
 */
public void movesuggestedPlayertoRoom(Player player, String roomtoMoveInto) {
	for (Square[] s: this.getBoard().getSquares()) {
		for (Square a:s) {
			if (a.getName().equals(roomtoMoveInto) && !a.isOccupied()) {
				//Player suspect = this.getPlayer(suspectName);
				Position current = new Position(player.getLocation().getX(), player.getLocation().getY());
				Position target = new Position(a.getX(), a.getY());
				this.moveUser(target, current, player);
				// after the user has moved, we need to ensure user object  it has been placed into room
				player.isInRoom(true, roomtoMoveInto);
				break;
			}

		}


	}

}
/**
 *  move player from one room, through tunnel to connected room
 * @param player
 * @param roomtoMoveInto
 */
public boolean movesuggestedPlayertoTunnel(Player player, String roomtoMoveInto) {
	boolean match = false;
	for (Square[] s: this.getBoard().getSquares()) {
		for (Square a:s) {
			if ( !match && a instanceof Tunnel && !a.isOccupied() && ((Tunnel) a).matchedTunnel(roomtoMoveInto, Character.toString(((Tunnel) a).getAscroom()))) {


				Position current = new Position(player.getLocation().getX(), player.getLocation().getY());
				Position target = new Position(a.getX(), a.getY());
				this.moveUser(target, current, player);
				// after the user has moved, we need to ensure user object  it has been placed into room
				player.isInRoom(true, roomtoMoveInto);
				return true;
			}

		}


	}
	return false;

}
/**
 * method that brings weapon to room once suggestion is made by player
 * @param Weapon
 * @param roomtoMoveInto
 */

public void movesuggestedWeapontoRoom(String Weapon, String roomtoMoveInto) {
	boolean match = false;
	for (Square[] s: this.getBoard().getSquares()) {
		for (Square a:s) {
			if ( !match && a instanceof Room && ((Room) a).getFullName().equals(roomtoMoveInto) ) {
            String weaponName = Weapons.valueOf(Weapon).getVal();
            Weapon weaponSquare = new Weapon(a.getX(),a.getY(),weaponName, roomtoMoveInto);
            // update board
            match = true;
            this.getBoard().getSquares()[a.getX()][a.getY()] = weaponSquare;

				break;
			}

		}


	}

}
/**
 * this method returns a player based on the Character he is playing
 * @param suspectName
 * @return
 */
public Player getPlayerfromCharacter(String suspectName) {
	for (Player p: this.getPlayers()) {


		if (p.getCharacter().toString().equals(suspectName)) {
			return p;
		}
	}
	return null;
}
/**
 * check whether user accuasations are correct
 * @param suspect
 * @param weapon
 * @param roomName
 * @return
 */

public boolean checkAccusation(String suspect, String weapon, String roomName) {
	int count = 0;
	for (Card h:this.getSecretCards()) {
		if (h.getName().equals(suspect)) {
		count++;

		}
		if (h.getName().equals(weapon)) {
			count++;

		}
		if (h.getName().equals(roomName)) {
			count++;

		}

	}
	if (count == 3) {this.gameStatus = false;}

	return (count == 3);
}

/**
 * update status of game
 * @param b
 */
public void setStatus(boolean b) {
	this.gameStatus = b;

}
/**
 * removes all weapons from rooms
 */
public void removeWeaponsfromRoom() {

	for (Square[] s: this.getBoard().getSquares()) {
		for (Square a:s) {
			if ( a instanceof Weapon ) {
            Room Room = new Room(roomName.valueOf(((Weapon) a).getRoom()).getroomName(), a.getX(), a.getY());

            // update board
            this.getBoard().getSquares()[a.getX()][a.getY()] = Room;

				break;
			}

		}


	}



}
// move character token to room
public void moveTokentoRoom(String suspectName, String roomtoMoveInto) {
	for (Square[] s: this.getBoard().getSquares()) {
		for (Square a:s) {
			if (   a instanceof Room && ((Room) a).getFullName().equals(roomtoMoveInto)  && !a.isOccupied()) {
				//Player suspect = this.getPlayer(suspectName);
				System.out.println("i does here motherfucker");
				Position target = new Position(a.getX(), a.getY());

                PlayerSquare token = new PlayerSquare(cluedo.Player.Character.valueOf(suspectName).getNumVal(),a.getX(), a.getY(), cluedo.Player.Character.valueOf(suspectName).ImageEnum());
	            // update board
	            this.getBoard().getSquares()[a.getX()][a.getY()] = token;
	            return;
				//break;

			}

		}


	}

}

}
