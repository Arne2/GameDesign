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
    
    private int	soundVolume;
	
    public enum Type{
    	BUG(0, 70, 0, "dead_fly.png"),
    	WASP(0, 100, 1, "wasp_dead_64.png"),
    	LARVA(10, 300, 3, "larva.png"),
    	SCORPION(0, 150, 1, "scorp_dead.png"),
    	COIN(10, 0, 0, "coin.png");

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
        	soundVolume = Setting.getSFXVolume();
        	if(this.type == Consumable.Type.COIN ){
        		GreenfootSound coinSound = new GreenfootSound("coin.wav");
				coinSound.setVolume(soundVolume);
				coinSound.play();
        	}
        	else {
        		GreenfootSound coinSound = new GreenfootSound("mediumCrunch.wav");
				coinSound.setVolume((int) (soundVolume * 0.8));
				coinSound.play();
        	}
        }
    }

    public int getScore(){
    	return type.score;
    }
}
