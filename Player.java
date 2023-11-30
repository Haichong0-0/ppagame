import java.util.ArrayList;

public class Player {
    private ArrayList<String> inventory;
    private int baseStrength,baseStealth,maxHp;
    private int hp,strengthLv,stealthLv;
    private int sBoostDuration,fBoostDuration;

    public Player(int maxhp,int strengthLv, int stealthLv){
        inventory = new ArrayList<>();
        this.maxHp = maxhp;
        hp = maxHp;
        this.strengthLv = strengthLv;
        this.stealthLv = stealthLv;
    }

    public void setHp(int hp){this.hp = hp;}
    public void setStrengthLv(int strengthLv){this.strengthLv = strengthLv;}
    public void setStealthLv(int stealthLv){this.stealthLv = stealthLv;}

    public int getHp() {
        return hp;
    }
    public int getStrengthLv(){
        return strengthLv;
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
    public void stealthBoost(int time){
        stealthLv = 999;
        sBoostDuration +=time;
    }

    public void strengthBoost(int time){
        stealthLv = 999;
        sBoostDuration +=time;
    }
    public void nextRoom(){
        sBoostDuration -=1;
        fBoostDuration -=1;
        if (sBoostDuration ==0){
            stealthLv = baseStealth;
        }
        if (fBoostDuration == 0){
            strengthLv =  baseStrength;
        }
    }
}
