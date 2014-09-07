
import java.util.List;

/**
 * Player class. Created by Bernardo on 06/09/2014.
 */
public class Player extends Creature implements Playable {

    public Player() {
        super("Dude", "Yourself.", 1, 60, 5, CreatureID.UNKNOWN);
    }

    /**
     * Rest until the creature is completely healed.
     */
    // TODO: implement time system and make the rest time have some impact on the game.
    @Override
    public void rest() {
        curHealth = maxHealth;
        System.out.println("You are completely rested.");
    }

    // TODO: as this selection is really similar to that of getTarget, make a method for them.
    @Override
    public void lootWeapon() {
        StringBuilder builder = new StringBuilder("0. Abort\n");

        List<Weapon> lootable = getLocation().getVisibleWeapons();

        for (int i = 1; i - 1 < lootable.size(); i++) {
            builder.append(i).append(". ").append(toLootEntry(lootable.get(i - 1))).append("\n");
        }
        System.out.print(builder.toString());

        int index;
        while (true) {
            System.out.print("> ");
            try {
                index = Integer.parseInt(Game.sc.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(Game.INVALID_INPUT);
                continue;
            }
            if (0 <= index && index <= lootable.size()) {
                break;
            }
            System.out.println(Game.INVALID_INPUT);
        }
        // The player did not abort the operation.
        if (index > 0) {
            // TODO: do this more elegantly
            this.dropWeapon();
            this.equipWeapon(lootable.get(index - 1));
            getLocation().removeItem(lootable.get(index - 1));
        }
    }

    private String toLootEntry(Weapon w) {
        return String.format("%-20s (%d)", w.getName(), w.getDamage());
    }

}
