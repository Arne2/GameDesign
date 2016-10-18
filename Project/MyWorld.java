import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Color;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private static final int PLATFORM_WIDTH = new Platform(0,0).getImage().getWidth();
    private static final int PLATFORM_HEIGHT = new Platform(0,0).getImage().getHeight();
    
    private final Collection<Platform> platforms = new ArrayList<>();
    
    private int xPosition, yPosition;
    
    private final Spider spider = new Spider();
    
    private int worldHeight;
    
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
        addObject(spider,getWidth()/2, getHeight()/2);
        
        loadFromImage(new WorldMap());
    }
    
    private void loadFromImage(Actor map){
        GreenfootImage img = map.getImage();
        
        worldHeight = img.getHeight()*PLATFORM_HEIGHT;
        
        Platform next;
        for(int x = 0; x<img.getWidth(); x++) {
            for(int y = 0; y<img.getHeight(); y++) {
                next = getPlatform(img.getColorAt(x, y), x*PLATFORM_WIDTH+PLATFORM_WIDTH/2, y*PLATFORM_HEIGHT+PLATFORM_HEIGHT/2);
                if(next!=null){
                    platforms.add(next);
                }
            }
        }
    }
    
    public static Platform getPlatform(Color color, int x, int y){
        if(color.equals(Color.WHITE)){
            return null;
        } else if(color.equals(Color.BLACK)){
            return new Platform(x, y);
        } else {
            throw new IllegalArgumentException("Unknown Color");
        }
    }
    
    private int getWorldHeight(){
        return worldHeight;
    }
    
    public boolean hasSpiderFallen(){
        return yPosition > worldHeight;
    }
}
