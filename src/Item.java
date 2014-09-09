
import java.io.Serializable;

/**
 * Item class that defines common properties for all items.
 *
 * @author bernardo
 */
public class Item implements Serializable {

    private String name;
    private final boolean destructible;

    public Item(String name) {
        this.name = name;
        this.destructible = false;
    }

    public Item(String name, boolean destructible) {
        this.name = name;
        this.destructible = destructible;
    }

    protected String getName() {
        return name;
    }

    public boolean isDestructible() {
        return destructible;
    }
}
