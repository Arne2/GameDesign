import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level3_4 extends Level
{

	public Level3_4(Spider spider)
	{
		super(spider);
	}

	public Level3_4()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Desert_sky.png");
	private static final GreenfootSound	music		= new GreenfootSound("River of Io.wav");

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
		return 950;
	}

	@Override
	protected int getBestTimePossible()
	{
		return 7000;
	}

	@Override
	public SplorrtWorld getCurrentLevel()
	{
		return new Level3_4();
	}
	
	@Override
	public SplorrtWorld getNextLevel()
	{
		return new Level3_5();
	}
}
