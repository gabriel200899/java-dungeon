
public class WeaponSpecs {

    private int damage;

    /**
     * How often (from 0 [never] to 100 [always]) does the weapon miss?
     */
    private int missRate;

    /**
     * Makes a new WeaponSpecs (weapon specifications) object based on its arguments.
     *
     * @param damage the damage of the weapon.
     * @param missRate how often (from 0 [never] to 100 [always]) the weapon misses.
     */
    public WeaponSpecs(int damage, int missRate) {
        setDamage(damage);
        setMissRate(missRate);
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
}
