import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LevelSelector here.
 * 
 * @author maximilian-zollbrecht
 * @version 16.11.2016
 */
public class LevelSelectorClickPlatform extends Platform
{
	
	private final Class<? extends SplorrtWorld> worldToLoad;
	
	public LevelSelectorClickPlatform(Platform.Type type, Class<? extends SplorrtWorld> worldToLoad){
		this(type, 0, 0, worldToLoad);
	}
	
	public LevelSelectorClickPlatform(Platform.Type type, int levelX, int levelY, Class<? extends SplorrtWorld> worldToLoad){
		super(type, levelX, levelY);
		this.worldToLoad = worldToLoad;
	}
	
    /**
     * Act - do whatever the LevelSelector wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.mouseClicked(this)){
        	World world = getWorld();
            if(world != null && world instanceof SplorrtWorld){
         	   ((SplorrtWorld)world).loadWorld(SplorrtWorld.getWorld(worldToLoad));
            }
        }
    }    
}
