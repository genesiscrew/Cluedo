package cluedo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class BoardwithSquares extends JPanel{

private static final long serialVersionUID = 1L;


Image              background;
JButton            continueresume;
JButton            newresume;
JButton            settings;
JButton            exit;
private JButton[][] cluedoBoardSquares = new JButton[27][27];
private static final String COLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
private JPanel cluedoBoard;

public BoardwithSquares(){
	System.out.println("i have been fucking launched");

    try {
		this.background           =  ImageIO.read(new File(System.getProperty("user.dir") + "/src/boardimage.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    this.setLayout(null);

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

			if ((jj % 2 == 1 && ii % 2 == 1)
					// ) {
					|| (jj % 2 == 0 && ii % 2 == 0)) {
				b.setBackground(Color.BLUE);
			} else {
				b.setBackground(Color.GREEN);
			}
			cluedoBoardSquares[jj][ii] = b;
		}
	}

	// fill the chess board
	// cluedoBoard.add(new JLabel(""));
	// fill the top row
	for (int ii = 0; ii < 26; ii++) {
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
	this.add(cluedoBoard);
}





@Override
public void paintComponent(Graphics g){
    
    Graphics2D g2d = (Graphics2D) g;
    if(background != null){
      //g2d.drawImage(background, 0, 0, this);
    }
    g2d.dispose();
    super.paintComponent(g);
}
}