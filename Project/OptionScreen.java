import java.awt.Color;

import greenfoot.Greenfoot;
import greenfoot.MouseInfo;

/**
 * Write a description of class KeybindScreen here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class OptionScreen extends SplorrtWorld
{
	private SwitchBar	haungsMode;

	private MouseActBar	sfxVolume;

	private MouseActBar	musicVolume;

	/**
	 * Constructor for objects of class KeybindScreen.
	 * 
	 */
	public OptionScreen()
	{
		setBackground("OptionScreen.jpg");

		prepare();
	}

	private void prepare()
	{
		KeybindingActor kb = new KeybindingActor(Keybind.MOVE_LEFT, Keybind.MOVE_LEFT.getText(), Keybind.MOVE_LEFT.getKey(), Keybind.MOVE_LEFT.isMouseAction());
		// addObject(kb, 250, 200);
		addObject(kb, 250, 200);

		kb = new KeybindingActor(Keybind.MOVE_RIGHT, Keybind.MOVE_RIGHT.getText(), Keybind.MOVE_RIGHT.getKey(), Keybind.MOVE_RIGHT.isMouseAction());
		// addObject(kb, 250, 250);
		addObject(kb, 250, 250);

		kb = new KeybindingActor(Keybind.JUMP, Keybind.JUMP.getText(), Keybind.JUMP.getKey(), Keybind.JUMP.isMouseAction());
		// addObject(kb, 273, 300);
		addObject(kb, 250, 300);

		kb = new KeybindingActor(Keybind.SHOOT, Keybind.SHOOT.getText(), Keybind.SHOOT.getKey(), Keybind.SHOOT.isMouseAction());
		// addObject(kb, 250, 350);
		addObject(kb, 250, 350);

		kb = new KeybindingActor(Keybind.CANCEL_WEB, Keybind.CANCEL_WEB.getText(), Keybind.CANCEL_WEB.getKey(), Keybind.CANCEL_WEB.isMouseAction());
		// addObject(kb, 250, 400);
		addObject(kb, 250, 400);

		kb = new KeybindingActor(Keybind.PULL_UP, Keybind.PULL_UP.getText(), Keybind.PULL_UP.getKey(), Keybind.PULL_UP.isMouseAction());
		// addObject(kb, 227, 450);
		addObject(kb, 250, 450);

		kb = new KeybindingActor(Keybind.ROPE_DOWN, Keybind.ROPE_DOWN.getText(), Keybind.ROPE_DOWN.getKey(), Keybind.ROPE_DOWN.isMouseAction());
		// addObject(kb, 213, 500);
		addObject(kb, 250, 500);

		haungsMode = new SwitchBar("Haungs Mode", false);
		haungsMode.setAltSafeColor(Color.black);
		haungsMode.setAltTextColor(Color.black);
		haungsMode.setSafeColor(Color.black);
		haungsMode.setAltDangerColor(Color.black);
		addObject(haungsMode, 800, 200);

		sfxVolume = new MouseActBar("SFX Volume", "", 1, 100);
		sfxVolume.setAltDangerColor(Color.black);
		sfxVolume.setAltSafeColor(Color.black);
		sfxVolume.setAltTextColor(Color.black);
		sfxVolume.setTextColor(Color.black);
		sfxVolume.setSafeColor(Color.black);
		sfxVolume.setDangerColor(Color.black);
		sfxVolume.setValue(Setting.getSFXVolume());
		addObject(sfxVolume, 800, 250);

		musicVolume = new MouseActBar("Music Volume", "", 1, 100);
		musicVolume.setAltDangerColor(Color.black);
		musicVolume.setAltSafeColor(Color.black);
		musicVolume.setAltTextColor(Color.black);
		musicVolume.setTextColor(Color.black);
		musicVolume.setSafeColor(Color.black);
		musicVolume.setDangerColor(Color.black);
		musicVolume.setValue(Setting.getMusicVolume());
		addObject(musicVolume, 800, 300);
	}

	@Override
	public void act()
	{
		if (Greenfoot.mousePressed(null))
		{
			MouseInfo mi = Greenfoot.getMouseInfo();
			int clickX = mi.getX();
			int clickY = mi.getY();

			if (isBetween(clickX, 40, 250) && isBetween(clickY, 700, 770))
			{
				saveSettings();
				loadWorld(new StartScreen());
			}
		}
	}

	private void saveSettings()
	{
		Setting.setMusicVolume(musicVolume.getValue());
		Setting.setSFXVolume(sfxVolume.getValue());
		Setting.setHaungsMode(haungsMode.getState());
		Setting.saveSettings();
	}

	private boolean isBetween(int target, int lowerValue, int upperValue)
	{
		if (lowerValue < target && target < upperValue)
			return true;
		return false;
	}
}
