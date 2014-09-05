public class Game {

	public static void main(String[] args) {

		// Create a world.
		Map worldMap = new Map(new Location("Training Grounds"));

		// Make the player character.
		Creature player = new Creature("Dude", "You.", 60, 4);
		// The player's starting weapon.
		Weapon stick = new Weapon("Stick", 10);
		player.equipWeapon(stick);

		// Add it to the world's starting location.
		worldMap.addCreature(player);

		// Make an enemy and add it to the map.
		worldMap.addCreature(new Creature("Rat", "A nasty black rat.", 15, 2));

		// Print what the player can see.
		player.look();

	}

}
