import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import greenfoot.GreenfootImage;


public class Animation implements AnimationInterface{
	private int framesPerImage;
	private int framesLeft;
	private int currentImage;
	private boolean changed;

	private class AnimationFrame{
		private int framesLeft;
		private GreenfootImage image;
		
		private AnimationFrame(int framesLeft, GreenfootImage image) {
			super();
			this.framesLeft = framesLeft;
			this.image = image;
		}
	}
	
	private List<GreenfootImage> images = new ArrayList<>();
	private LinkedList<AnimationFrame> frames = new LinkedList<>();
	
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
	public void addImageForFrames(GreenfootImage img, int frames, boolean delayOthers){
		this.frames.push(new AnimationFrame(frames, img));
		changed = true;
		
		if(!delayOthers){
			for(int i = 1; i < this.frames.size(); i++){
				AnimationFrame next = this.frames.get(i);
				next.framesLeft = next.framesLeft - frames;
			}
		}
	}
	
	@Override
	public GreenfootImage getImage(){
		AnimationFrame special = frames.peek();
		if(special!=null){
			return special.image;
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
		AnimationFrame special = frames.size()==0 ? null : frames.peek();
		if(special!=null){
			if(special.framesLeft-- <= 0)
			{
				frames.pop();
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
	
	protected void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	@Override
	public void reset() {
		currentImage = 0;
		
		while(frames.size()>0){
			frames.pop();
		}
		
		this.framesLeft = framesPerImage;
	}
}
