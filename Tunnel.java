package cluedo;

public class Tunnel implements Square {
	 private int x;
	  private int y;
	  private String name = "T";
	  private Boolean notEmpty = false;

	public Tunnel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isOccupied(Square e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setStatus(boolean b) {
		// TODO Auto-generated method stub

	}

}
