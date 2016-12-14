import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level2_5 extends Level
{

	public Level2_5(Spider spider)
	{
		super(spider);
	}

	public Level2_5()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Cave_grey.png");
	private static final GreenfootSound	music		= new GreenfootSound("Professor Umlaut.wav");

	@Override
	public int getStartingWeb()
	{
		return 100;
	}

	@Override
	protected int getMaxWebPossible()
	{
		return 850;
	}

	@Override
	protected int getBestTimePossible()
	{
		return 7000;
	}

	@Override
	public GreenfootSound getBackgroundMusic()
	{
		return music;
	}

	@Override
	public GreenfootImage getBackgroundImage()
	{
		return background;
	}

	@Override
	public SplorrtWorld getCurrentLevel()
	{
		return new Level2_5();
	}
}
