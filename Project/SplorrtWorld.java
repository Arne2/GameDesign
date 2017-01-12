
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;

import greenfoot.core.WorldHandler;
import javax.swing.*;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * Our basic World. Provides functionality to load a SplorrtWorld into Greenfoot. Any world in Splorrt is supposed to to extend this class.
 * 
 * @author maximilian-zollbrecht
 * @version 16.11.2016
 */
public abstract class SplorrtWorld extends World
{
	public static final int								WIDTH			= 1200;
	public static final int								HEIGHT			= 800;

	public static final Class<? extends SplorrtWorld>	DEFAULT_WORLD	= StartScreen.class;

	private JPanel pan = WorldHandler.getInstance().getWorldCanvas();  
    /** The new Cursor */
    private Cursor newCursor;
	/**
	 * Constructor for objects of class SplorrtWorld.
	 * 
	 */
	public SplorrtWorld()
	{
		super(WIDTH, HEIGHT, 1, false);
		
		GreenfootImage cursorImg = new GreenfootImage("spiderweb.png");
		cursorImg.scale(32, 32);
		
		/** You need a toolkit to Create a completly new Cursor */
        Toolkit Tk = Toolkit.getDefaultToolkit();
        /** The Cursor Point, where the cursor have to click from the left uppon corner of the Cursor Picture */
        Point CursorPoint= new Point(0,0);
        /** Now you create the new Cursor */
        newCursor = 
            /** With the Toolkit you can Create a new Cursor */
        Tk.createCustomCursor(
            /** The Image, the Cursor should have, if you wont to clear the Cursor it have to be an empty Image */
            cursorImg.getAwtImage(),
            /** The Cursor Point */
            CursorPoint,
            /** A Name for the new Cursor */
            "Web");
	}

	/**
	 * Loads the given world into Greenfoot.
	 */
	public void loadWorld(Class<? extends SplorrtWorld> next)
	{
		loadWorld(getWorld(next));
	}

	/**
	 * Loads the given world into Greenfoot.
	 */
	public void loadWorld(SplorrtWorld world)
	{
		stopped();
		Greenfoot.setWorld(world);
		if (world instanceof Level)
		{
			((Level) world).load();
		}
		world.started();
	}

	/**
	 * Get an instance of the given SplorrtWorld or the default SplorrtWorld.
	 */
	public static SplorrtWorld getWorld(Class<? extends SplorrtWorld> clazz)
	{
		Object world = null;
		try
		{
			world = clazz.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			try
			{
				System.out.println("Couldn't create world of class: " + clazz.getName());
				world = DEFAULT_WORLD.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e1)
			{
				Greenfoot.stop();
				e1.printStackTrace();
			}
		}
		if (world instanceof SplorrtWorld)
		{
			return (SplorrtWorld) world;
		}
		else
		{
			throw new IllegalArgumentException("Given class doesn't extend SplorrtWorld.");
		}
	}

	/**
	 * Get an instance of a SplorrtWorld with the given name or the default SplorrtWorld.
	 */
	public static SplorrtWorld getWorld(String name)
	{
		Object world = null;
		try
		{
			world = Class.forName(name).newInstance();
		}
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			try
			{
				System.out.println("Couldn't create world with name: " + name);
				world = DEFAULT_WORLD.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e1)
			{
				Greenfoot.stop();
				e1.printStackTrace();
			}
		}
		if (world instanceof SplorrtWorld)
		{
			return (SplorrtWorld) world;
		}
		else
		{
			throw new IllegalArgumentException("Given name doesn't refer to a SplorrtWorld.");
		}
	}

	@Override
	public void act()
	{
		/** Sets the Cursor Image to the New Cursor */
        pan.setCursor(newCursor);
        
		if (Greenfoot.isKeyDown("escape"))
			loadWorld(new StartScreen());
	}
}
