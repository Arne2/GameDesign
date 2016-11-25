import java.util.ArrayList;
import java.util.List;

import greenfoot.GreenfootImage;


public class Animation {
	private final int framesPerImage;
	private int framesLeft;
	private int currentImage;
	private boolean changed;
	
	private GreenfootImage specialImage;
	private int framesForSpecialImage = 0;

	private List<GreenfootImage> images = new ArrayList<>();
	
	public Animation(int framesPerImage) {
		this.framesPerImage = framesPerImage;
		this.framesLeft = framesPerImage;
		this.currentImage = 0;
	}
	
	protected List<GreenfootImage> getImages() {
		return images;
	}
	
	public void addImage(GreenfootImage img){
		images.add(img);
	}
	
	public void addImageForFrames(GreenfootImage img, int frames){
		specialImage = img;
		framesForSpecialImage = frames;
		changed = true;
	}
	
	public GreenfootImage getImage(){
		if(framesForSpecialImage>0){
			return specialImage;
		} else if(getImages().size()>0){
			return getImages().get(currentImage);
		} else {
			return null;
		}
	}
	
	public boolean hasChanged(){
		if(changed){
			changed = false;
			return true;
		}
		
		return false;
	}
	
	public void next(){
		if(framesForSpecialImage>0){
			framesForSpecialImage--;
			if(framesForSpecialImage<=0){
				changed = true;
			}
		} else if(getImages().size()>1){
			framesLeft--;
			
			if(framesLeft<=0){
				framesLeft = framesPerImage;
				currentImage = (currentImage+1) % getImages().size();
				changed = true;
			}
		}
	}
	
	protected void setChanged(boolean changed){
		this.changed = changed;
	}
	
	public int getCurrentIndex(){
		return currentImage;
	}
	
	public void reset(){
		currentImage = 0;
		framesForSpecialImage = 0;
		this.framesLeft = framesPerImage;
	}
	
	protected void setImages(List<GreenfootImage> images) {
		this.images = images;
		reset();
	}
}
