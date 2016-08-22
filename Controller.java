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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.junit.experimental.theories.Theories;

import cluedo.Player.Character;
import cluedo.Room.roomName;
import cluedo.Weapon.Weapons;

public class Controller implements MouseListener {
	private static Runnable r;
	private static Game game;
	JFrame cb;
	private static GameView GUI;
	private int x;
	private static boolean endTurn;
	private static boolean helpActivated = false;
	public static String help = "How To Play Cluedo\n"
			+ "You want to move to a different room in the mansion on every turn. Roll the die and move your token the corresponding number of spaces. \n "
			+ "You can change directions as often as you like, \n"
			+ "as long as you still have moves left on the roll, but you can’t cross over the same tile more than once on a single turn.\n "
			+ "Additionally, you may not land on a square occupied by another suspect.\n"
			+ "If you are in a room with a secret passage, you may move through the secret passage\n "
			+ "instead of rolling, simply announce your play at the beginning of your turn.\n"
			+ "Entering and Exiting a Room:\n"
			+ "There are special rules for entering and exiting rooms. First, once you pass from one room to another, you may not move further,\n"
			+ " even if you have moves left on your roll. Second, you cannot pass into a room that is blocked by another suspect token.\n "
			+ "It will sometimes happen that both the entrance and exit of a room are blocked, in which case, you can’t leave the \n"
			+ "room until someone moves on their turn. Further, you may not enter, and then re-enter a room on the same turn.\n"
			+ "Suggesting Suspects/Rooms/Weapons:\n"
			+ "When you enter a room, make a suggestion to help solve the murder. To make a suggestion, move a suspect token and a weapon\n"
			+ " token into a room on the board, and suggest that they committed the crime with that weapon in that room.\n "
			+ "You can only suggest that the murder occurred in the room you presently occupy.\n"
			+ "You can make suggestions about items/rooms/suspects in your hand.\n"
			+ "You may only make a suggestion upon entering a room, and can’t make multiple suggestions by entering/exiting a room on a single turn.\n"
			+ "You can’t forfeit a turn to remain in a room (so you could make another suggestion there next turn),\n "
			+ "but if you are blocked in a room by other tokens, you must stay in the room.\n"
			+ "If your token was moved into a room, you may either roll or make a suggestion for that room on your next turn because you will\n"
			+ " have entered the room.\n"
			+ "You can make suggestions for suspect/weapons that are already in the room you occupy.\n"
			+ "There is no limit to the number of weapons and suspects that can be in a single room.\n"
			+ "Proving and Disproving Suggestions:\n"
			+ "Once you make a suggestion, your opponents attempt to prove the suggestion false, beginning with the player to your left.\n "
			+ "That player looks at their cards for one of the three cards that you just named, and if they have at least one of them,\n"
			+ " they must show you (and only you) the matching card of their choice. If the player on your left is unable to disprove your\n "
			+ "suggestion, the next player must attempt to do so. Once a player shows you a card that matches one in your suggestion, \n"
			+ "cross that card off of your detective notepad.\n"

			+ "Making an Accusation and Winning:\n"
			+ "When you think you’ve solved the mystery ,you can make an accusation. Unlike suggestions, you don’t have to be occupying\n "
			+ "a room to make an accusation that the crime occurred in there. You make an accusation by stating that you “accuse (suspect)\n "
			+ "of committing the crime in the (room) with the (weapon). You are allowed to make both a suggestion and an accusation on the\n "
			+ "same turn ,but keep in mind, if you are wrong on your accusation, you are unable to move further and cannot win the game\n "
			+ "(though you still try to disprove the other players suggestions). When you make your Accusation, look at the three cards \n"
			+ "in the envelope. If you are correct, you win the game. If you are incorrect, you cannot win the game.\n"
			+ "Note: If your token is in a door way and you make a false accusation, move it into the center of the room to free up the passage way.\n ";
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
	private static boolean diceRolled;
	private static int diceRoll1;
	private static int diceRoll2;

	public Game getGame() {

		return this.game;
	}

	// key method that listens to different kinds of input from the GUI and
	// feeds it into the controller

	@Override
	public void mouseClicked(MouseEvent e) {

		// check if tile pressed in board. if so send coordinates
		try {
			if (GUI.getGUIBoard() != null) {
				for (int i = 0; i < GUI.getGUIBoard().length; i++) {
					for (int j = 0; j < GUI.getGUIBoard()[i].length; j++) {
						if (GUI.getGUIBoard()[i][j] == e.getSource()) {
							// check if help button has been pressed

							try {
								if (e.getSource() instanceof JButton && GUI.getGUIBoard()[i][j].getName().equals("?")) {
									helpActivated = true;
								}
							} catch (Exception d) {

								helpActivated = false;
							}

							if (e.getSource() instanceof JButton && diceRoll > 0) {
								this.sendCoordinates(i, j);

								// confirmed one of the buttons in our tile
								// arrray
								// has
								// been
								// pressed
								moveRequest = true;
							}
						}
					}
				}
			}
			// check if user made accusation or rolled the dice
			if (game.getStatus()) {
				for (Component i : GUI.menuPanel.getComponents()) {
					if (i == e.getSource()) {

						JButton b = (JButton) i;
						String buttonType = b.getText();

						if (buttonType.equals("Accuse") && diceRoll > 0) {
							accuseRequest = true;

						} else if (buttonType.equals("Roll Dice") && !diceRolled) {
							diceRolled = true;

						} else if (buttonType.equals("End Turn") && diceRoll >= 0) {
							endTurn = true;

						}
					}
				}
			}
			// get character suggestion cards
			for (Component w : GUI.getCharacterCardMenuPanel().getComponents()) {

				if (e.getSource() == w) {

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
			for (Component i : GUI.menuPanel.getComponents()) {
				if (i == e.getSource()) {

					JButton tmp = null;
					if (i instanceof JButton) {
						tmp = (JButton) i;
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
		} catch (Exception f) {

		}

	}

	/*
	 * get current player
	 */
	private Player getCurrentPlayer() {

		return this.currentPlayer;
	}

	/**
	 * set current player
	 *
	 * @param p
	 */
	private static void setCurrentPlayer(Player p) {

		currentPlayer = p;
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
				// gets name from Jtextfield gui
				String name = Controller.GUI.initializeNames();
				if (i > 0) {
					while (names.contains(name)) {
						JOptionPane.showMessageDialog(GUI, "player name already selected. please try again",
								name + " already selected", JOptionPane.INFORMATION_MESSAGE);
						name = Controller.GUI.initializeNames();
					}
				}
				// gets token from jradio GUI
				String tokenName = Controller.GUI.initializeCharacters();
				Player.Character token = Player.Character.valueOf(tokenName);
				while (!tokens.contains(token)) {
					// loop until player selects an unchosen character
					JOptionPane.showMessageDialog(GUI, "Character already selected. please try again",
							tokenName + " already selected", JOptionPane.INFORMATION_MESSAGE);

					tokenName = Controller.GUI.initializeCharacters();
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
		// create a new instance of GUI
		Controller.GUI = new GameView(controller);

		Controller.GUI.add((Controller.GUI).getGui());

		Controller.GUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Controller.GUI.setLocationByPlatform(true);

		// ensures the frame is the minimum size it needs to be
		// in order display the components within it

		Controller.GUI.pack();
		// ensures the minimum size is enforced.
		Controller.GUI.setSize(1248, 768);
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

		// initialize number of players
		int nplayers1 = Controller.GUI.initializePlayerNum();

		// create player objects and assign them to characters
		ArrayList<Player> players = inputPlayers(nplayers1, game);

		// Draw Board
		game.drawBoard();
		GUI.updateBoard(game.getPlayers().get(0),
				game.getPlayers().get(0).getName() + "'s turn to play. you have " + diceRoll + " moves left.", 0, 0);

		// shuffle the deck of cards
		Collections.shuffle(game.getDeck().cards);

		// store three cards from deck into center of Board
		game.selectSecretCards();

		// deal the rest of the cards evenly to the players, while storing extra
		// cards that have not been dealt
		game.dealCards();

		// now start the game, and let the controller interact with user, GUI
		// and
		// game model
		game.setStatus(true);

		// run the game till we have winner
		while (game.getStatus()) {
			for (Player currentPlayer : game.getPlayers()) {
				setCurrentPlayer(currentPlayer);

				GUI.updateBoard(currentPlayer, currentPlayer + "'s turn to play. you have " + diceRoll + " moves left.",
						0, 0);

				if (currentPlayer.InGame) {

					// roll the dice
					diceRoll = rollDice();

					GUI.updateBoard(currentPlayer,
							currentPlayer + "'s turn to play. you have " + diceRoll + " moves left.", diceRoll1,
							diceRoll2);

					if (diceRoll < 2) {
						diceRoll = 2;
					}

					boolean moved = false;
					// here we interact with the player to move him/her on the
					// board
					// is player in a room? if not , he can make an accusation
					// or start moving
					if (!currentPlayer.inRoom) {
						// first we ask if user wants to accuse
						// is user still in game?
						if (currentPlayer.InGame) {
							// did user request accusation? before moving
							if (accuseRequest) {
								makeAccusation(game);
							}
							// if player still in game then move him

							while (diceRoll > 0 && !ended && !endTurn) {

								if (movePlayer(game)) {

									diceRoll--;

								}

							}
							moved = true;
							ended = false;
							currentPlayer.resetVisitedSquares();

						}

					}
					// if player in room, and did not suggest, make him suggest
					if (currentPlayer.inRoom() && !currentPlayer.getSuggest() && currentPlayer.InGame) {
						// ask user if he would like to make suggestion
						suggesting = Controller.GUI.makeSuggestion(currentPlayer);

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

							cardsSelected = 0;

							ArrayList<String> suggestionList = makeSuggestion(game);
							// if the user suggested, present(if any) the found
							// suggestions
							if (currentPlayer.getSuggest()) {
								presentSuggestions(suggestionList);
								currentPlayer.setSuggest(false);

							}
						}

						else {

							if (!moved) {
								while (diceRoll > 0 && !ended) {
									if (movePlayer(game)) {

										diceRoll--;

									}
								}
								moveRequest = false;
								moved = true;
								ended = false;
								currentPlayer.resetVisitedSquares();
							}
						}

					}
					// after he suggests/or not, ask if he wants to
					// player does not want to suggest, so we just move
					// him

					// turn has finished, so we reset all weapon squares

					while (!endTurn) {

						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}

					}

					endTurn = false;
					game.removeWeaponsfromRoom();

					currentPlayer.resetVisitedSquares();
					currentPlayer.setSuggest(false);
				}

			}
		}

	}

	private static int rollDice() {

		// wait till use has rolled the dice

		// check if help actuvated before dice rolled

		while (!diceRolled) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

		Random rand = new Random();
		diceRolled = false;

		diceRoll1 = rand.nextInt(6);
		diceRoll2 = rand.nextInt(6);
		while (diceRoll1 == 0 || diceRoll2 == 0) {
			diceRoll1 = rand.nextInt(6);
			diceRoll2 = rand.nextInt(6);

		}
		return diceRoll1 + diceRoll2;

	}

	/**
	 * method responsible for making accusations
	 *
	 * @param game
	 *
	 * @throws IOException
	 */
	private static void makeAccusation(Game game) throws IOException {

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
						currentPlayer.getName() + ", you have won the game, spot on accusations!", "You Win!",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				// player has failed accusation, he will now be spectator

				currentPlayer.InGame = false;
				JOptionPane.showMessageDialog(GUI,
						currentPlayer.getName() + ", your accusations were wrong, sorry, but you are out of the game!",
						"You Lost!", JOptionPane.INFORMATION_MESSAGE);

			}
		}

		accuseRequest = false;

	}

	/**
	 * helper method that presents suggestions to the user
	 *
	 * @param suggestionList
	 */
	private static void presentSuggestions(ArrayList<String> suggestionList) {
		if (suggestionList == null || suggestionList.size() < 1) {

			JOptionPane.showMessageDialog(GUI,
					currentPlayer.getName() + ", we found no matching suggestions with other players",
					" No matching suggestions found", JOptionPane.INFORMATION_MESSAGE);
		} else {
			// display what we found with other players
			String suggestString = currentPlayer.getName() + ", we found the following suggestions with other players";
			int count = 1;
			for (String suggestion : suggestionList) {
				suggestString += "\n";
				suggestString += (count + ":  " + suggestion);
				count++;
			}

			JOptionPane.showMessageDialog(GUI, suggestString, "Matching suggestions found!",
					JOptionPane.INFORMATION_MESSAGE);

		}
		// after suggesting, ask the user if he would like to accuse
		try {
			makeAccusation(game);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		Player suspect = null;

		currentPlayer.setSuggest(true);
		try {

			suspect = game.getPlayerfromCharacter(suspectName);

		} catch (Exception e) {

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

		GUI.updateBoard(currentPlayer, null, 0, 0);

		// now we check with each user whether they have one or many
		// of suggested cards

		for (Player e : game.getPlayers()) {
			int count = 1;
			if (!e.equals(currentPlayer)) {

				// we store what we find from each player into here
				suggestionList = game.checkSuggestion(e, suspectName, weapon, roomName);
				String suggestString = null;
				if (suggestionList.size() > 0) {
					suggestString = e.getName()
							+ ", the following is a list of cards in your hand that matches the suggestions made by "
							+ currentPlayer.getName() + ", please pick one to reveal by clicking on it's image ";

					for (String suggestion : suggestionList) {
						suggestString += "\n";
						suggestString += (count + ":  " + suggestion);
						count++;
					}

					JOptionPane.showMessageDialog(GUI, suggestString, e.getName() + "'s turn to confirm suggestions",
							JOptionPane.INFORMATION_MESSAGE);

					// + ", select the number associated with matching card that
					// you would like to reveal:");

					GUI.updateBoard(e,
							e.getName() + ", please select a card from the following that you would like to reveal to "
									+ currentPlayer.getName(),
							0, 0);

					// wait untill player selects a matching card
					while (!suggestionComplete) {
						try {
							Thread.sleep(5);
						} catch (InterruptedException a) {

							a.printStackTrace();
						}
					}
					// reset suggestion status so that next player can suggest
					// as well
					suggestionComplete = false;
					totSuggestions.add(chosenSuggestedCard);
				} else {

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

		// wait until user has selected a tile or has requested an accusation
		while (coordinate == null && !accuseRequest && !endTurn) {
			try {

				Thread.sleep(5);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
		// check if help activated during movement
		if (helpActivated) {
			helpActivated = false;
			JOptionPane.showMessageDialog(GUI, help, "help message", JOptionPane.INFORMATION_MESSAGE);
		}

		// check if user requested accusation during movement
		if (accuseRequest) {
			makeAccusation(game);
		}
		// check if user ended his turn during movement
		if (endTurn) {

			// exit the move method
			return false;
		}

		try {

			if (coordinate != null && !accuseRequest) {

				Position oldPosition = new Position(currentPlayer.getLocation().getX(),
						currentPlayer.getLocation().getY());

				Position newPosition = coordinate;

				Square oldSquare = currentPlayer.getlastSquare();
				Square newSquare = game.getBoard().squareAt(newPosition);
				// check if player move is valid
				if (currentPlayer.isValidMove(newPosition, oldPosition, game.getBoard())) {

					// code that deals with movement that leads to room
					// entrance event
					if (!currentPlayer.inRoom()) {

						if (oldSquare instanceof Door && newSquare instanceof Room) {
							// confirmed, user entered room
							diceRoll = 0;

							currentPlayer.isInRoom(true, ((Room) newSquare).getFullName());

						}

					}
					// code that deals with movement that leads to room exit
					// event
					if (currentPlayer.inRoom() && oldSquare instanceof Room && newSquare instanceof Door) {

						currentPlayer.isInRoom(false, "");
					}
					boolean tunneled = false;

					// code that deals with tunnel movement
					if (newSquare instanceof Tunnel) {

						Tunnel tunnel = (Tunnel) newSquare;
						String roomtoMove = String.valueOf(tunnel.getAscroom());

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

					GUI.updateBoard(currentPlayer, null, diceRoll1, diceRoll2);

					coordinate = null;

				} else {

					return false;
				}

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

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
