import java.util.List;

public class Creature {

	private String name;
	private String info;

	private int health;
	private int attack;
	private int level;

	private Weapon weapon;
	private Location location;

	public Creature(String name, String info, int level, int health, int attack) {
		this.name = name;
		this.info = info;
		this.level = level;
		this.health = health;
		this.attack = attack;
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
		return health > 0;
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
		for (int i = 0; i < visible.size(); i++) {
			builder.append('\n');
			builder.append(visible.get(i).getInfo());
		}
		System.out.println(builder.toString());
	}

	public void equipWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	private void takeDamage(int damage) {
		if (damage > health) {
			health = 0;
		} else {
			health -= damage;
		}
	}
}
