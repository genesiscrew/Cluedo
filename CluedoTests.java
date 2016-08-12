package cluedo;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

public class CluedoTests {
/**
 * testing the functionality of key implementation of making accusations
 */
	@Test
	public void testValidAccusation_1() {


		Controller control = new Controller();
		Game game = new Game();
		Board bb = null;

		;
		try {
			bb = game.createBoardFromFile(System.getProperty("user.dir") + "/src/board1",
					System.getProperty("user.dir") + "/src/doors.txt", System.getProperty("user.dir") + "/src/tunnels");
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {

			int[] xy = game.getTokenStartLocation(Player.Character.valueOf("MissScarlet"));
			PlayerSquare start = new PlayerSquare(Player.Character.valueOf("MissScarlet").getNumVal(), xy[0], xy[1]);
			int[] xy2 = game.getTokenStartLocation(Player.Character.valueOf("ProfessorPlum"));
			PlayerSquare start2 = new PlayerSquare(Player.Character.valueOf("ProfessorPlum").getNumVal(), xy2[0],
					xy2[1]);
			int[] xy3 = game.getTokenStartLocation(Player.Character.valueOf("MrsWhite"));
			PlayerSquare start3 = new PlayerSquare(Player.Character.valueOf("MrsWhite").getNumVal(), xy3[0], xy3[1]);
			Player player1 = new Player("Jack", Player.Character.valueOf("MissScarlet"), start);
			Player player2 = new Player("Jack", Player.Character.valueOf("ProfessorPlum"), start2);
			Player player3 = new Player("Jack", Player.Character.valueOf("MrsWhite"), start3);
			game.addPlayer(player1);
			game.addPlayer(player2);
			game.addPlayer(player3);
			game.selectSecretCards();
			game.dealCards();

			ArrayList<Card> secrets = game.getSecretCards();
			String weapon = secrets.get(0).getName();
			String roomName = secrets.get(1).getName();
			;
			String suspect = secrets.get(2).getName();
			// check whether accusations
			assertTrue(game.checkAccusation(suspect, weapon, roomName));
			assertTrue(!game.getStatus());



		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
