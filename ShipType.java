
/**
 * Enumeration class ShipType - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum ShipType
{
    PATROL(2), 
    DESTROYER(3), 
    SUB(3), 
    CARRIER(5), 
    BATTLESHIP(4);
    
    private int length;
    
    ShipType(int length)
    {
        this.length = length;
    }
    
    public int getLength()
    {
        return length;
    }
}
