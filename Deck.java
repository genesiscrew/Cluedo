package cluedo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Deck {

	  List<Card> cards = new ArrayList<Card>();
/**
 * every new instance of the deck object will automatically contain all game cards
 * most likely a redundant class, but i will keep it for now, as  i might find use for it later
 * @throws IOException
 */
   public Deck() throws IOException{

	    cards.add(new Card("MissScarlet", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/MissScarlet.jpg"))));
		cards.add(new Card("ColonelMustard", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/ColonelMustard.jpg"))));
		cards.add(new Card("MrsWhite", "Character",ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/MrsWhite.jpg"))));
		cards.add(new Card("TheReverendGreen", "Character",ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/TheReverendGreen.jpg"))));
		cards.add(new Card("MrsPeacock", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/MrsPeacock.jpg"))));
		cards.add(new Card("ProfessorPlum", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/ProfessorPlum.jpg"))));
		cards.add(new Card("Rope", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Rope.png"))));
		cards.add(new Card("Candlestick","Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Candlestick.png"))));
		cards.add(new Card("Revolver", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Revolver.png"))));
		cards.add(new Card ("LeadPipe", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/LeadPipe.png"))));
		cards.add(new Card ("Spanner", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Spanner.png"))));
		cards.add(new Card ("Dagger", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Dagger.png"))));
		cards.add(new Card ("Kitchen", "Room",  ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Kitchen.png"))));
		cards.add(new Card ("DiningRoom", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/DiningRoom.png"))));
		cards.add(new Card ("Lounge", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Lounge.png"))));
		cards.add(new Card ("BallRoom", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Ballroom.png"))));
		cards.add(new Card ("BilliardRoom", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/BilliardRoom.png"))));
		cards.add(new Card ("Library", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Library.png"))));
		cards.add(new Card ("Hall", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Hall.png"))));
		cards.add(new Card ("Conservatory", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Conservatory.png"))));
		cards.add(new Card ("Study", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Study.png"))));


   }


   {



		};

public boolean remove(Card card) {
	for (int i = 0; i < this.cards.size(); i++) {
		if (cards.get(i).getName().equals(card.getName())) {
			cards.remove(i);
			return true;
		}

	}
	return false;
}


}
