import java.util.*;

public class ItemList {
    private HashMap<String,Integer> items;
    public ItemList(){
        items = new HashMap<String,Integer>();
        items.put("Healthpotion",5);
        items.put("Windrock",5);
        items.put("Earthrock",5);
        items.put("Waterrock",5);
        items.put("Invisible",5);
    }

    public boolean chechItem(String term){
        return items.containsKey(term);
    }



}
