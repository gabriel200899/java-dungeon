package game;

import java.io.Serializable;
import java.util.List;

public class Creature implements Serializable {

    private final CreatureID id;
    private String name;

    private int level;
    private int experience;
    private int experienceDrop;

    private int gold;

    private int maxHealth;
    private int curHealth;

    private int attack;

    private Weapon weapon;
    private Location location;

    public Creature(CreatureID id, int level) {
        switch (id) {
            case BAT:
                this.name = "Bat";
                this.level = level;
                this.experienceDrop = level * level * 15;
                this.curHealth = this.maxHealth = 12 + 3 * level;
                this.attack = 5 + 2 * level;
                break;
            case BEAR:
                this.name = "Bear";
                this.level = level;
                this.experienceDrop = level * level * 40;
                this.curHealth = this.maxHealth = 30 + 10 * level;
                this.attack = 13 + 7 * level;
                break;
            case RABBIT:
                this.name = "Rabbit";
                this.level = level;
                this.experienceDrop = level * level * 5;
                this.curHealth = this.maxHealth = 10 + 2 * level;
                this.attack = 5 + 2 * level;
                break;
            case RAT:
                this.name = "Rat";
                this.level = level;
                this.experienceDrop = level * level * 10;
                this.curHealth = this.maxHealth = 15 + 5 * level;
                this.attack = 6 + 4 * level;
                break;
            case SPIDER:
                this.name = "Spider";
                this.level = level;
                this.experienceDrop = level * level * 10;
                this.curHealth = this.maxHealth = 17 + 8 * level;
                this.attack = 10 + 5 * level;
                break;
            case WOLF:
                this.name = "Wolf";
                this.level = level;
                this.experienceDrop = level * level * 15;
                this.curHealth = this.maxHealth = 24 + 6 * level;
                this.attack = 10 + 4 * level;
                break;
            case ZOMBIE:
                this.name = "Zombie";
                this.level = level;
                this.experienceDrop = level * level * 20;
                this.curHealth = this.maxHealth = 30 + 6 * level;
                this.attack = 12 + 4 * level;
                break;
            default:
                break;
        }
        this.id = id;
    }

    public Creature(String name, int level, int health, int attack, CreatureID id) {
        this.name = name;
        this.level = level;
        this.curHealth = health;
        this.maxHealth = health;
        this.attack = attack;
        this.id = id;
    }

    public CreatureID getId() {
        return id;
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

    public int getExperience() {
        return experience;
    }

    public int getExperienceDrop() {
        return experienceDrop;
    }

    public int getExperienceToNextLevel() {
        return level * level * 100;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void addExperience(int amount) {
        this.experience += amount;
        Game.writeString(name + " got " + amount + " experience points.");
        if (this.experience >= getExperienceToNextLevel()) {
            levelUp();
        }

    }

    public void levelUp() {
        if (experience >= level * level * 100) {
            this.level++;
            Game.writeString(String.format("%s leveld up. %s is now level %d", name, name, level));
        }
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        if (this.gold < 0) {
            this.gold = 0;
        }
    }

    public void addGold(int amount) {
        if (amount > 0) {
            this.gold += amount;
            Game.writeString(name + " got " + amount + " gold coins.");
        }
    }

    /**
     * Reduces the creature gold by a given amount.
     *
     * Gold will never become negative, so you should check that the creature has enough gold before subtracting any.
     */
    public void subtractGold(int amount) {
        if (this.gold - amount > 0) {
            this.gold = this.gold - amount;
        } else {
            this.gold = 0;
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurHealth() {
        return curHealth;
    }

    public void setCurHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    /**
     * Check if the creature is alive.
     *
     * @return
     */
    public boolean isAlive() {
        return curHealth > 0;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Disarm the creature. Placing its current weapon, if any, in the ground.
     */
    public void dropWeapon() {
        if (weapon != null) {
            location.addItem(weapon);
            Game.writeString(name + " dropped " + weapon.getName() + ".");
            weapon = null;
        } else {
            Game.writeString("You are not currently carrying a weapon.");
        }
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        Game.writeString(name + " equipped " + weapon.getName() + ".");
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Try to hit a target. If the creature has a weapon, it will be used to perform the attack. Otherwise, the creature
     * will attack with its bare hands.
     *
     * @param target
     */
    public void hit(Creature target) {
        int hitDamage;
        // Check that there is a weapon and that it is not broken.
        if (weapon != null && !weapon.isBroken()) {
            // Check if the attack is a miss.
            if (weapon.isMiss()) {
                Game.writeString(name + " missed.");
            } else {
                hitDamage = weapon.getDamage();
                target.takeDamage(hitDamage);
                weapon.decrementIntegrity();
                System.out.printf("%s inflicted %d damage points to %s.\n", name, hitDamage, target.getName());
            }
        } else {
            hitDamage = this.attack;
            target.takeDamage(hitDamage);
            System.out.printf("%s inflicted %d damage points to %s.\n", name, hitDamage, target.getName());
        }
    }

    private void takeDamage(int damage) {
        if (damage > curHealth) {
            curHealth = 0;
        } else {
            curHealth -= damage;
        }
    }

    public Creature selectTarget(String[] inputWords) {
        if (inputWords.length == 1) {
            return selectTargetFromList();
        } else {
            String targetName = inputWords[1];
            for (Creature possibleTarget : getLocation().getVisibleCreatures(this)) {
                if (targetName.compareToIgnoreCase(possibleTarget.getName()) == 0) {
                    return possibleTarget;
                }
            }
        }
        return null;
    }

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
        Game.writeString(builder.toString());
        int index;
        while (true) {
            try {
                index = Integer.parseInt(Game.readString());
            } catch (NumberFormatException exception) {
                Game.writeString(Game.INVALID_INPUT);
                continue;
            }
            if (0 <= index && index <= visible.size()) {
                break;
            }
            Game.writeString(Game.INVALID_INPUT);
        }
        if (index == 0) {
            return null;
        }
        return visible.get(index - 1);
    }

    /**
     * Output a table with the creature's status.
     */
    public void printStatus() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("  %s (%s)\n", name, this.id));
        builder.append(String.format("  %-20s%10d\n", "Level", level));
        builder.append(String.format("  %-20s%10s\n", "Experience",
                String.format("%d/%d", experience, getExperienceToNextLevel())));
        builder.append(String.format("  %-20s%10d\n", "Gold", gold));
        builder.append(String.format("  %-20s%10s\n", "Health",
                String.format("%d/%d", curHealth, maxHealth)));
        builder.append(String.format("  %-20s%10d\n", "Attack", attack));
        if (weapon != null) {
            builder.append(weapon.getStatusString());
        }
        Game.writeString(builder.toString());
    }

}
