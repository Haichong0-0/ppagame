import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<String> chest;
    private Monster mons;
    private double damageMod =2.5;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        chest = new ArrayList<>();
        mons = null;

    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    public HashMap getExit(){return exits;}

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * To initialize item in rooms
     * @param name of the added item
     */
    public void setItem(String name){
        chest.add(name);
    }



    public void addMonster(String name,int attack,float sense){
        mons = new Monster(name,attack,sense);
    }

    public String getMons(){
        if (mons == null){
            return "-1";
        }
        else{return mons.getName();}
    }
    public void monEnter(Monster monst){
        mons = monst;
    }

    public void monExit(){
        mons = null;
    }

    public int fight(int pstat){
        int attack  = mons.getAttack();
        double temp = 0.5 + (pstat - attack)*0.05;
        //probability of player fights well
        if (temp>Math.random()){
            return (int) Math.round(attack*damageMod*0.40);
        }
        else {
            return (int) Math.round(attack * damageMod * 0.80);
        }
    }

    public boolean stealthCheck(int pstat) {
        double temp = 0.5 + (pstat - mons.getPerception()) * 0.05;
        //probability of player not being discovered
        return temp > Math.random();
    }

    public int monsAttack(){
        return mons.getAttack();
    }

}

