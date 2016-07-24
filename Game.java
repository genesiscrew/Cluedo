package cluedo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import swen221.monopoly.Player;



public class Game {


	public static void main(String[] args) throws IOException {
		Board bb = createBoardFromFile(System.getProperty("user.dir") + "/board1");
		File file = new File(System.getProperty("user.dir") + "/board1.txt");
      for (Square[] s: bb.getSquares()) {
			for (Square a:s) {
				System.out.printf(a.getName());

			}
			System.out.println();

		}


	}


		private static Board createBoardFromFile(String filename) throws IOException {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			ArrayList<String> lines = new ArrayList<String>();
			int width = -1;
			String line;
			int counter = 0;
			while((!(line = br.readLine()).isEmpty())) {
				counter++;
				System.out.println(counter);
				lines.add(line);

				// now sanity check

				if(width == -1) {
					width = line.length();
				} else if(width != line.length()) {
					throw new IllegalArgumentException("Input file \"" + filename + "\" is malformed; line " + lines.size() + " incorrect width.");
				}
			}

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
							board.addRoom("B",y,x);
							break;
						case 'K' :
							board.addRoom("K",y,x);
							break;
						case 'C' :
							board.addRoom("C",y,x);
							break;
						case 'E' :
							board.addRoom("E",y,x);
							break;
						case 'W' :
							board.addRoom("W",y,x);
							break;
						case 'L' :
							board.addRoom("L",y,x);
							break;
						case 'O' :
							board.addRoom("O",y,x);
							break;
						case 'H' :
							board.addRoom("H",y,x);
							break;
						case 'Y' :
							board.addRoom("Y",y,x);
							break;
						case 'S' :
							board.addStart(y,x);
							break;
						case '*' :
							board.addBlock(y,x);
							break;
						case 'D' :
							board.addDoor(y,x);
							break;
						case 'T' :
							board.addTunnel(y,x);
							break;
						case 'F' :
							board.addCenter(y,x);
							break;



					}


				}


			}



			return board;
		}

		public void movePlayer(Player player, int diceRoll) {


		}
}
