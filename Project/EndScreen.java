
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;

/**
 * Write a description of class EndScreen here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EndScreen extends SplorrtWorld
{

	private int delay = 10;
	
	private final Class<? extends SplorrtWorld> level;

	/**
	 * Constructor for objects of class EndScreen.
	 * 
	 */
	public EndScreen(Class<? extends SplorrtWorld> level)
	{
		setBackground("EndScreen.jpg");
		this.level = level;
	}

	@Override
	public void act()
	{
		if (delay > 0)
		{
			delay--;
			Greenfoot.getKey();
		}
		else if (delay <= 0 && (Greenfoot.getKey() != null || Greenfoot.mouseClicked(null)))
		{
			loadWorld(SplorrtWorld.getWorld(level));
		}
	}
}
