package cluedo;

public class Empty implements Square {
  private int x;
  private int y;
  private String name;
  private Boolean notEmpty = false;



	public Empty(int x, int y) {
		this.x = x;
		this.y = y;
		this.name = "|-";

	}

	public void setStatus(boolean b){
		notEmpty = b;
	}

	public boolean isOccupied(){

		return notEmpty;
	}

	public String getName() {
		return name;
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
