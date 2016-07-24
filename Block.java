package cluedo;

public class Block implements Square {

	 private int x;
	  private int y;
	  private String name = "*";
	  private Boolean notEmpty = false;

	public Block(int x, int y) {
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
		return true;
	}

	@Override
	public void setStatus(boolean b) {
		// TODO Auto-generated method stub

	}

}
