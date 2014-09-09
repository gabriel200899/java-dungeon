
public class Weapon extends Item {

    private int damage;

    /**
     * How often (from 0 [never] to 100 [always]) does the weapon miss?
     */
    private int missRate;

    public Weapon(String name, int damage) {
        super(name);
        this.damage = damage;
        this.missRate = 0;
    }

    public Weapon(String name, int damage, int missRate) {
        super(name);
        this.damage = damage;
        this.missRate = missRate;
    }

    public int getDamage() {
        return damage;
    }

    public final void setDamage(int damage) {
        if (damage < 0) {
            this.damage = 0;
        } else {
            this.damage = damage;
        }
    }

    public int getMissRate() {
        return missRate;
    }

    public final void setMissRate(int missRate) {
        if (missRate < 0) {
            this.missRate = 0;
        } else if (missRate > 100) {
            this.missRate = 100;
        } else {
            this.missRate = missRate;
        }
    }

    /**
     * Randomly evaluates if the next attack of this weapon will miss.
     *
     * @return true if the weapon will miss, false otherwise.
     */
    public final boolean isMiss() {
        return missRate > Math.random() * 100 + 1;
    }

}
