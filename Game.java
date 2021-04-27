


import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
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
 * @author  Kyle Balao
 * @version 04/27/21
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     *  
     *  Difficult Setting:
     *  Easy: 50 Turns
     *  Medium: 35 Turns
     *  Hard: 15 Turns  
     */
    public void play(int turns) 
    {            
        turnsLeft = turns;
        // checks that the value of turns is set to a positive value.
        Scanner reader = new Scanner(System.in);
        int inputNewTurns;
        while(turnsLeft<0){
            System.out.print("The value of turns is negative, Please use a positive value! : ");
            inputNewTurns = reader.nextInt();
            setTurns(inputNewTurns);
        }

        printWelcome();
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        while (! finished) {
            System.out.println("Turns left: " + getTurns());
            System.out.println("Key : "+ getKeyStatus() + " of 3");
            Command command = parser.getCommand();
            finished = processCommand(command);
            if(getTurns() == 0){
                finished = true;
                System.out.println("You didn't make it in time!!!");
            }
        }
        System.out.println("\nThank you for playing.  Good bye.");
    }


    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Helps you look around surrounding area
     * 
     * @return description of the current room
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Simple command stating you have eaten
     */
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry any more");
    }
    
    /**
     * Add locked doors to your game. The player needs to find (or otherwise obtain)
     * a key to open a door.
     * 
     * Inspect the room to see is there is a key.
     */
    private void inspect(){
        if (currentRoom.equals(room[keyRoomIndex])){
            System.out.println("\nYou found the Victory Key");
            System.out.println(currentRoom.getExitString());
            setKeyStatus();
        }else{
            System.out.println("\nThere is nothing useful here...");
            System.out.println(currentRoom.getExitString());
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
        if (commandWord.equals("help")) {
            System.out.println("");
            printHelp();
        }
        else if (commandWord.equals("go")) {
            System.out.println("");
            goRoom(command);
        }
        else if (commandWord.equals("look")){
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
 
        else if(commandWord.equals("inspect")){
            inspect();
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
     * Try to go in one direction. If there is an exit, enter the new
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
        else if(nextRoom.equals(room[5])){
            System.out.println("You got teleported and dont know where you are");
            setTeleport();
            countDownTurns();
            currentRoom = room[5];
            System.out.println(currentRoom.getLongDescription());
        }else if(nextRoom.equals(room[6])){
            if (getKeyStatus()== 3){
                System.out.println("You enterd the victory room");
                System.out.println("Here you are confronted by a friend of yours who tells you");
                System.out.println("that you are actually are dead...");
                turnsLeft = 1;
            }else{
                System.out.println("The door is locked! Try find the three keys");
            }
        }else{
            currentRoom = nextRoom;
            countDownTurns();
            System.out.println(currentRoom.getLongDescription());
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
}