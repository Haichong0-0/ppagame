import java.lang.Math;

public class Monster {
    private String name;
    private int attack; //how much the player hurt from the monster
    private float perception; //how easily can the monster discover the player
    //from a scale of 0 to 10

    /**
     * Create a Monster class
     * no exits. "description" is something like "a kitchen" or
     */
    public Monster(String nam, int att, float sne){
        name = nam;
        attack = att;
        perception = sne;
    }
    





    public String getName(){ return name;}
    public int getAttack(){ return attack;}
    public float getPerception(){ return perception;}


}
