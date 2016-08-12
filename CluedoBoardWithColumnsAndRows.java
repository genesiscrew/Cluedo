package cluedo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class CluedoBoardWithColumnsAndRows {

	private final JPanel gui = new JPanel(new BorderLayout(9, 9));

	JPanel picLabel = new BoardwithSquares();
	private JButton[][] cluedoBoardSquares = new JButton[26][27];
	private static final String COLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private JPanel cluedoBoard;
	private final JLabel message = new JLabel("Cluedo is ready to play!");

	private Game game;


	CluedoBoardWithColumnsAndRows(Game game) {
		this.game = game;
		initializeGui();

	}

	public final void initializeGui() {
		// set up the main GUI

		BufferedImage myPicture = null;

		// try {

			//picLabel2.setLayout(new BorderLayout());

			//Insets imageMargin = new Insets(0, 0, 0, 0);
			//picLabel.setBounds(0, 0, 0, 0);
			picLabel.setOpaque(true);
			picLabel.setVisible(true);
			picLabel.setLayout(new BorderLayout());


			//picLabel.

			gui.add(picLabel, BorderLayout.PAGE_START);
			cluedoBoard = new JPanel(new GridLayout(0, 27));
			cluedoBoard.setBorder(new LineBorder(Color.BLACK));

			// create the chess board squares
			Insets buttonMargin = new Insets(0, 0, 0, 0);
			for (int ii = 0; ii < cluedoBoardSquares.length; ii++) {
				for (int jj = 0; jj < cluedoBoardSquares[ii].length; jj++) {
					JButton b = new JButton();
					b.setMargin(buttonMargin);
					b.setBounds(0, 0, 0, 0);
					b.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (!e.getActionCommand().isEmpty()) {

								System.out.println("i have been fucking clicked");
							}


						}
					});
					// our chess pieces are 64x64 px in size, so we'll
					// 'fill this in' using a transparent icon..
					ImageIcon icon = new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB));
					b.setIcon(icon);

					b.setOpaque(true);
					b.setVisible(true);
					b.setContentAreaFilled(true);
					b.setBorderPainted(true);



					if (game.getBoard().getSquares()[ii][jj].getName().equals("|-")) {
						b.setBackground(Color.BLUE);
						System.out.println("BLUE");



					}
					else if (game.getBoard().getSquares()[ii][jj].getName().equals("|D")) {
						b.setBackground(Color.ORANGE);
						
					}
					else if (game.getBoard().getSquares()[ii][jj].getName().equals("|S")) {
						b.setBackground(Color.CYAN);
						
					}
					else if (game.getBoard().getSquares()[ii][jj].getName().equals("|D")) {
						b.setBackground(Color.ORANGE);
						
					}
					else if (game.getBoard().getSquares()[ii][jj].getName().equals("|F")) {
						b.setBorderPainted(false);
						b.setBackground(Color.RED);
						
					}
					else {
						b.setBackground(Color.BLACK);
						b.setBorderPainted(false);
						System.out.println("BLACK");

					}

					cluedoBoardSquares[ii][jj] = b;
					cluedoBoard.add(cluedoBoardSquares[ii][jj]);
				}
			}

			// fill the chess board
			// cluedoBoard.add(new JLabel(""));
			// fill the top row
		/*	for (int ii = 0; ii < 26; ii++) {

				cluedoBoard.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
			}
			// fill the black non-pawn piece row
			for (int ii = 0; ii < 27; ii++) {
				for (int jj = 0; jj < 27; jj++) {
					switch (jj) {
					case 0:
						///cluedoBoard.add(new JLabel("" + (ii + 1),
						// SwingConstants.CENTER));
					default:
						cluedoBoard.add(cluedoBoardSquares[jj][ii]);
					}
				}
			}
			*/
			/*
			  PaintPane boardImage = new PaintPane(ImageIO.read(new File(System.getProperty("user.dir") + "/src/boardimage.jpg")));

			 boardImage.setLayout(new BorderLayout());
			 gui.add(boardImage, BorderLayout.NORTH);
			 */
			System.out.println("i am here");
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
	//	}




		picLabel.add(cluedoBoard);

		//gui.add(cluedoBoard);




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
		Runnable r = new Runnable() {

			@Override
			public void run() {
				  Game game = new Game();
			        Board bb = null;
			        ;
			        try {

			            bb = game.createBoardFromFile(System.getProperty("user.dir") + "/src/board1",
			                    System.getProperty("user.dir") + "/src/doors.txt", System.getProperty("user.dir") + "/src/tunnels");
			        } catch (IOException e) {

			            e.printStackTrace();
			        }
				CluedoBoardWithColumnsAndRows cb = new CluedoBoardWithColumnsAndRows(game);
				JFrame f = new JFrame("CluedoGUI");

				f.add(cb.getPic());
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setLocationByPlatform(true);
                // wow.se
				// ensures the frame is the minimum size it needs to be
				// in order display the components within it
				//f.setContentPane(cb.);
				f.pack();
				// ensures the minimum size is enforced.
				f.setSize(1000,1000);
				f.setVisible(true);

			}
		};
		SwingUtilities.invokeLater(r);
	}
}
