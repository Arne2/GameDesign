import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndScreen extends SplorrtWorld
{

    /**
     * Constructor for objects of class EndScreen.
     * 
     */
    public EndScreen()
    {
    }
    @Override
    public void act() {
    	if(Greenfoot.mouseClicked(null)) {
    		loadWorld(SplorrtWorld.getWorld(Level1.class));
    	}
    }
}
