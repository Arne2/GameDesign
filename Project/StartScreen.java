
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

/**
 * Write a description of class StartScreen here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class StartScreen extends SplorrtWorld
{

	/**
	 * Constructor for objects of class StartScreen.
	 * 
	 */
	public StartScreen()
	{
		setBackground("StartScreen.jpg");
		Keybind.loadKeybinds();
		Setting.loadSettings();
	}

	@Override
	public void act()
	{
		if (Greenfoot.mousePressed(null))
		{
			MouseInfo mi = Greenfoot.getMouseInfo();
			int clickX = mi.getX();
			int clickY = mi.getY();

			if (isBetween(clickX, 550, 680) && isBetween(clickY, 510, 540))
			{
				loadWorld(new InfoScreen(new GreenfootImage("MovementHelp.jpg"), new Level1_1()));
			}
			else if (isBetween(clickX, 450, 800) && isBetween(clickY, 565, 595))
			{
				loadWorld(new LevelSelection());
			}
			else if (isBetween(clickX, 510, 715) && isBetween(clickY, 615, 640))
			{
				loadWorld(new OptionScreen());
			}
			else if (isBetween(clickX, 560, 665) && isBetween(clickY, 665, 700))
			{
				System.exit(0);
			}
		}
//		if (Greenfoot.mousePressed(null))
//		{
//			System.out.println("X Coord: " + Greenfoot.getMouseInfo().getX() + " - Y Coord: " + Greenfoot.getMouseInfo().getY());
//		}

		/*
		 * 
		 * X Coord: 554 - Y Coord: 514 X Coord: 676 - Y Coord: 540 X Coord: 451 - Y Coord: 565 X Coord: 800 - Y Coord: 595 X Coord: 518 - Y Coord: 619 X Coord: 714 - Y Coord: 644 X Coord: 561 - Y Coord: 669 X Coord: 665 - Y Coord: 697
		 */
	}

	private boolean isBetween(int target, int lowerValue, int upperValue)
	{
		if (lowerValue < target && target < upperValue)
			return true;
		return false;
	}
}
