public class Item {
    private String name; //name of the item
    private int mass; //how heavy is the object
    public Item(String nam, int mas){
        name = nam;
        mass = mas;
    }
    public String getName(){ //returns the nane of the item
        return name;
    }

    public int getMass(){ //returns the mass of the item
        return mass;
    }

}
