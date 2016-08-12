package cluedo;

public class Tunnel implements Square {
	 private int x;
	  private int y;
	  private char room;
	  private String name = "|T";
	  private Boolean notEmpty = false;

	public Tunnel(int x, int y, char room) {
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
	public boolean matchedTunnel(String match, String match2) {
		return (match.equals("K") && match2.equals("Y")) ||
				(match.equals("O") && match2.equals("C")) ||
				(match.equals("C") && match2.equals("O")) ||
				(match.equals("Y") && match2.equals("K"));



	}

}
