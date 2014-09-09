
import java.util.ArrayList;
import java.util.List;

public class World {

    private final List<Location> locations;
    private final SpawnCounter spawnCounter;

    public World(Location startingLocation) {
        spawnCounter = new SpawnCounter();
        locations = new ArrayList<>();
        locations.add(startingLocation);
    }

    /**
     * Add a creature to a specific location.
     *
     * @param creature
     * @param locationIndex
     */
    public void addCreature(Creature creature, int locationIndex) {
        if (-1 < locationIndex && locationIndex < locations.size()) {
            spawnCounter.incrementCounter(creature.getId());
            locations.get(locationIndex).addCreature(creature);
            creature.setLocation(locations.get(locationIndex));
        }
    }

    /**
     * Add an item to a specific location.
     *
     * @param item
     * @param locationIndex
     */
    public void addItem(Item item, int locationIndex) {
        if (-1 < locationIndex && locationIndex < locations.size()) {
            locations.get(locationIndex).addItem(item);
        }
    }

    public Location getLocation(int locationIndex) {
        if (-1 < locationIndex && locationIndex < locations.size()) {
            return locations.get(locationIndex);
        } else {
            return null;
        }
    }

    public void removeAllDead() {
        for (Location worldLocation : locations) {
            worldLocation.removeDead();
        }
    }

    /**
     * Prints all the spawn counters to the console.
     */
    public void printSpawnCounters() {
        spawnCounter.printCounters();
    }
}
