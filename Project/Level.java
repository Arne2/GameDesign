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
public abstract class Level extends SplorrtWorld
{
    private final Collection<LevelActor> actors = new ArrayList<>();
    
    private int xPosition, yPosition;
    
    private final Spider spider;

    private int worldHeight;
    private int worldWidth;

    private int spawnX = 100;
    
    private int spawnY = 100;
    
    private LevelActorLoader loader;

    private String map = null;
    
    /**
     * Constructor.
     */
    public Level()
    {    
        this(null);
    }
    
    public Level(Spider spider)
    {
        this(spider, true);
    }
    
    public Level(Spider spider, boolean loadFromImage)
    {

        loader = new LevelActorLoader(this);

    	if(loadFromImage)
    		this.map = "levels" + File.separatorChar + getClass().getName().toLowerCase()+".png";
        if(spider != null)
            this.spider = spider;
        else
            this.spider = new Spider();
        
        setBackground(getBackgroundImage());
        setPaintOrder(Spider.class, WebBar.class, Bar.class, SpiderMenu.class, Enemy.class, Web.class, Platform.class);
    }

	public void load(){
    	prepare();
        update();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    	if(map!=null){
    		loadFromImage(new GreenfootImage(map));
    	}
        
        addObject(spider, getWidth()/2, getHeight()/2);
        
        SpiderMenu sm = new SpiderMenu();
        
        addObject(sm, sm.getImage().getWidth()/2, getHeight()-sm.getImage().getHeight()/2);
        addObject(spider.getHealthBar(), 107, getHeight()-60);
        addObject(spider.getWebBar(), 107, getHeight()-20);
        
        movePosition(-getWidth()/2+spawnX, -getHeight()/2+spawnY);
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
    
    protected void setWorldHeight(int heightInPlatforms) {
		this.worldHeight = Platform.SIZE * heightInPlatforms;
	}
    
    protected void setWorldWidth(int widthInPlatforms) {
		this.worldWidth = Platform.SIZE * widthInPlatforms;
	}

    protected void setSpawn(int x, int y) {
		this.spawnX = x;
		this.spawnY = y;
	}
    
    public void addLevelActor(LevelActor actor){
        addObject(actor, actor.getLevelX()-xPosition, actor.getLevelY()-yPosition);
        actors.add(actor);
        actor.updatePosition();
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

        int xPos = this.xPosition+getWidth()/2;
        if(xPos<0){
        	System.out.println("left");
        	this.xPosition = -getWidth()/2;
        } else if(worldWidth>0 && xPos>worldWidth){
        	System.out.println("right");
        	this.xPosition = worldWidth+getWidth()/2;
        }

        this.yPosition += dy;
    }
    
    public int getXPosition(){
        return xPosition;
    }
    
    public int getYPosition(){
        return yPosition;
    }
    
    public abstract GreenfootSound getBackgroundMusic();
    public abstract GreenfootImage getBackgroundImage();
    
    private void removeAll(){
    	actors.clear();
    	removeObjects(getObjects(Actor.class));
    }
    
    private void loadFromImage(GreenfootImage map){
        worldHeight = map.getHeight()*Platform.SIZE;
        worldWidth = map.getWidth()*Platform.SIZE;

        LevelActor next;
        for(int x = 0; x<map.getWidth(); x++) {
            for(int y = 0; y<map.getHeight(); y++) {
                next = getActor(map.getColorAt(x, y), x*Platform.SIZE+Platform.SIZE/2, y*Platform.SIZE+Platform.SIZE/2);
                if(next!=null){
                    actors.add(next);
                }
            }
        }
    }
    
    // Recognize colors in the level to create blocks. 
    public LevelActor getActor(Color color, int x, int y){
        return loader.loadLevelActor(color, x, y);
    }
    
    public boolean hasSpiderFallen(){
        return yPosition > worldHeight;
    }
    
    @Override
    public void started(){
        getBackgroundMusic().playLoop();
    }
    
    @Override
    public void stopped(){
    	getBackgroundMusic().stop();
    }
    
    public Spider getSpider()
    {
        return spider;
    }

    /**
     * Override this to change the default Screen(SplorrtWorld) for loaded goal-tiles.
     */
    public SplorrtWorld getNextLevel(){
    	return SplorrtWorld.getWorld(DEFAULT_WORLD);
    }


}
