package cluedo;

public class Start implements Square {
	  private int x;
	  private int y;
	  private String name = "S";
	  private Boolean notEmpty = false;
	@Override
	public String getName() {

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


	public Start(int x, int y) {
		this.x = x;
		this.y = y;

	}

}
