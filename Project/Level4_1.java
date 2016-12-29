import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level4_1 extends Level
{

	public Level4_1(Spider spider)
	{
		super(spider);
	}

	public Level4_1()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Forrest_Sky.png");
	private static final GreenfootSound	music		= new GreenfootSound("Constance.wav");

	@Override
	public int getStartingWeb()
	{
		return 200;
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
		return 900;
	}

	@Override
	protected int getBestTimePossible()
	{
		return 7000;
	}

	@Override
	public SplorrtWorld getNextLevel()
	{
		return new Level4_2();
	}

	@Override
	public SplorrtWorld getCurrentLevel()
	{
		return new Level4_1();
	}
}
