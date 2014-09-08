
public class Creature {

    protected int curHealth;
    protected int maxHealth;
    private String name;

    private final CreatureID id;
    private int attack;
    private int level;

    private Weapon weapon;
    private Location location;

    public Creature(String name, int level, int health, int attack, CreatureID id) {
        this.name = name;
        this.level = level;
        this.curHealth = health;
        this.maxHealth = health;
        this.attack = attack;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Check if the creature is alive.
     */
    public boolean isAlive() {
        return curHealth > 0;
    }

    /**
     * Check if the creature has no loot.
     */
    public boolean isEmpty() {
        return weapon == null;
    }

    public boolean isLootable() {
        return (!isEmpty() && !isAlive());
    }

    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Disarm the creature. Placing its current weapon, if any, in the ground.
     */
    public void dropWeapon() {
        location.addItem(weapon);
        weapon = null;
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Attack a target. If the creature has a weapon, it will be used to perform the attack. Otherwise, the creature
     * will attack with its bare hands.
     */
    public void attack(Creature target) {
        int damage;
        if (weapon != null) {
            damage = weapon.getDamage();
        } else {
            damage = attack;
        }
        target.takeDamage(damage);
        System.out.printf("%s inflicted %d damage points to %s.\n", name,
                damage, target.getName());

    }

    /**
     * Output a table with the creature's status.
     */
    public void printStatus() {
        System.out.printf(String.format("  %s (%s)\n", name, this.id)
                + String.format("  %-20s%10d\n", "Level", level)
                + String.format("  %-20s%10s\n", "Health", String.format("%d/%d", curHealth, maxHealth))
                + String.format("  %-20s%10d\n", "Attack", attack)
                + String.format("  %-20s%10s\n", "Weapon", weapon.getName())
                + String.format("  %-20s%10s\n", "Weapon damage", weapon.getDamage())
        );
    }

    private void takeDamage(int damage) {
        if (damage > curHealth) {
            curHealth = 0;
        } else {
            curHealth -= damage;
        }
    }

    public CreatureID getId() {
        return id;
    }
}
