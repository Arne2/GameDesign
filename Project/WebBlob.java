import greenfoot.Actor;


public class WebBlob extends LevelActor{

	private final int speed;
	private boolean stationary = false;
	
	public WebBlob(int speed) {
		this.speed = speed;
	}
	
	@Override
	public void act() {
		if(!stationary){
			move(speed);
			
			Actor platform = getOneObjectAtOffset(0, 0, Platform.class);
			if(platform!=null){
				stationary = true;
			}
		}
		
		if(isAtEdge()){
			((Level)getWorld()).removeLevelActor(this);
			return;
		}
		updatePosition();
	}

	public boolean isStationary() {
		return stationary;
	}
}
