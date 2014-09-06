import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Location {

    private String name;
    private List<Creature> creatures;

    public Location(String name) {
        creatures = new ArrayList<Creature>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    /**
     * Remove all dead creatures in the current location.
     */
    public void removeDead() {
        // To safely remove from a collection we must use an iterator.
        Iterator<Creature> i = creatures.iterator();
        while (i.hasNext()) {
            Creature creature = i.next();
            if (!creature.isAlive() && creature.isEmpty())
                i.remove();
        }
    }

    /**
     * Retrieves all visible creatures to the observer. Currently, that
     * corresponds to all the creatures but the observer.
     */
    public List<Creature> getVisibleCreatures(Creature observer) {
        List<Creature> allButObserver = creatures;
        allButObserver.remove(observer);
        return allButObserver;
    }

    /**
     * Return a list with all the lootable creatures.
     */
    public List<Creature> getLootableCreatures(Creature observer) {
        List<Creature> lootable = new ArrayList<Creature>();
        for (Creature next : getVisibleCreatures(observer)) {
            if (next.isLootable()) {
                lootable.add(next);
            }
        }
        return lootable;
    }
}