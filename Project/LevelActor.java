import greenfoot.Actor;


public abstract class LevelActor extends Actor {
	private int levelX, levelY;
	
	public LevelActor() {
		
	}
	
	public LevelActor(int levelX, int levelY){
		this.levelX = levelX;
		this.levelY = levelY;
	}
	
	protected void updatePosition() {
		this.levelX = ((Level)getWorld()).getXPosition()+getX();
		this.levelY = ((Level)getWorld()).getYPosition()+getY();
	}
	
	public void setLevelX(int levelX) {
		this.levelX = levelX;
	}

	public void setLevelY(int levelY) {
		this.levelY = levelY;
	}

	public int getLevelX(){
		return levelX;
	}
    
    public int getLevelY(){
    	return levelY;
    }
}
