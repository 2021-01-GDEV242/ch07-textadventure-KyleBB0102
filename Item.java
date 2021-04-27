
/**
 * Class Item - the items that can be found in the game and is required
 * to complete the game
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game. 
 * 
 * 
 * 
 * @author Kyle Balao
 * @version 04/27/21
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;
    private int weight;
    
    /**
     * The constructor for the Item class
     */
    public Item (String name, int weight){
        this.name = name;
        this.weight = weight;
    }
    
    /**
     * returns the name of the key
     * 
     * @return name, the name of the key
     */
    public String getName(){
        return name;
    }
    
    /**
     * returns the weight of the key
     * 
     * @ return weight, the weight of the item
     */
    public int getWeight(){
        return weight;
    }
}
