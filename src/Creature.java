
import java.util.List;


public class Creature {

    /**
     * Let the player choose a target to attack.
     */
    private Creature selectTargetFromList() {
        StringBuilder builder = new StringBuilder();
        builder.append("0. Abort\n");
        List<Creature> visible = this.getLocation().getVisibleCreatures(this);
        for (int i = 1; i - 1 < visible.size(); i++) {
            builder.append(i).append(". ").append(visible.get(i - 1).getName()).append("\n");
        }
        System.out.print(builder.toString());
        int index;
        while (true) {
            System.out.print("> ");
            try {
                index = Integer.parseInt(Game.sc.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println(Game.INVALID_INPUT);
                continue;
            }
            if (0 <= index && index <= visible.size()) {
                break;
            }
            System.out.println(Game.INVALID_INPUT);
        }
        if (index == 0) {
            return null;
        }
        return visible.get(index - 1);
    }

    protected int curHealth;
    protected int maxHealth;
    private String name;

    private final CreatureID id;

    private int damage;
    private int level;

    private Weapon weapon;
    private Location location;

    public Creature(CreatureID id, int level) {
        switch (id) {
            case WOLF:
                this.name = "Wolf";
                this.level = level;
                this.curHealth = this.maxHealth = 25 + 5 * level;
                this.damage = 10 + 3 * level;
            default:
                break;
        }
        this.id = id;
    }

    public Creature(String name, int level, int health, int damage, CreatureID id) {
        this.name = name;
        this.level = level;
        this.curHealth = health;
        this.maxHealth = health;
        this.damage = damage;
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
    
    public Creature selectTarget(String[] inputWords) {
        Creature target = null;
        if (inputWords.length == 1) {
            
        } else {
            // Handle user-specified target.
        }
        return target;
    }

    /**
     * Check if the creature is alive.
     *
     * @return
     */
    public boolean isAlive() {
        return curHealth > 0;
    }

    /**
     * Check if the creature has no loot.
     *
     * @return
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
     *
     * @param target
     */
    public void attack(Creature target) {
        int hitDamage;
        if (weapon != null) {
            hitDamage = weapon.getDamage();
            if (weapon.isMiss()) {
                System.out.printf("%s missed.\n", name);
            } else {
                target.takeDamage(hitDamage);
                System.out.printf("%s inflicted %d damage points to %s.\n", name, hitDamage, target.getName());
            }
        } else {
            hitDamage = this.damage;
            target.takeDamage(hitDamage);
            System.out.printf("%s inflicted %d damage points to %s.\n", name, hitDamage, target.getName());
        }
    }

    /**
     * Output a table with the creature's status.
     */
    public void printStatus() {
        System.out.printf(String.format("  %s (%s)\n", name, this.id)
                + String.format("  %-20s%10d\n", "Level", level)
                + String.format("  %-20s%10s\n", "Health", String.format("%d/%d", curHealth, maxHealth))
                + String.format("  %-20s%10d\n", "Attack", damage)
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

//Rat
//HP:20
//Attack:5-10
//Info: A disgusting rodent with sharp teeth that allows it to pierce its opponent.
//
//Bat
//HP:15
//Attack:5-10
//Info: A wild bloody mammal that hits its targets with a strong bite.
//
//Bear
//HP:40
//Attack:15-25
//Info: A great furious bear with big claws that can slash its targets.
//
//Spider
//HP:25
//Attack:10-20
//Info: A scaring arachnid with strong fangs capable to cause serious scars.
//
//Wolf
//HP:30
//Attack:5-15
//Info: A sanguinary beast with cutting preys able to flench everyone on his front.