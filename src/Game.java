
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final String TITLE = "Dungeon";

    private static final String DEMO_WORLD_PATH = "demoWorld.jdsave";

    private static final String SAVE_WORLD_ERROR = "Could not save the current world.";

    // DateFormats for time and date printing.
    private static final DateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat DATE = new SimpleDateFormat("dd/MM/yyyy");

    // The Scanner the method Game.readString() uses.
    public static final Scanner SCANNER = new Scanner(System.in);

    // The Random object used to control random events.
    public static final Random RANDOM = new Random();

    public static final String INVALID_INPUT = "Invalid input.";

    // Two 75-character long strings used to improve readability
    public static final String LINE_1 = "---------------------------------------------------------------------------";
    public static final String LINE_2 = "===========================================================================";

    public static void main(String[] args) {
        World world;
        // Check if the user wants to try to load an existing world.
        if (promptAttemptToLoad()) {
            // Attempt to load a serialized World object. If it fails, generate a new world.
            world = loadWorld();
        } else {
            world = demoWorld();
        }

        // Enter the loop with the world created.
        gameLoop(world);
    }

    /**
     * Prompts the user if he/she wants to attempt to load a serialized World object.
     *
     * @return true if the answer was positive, false otherwise.
     */
    private static boolean promptAttemptToLoad() {
        Game.writeString("Attempt to load a saved world? ( Y / N )");
        while (true) {
            String input = Game.readString();
            switch (input.toLowerCase()) {
                case "y":
                case "yes":
                    return true;
                case "n":
                case "no":
                    return false;
                default:
                    Game.writeString(Game.INVALID_INPUT);
            }
        }
    }

    /**
     * Attempts to load a serialized World object. In case of failure, uses demoWorld to generate a new World object.
     *
     * @return a World object.
     */
    private static World loadWorld() {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(DEMO_WORLD_PATH);
            ois = new ObjectInputStream(fis);
            World loadedWorld = (World) ois.readObject();
            ois.close();
            return loadedWorld;
        } catch (IOException | ClassNotFoundException ex) {
            return demoWorld();
        }
    }

    /**
     * Saves a World object to a binary file with the name specified by DEMO_WORLD_PATH.
     *
     * @param world: the object to be saved.
     */
    private static void saveWorld(World world) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(DEMO_WORLD_PATH);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(world);
            oos.close();
        } catch (IOException ex) {
            Game.writeString(SAVE_WORLD_ERROR);
        }

    }

    private static World demoWorld() {
        // Make the player character.
        Mage player = new Mage(1, new Weapon("Stick", 6, 20));
        player.setName("Seth");

        // Create a new world.
        World world = new World(new Location("Training Grounds"), player);

        // Add enemies to the starting location.
        world.addCreature(new Creature(CreatureID.BAT, 1), 0);
        world.addCreature(new Creature(CreatureID.BEAR, 1), 0);
        world.addCreature(new Creature(CreatureID.RABBIT, 1), 0);
        world.addCreature(new Creature(CreatureID.RAT, 1), 0);
        world.addCreature(new Creature(CreatureID.SPIDER, 1), 0);
        world.addCreature(new Creature(CreatureID.WOLF, 1), 0);
        world.addCreature(new Creature(CreatureID.ZOMBIE, 1), 0);
        world.addCreature(new Mage(1, new Weapon("Long Staff", 14, 15)), 0);

        // Add items to the starting location.
        world.addItem(new Weapon("Longsword", 18, 15), 0);

        return world;
    }

    /**
     * The main game loop. Continuously prompts the player for input.
     */
    private static void gameLoop(World world) {
        // Print the game heading.
        Game.writeString(LINE_2);
        Game.writeString(TITLE);
        Game.writeString(LINE_2);

        // Enter the main game loop.
        while (true) {
            // getTurn returns true if the Player did not issue an exit command.
            if (getTurn(world)) {
                // Remove all dead creatures from the world.
                world.removeAllDead();
                // Stop if the player died.
                if (!world.getPlayer().isAlive()) {
                    break;
                }
            } else {
                saveWorld(world);
                break;
            }
        }
    }

    /**
     * Let the player play a turn. Many actions are not considered a turn (e.g.: look).
     *
     * @param world
     * @param player
     * @return false if the player issued an exit command. True if the player played a turn.
     */
    private static boolean getTurn(World world) {
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
                    world.getPlayer().look();
                    break;
                case "loot":
                    world.getPlayer().lootWeapon();
                    break;
                case "destroy":
                    world.getPlayer().destroyItem();
                    break;
                case "rest":
                    world.getPlayer().rest();
                    return true;
                case "status":
                    world.getPlayer().printStatus();
                    break;
                case "kill":
                case "attack":
                    Creature target = world.getPlayer().selectTarget(input);
                    if (target != null) {
                        Game.battle(world.getPlayer(), target);
                        if (!world.getPlayer().isAlive()) {
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

    /**
     * Simulates a battle between two creatures.
     *
     * @param attacker
     * @param defender
     */
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

    /**
     * Read a string from the user.
     *
     * @return
     */
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
