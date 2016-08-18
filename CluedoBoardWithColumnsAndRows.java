package cluedo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import cluedo.Room.roomName;

public class CluedoBoardWithColumnsAndRows extends JFrame {

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

	public JButton[][] getGUIBoard() {

		return this.cluedoBoardSquares;
	}

	CluedoBoardWithColumnsAndRows(Controller controller2) {
		this.controller = controller2;
		for (int i = 0; i < 15; i++) {
			try {
				emptytileImages
						.add(ImageIO.read(new File(System.getProperty("user.dir") + "/src/squaretile" + i + ".jpeg")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// initialize ballroom

		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);

		System.out.println("done");
		initializeGui();

	}

	public void drawBoard() {

		//this.picLabel.repaint();
		//this.gui.repaint();
	}

	public final void initializeGui() {
		// set up the main GUI

		tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_END);
		JButton newButton = new JButton("New");
		newButton.addActionListener(this.controller);
		tools.add(newButton); // TODO - add functionality!
		JButton suggest = new JButton("Suggest");
		suggest.addActionListener(this.controller);
		tools.add(suggest); // TODO - add functionality!
		tools.add(newButton); // TODO - add functionality!
		JButton accuse = new JButton("Accuse");
		accuse.addActionListener(this.controller);
		tools.add(accuse); // TODO - add functionality!
		tools.addSeparator();
		tools.add(new JButton("Resign")); // TODO - add functionality!
		tools.addSeparator();
		tools.add(message);

		BufferedImage myPicture = null;
		// initialize ball room graphics

		picLabel.setOpaque(true);
		picLabel.setVisible(true);
		picLabel.setLayout(new BorderLayout());

		// picLabel.

		// panel where buttons will be added in grid layout
		cluedoBoard = new JPanel(new GridLayout(0, 27));
		cluedoBoard.setBorder(new LineBorder(Color.BLACK));

		// create the chess board squares
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

				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..

				b.setOpaque(true);
				b.setVisible(true);
				b.setContentAreaFilled(true);
				b.setBorderPainted(false);
				// b.setPreferredSize(new Dimension(200, 200));

				if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|-")) {

					Collections.shuffle(emptytileImages);
					int index = new Random().nextInt(this.emptytileImages.size() - 1);
					b.setIcon(new ImageIcon(emptytileImages.get(index)));
					this.chosentileImages.add(emptytileImages.get(index));

					// TODO Auto-generated catch block

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
						// TODO Auto-generated catch block
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
					// Image scaled = player.getImage().getScaledInstance( 300,
					// 300, java.awt.Image.SCALE_SMOOTH ) ;
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
						// TODO Auto-generated catch block
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

	public void updateBoard(Player p) {
		// update dashboard for each player
		cluedoBoard.removeAll();
		tools.removeAll();
		message = new JLabel(p.getCharacter().name() + "'s turn to play");
		tools.setFloatable(false);
		JButton newButton = new JButton("New");
		newButton.addActionListener(this.controller);
		tools.add(newButton); // TODO - add functionality!
		JButton suggest = new JButton("Suggest");
		suggest.addActionListener(this.controller);
		tools.add(suggest); // TODO - add functionality!
		tools.add(newButton); // TODO - add functionality!
		JButton accuse = new JButton("Accuse");
		accuse.addActionListener(this.controller);
		tools.add(accuse); // TODO - add functionality!
		tools.addSeparator();
		tools.add(new JButton("Resign")); // TODO - add functionality!
		tools.addSeparator();
		tools.add(message);

		tools.add(message);
		tools.addSeparator();
		for (Card c : p.getHand()) {
			JButton imageButton = new JButton();
			Image dimg = c.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			imageButton.setIcon(new ImageIcon(dimg));
			imageButton.addActionListener(this.controller);
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
		//cluedoBoard.repaint();

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

	public static void main(String[] args) {

	}
}
