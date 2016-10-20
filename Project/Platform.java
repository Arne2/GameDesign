import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Platform extends Actor
{
    private final int xWorldPosition, yWorldPosition;
    
    public Platform(int x, int y){
        this.xWorldPosition = x;
        this.yWorldPosition = y;
    }
    
    /**
     * Act - do whatever the Platform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }
    
    public int getXWorldPosition(){
        return xWorldPosition;
    }
    
    public int getYWorldPosition(){
        return yWorldPosition;
    }
}
