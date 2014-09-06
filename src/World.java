import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Location> locations;

    public World(Location startingLocation) {
        locations = new ArrayList<Location>();
        locations.add(startingLocation);
    }

    // Add a creature to the starting location of the map.
    public void addCreature(Creature creature) {
        locations.get(0).addCreature(creature);
        creature.setLocation(locations.get(0));
    }
}
