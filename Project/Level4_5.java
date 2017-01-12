import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level4_5 extends Level
{

	public Level4_5(Spider spider)
	{
		super(spider);
	}

	public Level4_5()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Forrest_Sky.png");
	private static final GreenfootSound	music		= new GreenfootSound("Constance.wav");

	@Override
	public int getStartingWeb()
	{
		return 400;
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
	protected int getMaxWebPossible()
	{
		return 800;
	}

	@Override
	protected int getBestTimePossible()
	{
		return 3500;
	}

	@Override
	public SplorrtWorld getNextLevel()
	{
		return new Level1_1();
	}

	@Override
	public SplorrtWorld getCurrentLevel()
	{
		return new Level4_5();
	}
}
