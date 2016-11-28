import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ProjectileScorpion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends LevelActor
{
    
    private boolean moving = false;
    
    private final int speed;
    
    private final int damage = 1;
    
    private boolean destroyed = false;
    
    public Projectile(int x, int y, int speed)
    {
        super(x, y);
        this.speed = speed;
        setImage("enemy_projectile.png");
    }
    
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
                move(speed);
                updatePosition();
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

               s.decreaseHealth(damage);
               s.knockback(getLevelX(), getLevelY());
               ((Level) getWorld()).removeLevelActor(this);
               destroyed = true;
               return;
            }
    }

}
