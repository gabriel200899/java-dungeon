
public class Weapon extends Item {

    private final WeaponSpecs specs;

    public Weapon(String name, int damage) {
        super(name);
        specs = new WeaponSpecs(damage, 0);
    }

    public Weapon(String name, int damage, int missRate) {
        super(name);
        specs = new WeaponSpecs(damage, missRate);
    }

    public int getDamage() {
        return specs.getDamage();
    }

    /**
     * Randomly evaluates if the next attack of this weapon will miss.
     *
     * @return true if the weapon will miss, false otherwise.
     */
    public final boolean isMiss() {
        return specs.getMissRate() > Math.random() * 100 + 1;
    }

}
