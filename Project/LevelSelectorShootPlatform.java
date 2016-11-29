import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LevelSelector here.
 * 
 * @author maximilian-zollbrecht
 * @version 16.11.2016
 */
public class LevelSelectorShootPlatform extends Platform
{
	
	private final SplorrtWorld worldToLoad;
	
	public LevelSelectorShootPlatform(Platform.Type type, SplorrtWorld worldToLoad){
		this(type, 0, 0, worldToLoad);
	}
	
	public LevelSelectorShootPlatform(Platform.Type type, int levelX, int levelY, SplorrtWorld worldToLoad){
		super(type, levelX, levelY);
		this.worldToLoad = worldToLoad;
	}
	
    /**
     * Act - do whatever the LevelSelector wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(getOneIntersectingObject(WebBlob.class)!=null){
        	World world = getWorld();
            if(world != null && world instanceof SplorrtWorld){
         	   ((SplorrtWorld)world).loadWorld(worldToLoad);
            }
        }
    }
}
