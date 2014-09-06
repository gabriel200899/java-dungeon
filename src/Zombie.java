/**
 * Created by Bernardo on 06/09/2014.
 */
public class Zombie extends Creature {

    public Zombie(int level) {
        super("Zombie", "An undead walker.", level, 30 + 6 * level, 12 + 4 * level, CreatureID.ZOMBIE);
    }

    public Zombie(int level, Weapon weapon) {
        super("Zombie", "An undead walker.", level, 30 + 6 * level, 12 + 4 * level, CreatureID.ZOMBIE);
        equipWeapon(weapon);
    }

}
