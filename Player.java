package cluedo;

import java.util.ArrayList;

import cluedo.Room.roomName;
import cluedo.Weapon.Weapons;






public class Player{


	private PlayerSquare location;
	private  Character character;
	private  Weapons weapon;
	private String name;
	private ArrayList<Card> cards;
	private ArrayList<Square> visitedBefore;
	boolean inRoom = false;
	boolean InGame = false;
	private Square lv;
	String roomName;
	private boolean suggest = false;
	int doorToken = 0;



	public Player(String name, Character character, PlayerSquare location) {
		this.name = name;
		this.character = character;
		this.location = location;
		cards = new ArrayList<Card>();
		this.visitedBefore = new ArrayList<Square>();
		this.InGame = true;
		//this.visitedBefore.add(location);

	}
	/**
	 * return the character of the player
	 * @return
	 */

	public Character getCharacter(){

		return this.character;
	}
	/**
	 * return the players name
	 * @return
	 */
	public String getName(){

		return this.name;
	}
/**
 * return the current location
 * @return
 */
public PlayerSquare getLocation(){

		return this.location;
	}
/**
 * get the players weapon
 * @return
 */

public Weapons getWeapon(){

	return this.weapon;
}

	public enum Character {
		MissScarlet(1),
		ColonelMustard(2),
		MrsWhite(3),
		TheReverendGreen(4),
		MrsPeacock(5),
		ProfessorPlum(6);

		private int a;

		Character(int a){

			this.a = a;
		}

		 public int getNumVal() {
	        return a;
	    }


	}

 /**
  * give a card to the player
  * @param card
  */
	public void GiveCard(Card card)
	{
		cards.add(card);
	}
	/**
	 * get the players hand of cards
	 * @return
	 */
	public ArrayList<Card> getHand(){

		return this.cards;
	}

   /**
    * this is a key method responsible for checking valid movements,
    *  it checks whether the player can move to the position selected
    * @param newPosition
    * @param board
    * @return
    */
	public boolean isValidMove(Position newPosition, Position oldPosition, Board board) {

        Square newSquare = board.squareAt(newPosition);



		 return // first condition is for movement outside the rooms
				 !board.squareAt(newPosition).isOccupied()&&!this.visitedBefore.contains(board.squareAt(newPosition))
				&& newSquare instanceof Room == false && newSquare instanceof Tunnel == false && !inRoom
				//second expression is for entering rooms from doors
				||(newSquare instanceof Room && this.lv instanceof Door && !this.inRoom
						&& !board.squareAt(newPosition).isOccupied()
						&&!this.visitedBefore.contains(board.squareAt(newPosition)))
				//third expression is for movement within rooms
				 || (newSquare instanceof Room == true  && inRoom && !board.squareAt(newPosition).isOccupied()) && this.lv instanceof Room
				//fourth expression is for exiting rooms from doors
		        || (newSquare instanceof Door && this.inRoom && this.lv instanceof Room)
		        //fifth expression is for tunneling out of room
		  || (newSquare instanceof Tunnel && this.lv instanceof Room)
		//fifth expression is for tunneling into room
		  || (newSquare instanceof Room && this.lv instanceof Tunnel);


	}

    /**
     * we add a new square to the list of visited square
     * @param previous
     */
	public void updateVisitedSquares(Square previous){
		boolean visited = false;
		for (Square s: this.visitedBefore){
			if (previous.getX() == s.getX() && previous.getY() == s.getY()){
				visited = true;

		}
		}
		if (!visited) {
			// we store our last visited square in a field and also into an array of visited squares
			this.visitedBefore.add(previous);
			lv = previous;



		}





	}
	/**
	 * get the list of squares the user has visited in this turn
	 * @return
	 */
	public Square getlastVisited() {

		return this.visitedBefore.get(this.visitedBefore.size()-1);
	}
	/**
	 * get the last square the player has visited
	 * @return
	 */
	public Square getlastSquare() {

		return this.lv;
	}
	/**
	 * reset all visited squares, so that he may visit them in next turn unhindered
	 */

	public void resetVisitedSquares() {

		this.visitedBefore.clear();
	}
	/**
	 * return whether user is in room or not
	 * @return
	 */
	public boolean inRoom() {

		return this.inRoom;
	}
 /**
  * set that use is in a room and the room name he is in
  * @param in
  * @param roomName
  */
	public void isInRoom(Boolean in, String roomName) {

		this.inRoom = true;
		this.roomName = roomName;
	}
	/**
	 * return  the status whether the user has suggested or not
	 * @return
	 */
	public boolean getSuggest() {

		return this.suggest ;
	}
	/**
	 *  set the status of use as having suggested
	 * @param b
	 */
	public void setSuggest(boolean b) {
		this.suggest = b;

	}





}
