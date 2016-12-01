
public class SpawnPoint extends LevelActor {
	
	private LevelActor spawn;

	public SpawnPoint(LevelActor spawn) {
		super(spawn.getLevelX(), spawn.getLevelY());
		this.spawn = spawn;
	}
	
	public void killSpawn(){
		// if enemy is not in the world anymore
		if(spawn==null){
			return;
		} else if(spawn instanceof Enemy){
			System.out.println("spawn changed");
			spawn = ((Enemy)spawn).getSpawnOnDeath();
		} else {
			System.out.println("spawn nulled");
			spawn = null;
		}
	}
	
	public LevelActor getSpawn(){
		return spawn;
	}
	
	public boolean isSpawned(){
		if(spawn==null || (spawn!=null && spawn.getWorld()==null)){
			return true;
		}
		return false;
	}
}
