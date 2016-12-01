import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WaspProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaspProjectile extends Projectile
{
    
    public static final int DAMAGE = 1;
    
    public static final int SPEED = 3;
    
    public static final String IMAGE = "enemy_projectile.png";
    
    
    public WaspProjectile(ProjectileEnemy shooter)
    {
        super(shooter);
    }
    
    public void act() 
    {
        super.act();
    }   
    
    @Override
    public int getDamage()
    {
        return DAMAGE;
    }
    
    @Override
    public int getSpeed()
    {
        return SPEED;
    }
    
    @Override
    public String getProjectileImage()
    {
        return IMAGE;
    }
}
