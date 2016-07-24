package cluedo;

import java.util.BitSet;



public class Board {
	int width;
	int height;
	private Square[][] squares = new Square[26][26];
	public Board(int width, int height) {
		this.width = width;
		this.height = height;

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
	public void addDoor(int x, int y) {
		Square door = new Door(x,y);
		squares[x][y] = door;

	}
	public void addTunnel(int x, int y) {
		Square tunnel = new Tunnel(x,y);
		squares[x][y] = tunnel;
		
	}
	public void addCenter(int x, int y) {
		Square center = new Center(x,y);
		squares[x][y] = center;
		
	}


}
