import java.awt.Color;

import greenfoot.Greenfoot;

/**
 * Class SwitchBar: a subclass of the Bar class that gives the bar user click functionality of a two-way switch. It can display alternating text as switch is toggled.
 */
public class SwitchBar extends Bar
{
	private boolean		changed				= false;
	private Color		altSafeColor		= Color.blue;
	private Color		altDangerColor		= new Color(0, 0, 0, 0);
	private Color		altTextColor		= Color.blue;
	private Color		altBackgroundColor	= new Color(0, 0, 0, 0);;
	private boolean		mouseOn				= false;
	private boolean		alternatingText		= false;
	private String[]	valueText			=
	{
			"", ""
	};

	/**
	 * SwitchBar Constructor: calls the super-class 'Bar' constructor with the same parameters
	 *
	 * @param 'ref'
	 *            the title for the bar
	 * @param 'status'
	 *            the initial value of the bar
	 */
	public SwitchBar(String ref, boolean status)
	{
		super(ref, "", 1, 1);
		setBarWidth(10);
		setBreakPercent(50);
		setBreakLow(status);
		setDangerColor(new Color(0, 0, 0, 0));
		setShowTextualUnits(false);
	}

	/**
	 * Method 'act': allows the user to perform mouse clicks to change its value (and text if alternating text is being used).
	 */
	public void act()
	{
		// check mouse hovering
		if ((!mouseOn && Greenfoot.mouseMoved(this)) || (mouseOn && Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)))
		{
			Color tColor = getTextColor();
			setTextColor(altTextColor);
			setAltTextColor(tColor);
			Color sColor = getSafeColor();
			setSafeColor(altSafeColor);
			setAltSafeColor(sColor);
			Color dColor = getDangerColor();
			setDangerColor(altDangerColor);
			setAltDangerColor(dColor);
			Color bColor = getBackgroundColor();
			setBackgroundColor(altBackgroundColor);
			setAltBackgroundColor(bColor);
			mouseOn = !mouseOn;
		}
		// check for mouse button click
		if (Greenfoot.mouseClicked(this))
		{
			// switch color of bar and set 'changed' to true
			setBreakLow(!getBreakLow());
			if (alternatingText)
				setReferenceText(getBreakLow() ? valueText[1] : valueText[0]);
			changed = true;
		}
	}

	/**
	 * Method 'setAltSafeColor': sets a new mouse hovering safe color
	 *
	 * @param 'color':
	 *            the new color to use as safeColor when mouse hovers over the bar
	 */
	public void setAltSafeColor(Color color)
	{
		altSafeColor = color;
	}

	/**
	 * Method 'getAltSafeColor': gets the current mouse hovering safe color
	 *
	 * @return: the current mouse hovering safe color
	 */
	public Color getAltSafeColor()
	{
		return altSafeColor;
	}

	/**
	 * Method 'setAltDangerColor': sets a new mouse hovering danger color
	 *
	 * @param 'color':
	 *            the new color to use as dangerColor when mouse hovers over the bar
	 */
	public void setAltDangerColor(Color color)
	{
		altDangerColor = color;
	}

	/**
	 * Method 'getAltDangerColor': gets the current mouse hovering danger color
	 *
	 * @return: the current mouse hovering danger color
	 */
	public Color getAltDangerColor()
	{
		return altDangerColor;
	}

	/**
	 * Method setAltTextColor': sets a new mouse hovering text color
	 *
	 * @param 'color':
	 *            the new color to use as textColor when mouse hovers over the bar
	 */
	public void setAltTextColor(Color color)
	{
		altTextColor = color;
	}

	/**
	 * Method 'getAltTextColor': gets the current mouse hovering text color
	 *
	 * @return: the current mouse hovering text color
	 */
	public Color getAltTextColor()
	{
		return altTextColor;
	}

	/**
	 * Method 'setAltBackgroundColor': sets a new mouse hovering background color
	 *
	 * @param 'color':
	 *            the new color to use as backgroundColor when mouse hovers over the bar
	 */
	public void setAltBackgroundColor(Color color)
	{
		altBackgroundColor = color;
	}

	/**
	 * Method 'getAltBackgroundColor': gets the current mouse hovering background color
	 *
	 * @return: the current mouse hovering background color
	 */
	public Color getAltBackgroundColor()
	{
		return altBackgroundColor;
	}

	/**
	 * Method 'isChanged': gets the current status of whether the value has been changed or not. The status is reset to 'false' automatically when this method is called
	 *
	 * @return: the current true/false value of 'changed'
	 */
	public boolean isChanged()
	{
		if (!changed)
			return false;
		changed = false;
		return true;
	}

	/**
	 * Method 'setValueText': sets the text to display for both states of the switch. The text for false (or off) is listed first.
	 *
	 * @param 'vText':
	 *            a String array that contains the text for both switch states
	 */
	public void setValueText(String[] vText)
	{
		valueText = vText;
		setReferenceText(getBreakLow() ? valueText[1] : valueText[0]);
		alternatingText = true;
	}

	/**
	 * Method 'getState': returns the state (status value) of the switch; 'on' (true) or 'off' (false)
	 *
	 * @return: the current 'value' of the switch
	 */
	public boolean getState()
	{
		return getBreakLow();
	}
}
