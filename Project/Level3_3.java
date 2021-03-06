import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level3_3 extends Level
{

	public Level3_3(Spider spider)
	{
		super(spider);
	}

	public Level3_3()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Desert_sky.png");
	private static final GreenfootSound	music		= new GreenfootSound("River of Io.mp3");

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
		return 860;
	}

	@Override
	protected int getBestTimePossible()
	{
		return 7000;
	}

	@Override
	public SplorrtWorld getCurrentLevel()
	{
		return new Level3_3();
	}
	
	@Override
	public SplorrtWorld getNextLevel()
	{
		return new Level3_4();
	}
}
