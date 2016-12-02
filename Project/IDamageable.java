/**
 * Write a description of class IDamageable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface IDamageable  
{
    /** Decreases the health h units. */
    public void decreaseHealth(int h);
    
    /** Decreses the health 1 unit. */
    public void decreaseHealth();
    
    /** Sets the health to h units. */
    public void setHealth(int h);
    
    /** Getter for current health. */
    public int getHealth();
    
    /** Getter for the maximum health. */
    public int getMaxHealth();
    
    /** Instantly kills the entity. */
    public void instantKill();
    
    /** Is entity dead. */
    public boolean isDead();
}
