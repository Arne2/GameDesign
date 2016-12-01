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
    private final Collection<SpawnPoint> spawnPoints = new ArrayList<>();
    
    private int xPosition, yPosition;
    
    private final Spider spider;
    
    private int worldHeight;
    
    private int spawnX = 100;
    
    private int spawnY = 100;
    
    private String map = null;
    
    private int maxEnemyNumber;
    private int leftEnemyNumber;
    
    private int maxConsumableScore;
    private int maxConsumableNumber;
    
    private int leftConsumableNumber;
    private int leftConsumableScore;
    
    private int ticks = 0;
    
    private boolean loaded = false;
    
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
		if(!loaded){
	    	prepare();
	        update();
	        loaded = true;
		} else {
			spiderDie();
		}
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
        
        LevelActor next;
        for(int x = 0; x<map.getWidth(); x++) {
            for(int y = 0; y<map.getHeight(); y++) {
                next = getActor(map.getColorAt(x, y), x*Platform.SIZE+Platform.SIZE/2, y*Platform.SIZE+Platform.SIZE/2);

                if(next instanceof SpawnPoint){
                	spawnPoints.add((SpawnPoint)next);
                	next = ((SpawnPoint)next).getSpawn();
                }
                if(next!=null){
                    actors.add(next);
                }
                
                // calculate max scores
                if(next instanceof Enemy && ((Enemy)next).defeatable){
                	maxEnemyNumber++;
                }
                while(next instanceof Enemy){
                	next = ((Enemy)next).getSpawnOnDeath();
                }
                if(next instanceof Consumable){
                	maxConsumableScore += ((Consumable)next).getScore();
                	maxConsumableNumber++;
                }
            }
        }
    }
    
    // Recognize colors in the level to create blocks. 
    public LevelActor getActor(Color color, int x, int y){
        if(color.equals(Color.WHITE)){
            return null;
        } else if(color.equals(new Color(128,0,0))){
            return new Consumable(Consumable.Type.BUG, x, y);
        } else if(color.equals(new Color(200,200,20))){
            return new Consumable(Consumable.Type.WASP, x, y);            
        } else if(color.equals(new Color(71,130,32))){
            return new Consumable(Consumable.Type.LARVA, x, y);
        } else if(color.equals(new Color(246,150,121))){
            return new Platform(Platform.Type.BRICK, x, y);
        } else if(color.equals(Color.GREEN)){
            return new Platform(Platform.Type.GRASS, x, y);
        } else if(color.equals(Color.LIGHT_GRAY)){
            return new Platform(Platform.Type.DIRT, x, y);
        } else if(color.equals(new Color(151,149,92))){
            return new Platform(Platform.Type.SAND, x, y);
        }else if(color.equals(new Color(17,149,92))){
            return new Platform(Platform.Type.CACTUS, x, y);
        } else if(color.equals(new Color(125,125,125))){
            return new Platform(Platform.Type.COBBLE, x, y);
        } else if(color.equals(new Color(81,81,81))){
            return new Platform(Platform.Type.STONE, x, y);
        } else if(color.equals(new Color(0,78,0))){
            return new Platform(Platform.Type.LEAVES, x, y);
        } else if(color.equals(Color.YELLOW)){
            spawnX = x;
            spawnY = y;
        } else if(color.equals(new Color(246,49,121))){
            return new Spikes(x, y);
        } else if(color.equals(Color.RED)){
        	return new SpawnPoint(new EnemyWasp(x, y));
        } else if(color.equals(Color.ORANGE)){
        	return new Goal(x, y, getNextLevel());
        }else {
            throw new IllegalArgumentException("Unknown Color" + color.toString() + " " + x + " " + y);
        }
        
        return null;
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
    
    public void calculateScore(){
    	leftConsumableScore = 0;
    	
    	for(LevelActor next : actors) {
            // calculate left scores
            if(next instanceof Enemy && ((Enemy)next).defeatable){
            	leftEnemyNumber++;
            }
            while(next instanceof Enemy){
            	next = ((Enemy)next).getSpawnOnDeath();
            }
            if(next instanceof Consumable){
            	leftConsumableScore += ((Consumable)next).getScore();
            	leftConsumableNumber++;
            }
        }
    }

    /** Only Available after load was called. */
	public int getMaxEnemyNumber() {
		return maxEnemyNumber;
	}

	/** Only Available after load was called. */
	public int getLeftEnemyNumber() {
		return leftEnemyNumber;
	}

	/** Only Available after load was called. */
	public int getMaxConsumableScore() {
		return maxConsumableScore;
	}

	/** Only Available after calculateScore was called. */
	public int getMaxConsumableNumber() {
		return maxConsumableNumber;
	}

	/** Only Available after calculateScore was called. */
	public int getLeftConsumableNumber() {
		return leftConsumableNumber;
	}

	/** Only Available after calculateScore was called. */
	public int getLeftConsumableScore() {
		return leftConsumableScore;
	}

	public void finish() {
		calculateScore();
		
    	System.out.println("Took "+ticks+" ticks to complete the Level.");
    	System.out.println("Collected consumables worth "+(maxConsumableScore-leftConsumableScore)+". ("+maxConsumableScore+" available)");
    	System.out.println("Collected "+(maxConsumableNumber-leftConsumableNumber)+" consumables. ("+maxConsumableNumber+" available)");
    	System.out.println("Killed "+(maxEnemyNumber-leftEnemyNumber)+" enemies. ("+maxEnemyNumber+" available)");
    }
	
	public void spiderDie(){
		// respawn everything
		for(SpawnPoint next : spawnPoints){
			if(!next.isSpawned()){
				actors.add(next.getSpawn());
			}
		}
		
		getSpider().reload();
		
		xPosition = spawnX-getWidth()/2;
		yPosition = spawnY-getHeight()/2;
		update();
	}

    /**
     * Override this to change the default Screen(SplorrtWorld) for loaded goal-tiles.
     */
    public SplorrtWorld getNextLevel(){
    	return SplorrtWorld.getWorld(DEFAULT_WORLD);
    }
    
    @Override
    public void act() {
    	super.act();
    	
    	ticks++;
    }

}
