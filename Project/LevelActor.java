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
	
	public int getLevelX(){
		return levelX;
	}
    
    public int getLevelY(){
    	return levelY;
    }
    
    public void setLevelX(int x)
    {
        this.levelX = x;
    }
    
    public void setLevelY(int y)
    {
        this.levelY= y;
    }
}
