import greenfoot.GreenfootImage;


public class LevelRating extends LevelActor {
	private static final GreenfootImage emptyStar = new GreenfootImage("star_empty.png");
	private static final GreenfootImage fullStar = new GreenfootImage("star_full.png");
	
	public LevelRating(int stars) {
		if(stars<0){
			stars = 0;
		} else if(stars>5) {
			stars = 5;
		}
		
		int width = emptyStar.getWidth()*(5-stars) + fullStar.getWidth()*stars+8;
		int height = Math.max(fullStar.getHeight(), emptyStar.getHeight());
		
		int x = 0;
		GreenfootImage image = new GreenfootImage(width, height);
		for(int i=0; i<5; i++){
			GreenfootImage next = i<stars ? fullStar : emptyStar;
			image.drawImage(next, x, 0);
			x += next.getWidth()+2;
		}
		
		setImage(image);
	}
	
	public LevelRating(int stars, int newHeight) {
		if(stars<0){
			stars = 0;
		} else if(stars>5) {
			stars = 5;
		}
		
		int width = emptyStar.getWidth()*(5-stars) + fullStar.getWidth()*stars+8;
		int height = Math.max(fullStar.getHeight(), emptyStar.getHeight());
		
		int x = 0;
		GreenfootImage image = new GreenfootImage(width, height);
		for(int i=0; i<5; i++){
			GreenfootImage next = i<stars ? fullStar : emptyStar;
			image.drawImage(next, x, 0);
			x += next.getWidth()+2;
		}
		
		int newWidth = (int)((newHeight/(float)height) * width);
		image.scale(newWidth, newHeight);
		
		setImage(image);
	}
	
	
}
