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
			
			Actor enemyActor = getOneObjectAtOffset(0, 0, Enemy.class);
			if(enemyActor != null && enemyActor instanceof Enemy){
				WebBar bar = ((Level)getWorld()).getSpider().getWebBar();
				if(bar.getValue() >= Spider.ENEMY_STUN_COST){
					bar.subtract(Spider.ENEMY_STUN_COST);
					
				    Enemy enemy = (Enemy) enemyActor;
				    enemy.setStunned();
				}
				
				((Level)getWorld()).removeLevelActor(this);
				return;
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
