import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author Kyle Balao
 * @version 04/27/21
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
            "go", "quit", "help", "look", "eat","inspect"
    };
    private static final String[] commandDescription = {
            " - To go to your next location using a exit direction.",
            " - To quit the game.",
            " - To get the list of commands",
            " - You have eaten now and you are not hungry any more",
            " - Inspect the room too see if there is any useful stuff",
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     * 
     * 
     */
    public String getCommandList() {
        String commandList = "";
        for(int i = 0; i < validCommands.length; i++) {
            commandList = commandList + validCommands[i] + commandDescription[i] + " \n";
        }
        return commandList;
    }
}
