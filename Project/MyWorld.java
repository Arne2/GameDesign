import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collection;
import java.util.ArrayList;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private final Collection<Platform> platforms = new ArrayList<>();
    
    private int xPosition, yPosition;
    
    /**
     * Constructor for objects of class MyWorld.
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false); 
        prepare();
        update();
    }
    
    /**
     * Update all world-objects(platforms).
     */
    public void update(){
        int xMax = xPosition + getWidth();
        int yMax = yPosition + getHeight();
        
        for(Platform next : platforms){
            removeObject(next);
            if(next.getXWorldPosition()+next.getImage().getWidth()/2 > xPosition &&
                next.getXWorldPosition() < xMax+next.getImage().getWidth()/2 &&
                next.getYWorldPosition()+next.getImage().getHeight()/2 > yPosition && 
                next.getYWorldPosition() < yMax+next.getImage().getHeight()/2){
                addObject(next, next.getXWorldPosition()-xPosition, next.getYWorldPosition()-yPosition);
            }
        }
    }
    
    /**
     * Moves the screen-position by dx in x-direction and dy in y-direction.
     */
    public void movePosition(int dx, int dy){
        this.xPosition += dx;
        this.yPosition += dy;
    }
    
    public int getXPosition(){
        return xPosition;
    }
    
    public int getYPosition(){
        return yPosition;
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Spider spider = new Spider();
        addObject(spider,getWidth()/2, getHeight()/2);
        
        platforms.add(new Platform(210,280));
        platforms.add(new Platform(210,300));
        platforms.add(new Platform(230,300));
        platforms.add(new Platform(250,300));
        platforms.add(new Platform(270,300));
        platforms.add(new Platform(290,300));
        platforms.add(new Platform(310,300));
        platforms.add(new Platform(330,300));
        platforms.add(new Platform(350,300));
        platforms.add(new Platform(370,300));
        platforms.add(new Platform(390,300));
        
        platforms.add(new Platform(510,300));
        platforms.add(new Platform(530,300));
        platforms.add(new Platform(550,300));
        platforms.add(new Platform(570,300));
        platforms.add(new Platform(590,300));
        
        platforms.add(new Platform(710,300));
        platforms.add(new Platform(730,300));
        platforms.add(new Platform(750,300));
        platforms.add(new Platform(770,300));
        platforms.add(new Platform(790,300));
        
        platforms.add(new Platform(910,300));
        platforms.add(new Platform(930,300));
        platforms.add(new Platform(950,300));
        platforms.add(new Platform(970,300));
        platforms.add(new Platform(990,300));
        
        platforms.add(new Platform(1110,250));
        platforms.add(new Platform(1130,250));
        platforms.add(new Platform(1150,250));
        platforms.add(new Platform(1170,250));
        platforms.add(new Platform(1190,250));
        
        platforms.add(new Platform(1310,200));
        platforms.add(new Platform(1330,200));
        platforms.add(new Platform(1350,200));
        platforms.add(new Platform(1370,200));
        platforms.add(new Platform(1390,200));
    }
}
