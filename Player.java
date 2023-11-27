import java.util.ArrayList;

public class Player {
    private ArrayList<String> inventory;
    private int hp,fightLv,stealthLv;

    public Player(){
        inventory = new ArrayList<>();
    }

    public void setHp(int hp){this.hp = hp;}
    public void setFightLv(int fightLv){this.fightLv = fightLv;}
    public void setStealthLv(int stealthLv){this.stealthLv = stealthLv;}

    public int getHp() {
        return hp;
    }
    public int getFightLv(){
        return fightLv;
    }
     public int getStealthLv(){
        return stealthLv;
     }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(String item) {
        inventory.add(item);
    }
    public void removeItem(String item){
        inventory.remove(item);
    }
}
