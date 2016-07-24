package cluedo;

public class Center implements Square {
	 private int x;
	  private int y;
	  private String name = "F";
	  private Boolean notEmpty = false;

	public Center(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
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
