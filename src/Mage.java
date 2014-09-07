
import java.util.List;

/**
 * Mage class that implements the only Playable creature until now.
 */
public class Mage extends Creature implements Playable {

    public Mage(String name, String sInfo) {
        super(name, sInfo, 1, 60, 5, CreatureID.MAGE);
    }
    
    public Mage(int level) {
        super("Mage", "A mage.", level, 40 + 8 * level, 4 + 2 * level, CreatureID.MAGE);
    }

    /**
     * Print the name of the player's current location and list all creatures and items the player can see.
     */
    @Override
    public void look() {
        // Should this raise an exception?
        if (getLocation() == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        // The location's name.
        builder.append(getLocation().getName());
        // All visible creatures.
        builder.append('\n').append(Game.LINE).append('\n');
        String creatures = lookCreatures();
        if (!creatures.isEmpty())
            builder.append(creatures);
        else
            builder.append("You do not see any creatures here.");
        // All visible items.
        builder.append('\n').append(Game.LINE).append('\n');
        String items = lookItems();
        if (!items.isEmpty())
            builder.append(items);
        else
            builder.append("You do not see any items here.");
        builder.append('\n').append(Game.LINE);
        System.out.println(builder.toString());
    }
    
    private String lookCreatures() {
        StringBuilder builder = new StringBuilder();
        List<Creature> visibleCreatures = getLocation().getVisibleCreatures(this);
        for (Creature c : visibleCreatures) {
            builder.append(c.getName()).append('\n');
        }
        // Remove the last newline if there is one.
        if (builder.length() != 0)
            builder.setLength(builder.length() - 1);
        return builder.toString();
    }
    
    private String lookItems() {
        StringBuilder builder = new StringBuilder();
        List<Weapon> visibleWeapons = getLocation().getVisibleWeapons();
        for (Weapon c : visibleWeapons) {
            builder.append(c.getName()).append('\n');
        }
        // Remove the last newline if there is one.
        if (builder.length() != 0)
            builder.setLength(builder.length() - 1);
        return builder.toString();
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

    private String toLootEntry(Weapon weapon) {
        return String.format("%-20s (%d)", weapon.getName(), weapon.getDamage());
    }

}
