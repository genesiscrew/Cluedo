package cluedo;

import cluedo.Room.roomName;

public class Room implements Square {

	  private int x;
	  private int y;
	  private String name;
	  public roomName fullName;
	  private Boolean notEmpty = false;

	public Room(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;


	}

	public enum roomName {
		Ballroom("|B"),
		Kitchen("|K"),
		BilliardRoom("|W"),
		DiningRoom("|E"),
		Lounge("|O"),
		Study("|Y"),
		Hall("|H"),
		Library("|L"),
		Conservatory("|C"),
		Center("|F");

		private String a;

		roomName(String a){

			this.a = a;
		}

		 public String getroomName() {
	        return a;
	    }

	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public boolean isOccupied() {

			return notEmpty;
	}

	@Override
	public void setStatus(boolean b) {
		notEmpty = b;

	}


	public Room(int x, int y) {
		this.x = x;
		this.y = y;

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
		// TODO Auto-generated method stub
		return new Position(this.x, this.y);
	}
/**
 * 	convert pseodonym room name from board to full room name

 */
	public String getFullName() {
		//iterate throught all enums
		for (Room.roomName r:roomName.values()){
			if (r.getroomName().equals(this.getName())) {
				return r.name();

			}
		}
		return null;

	}

}
