import java.util.List;
import java.util.Scanner;

/**
 * Player class.
 * Created by Bernardo on 06/09/2014.
 */
public class Player extends Creature implements Playable {

    public Player() {
        super("Dude", "Yourself.", 1, 60, 5, CreatureID.UNKNOWN);
    }

    /**
     * Rest until the creature is completely healed.
     */
    // TODO: implement time system and make the rest time have some impact on the game.
    public void rest() {
        curHealth = maxHealth;
        System.out.println("You are completely rested.");
    }

    // TODO: as this selection is really similar to that of getTarget, make a method for them.
    public void loot() {
        StringBuilder builder = new StringBuilder();
        builder.append("0. Abort\n");

        List<Creature> lootable = getLocation().getLootableCreatures(this);

        for (int i = 1; i - 1 < lootable.size(); i++)
            builder.append(i).append(". ").append(toLootEntry(lootable.get(i - 1))).append("\n");
        System.out.print(builder.toString());

        Scanner sc = new Scanner(System.in);

        int index;
        while (true) {
            System.out.print("> ");
            try {
                index = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input.");
                continue;
            }
            if (0 <= index && index <= lootable.size())
                break;
            System.out.println("Invalid input.");
        }
        // If the player did not abort the operation.
        if (index > 0) {
            // TODO: do this more elegantly
            Weapon loot = lootable.get(index - 1).getWeapon();
            lootable.get(index - 1).equipWeapon(null);
            this.equipWeapon(loot);
        }
    }

    private String toLootEntry(Creature corpse) {
        return String.format("%-20s:%20s (%d)", corpse.getName(), corpse.getWeapon().getName(), corpse.getWeapon().getDamage());
    }

}
