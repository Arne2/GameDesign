import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collection;
import java.util.ArrayList;
import java.awt.Color;
import java.io.File;

/**
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level extends World
{
    private static final int PLATFORM_WIDTH = new Platform(0,0).getImage().getWidth();
    private static final int PLATFORM_HEIGHT = new Platform(0,0).getImage().getHeight();
    
    private final Collection<Platform> platforms = new ArrayList<>();
    
    private int xPosition, yPosition;
    
    private final Spider spider;
    
    private int worldHeight;
    
    private int spawnX;
    
    private int spawnY;
    
    /**
     * The background music
     */
    private GreenfootSound music = new GreenfootSound("Constance.mp3");
    
    private String map;
    
    /**
     * Constructor.
     */
    public Level(String map)
    {    
        this(map, null);
    }
    
    public Level(String map, Spider spider)
    {
                // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1, false);
        
        this.map = "levels" + File.separatorChar + map;
        if(spider != null)
        this.spider = spider;
        else
        this.spider = new Spider();
        
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
    
    public GreenfootSound getBackgroundMusic(){
        return music;
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        
        loadFromImage(new GreenfootImage(map));
        
        addObject(spider,spawnX, spawnY);
    }
    
    private void loadFromImage(GreenfootImage map){
        worldHeight = map.getHeight()*PLATFORM_HEIGHT;
        
        Platform next;
        for(int x = 0; x<map.getWidth(); x++) {
            for(int y = 0; y<map.getHeight(); y++) {
                next = getPlatform(map.getColorAt(x, y), x*PLATFORM_WIDTH+PLATFORM_WIDTH/2, y*PLATFORM_HEIGHT+PLATFORM_HEIGHT/2);
                if(next!=null){
                    platforms.add(next);
                }
            }
        }
    }
    
    public Platform getPlatform(Color color, int x, int y){
        if(color.equals(Color.WHITE)){
            return null;
        } else if(color.equals(Color.BLACK)){
            return new Platform(x, y);
        } else if(color.equals(Color.GREEN)){
            return new Grass(x, y);
        } else if(color.equals(Color.LIGHT_GRAY)){
            return new Dirt(x, y);
        } else if(color.equals(Color.YELLOW)){
            spawnX = x;
            spawnY = y;
        } else {
            throw new IllegalArgumentException("Unknown Color");
        }
        
        return null;
    }
    
    public boolean hasSpiderFallen(){
        return yPosition > worldHeight;
    }
    
    @Override
    public void started(){
        music.playLoop();
    }
    
    @Override
    public void stopped(){
        music.stop();
    }
    
    public void loadLevel(LevelID levelID)
    {
        switch(levelID)
        {
            case START:
                Greenfoot.setWorld(new StartLevel(spider));
                break;
            case LEVEL1:
                Greenfoot.setWorld(new Level1(spider));
                break;
            
        }
    }
    
    public Spider getSpider()
    {
        return spider;
    }

    

}