import java.util.ArrayList;
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
     * Retrieves all visible creatures to the observer. Currently, that
     * corresponds to all the creatures but the observer.
     */
    public List<Creature> getVisibleCreatures(Creature observer) {
        List<Creature> allButObserver = creatures;
        allButObserver.remove(observer);
        return allButObserver;
    }

}
