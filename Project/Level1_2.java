import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level1_2 extends Level
{

	public Level1_2(Spider spider)
	{
		super(spider);
	}

	public Level1_2()
	{
		this(null);
	}

	private static final GreenfootImage	background	= new GreenfootImage("Sky_blue.png");
	private static final GreenfootSound	music		= new GreenfootSound("On My Way.wav");

	@Override
	public int getStartingWeb(){
	    return 500;
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
	public SplorrtWorld getNextLevel()
	{
		return new InfoScreen(new GreenfootImage("ResourceHelp.jpg"), new Level1_3());
	}
}
