import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Platform extends LevelActor
{
	public static final int SIZE = 32;
    
    public enum Type{
    	GRASS("GrassBlock_128x128.png", true),
    	DIRT("DirtBlock_128x128.png", true),
    	BRICK("Bricks_128x128.png", true),
        //SPIKES("Spikes32x32.png", true),
        SAND("Sand32x32.png", true),
        CACTUS("Cactus32x32.png", true),
        COBBLE("Cobblestone32x32.png", true),
        STONE("Stone32x32.png", true),
        LEAVES("Leaves32x32.png", true);
    	
    	private final String image;
    	private final boolean sticky;
    	
    	private Type(String image, boolean sticky){
    		this.image = image;
    		this.sticky = sticky;
    	}
    	
    	public boolean isSticky(){
    		return sticky;
    	}
    }
    
    public Platform(Type type, int x, int y){
    	super(x, y);
        
        GreenfootImage image = new GreenfootImage(type.image);
        image.scale(SIZE, SIZE);
        setImage(image);
    }
    
    /**
     * Act - do whatever the Platform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }
}
