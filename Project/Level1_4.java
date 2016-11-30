import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Level1_4 extends Level
{

	public Level1_4(Spider spider)
	{
		super(spider);
		
		getSpider().getWebBar().add(300);
	}

	public Level1_4()
	{
		this(null);
	}
	
	private static final GreenfootImage background = new GreenfootImage("Sky_blue.png");
	private static final GreenfootSound music = new GreenfootSound("On My Way.mp3");

	@Override
	public GreenfootSound getBackgroundMusic() {
		return music;
	}

	@Override
	public GreenfootImage getBackgroundImage() {
		return background;
	}
}
