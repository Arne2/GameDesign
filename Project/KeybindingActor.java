
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

/**
 * Write a description of class Keybinding here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class KeybindingActor extends Actor
{
	private boolean			selected	= false;
	private String			text;
	private String			key;
	private boolean			mouseAction;
	private GreenfootImage	background;
	private Keybind			keybind;

	public KeybindingActor(Keybind keybind, String text, String key, boolean mouseAction)
	{
		this.keybind = keybind;
		this.text = text;
		this.key = key;
		this.mouseAction = mouseAction;

		background = new GreenfootImage(150, 30);
		background.setColor(Color.GRAY);
		background.setTransparency(125);
		background.fillRect(0, 0, 150, 30);
		setImage(background);

		update();

	}

	private void update()
	{
		// setImage(new GreenfootImage(text + ": " + key, 18, Color.BLACK, new Color(0, 0, 0, 0)));

		GreenfootImage textBackground = new GreenfootImage(200, 50);
		GreenfootImage textFront = new GreenfootImage(textBackground);
		textFront.drawImage(new GreenfootImage(text + ":", 18, Color.BLACK, new Color(0, 0, 0, 0)), 25, 5);

		GreenfootImage keyBackground = new GreenfootImage(200, 50);
		GreenfootImage keyFront = new GreenfootImage(keyBackground);
		keyFront.drawImage(new GreenfootImage(key, 18, Color.BLACK, new Color(0, 0, 0, 0)), 25, 5);

		GreenfootImage combined = new GreenfootImage(400, 50);
		combined.drawImage(textFront, 0, 0);
		combined.drawImage(keyFront, 200, 0);
		setImage(combined);
	}

	/**
	 * Act - do whatever the Keybinding wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
	 */
	public void act()
	{
		// Add your action code here.
		if (!selected && Greenfoot.mousePressed(this))
		{
			setImage(new GreenfootImage(text + ":                                 assign key now", 18, Color.BLACK, new Color(0, 0, 0, 0)));

			GreenfootImage textBackground = new GreenfootImage(200, 50);
			GreenfootImage textFront = new GreenfootImage(textBackground);
			textFront.drawImage(new GreenfootImage(text + ":", 18, Color.BLACK, new Color(0, 0, 0, 0)), 25, 5);

			GreenfootImage keyBackground = new GreenfootImage(200, 50);
			GreenfootImage keyFront = new GreenfootImage(keyBackground);
			keyFront.drawImage(new GreenfootImage("assign key now", 18, Color.BLACK, new Color(0, 0, 0, 0)), 25, 5);

			GreenfootImage combined = new GreenfootImage(400, 50);
			combined.drawImage(textFront, 0, 0);
			combined.drawImage(keyFront, 200, 0);
			setImage(combined);

			selected = true;
			Greenfoot.getKey();
		}
		else if (selected)
		{
			MouseInfo mi = Greenfoot.getMouseInfo();
			String newKey = Greenfoot.getKey();
			if (mi != null && Greenfoot.mousePressed(null) && mi.getButton() != 0)
			{
				if (mi.getButton() == 1)
				{
					key = "LMB";
				}
				else if (mi.getButton() == 3)
				{
					key = "RMB";
				}
				keybind.setKey(key);
				selected = false;
				update();
			}
			else if (newKey != null)
			{
				key = newKey;
				keybind.setKey(key);
				selected = false;
				update();
			}

		}
	}
}
