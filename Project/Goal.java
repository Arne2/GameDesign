import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Goal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Goal extends LevelActor
{
	/* The SplorrtWorld to load, when a spider reaches this goal. */
	private final Class<? extends SplorrtWorld> next;
    
    public Goal(int x, int y, Class<? extends SplorrtWorld> next)
    {
        super(x,y);
        this.next = next;
    }
    
    /**
     * Act - do whatever the Goal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
       Spider s = null;
       s = (Spider)getOneIntersectingObject(Spider.class);
       if(s != null)
       {
           World world = getWorld();
           if(world != null && world instanceof SplorrtWorld){
        	   ((SplorrtWorld)world).loadWorld(SplorrtWorld.getWorld(next));
           }
       }
    }    
}
