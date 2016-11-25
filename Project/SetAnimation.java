import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greenfoot.GreenfootImage;


public class SetAnimation extends Animation {

	private Map<String, List<GreenfootImage>> imageSets = new HashMap<>();
	
	public SetAnimation(int framesPerImage) {
		super(framesPerImage);
	}
	
	@Override
	public void addImage(GreenfootImage img) {
		this.addImage("idle", img);
	}
	
	public void addImage(String set, GreenfootImage img){
		List<GreenfootImage> imageSet = imageSets.get(set);
		if(imageSet==null){
			imageSet = new ArrayList<GreenfootImage>();
			imageSet.add(img);
			imageSets.put(set, imageSet);
		} else {
			imageSet.add(img);
		}
	}
	
	public void useSet(String set){
		List<GreenfootImage> imageSet = imageSets.get(set);
		if(imageSet!=null){
			super.setImages(imageSet);
		} else {
			throw new IllegalArgumentException("No AnnimationSet with identifier "+set+" found.");
		}
	}
}
