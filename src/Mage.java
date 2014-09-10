
import java.util.List;

/**
 * Mage class that is the only playable creature now.
 */
public class Mage extends Creature {

    private static final String name = "Mage";

    public Mage(int level) {
        super(name, level, 40 + 8 * level, 4 + 2 * level, CreatureID.MAGE);
    }

    public Mage(int level, Weapon weapon) {
        super(name, level, 40 + 8 * level, 4 + 2 * level, CreatureID.MAGE);
        equipWeapon(weapon);
    }

    /**
     * Rest until the creature is completely healed.
     */
    // TODO: implement time system and make the rest time have some impact on the game.
    public void rest() {
        curHealth = maxHealth;
        System.out.println("You are completely rested.");
    }

    /**
     * Print the name of the player's current location and list all creatures and items the player can see.
     */
    public void look() {
        // Should this raise an exception?
        if (getLocation() == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        // The location's name.
        builder.append(getLocation().getName());
        // All visible creatures.
        builder.append('\n').append(Game.LINE_1).append('\n');
        String creatures = lookCreatures();
        if (!creatures.isEmpty()) {
            builder.append(creatures);
        } else {
            builder.append("You do not see any creatures here.");
        }
        // All visible items.
        builder.append('\n').append(Game.LINE_1).append('\n');
        String items = lookItems();
        if (!items.isEmpty()) {
            builder.append(items);
        } else {
            builder.append("You do not see any items here.");
        }
        builder.append('\n').append(Game.LINE_1);
        System.out.println(builder.toString());
    }

    private String lookCreatures() {
        StringBuilder builder = new StringBuilder();
        List<Creature> visibleCreatures = getLocation().getVisibleCreatures(this);
        for (Creature c : visibleCreatures) {
            builder.append(c.getName()).append('\n');
        }
        // Remove the last newline if there is one.
        if (builder.length() != 0) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    private String lookItems() {
        StringBuilder builder = new StringBuilder();
        List<Weapon> visibleWeapons = getLocation().getVisibleWeapons();
        for (Weapon c : visibleWeapons) {
            builder.append(c.getName()).append('\n');
        }
        // Remove the last newline if there is one.
        if (builder.length() != 0) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     *
     */
    public void lootWeapon() {
        List<Weapon> visibleWeapons = getLocation().getVisibleWeapons();
        int selectedIndex = selectWeapon(visibleWeapons);
        if (selectedIndex != -1) {
            dropWeapon();
            equipWeapon(visibleWeapons.get(selectedIndex));
            getLocation().removeItem(visibleWeapons.get(selectedIndex));
        }

    }

    /**
     * Tries to destroy an item from the current location.
     */
    public void destroyItem() {
        // TODO implement this.
    }

    /**
     * Let the user select one item from a list of options.
     *
     * @return the index of the item in the list. (-1 if the user aborted)
     */
    private int selectWeapon(List<Weapon> options) {
        int index;
        StringBuilder builder = new StringBuilder("0. Abort\n");
        for (int i = 1; i - 1 < options.size(); i++) {
            builder.append(i).append(". ").append(toSelectionEntry(options.get(i - 1))).append("\n");
        }
        System.out.print(builder.toString());
        while (true) {
            System.out.print("> ");
            try {
                index = Integer.parseInt(Game.SCANNER.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(Game.INVALID_INPUT);
                continue;
            }
            if (0 <= index && index <= options.size()) {
                break;
            }
            System.out.println(Game.INVALID_INPUT);
        }
        return index - 1;

    }

    /**
     *
     */
    private String toSelectionEntry(Item item) {
        return String.format("%-20s", item.getName());
    }

    /**
     * Helper method of selectWeapon.
     */
    private String toSelectionEntry(Weapon weapon) {
        return String.format("%-20s (%d)", weapon.getName(), weapon.getDamage());
    }

}
