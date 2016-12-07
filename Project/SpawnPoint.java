
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
			spawn = ((Enemy)spawn).getSpawnOnDeath();
		} else {
			spawn = null;
		}
	}
	
	public LevelActor getSpawn(){
		return spawn;
	}
}
