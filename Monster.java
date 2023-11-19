import java.lang.Math;

public class Monster {
    private String name;
    private int attack; //how much the player hurt from the monster
    private float sense; //how easily can the monster discover the player
    //from a scale of 0 to 10
    /**
     * Create a Monster class
     * no exits. "description" is something like "a kitchen" or
     */
    public Monster(String nam, int att, float sne){
        name = nam;
        attack = att;
        sense = sne;
    }

    public boolean alarm(int playerstat) {
        double temp = 0.5 - (playerstat - sense) * 0.05;
        //a probobility of player being discovered
        if (temp > Math.random()) {
            return true;
        } else {
            return false;
        }
    }
}
