import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static final String HELP_FILENAME = "JDHelp.txt";
    private static final String GET_TEXT_FROM_FILE_ERROR = "File could not be open.";
    private static final String TITLE = "Dungeon";
    private static final String INVALID_INPUT = "Invalid input.";
    private static final DateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat DATE = new SimpleDateFormat("dd/MM/yyyy");
    private static String HELP;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Create a world.
        World world = new World(new Location("Training Grounds"));

        // Make the player character.
        Player player = new Player();
        // The player's starting weapon.
        Weapon stick = new Weapon("Stick", 10);
        player.equipWeapon(stick);

        // Add it to the world's starting location.
        world.addCreature(player);

        // Make an enemy and add it to the map.
        world.addCreature(new Rat(2));
        world.addCreature(new Wolf(1));
        world.addCreature(new Rabbit(1));
        world.addCreature(new Zombie(2, new Weapon("Pipe", 8)));
        // Call a magic method that loads necessary data.
        gameInit();

        // Enter the loop with the world created.
        gameLoop(world, player);
    }

    /**
     * Get the text of a file in the classpath.
     *
     * @param filename the filename of the file.
     * @return a string with all the text in the file.
     */
    private static String getTextFromFile(String filename) {
        // Get the complete path of the file.
        URL path = ClassLoader.getSystemResource(filename);
        if (path == null) {
            return GET_TEXT_FROM_FILE_ERROR;
        }
        // Make a BufferedReader from that path.
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(path.toURI())));
        } catch (URISyntaxException e) {
            return GET_TEXT_FROM_FILE_ERROR;
        } catch (FileNotFoundException e) {
            return GET_TEXT_FROM_FILE_ERROR;
        }
        // Make a StringBuilder and a String to hold the text.
        StringBuilder builder = new StringBuilder();
        String curLine;
        try {
            // Loop while the file has not been read completely.
            while ((curLine = reader.readLine()) != null) {
                builder.append(curLine).append('\n');
            }
        } catch (IOException e) {
            return GET_TEXT_FROM_FILE_ERROR;
        }
        return builder.toString();
    }

    /**
     * Initialize the game content.
     */
    private static void gameInit() {
        System.out.println("Initializing...");
        HELP = getTextFromFile(HELP_FILENAME);
        System.out.println("Successfully initialized.");
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
        System.out.print(HELP);
    }

    /**
     * The main game loop. Continuously prompts the player for input.
     */
    private static void gameLoop(World world, Player player) {
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

    private static boolean getTurn(World world, Player player) {
        String input;
        while (true) {
            System.out.print("> ");
            // Get the next line of input and convert it to lower.
            input = sc.nextLine().toLowerCase();
            if (input.equals("time")) {
                printTime();

            } else if (input.equals("date")) {
                printDate();

            } else if (input.equals("spawns")) {
                world.printSpawnCounter();

            } else if (input.equals("look")) {
                player.look();

            } else if (input.equals("loot")) {
                player.loot();
                return true;

            } else if (input.equals("rest")) {
                player.rest();

            } else if (input.equals("status")) {
                player.printStatus();

            } else if (input.equals("attack")) {
                Creature target = getTarget(player);
                if (target != null) {
                    new Battle(player, target);
                    return true;
                }

            } else if (input.equals("help")) {
                printHelp();

            } else if (input.equals("quit") || input.equals("exit")) {
                return false;
            } else {
                System.out.println(INVALID_INPUT);
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
            if (0 <= index && index <= visible.size())
                break;
            System.out.println(INVALID_INPUT);
        }
        if (index == 0)
            return null;
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
