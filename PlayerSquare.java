package cluedo;

public class PlayerSquare implements Square {

	 private int x;
	  private int y;
	  private int name;
	  private Boolean notEmpty = true;

		public PlayerSquare(int name, int x, int y) {
			this.x = x;
			this.y = y;
			this.name = name;
		}



	@Override
	public String getName() {
		
		return "|"+Integer.toString(name);
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

	public void setX(int x) {
		
		this.x =  x;
	}


	public void setY(int y) {
		
		this.y = y;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return new Position(this.x, this.y);
	}





}
