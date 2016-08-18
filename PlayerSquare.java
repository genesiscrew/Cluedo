package cluedo;

import java.awt.Image;

import cluedo.Player.Character;

public class PlayerSquare implements Square {

	 private int x;
	  private int y;
	  private int name;
	  private Boolean notEmpty = true;
	  private Image image;
	  private Room room = null;

		public PlayerSquare(int name, int x, int y, Image image) {
			this.x = x;
			this.y = y;
			this.name = name;
			this.image = image;
		}

		public void  setRoom(Room room) {
			this.room = room;

		}
		public Room getRoom() {
			return this.room;

		}



	@Override
	public String getName() {

		return "|"+Integer.toString(name);
	}

	public String getFullName() {

		for (Player.Character r : Character.values()) {
			if (r.getNumVal() == this.name) {
				return r.name();

			}
		}
		return null;

	}

	public Image getImage() {

		return this.image;
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
