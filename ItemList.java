import java.util.*;

public class ItemList {
    private HashMap<String,Integer> items;
    public ItemList(){
        items = new HashMap<String,Integer>();
        items.put("Windrock",5);
        items.put("Earthrock",5);
        items.put("Waterrock",5);
        items.put("InvisibleSpell",5);
        items.put("HealingPotion",5);


    }

    public boolean checkItem(String term){
        return items.containsKey(term);
    }

    public int totalMass(ArrayList<String> bag){
        int weight=0;
        for (String i: bag){
            weight+= items.get(i);
        }
        return weight;
    }



}
