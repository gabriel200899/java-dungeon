
/**
 * Created by Bernardo Sulzbach on 06/09/2014.
 */
public enum CreatureID {

    BAT("Bat"), BEAR("Bear"), MAGE("Mage"), RABBIT("Rabbit"), RAT("Rat"), SPIDER("Spider"), UNKNOWN("Unknown"), WOLF("Wolf"), ZOMBIE("Zombie");

    private final String stringRepresentation;

    CreatureID(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
