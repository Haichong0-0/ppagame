import java.util.ArrayList;

public class Chest {

    private ArrayList<Item> items; // contents of the chest
    public Chest(){
        items = new ArrayList<Item>();
    }

     public Item take(String ite){

         for (int i = 0; i < items.size(); i++) {
             if (items.get(i).getName() == ite){
                 Item temp = items.get(i);
                 items.remove(i);
                 return temp;
             }
         }

         Item temp = new Item("void",0);
         return temp;

     }

     public void store(Item ite){
        items.add(ite);
     }

}
