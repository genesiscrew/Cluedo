package cluedo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import java.util.zip.CheckedOutputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.junit.experimental.theories.Theories;

import cluedo.Player.Character;
import cluedo.Room.roomName;
import cluedo.Weapon.Weapons;

public class Controller implements ActionListener {
	private static Runnable r;
	private static Game game;
	JFrame cb;
	private static View GUI;
	private int x;
	private static String chosenSuggestedCard;
	private static boolean suggestionComplete;
	private static Player currentPlayer;
	private static boolean moveRequest = false;
	private static Position coordinate;
	private static boolean ended = false;
	private static int diceRoll = 0;
	private static boolean accuseRequest;
	private static int cardsSelected = 0;
	private static boolean suggestAccepted;
	private static int suggesting = -1;
	private static JFrame characterCardMenuFrame;
	private static String suspectName;
	private static String weapon;
	private static String room;
	private static JFrame weaponCardMenuFrame;
	private static JFrame roomCardMenuFrame;
	private static ArrayList<String> suggestionList = new ArrayList<String>();
	private static int accuseDecision = -1;

	public Game getGame() {

		return this.game;
	}

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
			} catch (IOException | NumberFormatException e) {
				System.out.println("Please enter a number!");
			}
		}
	}

	// listens to input in GUI
	@Override
	public void actionPerformed(ActionEvent e) {

		// check if tile pressed in board. if so send coordinates

		for (int i = 0; i < GUI.getGUIBoard().length; i++) {
			for (int j = 0; j < GUI.getGUIBoard()[i].length; j++) {
				if (GUI.getGUIBoard()[i][j] == e.getSource()) {
					this.sendCoordinates(i, j);

					// confirmed one of the buttons in our tile arrrat has been
					// pressed
					moveRequest = true;

				}
			}
		}
		// check if user made suggestion or accusation
		if (game.getStatus()) {
			for (Component i : GUI.tools.getComponents()) {
				if (i == e.getSource()) {

					JButton b = (JButton) i;
					String buttonType = b.getText();

					if (buttonType.equals("Accuse")) {
						accuseRequest = true;

					}
				}
			}
		}
		// get character suggestion cards
		for (Component w : GUI.getCharacterCardMenuPanel().getComponents()) {

			if (e.getSource() == w) {
				System.out.println(cardsSelected);
				// get the name of card
				if (cardsSelected == 0) {
					suspectName = w.getName();

					cardsSelected++;
				}
			}

		}
		// getting weapon card
		for (Component w : GUI.getWeaponCardMenuPanel().getComponents()) {

			if (e.getSource() == w) {

				// get the name of card
				if (cardsSelected == 1) {
					weapon = w.getName();
					cardsSelected++;
				}
			}

		}
		// get room card
		for (Component w : GUI.getRoomCardMenuPanel().getComponents()) {

			if (e.getSource() == w) {

				// get the name of card
				if (cardsSelected == 2) {
					room = w.getName();
					cardsSelected++;
				}
			}

		}

		// check if user clicked on card image suggested
		JPanel test = null;
		for (Component i : GUI.tools.getComponents()) {
			if (i == e.getSource()) {

				JButton tmp = (JButton) i;

				if (!suggestionComplete && !suggestionList.isEmpty()) {

					if (suggestionList.contains(tmp.getName())) {

						chosenSuggestedCard = tmp.getName();
						suggestionComplete = true;
					}
				}

				ImageIcon tmpIcon = (ImageIcon) ((JButton) tmp).getIcon();

			}
		}

	}

	private Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return this.currentPlayer;
	}

	private static void setCurrentPlayer(Player p) {
		// TODO Auto-generated method stub
		currentPlayer = p;
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
	 *
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
			if (selection < 1 || selection > 6) {
				System.out.println("Please select a number between 1 to 6!");
				// recursively call method untill proper selection is made
				token = characterList();
				return token;
			}
			;
		} catch (Exception e) {
			System.out.println("Invalid Input");

		}

		for (Player.Character r : Character.values()) {
			if (r.getNumVal() == selection) {
				token = r.name();

			}
		}

		return token;

	}

	/**
	 * helper method to enable weapon selection
	 *
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
			if (selection < 1 || selection > 6) {
				System.out.println("Please select a number between 1 to 6!");
				// recursively call method untill proper selection is made
				token = characterList();
				return token;
			}
			;
		} catch (Exception e) {
			System.out.println("Invalid Input");

		}
		// convert the integer input into a character name

		for (int i = 0; i < Weapons.values().length; i++) {
			if (i == (selection - 1)) {
				token = Weapons.values()[i].toString();

			}
		}

		return token;

	}

	/**
	 * helper method to enable room selection
	 *
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
			if (selection < 1 || selection > 9) {
				System.out.println("Please select a number between 1 to 9!");
				// recursively call method untill proper selection is made
				token = characterList();
				return token;
			}
			;
		} catch (Exception e) {
			System.out.println("Invalid Input");

		}
		// convert the integer input into a character name

		for (int i = 0; i < Room.roomName.values().length; i++) {
			if (i == (selection - 1)) {
				token = Room.roomName.values()[i].toString();

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
	 *             [
	 */
	private static ArrayList<Player> inputPlayers(int nplayers, Game game) throws IOException {
		// set up the tokens
		ArrayList<Player> players = null;
		ArrayList<String> names = new ArrayList<String>();
		try {
			ArrayList<Player.Character> tokens = new ArrayList<Player.Character>();
			for (Player.Character t : Player.Character.values()) {
				tokens.add(t);

			}
			Square[] startPos = game.getBoard().getStartingLocations();
			players = new ArrayList<Player>();

			for (int i = 0; i != nplayers; ++i) {
				String name = inputString("Player #" + (i + 1) + " name?");
				if (i > 0) {
					while (names.contains(name)) {
						System.out.println("Duplicate name entered! please modify your name of enter a new name");
						name = inputString("Player #" + (i + 1) + " name?");
					}
				}
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
				// we get the required starting location based on character
				// chosen
				int[] xy = game.getTokenStartLocation(token);
				PlayerSquare start = new PlayerSquare(token.getNumVal(), xy[0], xy[1], token.ImageEnum());
				tokens.remove(token);
				names.add(name);
				game.addPlayer(new Player(name, token, start));

			}
		} catch (Exception e) {

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
		game = new Game();
		Board bb = null;
		Controller controller = new Controller();

		;
		try {

			bb = game.createBoardFromFile(System.getProperty("user.dir") + "/src/board1",
					System.getProperty("user.dir") + "/src/doors.txt", System.getProperty("user.dir") + "/src/tunnels");
		} catch (IOException e) {

			e.printStackTrace();
		}

		Controller.GUI = new View(controller);

		Controller.GUI.add((Controller.GUI).getGui());
		Controller.GUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Controller.GUI.setLocationByPlatform(true);

		// ensures the frame is the minimum size it needs to be
		// in order display the components within it

		Controller.GUI.pack();
		// ensures the minimum size is enforced.
		Controller.GUI.setSize(1248, 768);
		// f.setResizable(false);
		Controller.GUI.setVisible(true);
		Controller.GUI.repaint();
		// initialize card menu frames
		Controller.GUI.characterCardMenuCreate();
		Controller.GUI.weaponCardMenuCreate();
		Controller.GUI.roomCardMenuCreate();
		// assign card menus to variables
		characterCardMenuFrame = Controller.GUI.getCharacterCardMenuFrame();
		weaponCardMenuFrame = Controller.GUI.getWeaponCardMenuFrame();
		roomCardMenuFrame = Controller.GUI.getRoomCardMenuFrame();

		// input player info and position players on the board
		int nplayers1 = 0;
		while (nplayers1 < 3) {
			nplayers1 = inputNumber("How many players? Minimum of 3");
		}
		ArrayList<Player> players = inputPlayers(nplayers1, game);

		// Draw Board
		game.drawBoard();
		GUI.updateBoard(game.getPlayers().get(0), game.getPlayers().get(0).getName() + "'s turn to play");

		// shuffle the deck of cards
		Collections.shuffle(game.getDeck().cards);
		System.out.println("deck size is " + game.getDeck().cards.size());

		// store three cards from deck into center of Board
		game.selectSecretCards();

		System.out.println("deck size is " + game.getDeck().cards.size());

		// deal the rest of the cards evenly to the players, while storing extra
		// cards that have not been dealt
		game.dealCards();

		// now start the game, and let the controller interact with user and
		// game model
		game.setStatus(true);
		Random rand = new Random();
		// run the game till we have winner
		while (game.getStatus()) {
			for (Player p : game.getPlayers()) {
				setCurrentPlayer(p);
				if (p.InGame) {
					diceRoll = rand.nextInt(12);

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
						// is user still in game?
						if (p.InGame) {
							// did user request accusation? before moving
							if (accuseRequest) {
								makeAccusation(game);
							}
							// if player still in game then move him
							// if (p.InGame) {
							while (diceRoll > 0 && !ended) {

								if (movePlayer(game)) {
									System.out.println("player moved");
									diceRoll--;

								}

								// check again for accusation during movement

							}
							moved = true;
							ended = false;
							p.resetVisitedSquares();

						}
						
					}
					// if player in room, and did not suggest, make him suggest
					if (p.inRoom() && !p.getSuggest() && p.InGame) {
						// ask user if he would like to make suggestion
						suggesting = Controller.GUI.makeSuggestion(p);
						System.out.println(suggesting);
						// wait untill player makes

						if (suggesting == 0) {
							suggesting = -1;
							// we show up the character card menu and set
							// suggestion mode to true
							
							characterCardMenuFrame.setVisible(true);

							while (cardsSelected < 2) {
								if (cardsSelected == 1) {
									characterCardMenuFrame.setVisible(false);
									weaponCardMenuFrame.setVisible(true);
								}

								try {
									Thread.sleep(5);
								} catch (InterruptedException e) {

									e.printStackTrace();
								}
							}

							weaponCardMenuFrame.setVisible(false);
							System.out.println("three cards chosen");
							cardsSelected = 0;

							ArrayList<String> suggestionList = makeSuggestion(game);
							// if the user suggested, present(if any) the found
							// suggestions
							if (p.getSuggest()) {
								presentSuggestions(suggestionList);
								p.setSuggest(false);

							}
						}

						else {
							System.out.println("we should be here");
							System.out.println(moved);
							if (!moved) {
								while (diceRoll > 0 && !ended) {
									if (movePlayer(game)) {
										System.out.println("player moved");
										diceRoll--;

									}
								}
								moveRequest = false;
								moved = true;
								ended = false;
								p.resetVisitedSquares();
							}
						}

					}
					// after he suggests/or not, ask if he wants to
					// player does not want to suggest, so we just move
					// him

					// turn has finished, so we reset all weapon squares
					game.removeWeaponsfromRoom();

					p.resetVisitedSquares();
					p.setSuggest(false);
				}

			}
		}

	

	}

	/**
	 * method responsible for making accusations
	 * 
	 * @param game
	 *
	 * @throws IOException
	 */
	private static void makeAccusation(Game game) throws IOException {
		// String acuse = inputString(p.getName() + ", would you like to make an
		// accusation? Enter Y or N");
		// if (acuse.equalsIgnoreCase("Y")) {
		// we make accusation

		accuseDecision = GUI.makeAccusation(currentPlayer);

		if (accuseDecision == 0) {
			// we show up the character card menu and set
			// suggestion mode to true
			accuseRequest = true;
			characterCardMenuFrame.setVisible(true);

			while (cardsSelected < 3) {
				if (cardsSelected == 1) {
					characterCardMenuFrame.setVisible(false);
					weaponCardMenuFrame.setVisible(true);
				} else if (cardsSelected == 2) {
					weaponCardMenuFrame.setVisible(false);
					roomCardMenuFrame.setVisible(true);
				}

				try {
					Thread.sleep(5);
				} catch (InterruptedException a) {

					a.printStackTrace();
				}
			}
			roomCardMenuFrame.setVisible(false);

			cardsSelected = 0;

			// next , we check with game whether accusations are valid
			boolean accusation = game.checkAccusation(suspectName, weapon, room);
			if (accusation) {
				JOptionPane.showMessageDialog(GUI,
						currentPlayer.getName() + ", you have won the game, spot on accusations!", "You Win!", JOptionPane.INFORMATION_MESSAGE);

			} else {
				// player has failed accusation, he will now be spectator

				currentPlayer.InGame = false;
				JOptionPane.showMessageDialog(GUI,
						currentPlayer.getName() + ", your accusations were wrong, sorry, but you are out of the game!",  "You Lost!", JOptionPane.INFORMATION_MESSAGE);

			}
		}

		// }
		accuseRequest = false;

	}

	/**
	 * helper method that presents suggestions to the user
	 * 
	 * @param suggestionList
	 */
	private static void presentSuggestions(ArrayList<String> suggestionList) {
		if (suggestionList == null || suggestionList.size() < 1) {
	
			JOptionPane.showMessageDialog(GUI, currentPlayer.getName() + ", we found no matching suggestions with other players", " No matching suggestions found", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// display what we found with other players
			String suggestString = currentPlayer.getName() + ", we found the following suggestions with other players";
			int count = 1;
			for (String suggestion : suggestionList) {
				suggestString += "\n";
				suggestString += (count + ":  " + suggestion);
				count++;
			}

			JOptionPane.showMessageDialog(GUI, suggestString, "Matching suggestions found!", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	/**
	 * this method is responsible for interacting with the user about his
	 * suggestions and confirms with other players whether they have matching
	 * cards. if they do a list is returned by this method which contains all
	 * matched suggestion cards with other players
	 * 
	 * @param game
	 *
	 * @return
	 */
	private static ArrayList<String> makeSuggestion(Game game) {
		ArrayList<String> totSuggestions = new ArrayList<String>();
		// suspectName = null;
		Player suspect = null;
		// weapon = null;

		currentPlayer.setSuggest(true);
		try {
			System.out.println(currentPlayer.getName() + ", its  time for you to give us some suggestions!");
			suspect = game.getPlayerfromCharacter(suspectName);
			// weapon = weaponList();
		} catch (Exception e) {
			System.out.println("Invalid Input");

		}
		String roomName = null;
		if (currentPlayer.getlastSquare() instanceof Room) {

			Room room = (Room) currentPlayer.getlastSquare();
			roomName = room.getFullName();

		}

		// first we bring the player token and the weapon to the same room
		// we check if token is chosen by a player, if not we just move a token
		// then, else we move an actual player there
		if (suspect == null) {
			// token not chosen

			game.moveTokentoRoom(suspectName, roomName);

		} else {
			game.movesuggestedPlayertoRoom(suspect, currentPlayer.getlastSquare().getName());
		}
		game.movesuggestedWeapontoRoom(weapon, roomName);
		game.updatePlayersonBoard();
		// game.drawBoard();
		GUI.updateBoard(currentPlayer, null);
		// ((CluedoBoardWithColumnsAndRows) f).drawBoard();
		// now we check with each user whether they have one or many
		// of suggested cards

		for (Player e : game.getPlayers()) {
			int count = 1;
			if (!e.equals(currentPlayer)) {
				// System.out.println("It is now " + e.getName() + "'s turn to
				// play");
				// we store what we find from each player into here
				suggestionList = game.checkSuggestion(e, suspectName, weapon, roomName);
				String suggestString = null;
				if (suggestionList.size() > 0) {
					suggestString = e.getName()
							+ ", the following is a list of cards in your hand that matches the suggestions made by " + currentPlayer.getName() 
							+ ", please pick one to reveal by clicking on it's image "
							;

					for (String suggestion : suggestionList) {
						suggestString += "\n";
						suggestString += (count + ":  " + suggestion);
						count++;
					}

					JOptionPane.showMessageDialog(GUI, suggestString, e.getName() + "'s turn to confirm suggestions", JOptionPane.INFORMATION_MESSAGE );

					// int suggest = inputNumber(e.getName()
					// + ", select the number associated with matching card that
					// you would like to reveal:");

					GUI.updateBoard(e,
							e.getName() + ", please select a card from the following that you would like to reveal to "
									+ currentPlayer.getName());

					// wait untill player selects a matching card
					while (!suggestionComplete) {
						try {
							Thread.sleep(5);
						} catch (InterruptedException a) {
							// TODO Auto-generated catch block
							a.printStackTrace();
						}
					}
					// reset suggestion status so that next player can suggest
					// as well
					suggestionComplete = false;
					totSuggestions.add(chosenSuggestedCard);
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
	 * @param game
	 * @param diceRoll
	 *
	 * @throws IOException
	 */
	public static boolean movePlayer(Game game) throws IOException {

		// wait untill user has selected a tile or has requested an accusation
		while (coordinate == null && !accuseRequest) {
			try {
         
				Thread.sleep(5);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

		// check if user requested accusation during movement
		if (accuseRequest) {
			makeAccusation(game);
		}

		try {
			// if user enters E, we end turn
			if (coordinate != null && !accuseRequest) {

				Position oldPosition = new Position(currentPlayer.getLocation().getX(),
						currentPlayer.getLocation().getY());
				// Position newPosition = game.updatePosition(oldPosition,
				// direction);
				Position newPosition = coordinate;
				System.out.println(oldPosition.row());
				System.out.println(newPosition.row());

				Square oldSquare = currentPlayer.getlastSquare();
				Square newSquare = game.getBoard().squareAt(newPosition);
				// check of player move is valid
				if (currentPlayer.isValidMove(newPosition, oldPosition, game.getBoard())) {

					// code that deals with movement that leads to room
					// entrance event
					if (!currentPlayer.inRoom()) {

						if (oldSquare instanceof Door && newSquare instanceof Room) {
							// confirmed, user entered room
							diceRoll = 0;

							// System.out.println(
							// player.getName() + ", you have entered the " +
							// ((Room) newSquare).getFullName());
							currentPlayer.isInRoom(true, ((Room) newSquare).getFullName());

						}

					}
					// code that deals with movement that leads to room exit
					// event
					if (currentPlayer.inRoom() && oldSquare instanceof Room && newSquare instanceof Door) {

						// System.out.println(
						// player.getName() + ", you have exited the " + ((Room)
						// oldSquare).getFullName());
						currentPlayer.isInRoom(false, "");
					}
					boolean tunneled = false;

					// code that deals with tunnel movement
					if (newSquare instanceof Tunnel) {

						Tunnel tunnel = (Tunnel) newSquare;
						String roomtoMove = String.valueOf(tunnel.getAscroom());
						// System.out.println("i am in tunnel that leads to " +
						// roomtoMove);
						tunneled = game.movesuggestedPlayertoTunnel(currentPlayer, roomtoMove);
						currentPlayer.isInRoom(true, roomtoMove);

					}

					// move is valid, update the Players position
					// move player into the new position in the board,
					// assuming he has not already tunneled out!
					// then we update all board positions and draw.
					if (!tunneled) {

						game.moveUser(newPosition, oldPosition, currentPlayer);

					}
					// finally we update the board and draw the board

					game.updatePlayersonBoard();
					//

					GUI.updateBoard(currentPlayer, null);

					coordinate = null;
					// check if player is in room. if they are then stop
					// their
					// movement and exit this method

				} // player made invalid move
				else {
					
					return false;
				}
				// }
			} else {
				
				coordinate = null;
				ended = true;
				if (!currentPlayer.inRoom) {
					return true;
				}
				return false;

			}
		} catch (Exception e) {
			
			return false;

		}

		if (!currentPlayer.inRoom) {
			return true;
		}
		return false;

	}

	public void sendCoordinates(int ii, int jj) {
		this.coordinate = new Position(ii, jj);

	}

}
