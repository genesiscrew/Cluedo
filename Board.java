package cluedo;

import java.util.ArrayList;
import java.util.BitSet;



public class Board {
/**
 * squares array contains the square objects which are displayed on UI
 */
	private Square[][] squares = new Square[26][27];
	public Board(int width, int height) {


	}
	public Square squareAt(Position p){

	return squares[p.row()][p.column()];
	}
	public void addEmpty(int x, int y) {
		Square empty = new Empty(x, y);
		squares[x][y] = empty;

	}
	public Square[][] getSquares() {

		return squares;


	}
	public void addRoom(String string, int x, int y) {
		Square room = new Room(string,x,y);
		squares[x][y] = room;
	}
	public void addStart(int x, int y) {
		Square start = new Start(x,y);
		squares[x][y] = start;


	}
	public void addBlock(int x, int y) {
		Square block = new Block(x,y);
		squares[x][y] = block;

	}
	public void addDoor(int x, int y, char Room) {
		Square door = new Door(x,y, Room);
		squares[x][y] = door;

	}
	public void addTunnel(int x, int y, char room) {
		Square tunnel = new Tunnel(x,y, room);
		squares[x][y] = tunnel;

	}
	public void addCenter(int x, int y) {
		Square center = new Center(x,y);
		squares[x][y] = center;

	}
	public Square[] getStartingLocations(){
		Square[] start = new Square[6];
		int count = 0;
		 for (Square[] s: this.getSquares()) {
				for (Square a:s) {

					if (a instanceof Start) {
					start[count] = a;
					count++;
					}
					}
				}
		 return start;
		 }


	public void setPlayersOnBoard(int nplayers) {
		// TODO Auto-generated method stub
		int count = 1;
		assert(nplayers > 0);

			 for (Square s: this.getStartingLocations()) {
                        s = new PlayerSquare(count, s.getX(), s.getY());

			            }

	}


}
