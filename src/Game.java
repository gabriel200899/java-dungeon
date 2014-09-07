
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static final String TITLE = "Dungeon";
    private static final String HELP_FILENAME = "JDHelp.txt";

    // DateFormats for time and date printing.
    private static final DateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat DATE = new SimpleDateFormat("dd/MM/yyyy");

    // The only Scanner object of the program.
    public static final Scanner sc = new Scanner(System.in);

    public static final String INVALID_INPUT = "Invalid input.";
    private static final String GET_TEXT_FROM_FILE_ERROR = "File could not be open.\n";

    public static final char LINE_CHAR = '-';
    public static final int LINE_SIZE = 80;
    public static String LINE;

    public static void main(String[] args) {
        // Create a world.
        World world = new World(new Location("Training Grounds"));

        // Make the player character.
        Mage player = new Mage("Seth", "A tall mage wearing a black robe.");
        
        // The player's starting weapon.
        Weapon stick = new Weapon("Stick", 10);
        player.equipWeapon(stick);

        // Add it to the world's starting location.
        world.addCreature(player);
        
        // Place a nice sword on the floor.
        player.getLocation().addItem(new Weapon ("Longsword", 18));

        // Make some enemies and add them to the map.
        world.addCreature(new Rat(2));
        world.addCreature(new Wolf(1));
        world.addCreature(new Rabbit(1));
        world.addCreature(new Zombie(2, new Weapon("Pipe", 8)));
        
        // Create another mage.
        Mage anotherMage = new Mage(1);
        anotherMage.equipWeapon(new Weapon("Long Staff", 14));
        world.addCreature(anotherMage);

        // Prepare and load all necessary data.
        gameInit();

        // Enter the loop with the world created.
        gameLoop(world, player);
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
        while (true) {
            // getTurn returns false if the player issued an exit command.
            if (!getTurn(world, player)) {
                // TODO: handle player's death.
                break;
            }
            // Remove all the dead creatures from the world.
            world.removeDead();
        }
    }

    private static boolean getTurn(World world, Mage player) {
        String input;
        while (true) {
            System.out.print("> ");
            // Get the next line of input and convert it to lower.
            input = sc.nextLine().toLowerCase();
            switch (input) {
                case "time":
                    printTime();
                    break;
                case "date":
                    printDate();
                    break;
                case "spawns":
                    world.printSpawnCounter();
                    break;
                case "look":
                    player.look();
                    break;
                case "loot":
                    player.lootWeapon();
                    return true;
                case "rest":
                    player.rest();
                    break;
                case "status":
                    player.printStatus();
                    break;
                case "attack":
                    Creature target = getTarget(player);
                    if (target != null) {
                        new Battle(player, target);
                        return true;
                    }
                    break;
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
     * Let the player choose a target to attack.
     */
    private static Creature getTarget(Creature player) {
        StringBuilder builder = new StringBuilder();
        builder.append("0. Abort\n");

        List<Creature> visible = player.getLocation().getVisibleCreatures(
                player);

        for (int i = 1; i - 1 < visible.size(); i++) {
            builder.append(i).append(". ").append(visible.get(i - 1).getName()).append("\n");
        }

        System.out.print(builder.toString());

        int index;
        while (true) {
            System.out.print("> ");
            try {
                index = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(INVALID_INPUT);
                continue;
            }
            if (0 <= index && index <= visible.size()) {
                break;
            }
            System.out.println(INVALID_INPUT);
        }
        if (index == 0) {
            return null;
        }
        return visible.get(index - 1);
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

}
