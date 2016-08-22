package cluedo;

public class Door implements Square {
	 private int x;
	  private int y;
	  private String name = "|D";
	  private Boolean notEmpty = false;
	  private char room = 0;


	public Door(int x, int y, char room) {
		this.x = x;
		this.y = y;
		this.room = room;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public boolean isOccupied() {

		return notEmpty;
	}

	@Override
	public void setStatus(boolean b) {
		notEmpty = b;


	}

	@Override
	public int getX() {

		return x;
	}

	@Override
	public int getY() {

		return y;
	}

	@Override
	public Position getPosition() {

		return new Position(this.x, this.y);
	}

	public char getAscroom() {
		return room;

	}

	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return "Door";
	}

}
