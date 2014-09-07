
/**
 * Created by Bernardo on 06/09/2014.
 */
public class Rat extends Creature {

    public Rat(int level) {
        super("Rat", "A nasty black rat.", level, 11 + 4 * level, 3 + 2 * level, CreatureID.RAT);
    }
}
