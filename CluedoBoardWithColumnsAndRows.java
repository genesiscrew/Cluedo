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
	private ArrayList<Image> ballRoomImages = new ArrayList<Image>();
	String text = "CLUEDO";
	int textlen = text.length();
	ArrayList<Color> colors = new ArrayList<>();

	private Game game;

	private int index4 = 0;

	private int index3 = 0;

	public static int x;

	public static int y;

	private Controller controller;

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

		this.picLabel.repaint();
		this.gui.repaint();
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
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		int index1 = 0;
		int index2 = 0;
		for (int ii = 0; ii < cluedoBoardSquares.length; ii++) {
			for (int jj = 0; jj < cluedoBoardSquares[ii].length; jj++) {
				JButton b = new JButton();

				b.setMargin(buttonMargin);
				b.setBounds(0, 0, 0, 0);
				// b.setPreferredSize(new Dimension(25,25));
				b.addActionListener(this.controller);

				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..
				// ImageIcon icon = new ImageIcon(new BufferedImage(40, 40,
				// BufferedImage.TYPE_INT_ARGB));
				// b.setIcon(icon);

				b.setOpaque(true);
				b.setVisible(true);
				b.setContentAreaFilled(true);
				b.setBorderPainted(false);
				// b.setPreferredSize(new Dimension(200, 200));

				if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|-")) {

					Collections.shuffle(emptytileImages);
					int index = new Random().nextInt(this.emptytileImages.size() - 1);
					b.setIcon(new ImageIcon(emptytileImages.get(index)));

					// TODO Auto-generated catch block

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|S")) {
					b.setBackground(Color.CYAN);

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|L")) {
					b.setBackground(Color.DARK_GRAY);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|E")) {

					b.setBackground(Color.decode("#000066"));
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof PlayerSquare) {

					PlayerSquare player = (PlayerSquare) this.controller.getGame().getBoard().getSquares()[ii][jj];
					ImageIcon playerIcon = new ImageIcon(player.getImage());
					b.setIcon(playerIcon);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|B")) {

					b.setBackground(Color.ORANGE);
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|H")) {

					b.setBackground(Color.decode("#370000"));
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

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
			Image dimg = c.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			imageButton.setIcon(new ImageIcon(dimg));
			tools.add(imageButton);
			tools.addSeparator();
		}

		Insets buttonMargin = new Insets(0, 0, 0, 0);
		int index1 = 0;
		int index2 = 0;
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
				b.setIcon(icon);

				b.setOpaque(true);
				b.setVisible(true);
				b.setContentAreaFilled(true);
				b.setBorderPainted(false);
				// b.setPreferredSize(new Dimension(200, 200));

				if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|-")) {

					Collections.shuffle(emptytileImages);
					int index = new Random().nextInt(this.emptytileImages.size() - 1);
					b.setIcon(new ImageIcon(emptytileImages.get(index)));

					// TODO Auto-generated catch block

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|L")) {
					b.setBackground(Color.DARK_GRAY);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|S")) {
					b.setBackground(Color.CYAN);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj] instanceof PlayerSquare) {
					PlayerSquare player = (PlayerSquare) this.controller.getGame().getBoard().getSquares()[ii][jj];
					ImageIcon playerIcon = new ImageIcon(player.getImage());
					b.setIcon(playerIcon);

				}

				else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|B")) {

					b.setBackground(Color.ORANGE);
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|H")) {
					if ( p.inRoom && p.getRoomName().equals(roomName.Hall.toString())) {
				
						b.setBackground(Color.decode("#B60000"));
						b.setBorderPainted(false);
						b.setBorder(null);
						b.setHorizontalTextPosition(SwingConstants.LEFT);
					} else {
						b.setBackground(Color.decode("#370000"));
						b.setBorderPainted(false);
						b.setBorder(null);
						b.setHorizontalTextPosition(SwingConstants.LEFT);
					}

				} else if (this.controller.getGame().getBoard().getSquares()[ii][jj].getName().equals("|E")) {

					b.setBackground(Color.decode("#000066"));
					b.setBorderPainted(false);
					b.setBorder(null);
					b.setHorizontalTextPosition(SwingConstants.LEFT);

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

		this.revalidate();
		this.repaint();

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
