import java.util.List;

public class Creature {

    private String name;
    private String info;

    private CreatureID id;

    private int curHealth;
    private int maxHealth;
    private int attack;
    private int level;

    private Weapon weapon;
    private Location location;

    public Creature(String name, String info, int level, int health, int attack, CreatureID id) {
        this.name = name;
        this.info = info;
        this.level = level;
        this.curHealth = health;
        this.maxHealth = health;
        this.attack = attack;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Checks if the creature is alive.
     */
    public boolean isAlive() {
        return curHealth > 0;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Attack a target. If the creature has a weapon, it will be used to perform
     * the attack. Otherwise, the creature will attack with its bare hands.
     */
    public void attack(Creature target) {
        int damage;
        if (weapon != null)
            damage = weapon.getDamage();
        else
            damage = attack;
        target.takeDamage(damage);
        System.out.printf("%s inflicted %d damage points to %s.\n", name,
                damage, target.getName());

    }

    public void look() {
        // Should this raise an exception?
        if (location == null) {
            return;
        }
        StringBuilder builder = new StringBuilder(location.getName());
        List<Creature> visible = location.getVisibleCreatures(this);
        for (Creature visibleCreature : visible) {
            builder.append('\n');
            builder.append(visibleCreature.getInfo());
        }
        System.out.println(builder.toString());
    }

    /**
     * Output a table with the creature's status.
     */
    public void printStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  %-20s\n", name));
        sb.append(String.format("  %-20s%10d\n", "Level", level));
        sb.append(String.format("  %-20s%10s\n", "Health", String.format("%d/%d", curHealth, maxHealth)));
        sb.append(String.format("  %-20s%10d\n", "Attack", attack));
        sb.append(String.format("  %-20s%10s\n", "Weapon", weapon.getName()));
        sb.append(String.format("  %-20s%10s\n", "Weapon damage", weapon.getDamage()));
        System.out.printf(sb.toString());
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
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
