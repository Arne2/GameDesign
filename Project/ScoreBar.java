import greenfoot.GreenfootImage;


public class ScoreBar extends Bar {

	public ScoreBar(String refText, int initValue, int maxValue) {
		super(refText, "", initValue, maxValue);
	}
	
	@Override
	protected GreenfootImage getRightImage() {
		return new GreenfootImage(" " + value + " / " + maximumValue, (int) fontSize, textColor, backgroundColor);
	}
}
