import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level2_2 extends Level
{

	public Level2_2(Spider spider)
	{
		super(spider);

		getSpider().getWebBar().add(800);
	}

	public Level2_2()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Cave_grey.png");
	private static final GreenfootSound	music		= new GreenfootSound("Professor Umlaut.wav");

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
	public SplorrtWorld getNextLevel()
	{
		return new Level2_3();
	}
}