import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level1_3 extends Level
{

	public Level1_3(Spider spider)
	{
		super(spider);
	}

	public Level1_3()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Sky_blue.png");
	private static final GreenfootSound	music		= new GreenfootSound("On My Way.mp3");

	@Override
	public int getStartingWeb()
	{
		return 450;
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
	protected int getBestTimePossible()
	{
		return 4000;
	}

	@Override
	public SplorrtWorld getNextLevel()
	{
		return new Level1_4();
	}

	@Override
	public SplorrtWorld getCurrentLevel()
	{
		return new Level1_3();
	}
}
