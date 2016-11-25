import java.util.ArrayList;
import java.util.List;

import greenfoot.GreenfootImage;


public class Animation {
	private final int framesPerImage;
	private int framesLeft;
	private int currentImage;

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
	
	public GreenfootImage getImage(){
		return getImages().get(currentImage);
	}
	
	public void next(){
		framesLeft--;
		
		if(framesLeft<=0){
			framesLeft = framesPerImage;
			currentImage = (currentImage+1) % getImages().size();
		}
	}
	
	public int getCurrentIndex(){
		return currentImage;
	}
	
	public void reset(){
		currentImage = 0;
		this.framesLeft = framesPerImage;
	}
	
	protected void setImages(List<GreenfootImage> images) {
		this.images = images;
		reset();
	}
}
