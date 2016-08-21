package cluedo;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.Room.roomName;

public class Weapon implements Square {
	private int x;
	private int y;
	private String name;
	private Boolean notEmpty;
	private String room;
	Image image;

	@Override
	public String getName() {

		return this.name;
	}

	public enum Weapons {
		CandleStick("|s"), 
		Dagger("|r"), 
		LeadPipe("|l"), 
		Revolver("|v"), 
		Rope("|p"), 
		Spanner("|n");

		private String a;

		Weapons(String a) {

			this.a = a;
		}

		public String getVal() {

			return a;
		}

		public Image ImageEnum() {

			try {

				return ImageIO.read(new File(System.getProperty("user.dir")
						+ "/src/characterImages/images/tokens/weapontokens/" + this.a + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	@Override
	public boolean isOccupied() {

		return true;
	}

	@Override
	public void setStatus(boolean b) {

	}

	public Weapon(int x, int y, String name, String room, Image image) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.room = room;
		this.image = image;

	}

	@Override
	public int getX() {

		return x;
	}

	@Override
	public int getY() {

		return y;
	}

	public String getRoom() {

		return this.room;
	}

	@Override
	public Position getPosition() {

		return new Position(this.x, this.y);
	}

	public String getFullName() {
		// iterate throught all enums
		for (Weapons r : Weapons.values()) {
			if (r.getVal().equals(this.getName())) {
				return r.name();

			}
		}
		return null;

	}

	public Image getImage() {
		// TODO Auto-generated method stub
		return this.image;
	}

}
