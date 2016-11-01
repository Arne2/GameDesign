public abstract class Enemy extends LevelActor
{
    
    public Enemy(int x, int y)
    {
        super(x, y);
    }
    
	/**
	 * Act - do whatever the Enemy wants to do. This method is called whenever
	 * the 'Act' or 'Run' button gets pressed in the environment.
	 */
	public abstract void act();
}
