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

public class View extends JFrame {

	private final JPanel gui = new JPanel(new BorderLayout(9, 9));

	JPanel picLabel = new JPanel();
	private JButton[][] cluedoBoardSquares = new JButton[26][27];
	private static final String COLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	JToolBar tools;
	private JPanel cluedoBoard;
	private JLabel message = new JLabel("Cluedo is ready to play!");
	private ArrayList<Image> emptytileImages = new ArrayList<Image>();
	private ArrayList<Image> chosentileImages = new ArrayList<Image>();
	private ArrayList<Image> ballRoomImages = new ArrayList<Image>();
	private HashMap<String, Image> CardImages = new HashMap<String, Image>();
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

		return this.cluedoBoardSquares;
	}

	View(Controller controller2) throws IOException {
		this.controller = controller2;
		for (int i = 0; i < 15; i++) {
			try {
				emptytileImages
						.add(ImageIO.read(new File(System.getProperty("user.dir") + "/src/squaretile" + i + ".jpeg")));
			} catch (IOException e) {
			
				e.printStackTrace();
			}

		}
		// initialize ballroom

		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);

		// fill up hashmap with card images

	

		System.out.println(CardImages.size());
        
		initializeGui();
		

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
	    optionPane.add(new JTextField());
	    //optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);

	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(3,1));
	
	    for (int i = 0; i < Character.values().length; i++) {
			String suspectName = Character.values()[i].name();

			JRadioButton characterRadioBtn = new JRadioButton(suspectName); 

			characterButtonArray[i] = characterRadioBtn; 
			group.add(characterRadioBtn); 

			characterButtonArray[i].setActionCommand(suspectName); 
			characterButtonArray[i].addActionListener(this.controller);
			characterRadioBtn.setToolTipText("Click here to select this suspect");
			panel.add(characterRadioBtn);
		}
	    optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
	    optionPane.add(panel);
	    gui.add(optionPane);
	    dialog = optionPane.createDialog(null, "Select a Character");
	    dialog.setVisible(true);
	    String selection = null;
       for (JRadioButton r: characterButtonArray) {
	    	
	    	if (r.isSelected()) {
	    		System.out.println("  iam here");
	    		return selection = r.getText();
	    	}
	    }
	    
	    if (selection == null) {
	    	System.out.println(" i am not here");
	    	return initializeCharacters();
	    	
	    }
	    
	   return null;
	    
	}
	
public int initializePlayerNum() {
		

		userNumButtonArray = new JRadioButton[4];
		group = new ButtonGroup();
		JDialog dialog = null;
	    JOptionPane optionPane = new JOptionPane();
	    optionPane.setMessage("Please select the number of character");
	    optionPane.add(new JTextField());
	    //optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(3,1));
	   
	    for (int i = 0; i < 4; i++) {
			

			JRadioButton characterRadioBtn = new JRadioButton(Integer.toString(i+3)); 

			userNumButtonArray[i] = characterRadioBtn; 
			group.add(characterRadioBtn); 

			userNumButtonArray[i].setActionCommand(Integer.toString(i+3)); 
			userNumButtonArray[i].addActionListener(this.controller);
			characterRadioBtn.setToolTipText("Click here to select number of players in game");
			panel.add(characterRadioBtn);
		}
	    //optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
	   // optionPane.add(new JButton("OK"));
	    optionPane.add(panel);
	    gui.add(optionPane);
	    dialog = optionPane.createDialog(null, "Select the numbe of players");
	    dialog.setVisible(true);
	    int selection = 0;
	    for (JRadioButton r: userNumButtonArray) {
	    	
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

		tools = new JToolBar();
		tools.setFloatable(false);
		tools.setBackground(Color.GRAY);
		gui.add(tools, BorderLayout.PAGE_END);
		JButton accuse = new JButton("Accuse");
		accuse.addActionListener(this.controller);
		tools.add(accuse);
		tools.add(message);
		

		BufferedImage myPicture = null;
		// initialize ball room graphics

		picLabel.setOpaque(true);
		picLabel.setVisible(true);
		picLabel.setLayout(new BorderLayout());
		picLabel.setBackground(Color.black);

		// panel where buttons will be added in grid layout
		cluedoBoard = new JPanel(new GridLayout(0, 27));
		cluedoBoard.setBorder(new LineBorder(Color.BLACK));

		Insets buttonMargin = new Insets(5, 5, 5, 5);
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
		for (int ii = 0; ii < cluedoBoardSquares.length; ii++) {
			for (int jj = 0; jj < cluedoBoardSquares[ii].length; jj++) {
				JButton b = new JButton();

				b.setMargin(buttonMargin);
				b.setBounds(0, 0, 0, 0);

				b.addActionListener(this.controller);

				b.setOpaque(true);
				b.setVisible(true);
				b.setContentAreaFilled(true);
				b.setBorderPainted(false);

				if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|-")) {

					Collections.shuffle(emptytileImages);
					int index = new Random().nextInt(this.emptytileImages.size() - 1);
					b.setIcon(new ImageIcon(emptytileImages.get(index)));
					this.chosentileImages.add(emptytileImages.get(index));

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|S")) {
					b.setBackground(Color.CYAN);

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|L")) {
					b.setBackground(Color.DARK_GRAY);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|T")) {
					try {
						Image image = ImageIO.read(
								new File(System.getProperty("user.dir") + "/src/characterImages/images/" + "1.jpg"));
						Image scaled = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
						ImageIcon tunnelIcon = new ImageIcon(scaled);
						b.setBackground(Color.BLACK);
						b.setIcon(tunnelIcon);

					} catch (IOException e) {

						e.printStackTrace();
					}

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|C")) {

					b.setBackground(Color.decode("#571800"));
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					this.textCons = "CONSERVATORY";

					if (index7 < textCons.length() && index8 >= 0) {
						b.setBorderPainted(false);

						b.setText(textCons.substring(index7, index7 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index7++;
					}
					index8++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|E")) {

					b.setBackground(Color.decode("#354C5C"));

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					this.textDining = "DINING   ROOM";

					if (index5 < textDining.length() && index6 >= 22) {
						b.setBorderPainted(false);

						b.setText(textDining.substring(index5, index5 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index5++;
					}
					index6++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof PlayerSquare) {

					PlayerSquare player = (PlayerSquare) this.controller.getGame().getBoard().getSquares()[ii][jj];
					ImageIcon playerIcon = new ImageIcon(player.getImage());

					b.setIcon(playerIcon);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof Weapon) {

					Weapon weapon = (Weapon) this.controller.getGame().getBoard().getSquares()[ii][jj];
					ImageIcon weaponIcon = new ImageIcon(weapon.getImage());
					b.setIcon(weaponIcon);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|B")) {

					this.textBall = "BALLROOM";
					b.setBackground(Color.decode("#574604"));
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index3 < textBall.length() && index4 >= 12) {
						b.setBorderPainted(false);

						b.setText(textBall.substring(index3, index3 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
						index3++;
					}
					index4++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|Y")) {
					textStudy = "STUDY";

					b.setBackground(Color.decode("#08330C"));

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index15 < this.textStudy.length() && index16 >= 7) {
						b.setBorderPainted(false);
						b.setText(textStudy.substring(index15, index15 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
						index15++;
					}
					index16++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|W")) {
					textBill = "BILLIARD    ROOM";

					b.setBackground(Color.decode("#043357"));

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index17 < this.textBill.length() && index18 >= 6) {
						b.setBorderPainted(false);
						b.setText(textBill.substring(index17, index17 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
						index17++;
					}
					index18++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|H")) {
					textHall = "HALL";
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					b.setBackground(Color.decode("#370000"));

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index11 < this.textHall.length() && index12 >= 13) {
						b.setBorderPainted(false);
						b.setText(textHall.substring(index11, index11 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index11++;
					}
					index12++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|D")) {
					try {
						b.setIcon(new ImageIcon(
								ImageIO.read(new File(System.getProperty("user.dir") + "/src/doorimage.jpeg"))));
						b.setBorder(null);
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|O")) {
					textLounge = "LOUNGE";
					b.setBackground(Color.decode("#611439"));
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index9 < this.textLounge.length() && index10 >= 14) {
						b.setBorderPainted(false);

						b.setText(textLounge.substring(index9, index9 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
						index9++;
					}
					index10++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|K")) {
					textKitch = "KITCHEN";
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					b.setBackground(Color.decode("#2A0947"));

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index13 < this.textKitch.length() && index14 >= 17) {
						b.setBorderPainted(false);
						b.setText(textKitch.substring(index13, index13 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index13++;
					}
					index14++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|F")) {
					Color randomcol = colors.get(new Random().nextInt(colors.size()));
					b.setBackground(Color.BLACK);
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index1 < textlen && index2 >= 12) {
						b.setBorderPainted(false);

						b.setText(text.substring(index1, index1 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(randomcol);
						index1++;
					}
					index2++;

				} else {
					b.setBackground(Color.BLACK);
					b.setBorderPainted(false);
					b.setForeground(Color.BLACK);

				}

				cluedoBoardSquares[ii][jj] = b;
				cluedoBoard.add(b);
			}
		}

		picLabel.add(cluedoBoard);

		gui.add(picLabel);

	}

	/**
	 * open dialog frame to ask if user would like to suggest
	 * @param p
	 * @return
	 */
	public int makeSuggestion(Player p) {
		JFrame frame = new JFrame("example");
		Object[] options = { "Yes, please", "No!" };
		int n = JOptionPane.showOptionDialog(frame, p.getName() + ", Would you like to make a suggestion?", "Suggestion?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); // default
																										// button
		return n;
	}
	/**
	 * open dialog frame to ask if user would like to accuse
	 * @param p
	 * @return
	 */
	public int makeAccusation(Player p) {
		JFrame frame = new JFrame("example");
		Object[] options = { "Yes, please", "No!" };
		int n = JOptionPane.showOptionDialog(frame, p.getName() + ", Would you like to make an accusation?", "Accusation?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]); // default
																										// button
		return n;
	}

	/**
	 * initialize character menu
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
			pic.addActionListener(this.controller);
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
	 * initializes weapons menu
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
			System.out.println(a.toString());
			image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/CardImages/"
					+ a.toString() + ".png"));

	
			JButton pic = new JButton(new ImageIcon(image));
			pic.setName(a.toString());
			Insets buttonMargin = new Insets(5, 5, 5, 5);
		    pic.setBackground(Color.BLACK);
		    pic.setBorderPainted(false);
			pic.setMargin(buttonMargin);
			pic.setBounds(0, 0, 0, 0);
			pic.addActionListener(this.controller);
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
	 * initialize room menu
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

			// Image scaled = image.getScaledInstance(40, 40,
			// java.awt.Image.SCALE_SMOOTH);
			JButton pic = new JButton(new ImageIcon(image));
			pic.setName(a.toString());
			Insets buttonMargin = new Insets(5, 5, 5, 5);
			pic.setMargin(buttonMargin);
			pic.setBorderPainted(false);
			pic.setBounds(0, 0, 0, 0);
			 pic.setBackground(Color.BLACK);
			pic.addActionListener(this.controller);
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

	public JPanel getRoomCardMenuPanel() {
		return this.roomCardMenuPanel;

	}

	public JFrame getRoomCardMenuFrame() {
		return this.roomCardMenuFrame;

	}

	public JPanel getWeaponCardMenuPanel() {
		return this.weaponCardMenuPanel;

	}

	public JFrame getWeaponCardMenuFrame() {
		return this.weaponCardMenuFrame;

	}

	public JPanel getCharacterCardMenuPanel() {
		return this.characterCardMenuPanel;

	}

	public JFrame getCharacterCardMenuFrame() {
		return this.characterCardMenuFrame;

	}

	public void updateBoard(Player p, String Message) {
		// update dashboard for each player
		cluedoBoard.removeAll();
		tools.removeAll();
		message = new JLabel(p.getCharacter().name() + "'s turn to play");
		tools.setFloatable(false);

		JButton accuse = new JButton("Accuse");
		accuse.addActionListener(this.controller);
		tools.add(accuse); 
		tools.add(message);
		tools.add(message);
		for (Card c : p.getHand()) {
			JButton imageButton = new JButton();
			Image dimg = c.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			imageButton.setIcon(new ImageIcon(dimg));
			imageButton.addActionListener(this.controller);
			imageButton.setName(c.getName());
			tools.add(imageButton);
			tools.addSeparator();
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
		for (int ii = 0; ii < cluedoBoardSquares.length; ii++) {
			for (int jj = 0; jj < cluedoBoardSquares[ii].length; jj++) {
				JButton b = new JButton();

				b.setMargin(buttonMargin);
				b.setBounds(0, 0, 0, 0);
				// b.setPreferredSize(new Dimension(25,25));
				b.addActionListener(this.controller);

				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..
				ImageIcon icon = new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB));
				// b.setIcon(icon);

				b.setOpaque(true);
				b.setVisible(true);
				b.setContentAreaFilled(true);
				b.setBorderPainted(false);
				// b.setPreferredSize(new Dimension(200, 200));

				if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|-")) {

					// Collections.shuffle(emptytileImages);
					// int index = new
					// Random().nextInt(this.emptytileImages.size() - 1);
					b.setIcon(new ImageIcon(chosentileImages.get(tileCounter)));
					tileCounter++;

					// TODO Auto-generated catch block

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|L")) {
					b.setBackground(Color.DARK_GRAY);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|S")) {
					b.setBackground(Color.CYAN);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof PlayerSquare) {
					PlayerSquare player = (PlayerSquare) this.controller.getGame().getBoard().getSquares()[ii][jj];
					Image scaled = player.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
					ImageIcon playerIcon = new ImageIcon(scaled);
					b.setIcon(playerIcon);
					b.setBackground(Color.GRAY);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|T")) {
					try {
						Image image = ImageIO.read(
								new File(System.getProperty("user.dir") + "/src/characterImages/images/" + "1.jpg"));
						Image scaled = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
						ImageIcon tunnelIcon = new ImageIcon(scaled);
						b.setBackground(Color.BLACK);
						b.setIcon(tunnelIcon);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|O")) {
					textLounge = "LOUNGE";
					if (p.inRoom && p.getRoomName().equals(roomName.Lounge.toString())) {
						b.setBackground(Color.decode("#B2152C"));

					} else {
						b.setBackground(Color.decode("#611439"));

					}

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index9 < this.textLounge.length() && index10 >= 14) {
						b.setBorderPainted(false);
						b.setText(textLounge.substring(index9, index9 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
						index9++;
					}
					index10++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|Y")) {
					textStudy = "STUDY";
					if (p.inRoom && p.getRoomName().equals(roomName.Study.toString())) {
						b.setBackground(Color.decode("#12701B"));

					} else {
						b.setBackground(Color.decode("#08330C"));

					}

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index15 < this.textStudy.length() && index16 >= 7) {
						b.setBorderPainted(false);
						b.setText(textStudy.substring(index15, index15 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
						index15++;
					}
					index16++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|W")) {
					textBill = "BILLIARD    ROOM";
					if (p.inRoom && p.getRoomName().equals(roomName.BilliardRoom.toString())) {
						b.setBackground(Color.decode("#085EA1"));

					} else {
						b.setBackground(Color.decode("#043357"));

					}

					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index17 < this.textBill.length() && index18 >= 6) {
						b.setBorderPainted(false);
						b.setText(textBill.substring(index17, index17 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
					
						index17++;
					}
					index18++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|B")) {
					textBall = "BALLROOM";
					if (p.inRoom && p.getRoomName().equals(roomName.Ballroom.toString())) {
						b.setBackground(Color.decode("#AD8B08"));

					} else {
						b.setBackground(Color.decode("#574604"));
					}
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					if (index3 < this.textBall.length() && index4 >= 12) {
						b.setBorderPainted(false);

						b.setText(textBall.substring(index3, index3 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.BLACK);
						index3++;
					}
					index4++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|H")) {
					textHall = "HALL";
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (p.inRoom && p.getRoomName().equals(roomName.Hall.toString())) {

						b.setBackground(Color.decode("#B60000"));

					} else {
						b.setBackground(Color.decode("#370000"));
					}
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index11 < this.textHall.length() && index12 >= 13) {
						b.setBorderPainted(false);
						b.setText(textHall.substring(index11, index11 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index11++;
					}
					index12++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof Weapon) {

					Weapon weapon = (Weapon) this.controller.getGame().getBoard().getSquares()[ii][jj];
					Image scaled = weapon.getImage().getScaledInstance(30, 25, java.awt.Image.SCALE_SMOOTH);
					ImageIcon weaponIcon = new ImageIcon(scaled);
					b.setIcon(weaponIcon);

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|K")) {
					textKitch = "KITCHEN";
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (p.inRoom && p.getRoomName().equals(roomName.Kitchen.toString())) {

						b.setBackground(Color.decode("#5E14A1"));

					} else {
						b.setBackground(Color.decode("#2A0947"));
					}
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index13 < this.textKitch.length() && index14 >= 12) {
						b.setBorderPainted(false);
						b.setText(textKitch.substring(index13, index13 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index13++;
					}
					index14++;

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|E")) {
					if (p.inRoom && p.getRoomName().equals(roomName.DiningRoom.toString())) {

						b.setBackground(Color.decode("#6793B2"));

					} else {
						b.setBackground(Color.decode("#354C5C"));
					}
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					this.textDining = "DINING   ROOM";

					if (index5 < textDining.length() && index6 >= 22) {
						b.setBorderPainted(false);

						b.setText(textDining.substring(index5, index5 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index5++;
					}
					index6++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|C")) {
					if (p.inRoom && p.getRoomName().equals(roomName.Conservatory.toString())) {
						b.setBackground(Color.decode("#A32D00"));
					} else {
						b.setBackground(Color.decode("#571800"));
					}
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

					this.textCons = "CONSERVATORY";

					if (index7 < textCons.length() && index8 >= 0) {
						b.setBorderPainted(false);

						b.setText(textCons.substring(index7, index7 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(Color.WHITE);
						index7++;
					}
					index8++;

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|D")) {
					try {
						b.setIcon(new ImageIcon(
								ImageIO.read(new File(System.getProperty("user.dir") + "/src/doorimage.jpeg"))));
						b.setBorder(null);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|F")) {
					Color randomcol = colors.get(new Random().nextInt(colors.size()));
					b.setBackground(Color.BLACK);
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);
					if (index1 < textlen && index2 >= 12) {
						b.setBorderPainted(false);
						b.setText(text.substring(index1, index1 + 1));
						b.setFont(new Font("Dialog", Font.BOLD, 12));
						b.setForeground(randomcol);
						index1++;
					}
					index2++;

				} else {
					b.setBackground(Color.BLACK);
					b.setBorderPainted(false);
					b.setForeground(Color.BLACK);

				}
				cluedoBoardSquares[ii][jj] = b;
				cluedoBoard.add(b);

			}
		}

		cluedoBoard.revalidate();
		// cluedoBoard.repaint();

	}

	public final JComponent getCluedoBoard() {
		return cluedoBoard;
	}

	public final JComponent getGui() {
		return gui;
	}

	public final JComponent getPic() {
		return this.picLabel;
	}

	

	
}
