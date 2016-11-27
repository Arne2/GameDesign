import java.util.ArrayList;
import java.util.List;

import greenfoot.GreenfootImage;


public class Animation implements AnimationInterface{
	private int framesPerImage;
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
	
	@Override
	public void addImage(GreenfootImage img){
		images.add(img);
	}

	@Override
	public void setFramesPerImage(int framesPerImage) {
		this.framesPerImage = framesPerImage;
	}
	
	@Override
	public void addImageForFrames(GreenfootImage img, int frames){
		specialImage = img;
		framesForSpecialImage = frames;
		changed = true;
	}
	
	@Override
	public GreenfootImage getImage(){
		if(framesForSpecialImage>0){
			return specialImage;
		} else if(getImages().size()>0){
			return getImages().get(currentImage);
		} else {
			return null;
		}
	}
	
	@Override
	public boolean hasChanged(){
		if(changed){
			changed = false;
			return true;
		}
		
		return false;
	}
	
	@Override
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
	
	@Override
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
