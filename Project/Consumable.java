import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Consumable extends LevelActor
{
	
	public static final int ANIMATION_FRAMES = 20;
	
    public enum Type{
    	BUG(70, 0, "dead_fly.png"),
    	WASP(200, 1, "wasp_dead_64.png");
    	
    	private final String[] images;
    	private final int web;
    	private final int health;
    	
    	private Type(int web, int health, String... images){
    		this.images = images;
    		this.web = web;
    		this.health = health;
    	}
    }
    
    private final Type type;
    private int animationImage = 0;
    private int animationFramesLeft = ANIMATION_FRAMES;
    
    public Consumable(Type type){
    	this(type, 0, 0);
    }
    
    public Consumable(Type type, int x, int y){
    	super(x, y);
        
    	this.type = type;
    	if(type.images!=null && type.images.length>0)
    		setImage(new GreenfootImage(type.images[0]));
    }
    
    /**
     * Act - do whatever the Platform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
    	if(type.images!=null && type.images.length>1){
	    	animationFramesLeft--;
	    	
	    	if(animationFramesLeft <= 0){
	    		animationFramesLeft = ANIMATION_FRAMES;
	    		animationImage = animationImage++ % type.images.length;
	    	}
    	}
    	
        Spider spider = (Spider)getOneIntersectingObject(Spider.class);
        if(spider!=null){
        	spider.getWebBar().add(type.web);
        	spider.getHealthBar().add(type.health);
        	((Level)getWorld()).removeLevelActor(this);
        }
    }
}
