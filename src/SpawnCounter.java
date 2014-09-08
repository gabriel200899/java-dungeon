
import java.util.HashMap;

/**
 * Created by Bernardo on 06/09/2014.
 */
public class SpawnCounter {

    HashMap<CreatureID, Integer> counters;

    public SpawnCounter() {
        counters = new HashMap<>();
    }

    public void incrementCounter(CreatureID id) {
        if (counters.containsKey(id)) {
            counters.put(id, counters.get(id) + 1);
        } else {
            counters.put(id, 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CreatureID id : CreatureID.values()) {
            sb.append(String.format("  %-20s%10d\n", id, counters.get(id)));
        }
        return sb.toString();
    }
}
