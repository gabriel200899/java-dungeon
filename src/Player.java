/**
 * Created by Bernardo on 06/09/2014.
 */
public class Player extends Creature implements Playable {


    public Player() {
        super("Dude", "Yourself.", 1, 60, 5, CreatureID.UNKNOWN);
    }

    /**
     * Rest until the creature is completely healed.
     */
    // TODO: implement time system and make the rest time have some impact on the game.
    public void rest() {
        curHealth = maxHealth;
        System.out.println("You are completely rested.");
    }
}
