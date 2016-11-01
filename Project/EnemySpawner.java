import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemySpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemySpawner extends LevelActor
{
    
    private EnemyID enemy;
    
    public EnemySpawner(EnemyID enemy, int x, int y)
    {
        super(x, y);
        this.enemy = enemy;
    }
    
    
    /**
     * Act - do whatever the EnemySpawner wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.getRandomNumber(1000) < 10)
        {
            spawnEnemy(getEnemy());
        }
    }    
    
    public void spawnEnemy(Enemy enemy)
    {
        if(enemy != null)
        {
            Level level = (Level) getWorld();
            level.addLevelActor(enemy,this.getX(), this.getY());
        }
        
    }
    
    private Enemy getEnemy()
    {
        Enemy ret = null;
        switch(enemy)
        {
            case TEST_ENEMY:
                ret = new TestEnemy(this.getX(), this.getY());
                break;
        }
        return ret;
    }
}
