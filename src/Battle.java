
public class Battle {

    public Battle(Creature attacker, Creature defender) {
        while (attacker.isAlive() && defender.isAlive()) {
            attacker.attack(defender);
            if (defender.isAlive()) {
                defender.attack(attacker);
            }
        }
        String survivor, defeated;
        if (attacker.isAlive()) {
            survivor = attacker.getName();
            defeated = defender.getName();
        } else {
            survivor = defender.getName();
            defeated = attacker.getName();
        }
        System.out.printf("%s managed to kill %s.\n", survivor, defeated);
    }

}
