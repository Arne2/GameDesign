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
	private final Type type;
    
    public enum Type{
    	GRASS("GrassBlock_128x128.png", true),
    	DIRT("DirtBlock_128x128.png", true),
    	BRICK("Bricks_128x128.png", true),
        //SPIKES("Spikes32x32.png", true),
        SAND("Sand32x32.png", true),
        SANDSTONE("Sandstone(32x32).png", true),
        CACTUS("Cactus32x32.png", true),
        CACTUS_BASE("CactusBase(32x32).png", false),
        CACTUS_SIDE_L("CactusSideL(32x32).png", false),
        CACTUS_SIDE_R("CactusSideR(32x32).png", false),
        CACTUS_TOP("CactusTop(32x32).png", false),
        COBBLE("Cobblestone32x32.png", true),
        STONE("Stone32x32.png", true),
        WOOD("Wood(32x32).png", true),
        WOOD2("Wood2(32x32).png", true),
        LEAVES("Leaves32x32.png", false),
        LEAVES2("Leaves2(32x32).png", false);

    	
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
        
    	this.type = type;
        GreenfootImage image = new GreenfootImage(type.image);
        image.scale(SIZE, SIZE);
        setImage(image);
    }
    
    public Type getType(){
        return type;
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
