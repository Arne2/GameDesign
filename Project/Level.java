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
public abstract class Level extends World
{
    private final Collection<LevelActor> actors = new ArrayList<>();
    
    private int xPosition, yPosition;
    
    private final Spider spider;
    
    private int worldHeight;
    
    private int spawnX = 100;
    
    private int spawnY = 100;
    
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
        super(1200, 800, 1, false);
        
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
        
        for(LevelActor next : actors){
            removeObject(next);
            if(next.getLevelX()+next.getImage().getWidth()/2 > xPosition &&
                next.getLevelX() < xMax+next.getImage().getWidth()/2 &&
                next.getLevelY()+next.getImage().getHeight()/2 > yPosition && 
                next.getLevelY() < yMax+next.getImage().getHeight()/2){
                addObject(next, next.getLevelX()-xPosition, next.getLevelY()-yPosition);
            }
        }

    }
    
    public void addLevelActor(LevelActor actor, int x, int y){
        addObject(actor, x, y);
        actors.add(actor);
        actor.updatePosition();
    }
    
    public void removeLevelActor(LevelActor actor){
        removeObject(actor);
        actors.remove(actor);
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
        
        addObject(spider, getWidth()/2, getHeight()/2);
        
        movePosition(-getWidth()/2+spawnX, -getHeight()/2+spawnY);
    }
    
    private void loadFromImage(GreenfootImage map){
        worldHeight = map.getHeight()*Platform.SIZE;
        
        Platform next;
        for(int x = 0; x<map.getWidth(); x++) {
            for(int y = 0; y<map.getHeight(); y++) {
                next = getPlatform(map.getColorAt(x, y), x*Platform.SIZE+Platform.SIZE/2, y*Platform.SIZE+Platform.SIZE/2);
                if(next!=null){
                    actors.add(next);
                }
            }
        }
    }
    
    public Platform getPlatform(Color color, int x, int y){
        if(color.equals(Color.WHITE)){
            return null;
        } else if(color.equals(Color.BLACK)){
            return new Platform(Platform.Type.BRICK, x, y);
        } else if(color.equals(Color.GREEN)){
            return new Platform(Platform.Type.GRASS, x, y);
        } else if(color.equals(Color.LIGHT_GRAY)){
            return new Platform(Platform.Type.DIRT, x, y);
        } else if(color.equals(Color.YELLOW)){
            spawnX = x;
            spawnY = y;
        } else if(color.equals(Color.RED)){
            actors.add(new EnemySpawner(EnemyID.TEST_ENEMY, x, y));
        } else if(color.equals(Color.ORANGE)){
            actors.add(new Goal( x, y));
        }else {
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
    
    public void loadNextLevel()
    {
        if(getNextLevel() !=  null)
            loadLevel(getNextLevel());
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
    

    public abstract LevelID getNextLevel();

}
