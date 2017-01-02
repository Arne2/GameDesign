
// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;

/**
 * Write a description of class EnemySpawner here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemySpawner extends LevelActor
{

    private EnemyID enemy;

    private boolean loop;

    private boolean spawned = false;

    public EnemySpawner(EnemyID enemy, int x, int y)
    {
        this(enemy, x, y, false);
    }

    public EnemySpawner(EnemyID enemy, int x, int y, boolean loop)
    {
        super(x, y);
        this.enemy = enemy;
        this.loop = loop;
        setImage("empty.png");
    }

    /**
     * Act - do whatever the EnemySpawner wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {

        if (isLoop())
        {
            if (Greenfoot.getRandomNumber(1000) < 10)
            {
                spawnEnemy(getEnemy());
            }
        }
        else
        {
            if (!spawned)
            {
                spawnEnemy(getEnemy());
                spawned = true;
            }
        }

    }

    public void spawnEnemy(Enemy enemy)
    {
        if (enemy != null)
        {
            Level level = (Level) getWorld();
            level.addLevelActor(enemy, this.getX(), this.getY());
            enemy.setActivated(true);
        }

    }

    private Enemy getEnemy()
    {
        Enemy ret = null;
        switch  (enemy)
        {
            case TEST_ENEMY:
                ret = new TestEnemy(this.getX(), this.getY());
                break;
            case WASP:
                ret = new EnemyWasp(this.getX(), this.getY());
                break;
            case WASP_MOVEABLE:
                ret = new EnemyWasp(this.getX(), this.getY(), true, false);
                break;
            case WASP_SHOOTING:
                ret = new EnemyWasp(this.getX(), this.getY(), false, true);
                break;
            case SPIDER:
                ret = new EnemySpider(this.getX(), this.getY());
                break;
            case SCORPION:
                ret = new EnemyScorpion(this.getX(), this.getY());
                break;
            case SCORPION_SHOOTING:
                ret = new EnemyScorpion(this.getX(), this.getY(), false, true);
                break;
            case SCORPION_MOVEABLE:
                ret = new EnemyScorpion(this.getX(), this.getY(), true, false);
                break;
            case SPIKES:
                ret = new Spikes(this.getX(), this.getY());
                break;
        }
        return ret;
    }

    public boolean isLoop()
    {
        return loop;
    }
}
