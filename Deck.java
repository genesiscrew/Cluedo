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

	    cards.add(new Card("MissScarlet", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/scarlet.jpg"))));
		cards.add(new Card("ColonelMustard", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/mustard.jpg"))));
		cards.add(new Card("MrsWhite", "Character",ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/white.jpg"))));
		cards.add(new Card("TheReverendGreen", "Character",ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/green.jpg"))));
		cards.add(new Card("MrsPeacock", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/peacock.jpg"))));
		cards.add(new Card("ProfessorPlum", "Character", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/characters" + "/plum.jpg"))));
		cards.add(new Card("Rope", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Rope.png"))));
		cards.add(new Card("Candlestick","Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Candlestick.png"))));
		cards.add(new Card("Revolver", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Revolver.png"))));
		cards.add(new Card ("Lead Pipe", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Pipe.png"))));
		cards.add(new Card ("Spanner", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Spanner.png"))));
		cards.add(new Card ("Dagger", "Weapon", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/weapons" + "/Dagger.jpg"))));
		cards.add(new Card ("Kitchen", "Room",  ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/Kitchen.png"))));
		cards.add(new Card ("Dining Room", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/dining.png"))));
		cards.add(new Card ("Lounge", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/lounge.png"))));
		cards.add(new Card ("Ball Room", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/ball.png"))));
		cards.add(new Card ("Billiard Room", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/billiard.png"))));
		cards.add(new Card ("Library", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/library.png"))));
		cards.add(new Card ("Hall", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/hall.png"))));
		cards.add(new Card ("Conservatory", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/conservatory.png"))));
		cards.add(new Card ("Study", "Room", ImageIO.read(new File(System.getProperty("user.dir") + "/src/characterImages/images/rooms" + "/study.png"))));


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
