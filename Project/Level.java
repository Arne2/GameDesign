import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Level extends SplorrtWorld
{	
	private final Collection<LevelActor>	actors	= new ArrayList<>();
    private final Collection<SpawnPoint> 	spawnPoints = new ArrayList<>();

	private int								xPosition, yPosition;

	private final Spider					spider;

	private int								worldHeight;
	private int								worldWidth;

	private int								spawnX	= 100;

	private int								spawnY	= 100;

	private LevelActorLoader				loader;
	
    private Score 							score;
    
    private boolean 						loaded = false;

	/**
	 * The background music
	 */
	private GreenfootSound					music	= new GreenfootSound("Constance.wav");

	private String							map		= null;

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

		if (loadFromImage)
			this.map = "levels" + File.separatorChar + getClass().getName().toLowerCase() + ".png";
		if (spider != null)
			this.spider = spider;
		else
			this.spider = new Spider();

		setBackground(getBackgroundImage());
		setPaintOrder(Spider.class, WebBar.class, Bar.class, SpiderMenu.class, Enemy.class, Web.class, Platform.class);
	}

	public void load()
	{
		if(loaded){
			spiderDie();
			update();
		} else {
			prepare();
			update();
			loaded = true;
		}
	}

	/**
	 * Prepare the world for the start of the program. That is: create the initial objects and add them to the world.
	 */
	private void prepare()
	{
		if (map != null)
		{
			loadFromImage(new GreenfootImage(map));
		}
		
		spider.getWebBar().setValue(getStartingWeb());

		addObject(spider, getWidth() / 2, getHeight() / 2);

		SpiderMenu sm = new SpiderMenu();

		addObject(sm, sm.getImage().getWidth() / 2, getHeight() - sm.getImage().getHeight() / 2);
		addObject(spider.getHealthBar(), 107, getHeight() - 60);
		addObject(spider.getWebBar(), 107, getHeight() - 20);

		movePosition(-getWidth() / 2 + spawnX, -getHeight() / 2 + spawnY);
	}

	/**
	 * Update all world-objects(platforms).
	 */
	public void update()
	{
		int xMax = xPosition + getWidth();
		int yMax = yPosition + getHeight();

		for (LevelActor next : actors)
		{
			removeObject(next);
			if (next.getLevelX() + next.getImage().getWidth() / 2 > xPosition && next.getLevelX() < xMax + next.getImage().getWidth() / 2 && next.getLevelY() + next.getImage().getHeight() / 2 > yPosition && next.getLevelY() < yMax + next.getImage().getHeight() / 2)
			{
				addObject(next, next.getLevelX() - xPosition, next.getLevelY() - yPosition);
			}
		}

	}

	protected void setWorldHeight(int heightInPlatforms)
	{
		this.worldHeight = Platform.SIZE * heightInPlatforms;
	}

	protected void setWorldWidth(int widthInPlatforms)
	{
		this.worldWidth = Platform.SIZE * widthInPlatforms;
	}

	protected void setSpawn(int x, int y)
	{
		this.spawnX = x;
		this.spawnY = y;
	}

	public void addLevelActor(LevelActor actor)
	{
		addObject(actor, actor.getLevelX() - xPosition, actor.getLevelY() - yPosition);
		actors.add(actor);
		actor.updatePosition();
	}

	public void addLevelActor(LevelActor actor, int x, int y)
	{
		addObject(actor, x, y);
		actors.add(actor);
		actor.updatePosition();
	}

	public void removeLevelActor(LevelActor actor)
	{
		removeObject(actor);
		actors.remove(actor);
	}

	/**
	 * Moves the screen-position by dx in x-direction and dy in y-direction.
	 */
	public void movePosition(int dx, int dy)
	{
		this.xPosition += dx;

		int xPos = this.xPosition + getWidth() / 2;
		if (xPos < 0)
		{
			this.xPosition = -getWidth() / 2;
		}
		else if (worldWidth > 0 && xPos > worldWidth)
		{
			this.xPosition = worldWidth + getWidth() / 2;
		}

		this.yPosition += dy;
	}

	public int getXPosition()
	{
		return xPosition;
	}

	public int getYPosition()
	{
		return yPosition;
	}

	public abstract GreenfootSound getBackgroundMusic();

	public abstract GreenfootImage getBackgroundImage();
	
	/**
	 * As a fraction. 1 means 1 extra star per maximum score.
	 * 0.2 means 1 extra star for 0.2 of the maximum score.
	 */
	public float getRequiredScorePerStar(){
		return 0.2f;
	}
	
	public String getName(){
		return getClass().getName();
	}
    
    public int getStartingWeb(){
    	return 0;
    }

	private void loadFromImage(GreenfootImage map)
	{
		worldHeight = map.getHeight() * Platform.SIZE;
		worldWidth = map.getWidth() * Platform.SIZE;

		LevelActor next;
		
	    int maxEnemyNumber = 0;
	    int maxConsumableScore = 0;
	    int maxConsumableNumber = 0;
		
		for (int x = 0; x < map.getWidth(); x++)
		{
			for (int y = 0; y < map.getHeight(); y++)
			{
				next = getActor(map.getColorAt(x, y), x * Platform.SIZE + Platform.SIZE / 2, y * Platform.SIZE + Platform.SIZE / 2);
				
				if(next instanceof SpawnPoint){
                	spawnPoints.add((SpawnPoint)next);
                	next = ((SpawnPoint)next).getSpawn();
                }
				if (next != null)
				{
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
		score = new Score(getClass().getName(), maxConsumableNumber, maxConsumableScore, maxEnemyNumber, getSpider().getWebBar().getMaximumValue(), getRequiredScorePerStar());
	}

	// Recognize colors in the level to create blocks.
	public LevelActor getActor(Color color, int x, int y)
	{
		return loader.loadLevelActor(color, x, y);
	}

	public boolean hasSpiderFallen()
	{
		return yPosition > worldHeight;
	}

	@Override
	public void started()
	{
		getBackgroundMusic().playLoop();
	}

	@Override
	public void stopped()
	{
		getBackgroundMusic().stop();
	}

	public Spider getSpider()
	{
		return spider;
	}

	/**
	 * Override this to change the default Screen(SplorrtWorld) for loaded goal-tiles.
	 */
	public SplorrtWorld getNextLevel()
	{
		return SplorrtWorld.getWorld(DEFAULT_WORLD);
	}
	
    public void calculateScore(){
    	int leftConsumableScore = 0;
    	int leftEnemyNumber = 0;
    	int leftConsumableNumber = 0;
    	
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
    	
    	this.score.setConsumableLeftNumber(leftConsumableNumber);
    	this.score.setConsumableLeftScore(leftConsumableScore);
    	this.score.setEnemyLeftNumber(leftEnemyNumber);
    	this.score.setLeftWeb(getSpider().getWebBar().getValue());
    	
    	score.save();
    }

	public void finish() {
		calculateScore();
		loadWorld(new ScoreScreen(score, getNextLevel()));
    }
	
	public void spiderDie(){
		score.sincreaseDeaths(1);
		
		// reset web
		spider.getWebBar().setValue(getStartingWeb());
		
		// update all killed enemies to their consumables
		for(SpawnPoint next : spawnPoints){
			if(next.getSpawn() instanceof Enemy && ((Enemy)next.getSpawn()).isDead()){
				next.killSpawn();
			}
		}
		
		// remove all consumables and enemies
		Collection<LevelActor> toRemove = new ArrayList<>();
		for(LevelActor next : actors){
			if((next instanceof Enemy && ((Enemy)next).defeatable) ||  next instanceof Consumable){
				toRemove.add(next);
			}
		}
		for(LevelActor next : toRemove){
			removeLevelActor(next);
		}
		
		// respawn everything
		for(SpawnPoint next : spawnPoints){
			if(next.getSpawn()!=null){
				addLevelActor(next.getSpawn());
			}
		}
		
		getSpider().reload();
		
		xPosition = spawnX-getWidth()/2;
		yPosition = spawnY-getHeight()/2;
		
		update();
	}
	
	public int getStars(){
		return Score.loadStars(getClass().getName());
	}
    
    @Override
    public void act() {
    	super.act();
    	
    	if(loaded) {
    		score.increaseFrames(1);
    	}
    }

}