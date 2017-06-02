import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level1_1 extends Level
{
	public Level1_1(Spider spider)
	{
		super(spider);
	}

	public Level1_1()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Sky_blue.png");
	private static final GreenfootSound	music		= new GreenfootSound("On My Way.mp3");

	@Override
	public int getStartingWeb()
	{
		return 0;
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
		return 200;
	}

	@Override
	protected int getBestTimePossible()
	{
		return 2200;
	}

	@Override
	public SplorrtWorld getNextLevel()
	{
		return new InfoScreen(new GreenfootImage("WebHelp.jpg"), new InfoScreen(new GreenfootImage("EnemyHelp.jpg"), new Level1_2()));
	}

	@Override
	public SplorrtWorld getCurrentLevel()
	{
		return new Level1_1();
	}
}
