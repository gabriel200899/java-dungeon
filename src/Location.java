
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Location {

    private String name;
    private List<Creature> creatures;
    private List<Item> items;

    public Location(String name) {
        creatures = new ArrayList<>();
        items = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Remove all dead creatures in the current location.
     */
    public void removeDead() {
        // To safely remove from a collection we must use an iterator.
        Iterator<Creature> i = creatures.iterator();
        while (i.hasNext()) {
            Creature creature = i.next();
            if (!creature.isAlive()) {
                if (!creature.isEmpty()) {
                    items.add(creature.getWeapon());
                }
                i.remove();
            }
        }
    }

    /**
     * Retrieves all visible creatures to the observer. Currently, that corresponds to all the creatures but the
     * observer.
     */
    public List<Creature> getVisibleCreatures(Creature observer) {
        List<Creature> allButObserver = creatures;
        allButObserver.remove(observer);
        return allButObserver;
    }

    public List<Weapon> getVisibleWeapons() {
        List<Weapon> visibleWeapons = new ArrayList<>();
        for (Item visibleItem : items) {
            if (visibleItem instanceof Weapon) {
                visibleWeapons.add((Weapon) visibleItem);
            }
        }
        return visibleWeapons;
    }

    public List<Item> getVisibleItems() {
        // Currently, all dropped items are visible.
        return items;
    }
}
