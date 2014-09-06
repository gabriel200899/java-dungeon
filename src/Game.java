import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static final String HELP_PATH = System.getProperty("user.dir")
            + "\\src\\JDHelp.txt";
    private static final String TITLE = "Dungeon";
    private static final String INVALID_INPUT = "Invalid input.";
    private static final DateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat DATE = new SimpleDateFormat("dd/MM/yyyy");
    private static Scanner sc = new Scanner(System.in);
    private static String HELP;

    public static void main(String[] args) {

        // Create a world.
        World world = new World(new Location("Training Grounds"));

        // Make the player character.
        Creature player = new Creature("Dude", "You.", 1, 60, 4, CreatureID.UNKNOWN);
        // The player's starting weapon.
        Weapon stick = new Weapon("Stick", 10);
        player.equipWeapon(stick);

        // Add it to the world's starting location.
        world.addCreature(player);

        // Make an enemy and add it to the map.
        world.addCreature(new Rat(2));
        world.addCreature(new Wolf(1));
        world.addCreature(new Rabbit(1));

        // Call a magic method that loads necessary data.
        gameInit();

        // Enter the loop with the world created.
        gameLoop(world, player);
    }

    /**
     * Initializes the game content.
     */
    private static void gameInit() {
        System.out.println("Initializing...");
        try {
            FileReader fReader = new FileReader(HELP_PATH);
            BufferedReader bufferedReader = new BufferedReader(fReader);
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine);
                stringBuilder.append('\n');
            }
            HELP = stringBuilder.toString();
            bufferedReader.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

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
        System.out.println(HELP);
    }

    /**
     * The main game loop. Continuously prompts the player for input.
     */
    private static void gameLoop(World world, Creature player) {
        // Print the game heading.
        printHeading();
        while (true) {
            if (!getTurn(world, player)) {
                break;
            }
        }
    }

    private static boolean getTurn(World world, Creature player) {
        String input = "";
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
