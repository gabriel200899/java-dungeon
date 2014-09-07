
/**
 * Created by Bernardo Sulzbach on 06/09/2014.
 */
public enum CreatureID {

    UNKNOWN("Unknown"), WOLF("Wolf"), RABBIT("Rabbit"), RAT("Rat"), ZOMBIE("Zombie"), MAGE("Mage");

    private final String stringRepresentation;

    CreatureID(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
