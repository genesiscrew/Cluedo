package cluedo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck {

	  List<Card> cards = new ArrayList<Card>();
/**
 * every new instance of the deck object will automatically contain all game cards
 * most likely a redundant class, but i will keep it for now, as  i might find use for it later
 */
   public Deck(){
	    cards.add(new Card("MissScarlet", "Character"));
		cards.add(new Card("ColonelMustard", "Character"));
		cards.add(new Card("MrsWhite", "Character"));
		cards.add(new Card("TheReverendGreen", "Character"));
		cards.add(new Card("MrsPeacock", "Character"));
		cards.add(new Card("ProfessorPlum", "Character"));
		cards.add(new Card("Rope", "Weapon"));
		cards.add(new Card("Candlestick","Weapon"));
		cards.add(new Card("Revolver", "Weapon"));
		cards.add(new Card ("Lead Pipe", "Weapon"));
		cards.add(new Card ("Spanner", "Weapon"));
		cards.add(new Card ("Dagger", "Weapon"));
		cards.add(new Card ("Kitchen", "Room"));
		cards.add(new Card ("Dining Room", "Room"));
		cards.add(new Card ("Lounge", "Room"));
		cards.add(new Card ("Ball Room", "Room"));
		cards.add(new Card ("Billiard Room", "Room"));
		cards.add(new Card ("Library", "Room"));
		cards.add(new Card ("Hall", "Room"));
		cards.add(new Card ("Conservatory", "Room"));
		cards.add(new Card ("Study", "Room"));


   }
	      {



		};




}
