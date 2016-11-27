import java.util.HashMap;
import java.util.Map;

import greenfoot.GreenfootImage;


public class AnimationSet implements AnimationInterface {

	public static final String DEFAULT_SET = "idle";
	
	private final Map<String, Animation> animations = new HashMap<>();
	private String activeSet = DEFAULT_SET;
	private final int defaultFrames;
	
	public AnimationSet(int defaultFrames) {
		this.defaultFrames = defaultFrames;
		
		Animation animation = new Animation(defaultFrames);
		this.animations.put(activeSet, animation);
	}
	
	public void useDefault(){
		this.useSet(DEFAULT_SET);
	}
	
	public void useSet(String set){
		if(!this.activeSet.equals(set)){
			this.activeSet = set;
			
			Animation animation = animations.get(set);
			if(animation==null){
				animation = new Animation(defaultFrames);
				animations.put(set, animation);
			} else {
				animation.reset();
			}
			animation.setChanged(true);
		}
	}
	
	@Override
	public void addImage(GreenfootImage img) {
		animations.get(activeSet).addImage(img);
	}

	@Override
	public void setFramesPerImage(int framesPerImage) {
		animations.get(activeSet).setFramesPerImage(framesPerImage);
	}

	@Override
	public void addImageForFrames(GreenfootImage img, int frames, boolean delayOthers) {
		animations.get(activeSet).addImageForFrames(img, frames, delayOthers);
	}

	@Override
	public GreenfootImage getImage() {
		return animations.get(activeSet).getImage();
	}

	@Override
	public boolean hasChanged() {
		return animations.get(activeSet).hasChanged();
	}

	@Override
	public void next() {
		animations.get(activeSet).next();
	}

	@Override
	public void reset() {
		animations.get(activeSet).reset();
	}
	
}
