
public class Game {

	public static void main(String[] args) {
	
		// Make the player character.
		Creature player = new Creature("Dude", 60, 4);
		Weapon stick = new Weapon("Stick", 10);
		player.equipWeapon(stick);
		
		// Make an enemy.
		Creature rat = new Creature("Rat", 15, 2);
		
		
	}

}
