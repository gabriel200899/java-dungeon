import java.util.List;
import java.util.Scanner;

public class Game {

	private static Scanner sc = new Scanner(System.in);
	private static final String TITLE = "Dungeon";
	private static final String INVALID_INPUT = "Invalid input.";

	public static void main(String[] args) {

		// Create a world.
		World world = new World(new Location("Training Grounds"));

		// Make the player character.
		Creature player = new Creature("Dude", "You.", 60, 4);
		// The player's starting weapon.
		Weapon stick = new Weapon("Stick", 10);
		player.equipWeapon(stick);

		// Add it to the world's starting location.
		world.addCreature(player);

		// Make an enemy and add it to the map.
		world.addCreature(new Creature("Rat", "A nasty black rat.", 15, 2));

		// Enter the loop with the world created.
		gameLoop(world, player);
	}

	private static void printHeading() {
		StringBuilder heading = new StringBuilder();
		for (int i = 0; i < 5; i++)
			heading.append(' ');
		for (int i = 0; i < 70; i++)
			heading.append('=');
		heading.append('\n');
		for (int i = 0; i < 37; i++)
			heading.append(' ');
		heading.append(TITLE);
		heading.append('\n');
		for (int i = 0; i < 5; i++)
			heading.append(' ');
		for (int i = 0; i < 70; i++)
			heading.append('=');
		heading.append('\n');
		System.out.print(heading.toString());
	}

	private static void printHelp() {

	}

	/**
	 * The main game loop. Continuously prompts the player for input.
	 */
	private static void gameLoop(World world, Creature player) {
		// Print the game heading.
		printHeading();
		while (true) {
			if (!getTurn(player)) {
				break;
			}
		}
	}

	private static boolean getTurn(Creature player) {
		String input = "";
		boolean done = false;
		while (!done) {
			System.out.print("> ");
			input = sc.nextLine();
			switch (input) {
			case "time":
				printTime();
				break;
			case "date":
				printDate();
				break;
			case "look":
				player.look();
				break;
			case "attack":
				Creature target = getTarget(player);
				if (target != null) {
					new Battle(player, target);
				}
				break;
			case "help":
				printHelp();
				break;
			case "quit":
			case "exit":
				return false;
			default:
				System.out.println(INVALID_INPUT);
			}
		}
		return true;
	}

	/**
	 * Let the player choose a target to attack.
	 */
	private static Creature getTarget(Creature player) {
		StringBuilder builder = new StringBuilder();
		builder.append("0. Abort\n");

		List<Creature> visible = player.getLocation().getVisibleCreatures(
				player);

		for (int i = 1; i - 1 < visible.size(); i++) {
			builder.append(i + ". " + visible.get(i - 1).getName() + "\n");
		}

		System.out.print(builder.toString());

		int index = -1;

		while (true) {
			System.out.print("> ");
			try {
				index = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException exception) {
				System.out.println(INVALID_INPUT);
				continue;
			}
			if (0 <= index && index <= visible.size())
				break;
			System.out.println(INVALID_INPUT);
		}
		if (index == 0)
			return null;
		return visible.get(index - 1);
	}

	private static void printDate() {

	}

	private static void printTime() {

	}
}
