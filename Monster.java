public class Monster {
    private String name;
    private int attack; //how much the player hurt from the monster
    private float sense; //how easily can the monster discover the player
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     */
    public Monster(String nam, int att, float sne){
        name = nam;
        attack = att;
        sense = sne;
    }

}
