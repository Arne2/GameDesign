import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScorpionProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScorpionProjectile extends Projectile
{
    public static final int DAMAGE = 2;
    
    public static final int SPEED = 5;
    
    public static final String IMAGE = "enemy_projectile.png";
    
    
    public ScorpionProjectile(ProjectileEnemy shooter)
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
