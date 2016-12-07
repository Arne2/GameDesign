
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * This class represents a very basic InfoScreen. 
 * It shows an image until it is clicked, or a button is pressed. 
 * 
 * 
 * @author maximilian-zollbrecht
 * @version 28.11.2016
 */
public class InfoScreen extends SplorrtWorld
{

	public static final int DEFAULT_DELAY = 30;
	
	private int delay = DEFAULT_DELAY;
	
	private final SplorrtWorld next;
	
	public InfoScreen(GreenfootImage image, SplorrtWorld next)
	{
		setBackground(image);
		this.next = next;
	}

	public SplorrtWorld getNext() {
		return next;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public void act()
	{
		if (delay > 0)
		{
			delay--;
			Greenfoot.getKey();
		}
		else if (delay <= 0 && (Greenfoot.getKey() != null || Greenfoot.mouseClicked(null)))
		{
			loadWorld(next);
		}
	}
}
