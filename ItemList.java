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
        items.put("adrenaline",3);


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
    public void healingpotion(Player player){
        player.setHp(player.getHp()+50);
    }
    public void invisiableSpell(Player player){
        player.stealthBoost(2);
    }

    public void adrenaline(Player player){
        player.strengthBoost(2);
    }




}
