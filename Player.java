package cluedo;

import java.util.ArrayList;



public class Player {
	private ArrayList<Property> portfolio;
	private Square location;
	private  Character character;
	private  Weapon weapon;
	private String name;

	public enum Character {
		MissScarlet,
		ColonelMustard,
		MrsWhite,
		TheRevenandGreen,
		MrsPeacock,
		ProfessorPlum
	}
	public enum Weapon {
		CandleStick,
		Dagger,
		LeadPipe,
		Revolver,
		Rope,
		Spanner
	}

}
