package cluedo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import cluedo.Player.Character;
import cluedo.Room.roomName;
import cluedo.Weapon.Weapons;

public class GameView extends JFrame {

	private final JPanel mainFrame = new JPanel(new BorderLayout(9, 9));

	JPanel mainPanel = new JPanel();
	private JButton[][] squaresArray = new JButton[26][27];
	private static final String[] doorHeadings = { "R", "L", "U", "U", "U", "U", "R", "L", "U", "U", "U", "R", "D", "D",
			"D", "L", "D" };
	JToolBar menuPanel;
	private JPanel squareGridPanel;
	private JLabel message = new JLabel("Cluedo is ready to play!");
	private ArrayList<Image> emptytileImages = new ArrayList<Image>();
	private ArrayList<Image> chosentileImages = new ArrayList<Image>();
	private ArrayList<Image> ballRoomImages = new ArrayList<Image>();
	private ArrayList<Image> diceImages = new ArrayList<Image>();

	String text = "CLUEDO";
	int textlen = text.length();
	ArrayList<Color> colors = new ArrayList<>();

	private Game game;

	public static int x;

	public static int y;

	private Controller controller;

	private String textBall;

	private String textDining;

	private String textCons;

	private String textLounge;

	private String textHall;

	private String textKitch;

	private String textStudy;

	private String textBill;

	private JFrame characterCardMenuFrame;

	private JPanel characterCardMenuPanel;

	private JFrame weaponCardMenuFrame;

	private JPanel weaponCardMenuPanel;

	private JFrame roomCardMenuFrame;

	private JPanel roomCardMenuPanel;

	private JRadioButton[] characterButtonArray;

	private ButtonGroup group;

	private JDialog userSelect;

	private JRadioButton[] userNumButtonArray;

	public JButton[][] getGUIBoard() {

		return this.squaresArray;
	}

	/*
	 * initialize the view
	 */
	GameView(Controller controller2) throws IOException {
		this.controller = controller2;
		// initialize images for empty tiles
		for (int i = 0; i < 15; i++) {
			try {
				emptytileImages
						.add(ImageIO.read(new File(System.getProperty("user.dir") + "/src/squaretile" + i + ".jpeg")));
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		// initialize colours for game LOGO in center of game

		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);

		// initialize dice images
		for (int i = 0; i < 7; i++) {
			diceImages.add(ImageIO.read(
					new File(System.getProperty("user.dir") + "/src/characterImages/images/Dice/" + i + ".jpeg")));

		}

		initializeGui();

	}

	/*
	 * initialize the player names
	 */
	public String initializeNames() {

		group = new ButtonGroup();
		JDialog dialog = null;
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("Player name");
		JTextField name = new JTextField();
		optionPane.add(name);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(name);
		optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
		optionPane.add(panel);
		dialog = optionPane.createDialog(null, "Please enter your name");
		dialog.setVisible(true);
		String selection = null;

		if (!name.getText().equals("")) {

			return selection = name.getText();
		}

		if (name.getText().equals("")) {

			return initializeNames();

		}

		return null;

	}

	/**
	 * method that asks each user for character selection
	 */
	public String initializeCharacters() {

		characterButtonArray = new JRadioButton[6];
		group = new ButtonGroup();
		JDialog dialog = null;
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("Please select a character");

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));

		for (int i = 0; i < Character.values().length; i++) {
			String suspectName = Character.values()[i].name();

			JRadioButton characterRadioBtn = new JRadioButton(suspectName);

			characterButtonArray[i] = characterRadioBtn;
			group.add(characterRadioBtn);

			characterButtonArray[i].setActionCommand(suspectName);
			characterButtonArray[i].addMouseListener(this.controller);
			characterRadioBtn.setToolTipText("Click here to select this suspect");
			panel.add(characterRadioBtn);
		}
		optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
		optionPane.add(panel);
		// gui.add(optionPane);
		dialog = optionPane.createDialog(null, "Select a Character");
		dialog.setVisible(true);
		String selection = null;
		for (JRadioButton r : characterButtonArray) {

			if (r.isSelected()) {

				return selection = r.getText();
			}
		}

		if (selection == null) {

			return initializeCharacters();

		}

		return null;

	}

	/*
	 * initialize the number of players
	 */

	public int initializePlayerNum() {

		userNumButtonArray = new JRadioButton[4];
		group = new ButtonGroup();
		JDialog dialog = null;
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessage("Please select the number of character");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));

		for (int i = 0; i < 4; i++) {

			JRadioButton characterRadioBtn = new JRadioButton(Integer.toString(i + 3));

			userNumButtonArray[i] = characterRadioBtn;
			group.add(characterRadioBtn);

			userNumButtonArray[i].setActionCommand(Integer.toString(i + 3));

			characterRadioBtn.setToolTipText("Click here to select number of players in game");
			panel.add(characterRadioBtn);
		}

		optionPane.add(panel);

		dialog = optionPane.createDialog(null, "Select the number of players");
		dialog.setVisible(true);
		int selection = 0;
		for (JRadioButton r : userNumButtonArray) {

			if (r.isSelected()) {
				return selection = Integer.valueOf(r.getText());
			}
		}

		if (selection == 0) {

			return initializePlayerNum();

		}

		return 0;

	}

	public final void initializeGui() {
		// set up the main GUI


		//set up the menu tool bar
		menuPanel = new JToolBar();
		menuPanel.setFloatable(false);
		menuPanel.setBackground(Color.GRAY);
		mainFrame.add(menuPanel, BorderLayout.PAGE_END);
		menuPanel.add(message);
		mainPanel.setOpaque(true);
		mainPanel.setVisible(true);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.black);

		// panel where buttons will be added in grid layout
		squareGridPanel = new JPanel(new GridLayout(0, 27));
		squareGridPanel.setBorder(new LineBorder(Color.BLACK));

		Insets buttonMargin = new Insets(5, 5, 5, 5);
		// these integer are for adding room name to the rooms
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		int index4 = 0;
		int index5 = 0;
		int index6 = 0;
		int index7 = 0;
		int index8 = 0;
		int index9 = 0;
		int index10 = 0;
		int index11 = 0;
		int index12 = 0;
		int index13 = 0;
		int index14 = 0;
		int index15 = 0;
		int index16 = 0;
		int index17 = 0;
		int index18 = 0;
		int doorCounter = 0;
		for (int ii = 0; ii < squaresArray.length; ii++) {
			for (int jj = 0; jj < squaresArray[ii].length; jj++) {
				JButton button = new JButton();
				// create a new jbutton
				button.setMargin(buttonMargin);
				button.setBounds(0, 0, 0, 0);

				button.addMouseListener(this.controller);

				button.setOpaque(true);
				button.setVisible(true);
				button.setContentAreaFilled(true);
				button.setBorderPainted(false);

				// the code below sets the colour of each jbutton in relation to
				// its type, which is confirmed from the 2D Square object array
				if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|-")) {

					Collections.shuffle(emptytileImages);
					int index = new Random().nextInt(this.emptytileImages.size() - 1);
					button.setIcon(new ImageIcon(emptytileImages.get(index)));
					this.chosentileImages.add(emptytileImages.get(index));

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|S")) {
					button.setBackground(Color.CYAN);

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|L")) {
					button.setBackground(Color.DARK_GRAY);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|T")) {
					try {
						Image image = ImageIO.read(
								new File(System.getProperty("user.dir") + "/src/characterImages/images/" + "1.jpg"));
						Image scaled = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
						ImageIcon tunnelIcon = new ImageIcon(scaled);
						button.setBackground(Color.BLACK);
						button.setIcon(tunnelIcon);

					} catch (IOException e) {

						e.printStackTrace();
					}

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|C")) {

					button.setBackground(Color.decode("#571800"));
					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					this.textCons = "CONSERVATORY";

					if (index7 < textCons.length() && index8 >= 0) {
						button.setBorderPainted(false);

						button.setText(textCons.substring(index7, index7 + 1));
						button.setFont(new Font("Comic Sans", Font.BOLD, 15));
						button.setForeground(Color.WHITE);
						index7++;
					}
					index8++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|E")) {

					button.setBackground(Color.decode("#354C5C"));
					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					this.textDining = "DINING   ROOM";

					if (index5 < textDining.length() && index6 >= 22) {
						button.setBorderPainted(false);

						button.setText(textDining.substring(index5, index5 + 1));
						button.setFont(new Font("Dialog", Font.BOLD, 15));
						button.setForeground(Color.WHITE);
						index5++;
					}
					index6++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof PlayerSquare) {

					PlayerSquare player = (PlayerSquare) this.controller.getGame().getBoard().getSquares()[ii][jj];
					ImageIcon playerIcon = new ImageIcon(player.getImage());

					button.setIcon(playerIcon);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof Weapon) {

					Weapon weapon = (Weapon) this.controller.getGame().getBoard().getSquares()[ii][jj];
					ImageIcon weaponIcon = new ImageIcon(weapon.getImage());
					button.setIcon(weaponIcon);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|B")) {

					this.textBall = "BALLROOM";
					button.setBackground(Color.decode("#574604"));
					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index3 < textBall.length() && index4 >= 12) {
						button.setBorderPainted(false);

						button.setText(textBall.substring(index3, index3 + 1));
						button.setFont(new Font("Tahoma", Font.BOLD, 15));
						button.setForeground(Color.BLACK);
						index3++;
					}
					index4++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|Y")) {
					textStudy = "STUDY";

					button.setBackground(Color.decode("#08330C"));

					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index15 < this.textStudy.length() && index16 >= 7) {
						button.setBorderPainted(false);
						button.setText(textStudy.substring(index15, index15 + 1));
						button.setFont(new Font("Calisto MT", Font.BOLD, 15));
						button.setForeground(Color.BLACK);
						index15++;
					}
					index16++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|W")) {
					textBill = "BILLIARD    ROOM";

					button.setBackground(Color.decode("#043357"));

					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index17 < this.textBill.length() && index18 >= 6) {
						button.setBorderPainted(false);
						button.setText(textBill.substring(index17, index17 + 1));
						button.setFont(new Font("Corbel", Font.BOLD, 15));
						button.setForeground(Color.BLACK);
						index17++;
					}
					index18++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|H")) {
					textHall = "HALL";
					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					button.setBackground(Color.decode("#370000"));

					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index11 < this.textHall.length() && index12 >= 13) {
						button.setBorderPainted(false);
						button.setText(textHall.substring(index11, index11 + 1));
						button.setFont(new Font("Century Gothic", Font.BOLD, 15));
						button.setForeground(Color.WHITE);
						index11++;
					}
					index12++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|D")) {
					try {

						button.setIcon(new ImageIcon(ImageIO
								.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/Arrows/"
										+ doorHeadings[doorCounter] + ".jpeg"))
								.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
						button.setBorder(null);
						button.setBackground(Color.BLACK);
						doorCounter++;
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|O")) {
					textLounge = "LOUNGE";
					button.setBackground(Color.decode("#611439"));
					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index9 < this.textLounge.length() && index10 >= 14) {
						button.setBorderPainted(false);

						button.setText(textLounge.substring(index9, index9 + 1));
						button.setFont(new Font("Constantia", Font.BOLD, 15));
						button.setForeground(Color.BLACK);
						index9++;
					}
					index10++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|K")) {
					textKitch = "KITCHEN";
					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);

					button.setBackground(Color.decode("#2A0947"));

					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index13 < this.textKitch.length() && index14 >= 17) {
						button.setBorderPainted(false);
						button.setText(textKitch.substring(index13, index13 + 1));
						button.setFont(new Font("Arial Black", Font.BOLD, 15));
						button.setForeground(Color.WHITE);
						index13++;
					}
					index14++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|F")) {
					Color randomcol = colors.get(new Random().nextInt(colors.size()));
					button.setBackground(Color.BLACK);
					button.setBorderPainted(false);
					button.setBorder(null);
					button.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index1 < textlen && index2 >= 12) {
						button.setBorderPainted(false);

						button.setText(text.substring(index1, index1 + 1));
						button.setFont(new Font("Comic Sans", Font.BOLD, 30));
						button.setForeground(randomcol);
						index1++;
					}
					// here we integrate the help button into the actual game
					// pane
					if (index2 == 26) {
						button.setBorderPainted(false);
						button.setText("?");
						button.setName("?");
						;
						button.setFont(new Font("Comic Sans", Font.BOLD, 30));
						button.setForeground(randomcol);

					}
					System.out.println(index2);
					index2++;

				} else {
					button.setBackground(Color.BLACK);
					button.setBorderPainted(false);
					button.setForeground(Color.BLACK);

				}

				squaresArray[ii][jj] = button;
				// here we add the formatted jbutton into the grid panel
				squareGridPanel.add(button);
			}
		}
		// here we add grid panel into another panel.
		mainPanel.add(squareGridPanel);
		// we add the grid and the toolbar into the frame
		mainFrame.add(mainPanel);

	}

	/**
	 * open dialog frame to ask if user would like to suggest
	 *
	 * @param p
	 * @return
	 */
	public int makeSuggestion(Player p) {
		JFrame frame = new JFrame("example");
		Object[] options = { "Yes, please", "No!" };
		int n = JOptionPane.showOptionDialog(frame, p.getName() + ", Would you like to make a suggestion?",
				"Suggestion?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); // default
																													// button
		return n;
	}

	/**
	 * open dialog frame to ask if user would like to accuse
	 *
	 * @param p
	 * @return
	 */
	public int makeAccusation(Player p) {
		JFrame frame = new JFrame("example");
		Object[] options = { "Yes, please", "No!" };
		int n = JOptionPane.showOptionDialog(frame, p.getName() + ", Would you like to make an accusation?",
				"Accusation?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); // default
																													// button
		return n;
	}

	/**
	 * initialize character menu display
	 *
	 * @throws IOException
	 */
	public void characterCardMenuCreate() throws IOException {

		characterCardMenuPanel = new JPanel(new GridLayout(0, 3));
		characterCardMenuPanel.setForeground(Color.black);
		characterCardMenuPanel.setBackground(Color.white);
		Image image = null;

		// first line

		characterCardMenuPanel.setOpaque(true);

		for (Character a : Character.values()) {

			image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/CardImages/"
					+ a.toString() + ".jpg"));

			JButton pic = new JButton(new ImageIcon(image));
			pic.setName(a.toString());
			pic.setBackground(Color.BLACK);
			pic.setBorderPainted(false);
			Insets buttonMargin = new Insets(5, 5, 5, 5);
			pic.setMargin(buttonMargin);
			pic.setBounds(0, 0, 0, 0);
			pic.addMouseListener(this.controller);
			pic.setFocusable(true);
			pic.setAlignmentX(0.5f);
			pic.setAlignmentY(0.5f);

			characterCardMenuPanel.add(pic);

		}

		characterCardMenuFrame = new JFrame();
		characterCardMenuFrame.setLocationByPlatform(true);
		characterCardMenuFrame.setSize(600, 600);

		characterCardMenuFrame.getContentPane().add(characterCardMenuPanel);
		characterCardMenuFrame.setTitle("Select a Character who you think is Guilty!");

	}

	/**
	 * initializes weapons menu display
	 *
	 * @throws IOException
	 */
	public void weaponCardMenuCreate() throws IOException {

		weaponCardMenuPanel = new JPanel(new GridLayout(0, 3));
		weaponCardMenuPanel.setForeground(Color.black);
		weaponCardMenuPanel.setBackground(Color.white);
		Image image = null;

		// first line

		weaponCardMenuPanel.setOpaque(true);

		for (Weapons a : Weapons.values()) {

			image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/CardImages/"
					+ a.toString() + ".png"));

			JButton pic = new JButton(new ImageIcon(image));
			pic.setName(a.toString());
			Insets buttonMargin = new Insets(5, 5, 5, 5);
			pic.setBackground(Color.BLACK);
			pic.setBorderPainted(false);
			pic.setMargin(buttonMargin);
			pic.setBounds(0, 0, 0, 0);
			pic.addMouseListener(this.controller);
			pic.setFocusable(true);
			pic.setAlignmentX(0.5f);
			pic.setAlignmentY(0.5f);

			weaponCardMenuPanel.add(pic);

		}

		weaponCardMenuFrame = new JFrame();
		weaponCardMenuFrame.setLocationByPlatform(true);
		weaponCardMenuFrame.setSize(600, 600);

		weaponCardMenuFrame.getContentPane().add(weaponCardMenuPanel);
		weaponCardMenuFrame.setTitle("Select a Character who you think is Guilty!");

	}

	/**
	 * initialize room menu display
	 *
	 * @throws IOException
	 */
	public void roomCardMenuCreate() throws IOException {

		roomCardMenuPanel = new JPanel(new GridLayout(0, 3));
		roomCardMenuPanel.setForeground(Color.black);
		roomCardMenuPanel.setBackground(Color.white);
		Image image = null;

		// first line

		roomCardMenuPanel.setOpaque(true);

		for (roomName a : roomName.values()) {
			if (!a.toString().equals("Center")) {
				image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/CardImages/"
						+ a.toString() + ".png"));
			}

			JButton pic = new JButton(new ImageIcon(image));
			pic.setName(a.toString());
			Insets buttonMargin = new Insets(5, 5, 5, 5);
			pic.setMargin(buttonMargin);
			pic.setBorderPainted(false);
			pic.setBounds(0, 0, 0, 0);
			pic.setBackground(Color.BLACK);
			pic.addMouseListener(this.controller);
			pic.setFocusable(true);
			pic.setAlignmentX(0.5f);
			pic.setAlignmentY(0.5f);

			roomCardMenuPanel.add(pic);

		}

		roomCardMenuFrame = new JFrame();
		roomCardMenuFrame.setLocationByPlatform(true);
		roomCardMenuFrame.setSize(600, 600);

		roomCardMenuFrame.getContentPane().add(roomCardMenuPanel);
		roomCardMenuFrame.setTitle("Select a Character who you think is Guilty!");

	}

	/*
	 * get the room menu panel
	 */
	public JPanel getRoomCardMenuPanel() {
		return this.roomCardMenuPanel;

	}

	/**
	 * get the room menu frame
	 *
	 * @return
	 */
	public JFrame getRoomCardMenuFrame() {
		return this.roomCardMenuFrame;

	}

	/**
	 * get the weapon menu panel
	 *
	 * @return
	 */

	public JPanel getWeaponCardMenuPanel() {
		return this.weaponCardMenuPanel;

	}

	/**
	 * get the weapon card menu frame
	 *
	 * @return
	 */

	public JFrame getWeaponCardMenuFrame() {
		return this.weaponCardMenuFrame;

	}

	/**
	 * get the weapon card menu panel
	 *
	 * @return
	 */

	public JPanel getCharacterCardMenuPanel() {
		return this.characterCardMenuPanel;

	}

	/**
	 * get the character card menu frame
	 *
	 * @return
	 */
	public JFrame getCharacterCardMenuFrame() {
		return this.characterCardMenuFrame;

	}

	/**
	 * update the whole display
	 *
	 * @param p--
	 *            player object
	 * @param Message
	 *            -- message to display to the player
	 * @param dice1
	 *            - help us get te right dice image to draw
	 * @param dice2
	 *            - help us get te right dice image to draw
	 */

	public void updateBoard(Player p, String Message, int dice1, int dice2) {
		// update dashboard for each player
		try {

			squareGridPanel.removeAll();
			menuPanel.removeAll();
			message = new JLabel(p.getCharacter().name() + "'s turn to play");
			menuPanel.setFloatable(false);

			JButton accuse = new JButton("Accuse");
			accuse.addMouseListener(this.controller);

			JButton endTurn = new JButton("End Turn");
			endTurn.addMouseListener(this.controller);
			// setup the dice images on toolbar
			JButton rollDice = new JButton("Roll Dice");
			rollDice.addMouseListener(this.controller);
			menuPanel.add(rollDice);
			menuPanel.add(accuse);
			menuPanel.add(endTurn);
			menuPanel.add(message);
			JButton dice1Button = new JButton();
			Image dice1img = diceImages.get(dice1).getScaledInstance(90, 90, Image.SCALE_SMOOTH);
			;
			dice1Button.setIcon(new ImageIcon(dice1img));
			JButton dice2Button = new JButton();
			Image dice2img = diceImages.get(dice2).getScaledInstance(90, 90, Image.SCALE_SMOOTH);
			dice2Button.setIcon(new ImageIcon(dice2img));
			menuPanel.add(dice1Button);
			menuPanel.add(dice2Button);
			menuPanel.addSeparator();
			for (Card c : p.getHand()) {
				JButton imageButton = new JButton();
				Image dimg = c.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
				imageButton.setIcon(new ImageIcon(dimg));
				imageButton.addMouseListener(this.controller);
				imageButton.setName(c.getName());
				menuPanel.add(imageButton);

			}

			Insets buttonMargin = new Insets(0, 0, 0, 0);
			int tileCounter = 0;
			int index1 = 0;
			int index2 = 0;
			int index3 = 0;
			int index4 = 0;
			int index5 = 0;
			int index6 = 0;
			int index7 = 0;
			int index8 = 0;
			int index9 = 0;
			int index10 = 0;
			int index11 = 0;
			int index12 = 0;
			int index13 = 0;
			int index14 = 0;
			int index15 = 0;
			int index16 = 0;
			int index17 = 0;
			int index18 = 0;
			int doorCounter1 = 0;
			for (int ii = 0; ii < squaresArray.length; ii++) {
				for (int jj = 0; jj < squaresArray[ii].length; jj++) {
					JButton button = new JButton();

					button.setMargin(buttonMargin);
					button.setBounds(0, 0, 0, 0);
					button.addMouseListener(this.controller);

					button.setOpaque(true);
					button.setVisible(true);
					button.setContentAreaFilled(true);
					button.setBorderPainted(false);

					if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|-")) {

						button.setIcon(new ImageIcon(chosentileImages.get(tileCounter)));
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());
						tileCounter++;

					} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|L")) {
						button.setBackground(Color.DARK_GRAY);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|S")) {
						button.setBackground(Color.CYAN);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof PlayerSquare) {
						PlayerSquare player = (PlayerSquare) this.controller.getGame().getBoard().getSquares()[ii][jj];
						Image scaled = player.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
						ImageIcon playerIcon = new ImageIcon(scaled);
						button.setIcon(playerIcon);
						button.setBackground(Color.GRAY);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|T")) {
						try {
							Image image = ImageIO.read(new File(
									System.getProperty("user.dir") + "/src/characterImages/images/" + "1.jpg"));
							Image scaled = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
							ImageIcon tunnelIcon = new ImageIcon(scaled);
							button.setBackground(Color.BLACK);
							button.setIcon(tunnelIcon);
							button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

						} catch (IOException e) {

							e.printStackTrace();
						}

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|O")) {
						textLounge = "LOUNGE";
						if (p.inRoom && p.getRoomName().equals(roomName.Lounge.toString())) {
							button.setBackground(Color.decode("#B2152C"));

						} else {
							button.setBackground(Color.decode("#611439"));

						}

						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

						if (index9 < this.textLounge.length() && index10 >= 14) {
							button.setBorderPainted(false);
							button.setText(textLounge.substring(index9, index9 + 1));
							button.setFont(new Font("Comic Sans", Font.BOLD, 15));
							button.setForeground(Color.BLACK);
							index9++;
						}
						index10++;

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|Y")) {
						textStudy = "STUDY";
						if (p.inRoom && p.getRoomName().equals(roomName.Study.toString())) {
							button.setBackground(Color.decode("#12701B"));

						} else {
							button.setBackground(Color.decode("#08330C"));

						}

						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

						if (index15 < this.textStudy.length() && index16 >= 7) {
							button.setBorderPainted(false);
							button.setText(textStudy.substring(index15, index15 + 1));
							button.setFont(new Font("Dialog", Font.BOLD, 15));
							button.setForeground(Color.BLACK);
							index15++;
						}
						index16++;

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|W")) {
						textBill = "BILLIARD    ROOM";
						if (p.inRoom && p.getRoomName().equals(roomName.BilliardRoom.toString())) {
							button.setBackground(Color.decode("#085EA1"));

						} else {
							button.setBackground(Color.decode("#043357"));

						}

						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

						if (index17 < this.textBill.length() && index18 >= 6) {
							button.setBorderPainted(false);
							button.setText(textBill.substring(index17, index17 + 1));
							button.setFont(new Font("Tahoma", Font.BOLD, 15));
							button.setForeground(Color.BLACK);

							index17++;
						}
						index18++;

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|B")) {
						textBall = "BALLROOM";
						if (p.inRoom && p.getRoomName().equals(roomName.Ballroom.toString())) {
							button.setBackground(Color.decode("#AD8B08"));

						} else {
							button.setBackground(Color.decode("#574604"));
						}
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

						if (index3 < this.textBall.length() && index4 >= 12) {
							button.setBorderPainted(false);

							button.setText(textBall.substring(index3, index3 + 1));
							button.setFont(new Font("Calisto MT", Font.BOLD, 12));
							button.setForeground(Color.BLACK);
							index3++;
						}
						index4++;

					} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|H")) {
						textHall = "HALL";
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						if (p.inRoom && p.getRoomName().equals(roomName.Hall.toString())) {

							button.setBackground(Color.decode("#B60000"));

						} else {
							button.setBackground(Color.decode("#370000"));
						}
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());
						if (index11 < this.textHall.length() && index12 >= 13) {
							button.setBorderPainted(false);
							button.setText(textHall.substring(index11, index11 + 1));
							button.setFont(new Font("Corbel", Font.BOLD, 12));
							button.setForeground(Color.WHITE);
							index11++;
						}
						index12++;

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof Weapon) {

						Weapon weapon = (Weapon) this.controller.getGame().getBoard().getSquares()[ii][jj];
						Image scaled = weapon.getImage().getScaledInstance(30, 25, java.awt.Image.SCALE_SMOOTH);
						ImageIcon weaponIcon = new ImageIcon(scaled);
						button.setIcon(weaponIcon);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

					} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|K")) {
						textKitch = "KITCHEN";
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						if (p.inRoom && p.getRoomName().equals(roomName.Kitchen.toString())) {

							button.setBackground(Color.decode("#5E14A1"));

						} else {
							button.setBackground(Color.decode("#2A0947"));
						}
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());
						if (index13 < this.textKitch.length() && index14 >= 12) {
							button.setBorderPainted(false);
							button.setText(textKitch.substring(index13, index13 + 1));
							button.setFont(new Font("Century Gothic", Font.BOLD, 15));
							button.setForeground(Color.WHITE);
							index13++;
						}
						index14++;

					} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|E")) {
						if (p.inRoom && p.getRoomName().equals(roomName.DiningRoom.toString())) {

							button.setBackground(Color.decode("#6793B2"));

						} else {
							button.setBackground(Color.decode("#354C5C"));
						}
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());
						this.textDining = "DINING   ROOM";

						if (index5 < textDining.length() && index6 >= 22) {
							button.setBorderPainted(false);

							button.setText(textDining.substring(index5, index5 + 1));
							button.setFont(new Font("Constantia", Font.BOLD, 15));
							button.setForeground(Color.WHITE);
							index5++;
						}
						index6++;

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|C")) {
						if (p.inRoom && p.getRoomName().equals(roomName.Conservatory.toString())) {
							button.setBackground(Color.decode("#A32D00"));
						} else {
							button.setBackground(Color.decode("#571800"));
						}
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());

						this.textCons = "CONSERVATORY";

						if (index7 < textCons.length() && index8 >= 0) {
							button.setBorderPainted(false);

							button.setText(textCons.substring(index7, index7 + 1));
							button.setFont(new Font("Arial Black", Font.BOLD, 12));
							button.setForeground(Color.WHITE);
							index7++;
						}
						index8++;

					}

					else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|D")) {
						try {
							button.setIcon(new ImageIcon(ImageIO
									.read(new File(
											System.getProperty("user.dir") + "/src/characterImages/images/Arrows/"
													+ doorHeadings[doorCounter1] + ".jpeg"))
									.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
							button.setBorder(null);
							button.setBackground(Color.BLACK);
							doorCounter1++;
							button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());
						} catch (IOException e1) {

							e1.printStackTrace();
						}

					} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|F")) {
						Color randomcol = colors.get(new Random().nextInt(colors.size()));
						button.setBackground(Color.BLACK);
						button.setBorderPainted(false);
						button.setBorder(null);
						button.setHorizontalTextPosition(SwingConstants.LEFT);
						button.setToolTipText(this.controller.getGame().getBoard().getSquares()[ii][jj].getFullName());
						if (index1 < textlen && index2 >= 12) {
							button.setBorderPainted(false);
							button.setText(text.substring(index1, index1 + 1));
							button.setFont(new Font("Comic Sans", Font.BOLD, 30));
							button.setForeground(randomcol);
							index1++;
						}
						if (index2 == 26) {
							button.setBorderPainted(false);
							button.setText("?");
							button.setName("?");
							;
							button.setFont(new Font("Comic Sans", Font.BOLD, 30));
							button.setForeground(randomcol);
							button.setToolTipText("click here for help!");

						}
						index2++;

					} else {
						button.setBackground(Color.BLACK);
						button.setBorderPainted(false);
						button.setForeground(Color.BLACK);

					}
					squaresArray[ii][jj] = button;
					squareGridPanel.add(button);

				}
			}

			squareGridPanel.revalidate();
			menuPanel.revalidate();

		}

		catch (Exception a) {

		}

	}

	/**
	 * return the cluedo grid panel
	 *
	 * @return
	 */
	public final JComponent getCluedoBoard() {
		return squareGridPanel;
	}

	/**
	 * return the frame
	 *
	 * @return
	 */

	public final JComponent getGui() {
		return mainFrame;
	}

	/**
	 * return the main panel
	 * @return
	 */

	public final JComponent getPic() {
		return this.mainPanel;
	}

}
