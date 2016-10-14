import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false); 
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Spider spider = new Spider();
        addObject(spider,157,86);
        Platform platform = new Platform();
        addObject(platform,109,318);
        Platform platform2 = new Platform();
        addObject(platform2,232,324);
        Platform platform3 = new Platform();
        addObject(platform3,155,324);
        platform3.setLocation(127,318);
        platform2.setLocation(146,318);
        Platform platform4 = new Platform();
        addObject(platform4,236,322);
        platform4.setLocation(166,318);
        Platform platform5 = new Platform();
        addObject(platform5,308,285);
        Platform platform6 = new Platform();
        addObject(platform6,288,293);
        Platform platform7 = new Platform();
        addObject(platform7,335,285);
        Platform platform8 = new Platform();
        addObject(platform8,349,333);
        platform6.setLocation(288,285);
        platform7.setLocation(328,285);
        platform8.setLocation(346,285);
    }
}
