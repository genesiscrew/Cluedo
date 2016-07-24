package cluedo;

public class Room implements Square {

	  private int x;
	  private int y;
	  private String name;
	  private Boolean notEmpty = false;

	public Room(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public enum roomName {
		Ballroom,
		Kitchen,
		BilliardRoom,
		DiningRoom,
		Lounge,
		Study,
		Hall,
		Library,
		Conservatory,
		Center

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isOccupied(Square e) {

			return false;
	}

	@Override
	public void setStatus(boolean b) {
		notEmpty = b;

	}


	public Room(int x, int y) {
		this.x = x;
		this.y = y;

	}

}
