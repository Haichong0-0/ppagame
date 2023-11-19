import java.util.Objects;

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
    private int hp,fightLv,stealthLv;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        stealthLv = 5;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, rest, wind, water1, water2,earth1,earth2,earth3,fire1;
      
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

        earth1.setExit("north",fire1);
        earth1.setExit("west",rest);
        earth1.setExit("east",earth2);

        earth2.setExit("west",earth1);
        earth2.setExit("east",earth3);

        earth3.setExit("west",earth2);

        fire1.setExit("south",earth1);






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
        System.out.println(currentRoom.getLongDescription());
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
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("fight")) {

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
        if (command.getCommandWord().equals("fight")){
            int hplost = currentRoom.fight(fightLv);
            hp = hp - hplost;
            if (hp>0) {
                System.out.println("You won the fight. You have lost " + hplost + " HP.");
                System.out.println("Your current HP is " + hp);
            }
        }

        else if (command.getCommandWord().equals("sneak")) {
            if (currentRoom.stealthCheck(stealthLv)){
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
    }
}
