
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartScreen extends SplorrtWorld
{
	private int delay = 10;

	/**
	 * Constructor for objects of class StartScreen.
	 * 
	 */
	public StartScreen()
	{
		setBackground("StartScreen.jpg");
	}

	@Override
	public void act()
	{
		if (delay > 0)
		{
			delay--;
		}

		if (delay <= 0 && (Greenfoot.getKey() != null || Greenfoot.mouseClicked(null)))
		{
			loadWorld(SplorrtWorld.getWorld(Level1.class));
		}
	}
}
