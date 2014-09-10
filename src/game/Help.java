package game;

public class Help {

    /**
     * Text displayed when the help command is issued.
     */
    public static final String COMMAND_LIST
            = "  Command List\n"
            + "    Exploration\n"
            + "      look                   Looks around, describing what the player sees.\n"
            + "      status                 Prints the player's character current status.\n"
            + "    Combat\n"
            + "      attack                 Enters target chooser to perform an attack.\n"
            + "    Other\n"
            + "      time                   Get the current time.\n"
            + "      date                   Get the current date.\n"
            + "      exit                   Exit the game.\n"
            + "      quit                   Exit the game.\n"
            + "      spawns                 Get the spawn counters.";

    public static void printCommandList() {
        Game.writeString(COMMAND_LIST);
    }

}
