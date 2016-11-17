import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartScreen extends SplorrtWorld
{

    /**
     * Constructor for objects of class StartScreen.
     * 
     */
    public StartScreen()
    {
   
    }
    
    @Override
    public void act() {
    	if(Greenfoot.mouseClicked(null) || Greenfoot.getKey() != null) {
    		loadWorld(SplorrtWorld.getWorld(Level1.class));
    	}
    }
}
