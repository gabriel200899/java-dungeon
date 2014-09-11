package utils;

import game.Game;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * General utility class.
 *
 * @author Bernardo Sulzbach
 */
public class Utils {

    // DateFormats for time and date printing.
    private static final DateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat DATE = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Print the current date according to the final SimpleDateFormat DATE.
     */
    public static void printDate() {
        Game.writeString(Utils.DATE.format(new Date()));
    }

    /**
     * Print the current time according to the final SimpleDateFormat TIME.
     */
    public static void printTime() {
        Game.writeString(Utils.TIME.format(new Date()));
    }

    /**
     * Creates a string of a repeating character.
     *
     * @param repetitions the length of the string.
     * @param character the character to be used.
     * @return a String.
     */
    public static String makeRepeatedCharacterString(int repetitions, char character) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < repetitions; i++) {
            builder.append(character);
        }
        return builder.toString();
    }

}
