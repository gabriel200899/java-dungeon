public class Creature {

	private String name;
	private int health;
	private int attack;
	private Weapon weapon;

	public Creature(String name, int health, int attack) {
		this.name = name;
		this.health = health;
		this.attack = attack;
	}

	public String getName() {
		return name;
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
