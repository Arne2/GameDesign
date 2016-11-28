import greenfoot.GreenfootImage;


public interface AnimationInterface {
	void addImage(GreenfootImage img);
	void setFramesPerImage(int framesPerImage);
	void addImageForFrames(GreenfootImage img, int frames, boolean delayOthers);
	GreenfootImage getImage();
	boolean hasChanged();
	void next();
	void reset();
}
