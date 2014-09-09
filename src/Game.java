
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final String TITLE = "Dungeon";

    // DateFormats for time and date printing.
    private static final DateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat DATE = new SimpleDateFormat("dd/MM/yyyy");

    // The Scanner the method Game.readString() uses.
    public static final Scanner SCANNER = new Scanner(System.in);

    // The Random object used to control random events.
    public static final Random RANDOM = new Random();

    public static final String INVALID_INPUT = "Invalid input.";

    public static final char LINE_CHAR = '-';
    public static final int LINE_SIZE = 80;
    public static String LINE;

    public static void main(String[] args) {
        // Get a hardcoded demo world.
        World world = demoWorld();

        // Make the player character.
        Mage player = new Mage(1, new Weapon("Stick", 6, 20));
        player.setName("Seth");

        // Add the player to the world's starting location.
        world.addCreature(player, 0);

        // Prepare and load all necessary data.
        gameInit();

        // Enter the loop with the world created.
        gameLoop(world, player);

    }

    private static World demoWorld() {
        // Create a new world.
        World world = new World(new Location("Training Grounds"));

        // Add enemies to the starting location.
        world.addCreature(new Rat(1), 0);
        world.addCreature(new Creature(CreatureID.WOLF, 1), 0);
        world.addCreature(new Rabbit(1), 0);
        world.addCreature(new Zombie(1, new Weapon("Pipe", 8, 10)), 0);
        world.addCreature(new Mage(1, new Weapon("Long Staff", 14, 15)), 0);

        // Add items to the starting location.
        world.addItem(new Weapon("Longsword", 18, 15), 0);

        return world;
    }

    /**
     * Initialize the game content.
     */
    private static void gameInit() {
        System.out.println("Initializing...");
        // Initialize the separator line.
        char[] lineArray = new char[LINE_SIZE];
        Arrays.fill(lineArray, LINE_CHAR);
        LINE = new String(lineArray);
        System.out.println("Successfully initialized.");
    }

    private static void printHeading() {
        StringBuilder heading = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            heading.append(' ');
        }
        for (int i = 0; i < 70; i++) {
            heading.append('=');
        }
        heading.append('\n');
        for (int i = 0; i < 37; i++) {
            heading.append(' ');
        }
        heading.append(TITLE);
        heading.append('\n');
        for (int i = 0; i < 5; i++) {
            heading.append(' ');
        }
        for (int i = 0; i < 70; i++) {
            heading.append('=');
        }
        heading.append('\n');
        System.out.print(heading.toString());
    }

    /**
     * The main game loop. Continuously prompts the player for input.
     */
    private static void gameLoop(World world, Mage player) {
        // Print the game heading.
        printHeading();
        //
        while (true) {
            // 
            if (getTurn(world, player)) {
                // Remove all dead creatures from the world.
                world.removeAllDead();
                if (!player.isAlive()) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    /**
     * Let the player play a turn.
     *
     * @param world
     * @param player
     * @return
     */
    private static boolean getTurn(World world, Mage player) {
        String[] input;
        while (true) {
            System.out.print("> ");
            // Get the next line of input, convert it to lowercase, trim its endings and split it into separate words.
            input = SCANNER.nextLine().toLowerCase().trim().split("\\s+");
            // Currently, we only use the first word.
            switch (input[0]) {
                case "time":
                    printTime();
                    break;
                case "date":
                    printDate();
                    break;
                case "spawns":
                    world.printSpawnCounters();
                    break;
                case "look":
                    player.look();
                    break;
                case "loot":
                    player.lootWeapon();
                    break;
                case "destroy":
                    player.destroyItem();
                    break;
                case "rest":
                    player.rest();
                    break;
                case "status":
                    player.printStatus();
                    break;
                case "kill":
                case "attack":
                    Creature target = player.selectTarget(input);
                    if (target != null) {
                        Game.battle(player, target);
                        if (!player.isAlive()) {
                            System.out.println("You died.");
                        }
                    }
                    return true;
                case "help":
                    Help.printCommandList();
                    break;
                case "quit":
                case "exit":
                    return false;
                default:
                    System.out.println(INVALID_INPUT);
                    break;
            }
        }
    }

    private static void battle(Creature attacker, Creature defender) {
        while (attacker.isAlive() && defender.isAlive()) {
            attacker.attack(defender);
            if (defender.isAlive()) {
                defender.attack(attacker);
            }
        }
        String survivor, defeated;
        if (attacker.isAlive()) {
            survivor = attacker.getName();
            defeated = defender.getName();
        } else {
            survivor = defender.getName();
            defeated = attacker.getName();
        }
        System.out.printf("%s managed to kill %s.\n", survivor, defeated);
    }

    /**
     * Print the current time according to the final SimpleDateFormat TIME.
     */
    private static void printTime() {
        System.out.println(TIME.format(new Date()));
    }

    /**
     * Print the current date according to the final SimpleDateFormat DATE.
     */
    private static void printDate() {
        System.out.println(DATE.format(new Date()));
    }

    public static String readString() {
        String line;
        do {
            System.out.print("> ");
            line = SCANNER.nextLine();
        } while (line.equals(""));
        return line;
    }

    /**
     * Outputs a string to the console, stripping unnecessary newlines at the end.
     *
     * @param string the string to be printed.
     */
    public static void writeString(String string) {
        while (string.endsWith("\n")) {
            // Remove the newline.
            string = string.substring(0, string.length() - 1);
        }
        System.out.println(string);
    }

}
