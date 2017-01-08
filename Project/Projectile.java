
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ProjectileScorpion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Projectile extends LevelActor
{
    
    private boolean moving = false;
    
    private boolean destroyed = false;

    private ProjectileEnemy shooter = null;
    
    public Projectile(ProjectileEnemy shooter)
    {
        super(shooter.getLevelX(), shooter.getLevelY());
        setImage(getProjectileImage());
    }
    
    
    public abstract int getSpeed();
    
    public abstract int getDamage();
    
    public abstract String getProjectileImage();
    
    
    public void setMoving()
    {
        moving = true;
    }
    
    /**
     * Act - do whatever the ProjectileScorpion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!destroyed)
            move();
       if(!destroyed)
            checkCollision();
        if(!destroyed)
            checkDamage();
    }  
    
    public void move()
    {
        
        if(moving && !destroyed)
        {
            if(isAtEdge())
            {
                Level l = (Level) getWorld();
               
                l.removeLevelActor(this);
                destroyed = true;
                return;
            }
            else
            {
                move(getSpeed());
                updatePosition();
            }

        }
    }
    
    public void checkCollision()
    {
         if(destroyed)
            return;
        
         Platform a = null;
         a = (Platform)getOneIntersectingObject(Platform.class);
         if(a != null)
         {
        	 if(a.isBreakable()){
        		 ((Level) getWorld()).removeLevelActor(a);
		         return;
        	 } else {
		         ((Level) getWorld()).removeLevelActor(this);
		         destroyed = true;
		         return;
        	 }
	     }
    }
    
    public void checkDamage()
    {
        if(destroyed)
        return;
        
         Actor sA = null;
           sA = getOneIntersectingObject(Spider.class);
           if(sA != null && sA instanceof Spider)
           {
               Spider s = (Spider) sA;

               s.decreaseHealth(getDamage());
               s.knockback(getLevelX(), getLevelY());
               ((Level) getWorld()).removeLevelActor(this);
               destroyed = true;
               return;
            }
    }

}
