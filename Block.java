package cluedo;

public class Block implements Square {

	 private int x;
	  private int y;
	  private String name = "|*";
	  private Boolean notEmpty;

	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public boolean isOccupied() {
		
		return true;
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

}
