import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        Iterator<Creature> iter = creatures.iterator();
        while (iter.hasNext()) {
            Creature creature = iter.next();
            if (!creature.isAlive())
                iter.remove();
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

}
