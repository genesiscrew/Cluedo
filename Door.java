package cluedo;

public class Door implements Square {
	 private int x;
	  private int y;
	  private String name = "D";
	  private Boolean notEmpty = false;


	public Door(int x, int y) {
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
