import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greenfoot.GreenfootImage;


public class SetAnimation extends Animation {

	public static final String DEFAULT_SET = "idle";
	private Map<String, List<GreenfootImage>> imageSets = new HashMap<>();
	private String activeSet = DEFAULT_SET;
	
	public SetAnimation(int framesPerImage) {
		super(framesPerImage);
		
		imageSets.put(DEFAULT_SET, new ArrayList<>());
		useSet(DEFAULT_SET);
	}
	
	@Override
	public void addImage(GreenfootImage img) {
		this.addImage(DEFAULT_SET, img);
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
		if(!activeSet.equals(set)){
			activeSet = set;
			List<GreenfootImage> imageSet = imageSets.get(set);
			if(imageSet!=null){
				super.setImages(imageSet);
				super.setChanged(true);
			} else {
				throw new IllegalArgumentException("No AnnimationSet with identifier "+set+" found.");
			}
		}
	}
}
