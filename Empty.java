package cluedo;

public class Empty implements Square {
  private int x;
  private int y;
  private String name;
  private Boolean notEmpty = false;



	public Empty(int x, int y) {
		this.x = x;
		this.y = y;
		this.name = "-";

	}

	public void setStatus(boolean b){
		notEmpty = b;
	}

	public boolean isOccupied(Square e){

		return false;
	}

	public String getName() {
		return name;
	}




}
