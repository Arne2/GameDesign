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
	
	private static final GreenfootImage background = new GreenfootImage("Sky_blue.png");
	private static final GreenfootSound music = new GreenfootSound("Constance.mp3");


	@Override
	public GreenfootSound getBackgroundMusic() {
		return music;
	}

	@Override
	public GreenfootImage getBackgroundImage() {
		return background;
	}
}
