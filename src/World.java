
import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Location> locations;
    private SpawnCounter spawnCounter;

    public World(Location startingLocation) {
        spawnCounter = new SpawnCounter();
        locations = new ArrayList<Location>();
        locations.add(startingLocation);
    }

    // Add a creature to the starting location of the map.
    public void addCreature(Creature creature) {
        spawnCounter.incrementCreatureCounter(creature.getId());
        locations.get(0).addCreature(creature);
        creature.setLocation(locations.get(0));
    }

    public void removeDead() {
        for (Location worldLocation : locations) {
            worldLocation.removeDead();
        }
    }

    public void printSpawnCounter() {
        System.out.println(spawnCounter);
    }
}
