import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ProjectileEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class ProjectileEnemy extends Enemy
{
    public static final int TICKS_PER_SHOOT = 100;
    
    private int projectileShootTimer = TICKS_PER_SHOOT;
    

    public ProjectileEnemy(int x, int y, boolean stunnable, boolean defeatable)
    {
        this(x, y, stunnable, defeatable, null);
    }
    
    public ProjectileEnemy(int x, int y, boolean stunnable, boolean defeatable, LevelActor spawnOnDeath)
    {
        super(x, y, stunnable, defeatable, spawnOnDeath);
    }
    
    /**
     * Act - do whatever the ProjectileEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
//        shoot();
    } 
    
    private void shoot()
    {
        if(projectileShootTimer == 0)
        {
            if(!isStunned() && isSpiderInSight())
            {
                
                
                Projectile p = getProjectile();
                Level l = (Level) getWorld();
                l.addLevelActor(p, this.getX(), this.getY());
                   
                p.turnTowards(l.getSpider().getX(), l.getSpider().getY());
                p.setMoving();
                projectileShootTimer += TICKS_PER_SHOOT;
            }
        }
        else
        projectileShootTimer--;
        
    }
    
    public abstract Projectile getProjectile();

}
