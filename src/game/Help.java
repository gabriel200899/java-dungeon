package game;

public class Help {

    /**
     * Text displayed when the help command is issued.
     */
    public static final String COMMAND_LIST
            = "  Command List\n"
            + "    Exploration\n"
            + "      look                   Looks around, describing what the player sees.\n"
            + "      status                 Printrs the player's character curent status.\n"
            + "      pick                   Pick a weapon on the ground and equip it.\n"
            + "      rest                   Fills your health points.\n"
            + "    Combat\n"
            + "      attack                 Enters target chooser to perform an attack.\n"
            + "      kill x                 Starts a battle with the target chosen(Where x is the target).\n"
            + "    Other\n"
            + "      time                   Get the current time.\n"
            + "      date                   Get the current date.\n"
            + "      exit                   Exit the game.\n"
            + "      quit                   Exit the game.\n"
            + "      spawns                 Get the spawn counters.";

    // The correct usage of printHelp.
    public static final String PRINT_HELP_TEXT_USAGE = "  Usage: help (command)";

    /**
     * Print the proper help string based on the specifiers.
     *
     * @param specifiers
     */
    public static void printHelpText(String[] specifiers) {
        if (specifiers.length == 1) {
            // There are no specifiers, report the correct usage of this method.
            Game.writeString(PRINT_HELP_TEXT_USAGE);
        } else {

        }
    }

    public static void printCommandList() {
        Game.writeString(COMMAND_LIST);
    }

}
