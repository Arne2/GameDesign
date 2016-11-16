import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Basic Actor that loads a SplorrtWorld when clicked on.
 * 
 * @author maximilian-zollbrecht
 * @version 16.11.2016
 */
public class LevelSelector extends Actor
{
	private final Class<? extends SplorrtWorld> worldToLoad;
	
    public LevelSelector(Class<? extends SplorrtWorld> worldToLoad) {
		super();
		this.worldToLoad = worldToLoad;
	}

	/**
     * Loads a SplorrtWorld when clicked on.
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
