// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import greenfoot.World;

/**
 * Write a description of class Goal here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Goal extends LevelActor
{
	/* The SplorrtWorld to load, when a spider reaches this goal. */
	private final SplorrtWorld next;

	public Goal(int x, int y, SplorrtWorld next)
	{
		super(x, y);
		this.next = next;
		setImage("SpiderExit.png");
	}

	/**
	 * Act - do whatever the Goal wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
	 */
	public void act()
	{
		Spider s = null;
		s = (Spider) getOneIntersectingObject(Spider.class);
		if (s != null)
		{
			World world = getWorld();
			if (world != null && world instanceof Level)
			{
				((Level)world).finish();
				((SplorrtWorld)world).loadWorld(next);
			}
		}
	}
}
