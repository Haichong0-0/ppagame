import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<String>  bacDirec;
    private ArrayList<String> inventory;
    private ItemList itemList;
    private int weightLimit;
    private boolean canMove;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();

        inventory = new ArrayList<>();
        bacDirec = new ArrayList<>();
        itemList = new ItemList();
        weightLimit = 50;
        canMove = true;
        player= new Player();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, rest, wind, water1, water2,earth1,earth2,earth3,fire1,tproom,fire2;
      
        // create the rooms
        outside = new Room("outside the main entrance of the dungeon."+"\n"+"A large fallen tree in a murky morass marks the entrance.");
        rest = new Room("in a clear rectangular room."+"\n"+ "It seems safe in this room."+"\n"+" The torch on the wall is the only thing can give you a sense of warmth.");
        wind = new Room("in the wind sanctuary.\n You can feel the power of the rock blasting on your face.");
        water1 = new Room("in a circular chamber ");
        water2 = new Room("in front of a large lake under ground.\n You think this has to be the power of the elemental rocks.");
        earth1 = new Room("in a dry");
        earth2 = new Room("");
        earth3 = new Room("");
        fire1 = new Room("");
        fire2 = new Room("temp");
        tproom = new Room("in a magical room. Everything here feels unreal.");

        
        // initialise room exits
        outside.setExit("north", rest);

        rest.setExit("south",outside);
        rest.setExit("west",wind);
        rest.setExit("north",water1);
        rest.setExit("east",earth1);
        rest.addMonster("boss1",10,10);

        wind.setExit("east",rest);

        water1.setExit("north",water2);
        water1.setExit("south",rest);

        water2.setExit("south",water1);


        earth1.setExit("west",rest);
        earth1.setExit("east",earth2);
        earth1.setExit("north",tproom);

        tproom.setExit("south",earth1);

        earth2.setExit("north",fire1);
        earth2.setExit("west",earth1);
        earth2.setExit("east",earth3);

        earth3.setExit("west",earth2);

        fire1.setExit("south",earth1);
        fire1.setExit("north",fire2);

        fire2.setExit("south",fire1);


        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (currentRoom.getShortDescription().equals("in a magical room. Everything here feels unreal.")){
                System.out.println("description of magic");
                tpRoom();
            }
            monsterT();
            if (player.getHp()<=0){
                finished = true;
            }
            if(currentRoom.getShortDescription().equals("temp")){
                finished = endroom();
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("Pick your class from below:");
        System.out.println("Fighter ");
        System.out.println("The rogue");
        System.out.println("Barbarian");
        pickclass();
        System.out.println(currentRoom.getLongDescription());
    }

    public void  pickclass(){
        boolean finished = false;
        while (!finished){
            System.out.println("Please enter in the format of: class calssChosen");
            System.out.println("E.g class fighter");
            Command command = parser.getCommand();
            if (command.getCommandWord().equals("class")) {
                switch (command.getSecondWord()) {
                    case "fighter":
                        player.setHp(150);
                        player.setFightLv(10);
                        player.setStealthLv(3);
                        finished = true;
                        break;
                    case "rogue":
                        player.setHp(75);
                        player.setFightLv(3);
                        player.setStealthLv(8);
                        finished = true;
                        break;
                    case "Barbarian":
                        player.setHp(100);
                        player.setFightLv(6);
                        player.setStealthLv(5);
                        finished = true;
                        break;
                    default:
                        System.out.println("that is not a available class");
                }
            }

        }
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();


        if (canMove){
            if (commandWord.equals("go")){
                goRoom(command);
                return false;
            }
        }
        switch (commandWord){
            //case "go"-> goRoom(command);
            case "quit" -> wantToQuit = quit(command);
            case "help" -> printHelp();
            case "back" -> back();

            case "hack" -> hack(command);
            case "check" -> check();
            case "take" -> take(command);
            case "drop"-> drop(command);
            case "use" -> use(command);
        }


        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            switch (direction) { //tell back command the directions
                case "north" -> bacDirec.add("south");
                case "south" -> bacDirec.add("north");
                case "east" -> bacDirec.add("west");
                case "west" -> bacDirec.add("east");
            }
            if (Objects.equals(nextRoom.getMons(), "-1")){ //if there is no monster
                System.out.println(currentRoom.getLongDescription());
            }
            else{//if there is a monster
                System.out.println("As you opened the door you see a "+ nextRoom.getMons()+" in front of you");
                System.out.println("Now its up to you to decide to fight it or to sneak pass");
                System.out.println("(You might fail to sneak pass)");
                Command decision = parser.getCommand();
                processDecision(decision);
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    public void processDecision(Command command){
        int hp = player.getHp();
        if (command.getCommandWord().equals("fight")){
            int hplost = currentRoom.fight(player.getFightLv());
             hp = hp - hplost;
            if (hp>0) {
                System.out.println("You won the fight. You have lost " + hplost + " HP.");
                System.out.println("Your current HP is " + hp);
            }
        }

        else if (command.getCommandWord().equals("sneak")) {
            if (currentRoom.stealthCheck(player.getStealthLv())){
                System.out.println("You have successfully sneak passed the monster");
            }

            else{
                System.out.println("Oh no! The "+currentRoom.getMons()+" noticed you");
                System.out.println("It is now attacking you");
                hp = hp - currentRoom.monsAttack();
                if (hp>0){
                    System.out.println("You successfully defended yourself ");
                    System.out.println("Your current HP is " + hp);
                }
                else {
                    System.out.println("The monster is too strong for you. ");
                    System.out.println("Your HP has dropped below 0");
                }
            }
        }
        else if (command.getCommandWord().equals("help")) {
            System.out.println("You can now either choose to fight or sneak");
        }
        else{
            Command decision = parser.getCommand();
            processDecision(decision); //until the player enters a valid response
        }
        player.setHp(hp);
    }

    public void back(){
        String direction = bacDirec.get(bacDirec.size()-1);
        currentRoom = currentRoom.getExit(direction);
        bacDirec.remove(bacDirec.size()-1);
    }
    //tp the player to a random room he has been in
    public void tpRoom(){
        int upper = bacDirec.size();
        Random rand = new Random();
        int repeat = rand.nextInt(upper)+1;
        for( int i = 0; i<=repeat;i++){
            back();
        }
    }

    public void monsterT(){
        //this function traverses though all rooms
        Queue<Room> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(currentRoom);
        visited.add(currentRoom.getShortDescription());
        while(!queue.isEmpty()){

            Room current = queue.remove();
            //function

            for (Object n: current.getExit().values()){
                Room r = (Room) n;
                if (!visited.contains(r.getShortDescription())){
                    queue.add(r);
                    visited.add(r.getShortDescription());
                }
            }
        }
    }

    public void hack(Command command){
        if (command.getSecondWord().equals("player")){
            switch(command.getThirdWord()){
                case "health" -> player.setHp(99999999);
                case "attack" -> player.setFightLv(100);
                case "stealth" -> player.setStealthLv(100);
                default -> System.out.println("You can not do that.");
            }
        }
        else if (command.getSecondWord().equals("monster")){
            if (command.getThirdWord().equals("clear")){
                currentRoom.monExit();
            }
            else {
                System.out.println("You can not do that.");
            }
        }
        else{
            System.out.println("What???????");
        }
    }

    public void check(){
        ArrayList<String> chest = currentRoom.getChest();
        System.out.println("You found following items in the room");
        for (String i : chest){
            System.out.println(i);
        }
    }

    public void take(Command command){
        ArrayList<String> chest = currentRoom.getChest();
        String item = command.getSecondWord();
        if (item == null){
            System.out.println("You have just picked up the void from the room");
            System.out.println("You just realised what mistake you have made.ut all you can see is nothing as you ");
            System.out.println("But its already too late");
            System.out.println("You can not see anything");
            System.out.println("You can not hear anything");
            System.out.println("You are trapped by the void");
            player.setHp(-1);

        }
        else if(chest.contains(item)){
            currentRoom.removeItem(item);
            inventory.add(item);
            while (itemList.totalMass(inventory)>weightLimit){
                System.out.println("Your inventory is too heavy");
                System.out.println("You know you can not travel like this anymore");
                System.out.println("Your decides to drop something");
                canMove = false;
                processCommand(parser.getCommand());
            }
        }
        else{
            System.out.println("That item does not exist in the room");
        }
    }

    public void drop(Command command){
        String item = command.getSecondWord();
        if (!itemList.checkItem(item)){
            System.out.println("That it not a real item");
        }
        else if (!inventory.contains(item)){
            System.out.println("You do not have a" + item);

        }
        else {
            currentRoom.setItem(item);
            inventory.remove(item);
        }

    }
    public void use(Command command){
        String item = command.getSecondWord();
        if (item == null){
            System.out.println("waht is it that you wants to use?");
        }
        else {
            if (inventory.contains(item) && itemList.checkItem(item)) {
                switch (item ){
                    case "HealingPotion" -> player.setHp(player.getHp()+50);
                    case "InvisibleSpell" -> player.setStealthLv(99);
                }
            }
        }
    }

    public boolean endroom(){
        String[] rocks = {"Windrock","Earthrock","Waterrock"};
        int count = 0;
        for (String i:rocks){
            if (inventory.contains(i)){
                count +=1;
            }
        }
        if (count ==3){
            //finishing words
            return true;
        }
        return false;
    }

}
