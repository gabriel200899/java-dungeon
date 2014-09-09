
public class Weapon extends Item {

    /**
     * How much damage the weapon makes.
     */
    private int damage;

    /**
     * How often (from 0 [never] to 100 [always]) does the weapon miss.
     */
    private int missRate;

    /**
     * Weapon integrity variables.
     */
    private int maxIntegrity;
    private int curIntegrity;
    // How much integrity is lost per hit.
    // Frask  adsf 
    private int hitDecrement;

    public Weapon(String name, int damage) {
        this(name, damage, 0, 100, 100, 1);
    }

    public Weapon(String name, int damage, int missRate) {
        this(name, damage, missRate, 100, 100, 1);
    }

    public Weapon(String name, int damage, int missRate, int maxIntegrity, int curIntegrity, int hitDecrement) {
        super(name);
        this.damage = damage;
        this.missRate = missRate;
        this.maxIntegrity = maxIntegrity;
        this.curIntegrity = curIntegrity;
        this.hitDecrement = hitDecrement;
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

    public int getMaxIntegrity() {
        return maxIntegrity;
    }

    public int getCurIntegrity() {
        return curIntegrity;
    }

    public final boolean isBroken() {
        return curIntegrity == 0;
    }

    /**
     * Randomly evaluates if the next attack of this weapon will miss.
     *
     * @return true if the weapon will miss, false otherwise.
     */
    public final boolean isMiss() {
        return missRate > Math.random() * 100 + 1;
    }

    /**
     * Decrements the weapon's integrity.
     */
    public final void decrementIntegrity() {
        if (curIntegrity - hitDecrement > 0) {
            curIntegrity -= hitDecrement;
        } else {
            curIntegrity = 0;
            Game.output(getName() + " broke.");
        }
    }

    /**
     * Restores the weapon's integrity to its maximum value.
     */
    public final void repair() {
        curIntegrity = maxIntegrity;
    }

}
