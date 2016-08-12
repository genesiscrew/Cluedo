package cluedo;

public class Start implements Square {
	  private int x;
	  private int y;
	  private String name = "|S";
	  private Boolean notEmpty = false;
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


	public Start(int x, int y) {
		this.x = x;
		this.y = y;

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

}
