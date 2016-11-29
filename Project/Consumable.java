import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Consumable extends LevelActor
{
    public static final int ANIMATION_FRAMES = 10;
	
    public enum Type{
    	BUG(1, 70, 0, "dead_fly.png"),
    	WASP(1, 100, 1, "wasp_dead_64.png"),
    	LARVA(10, 300, 3, "larva.png");
    	
    	private final String[] images;
    	private final int score;
    	private final int web;
    	private final int health;
    	
    	private Type(int score, int web, int health, String... images){
    		this.images = images;
    		
    		this.score = score;
    		this.web = web;
    		this.health = health;
    	}
    }
    
    private final Type type;
    private final Animation animation;
    
    public Consumable(Type type){
    	this(type, 0, 0);
    }
    
    public Consumable(Type type, int x, int y){
    	super(x, y);
        
    	this.type = type;
    	
    	this.animation = new Animation(ANIMATION_FRAMES);
		for(String next : type.images){
			animation.addImage(new GreenfootImage(next));
		}
    	
    	setImage(animation.getImage());
    }
    
    /**
     * Act - do whatever the Platform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
    	animation.next();
    	if(animation.hasChanged()){
    		setImage(animation.getImage());
    	}
    	
        Spider spider = (Spider)getOneIntersectingObject(Spider.class);
        if(spider!=null){
        	spider.getWebBar().add(type.web);
        	spider.getHealthBar().add(type.health);
        	((Level)getWorld()).removeLevelActor(this);
        }
    }
    
    public int getScore(){
    	return type.score;
    }
}
