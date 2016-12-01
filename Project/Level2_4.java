import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level2_4 extends Level
{

	public Level2_4(Spider spider)
	{
		super(spider);
	}

	public Level2_4()
	{
		this(null);
	}
	
	private static final GreenfootImage background = new GreenfootImage("Cave_grey.png");
	private static final GreenfootSound music = new GreenfootSound("Professor Umlaut.wav");


	@Override
	public GreenfootSound getBackgroundMusic() {
		return music;
	}

	@Override
	public GreenfootImage getBackgroundImage() {
		return background;
	}
}
