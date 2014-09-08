
public class Rabbit extends Creature {

    public Rabbit(int level) {
        super("Rabbit", level, 10 + 2 * level, 5 + 2 * level, CreatureID.RABBIT);
    }
}
