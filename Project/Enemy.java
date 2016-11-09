import greenfoot.Actor;

public abstract class Enemy extends LevelActor
{
    protected int stunnedTicksLeft = 0;
    
    protected int healthPoints = 1;

    protected int damage = 1;
    
    protected boolean activated = false;

    /** timer for the damage interval which the enemy deals*/
    protected int enemyDamageTimer = 0;
    
    protected int spiderDamageTimer = 0;
    
    /** After how many ticks can the enemy attack again. */
    private int hitInterval = 50;
    
    
    public Enemy(int x, int y)
    {
        super(x, y);
        
    }

    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(activated)     
        {
            checkSpiderCollision();
            checkStunned();
            
        }
        
    }
    
    public int getHealthPoints()
    {
        return healthPoints;
                               
    }
    
    public void decreaseHealth()
    {
        healthPoints--;
        onDamaged();
        
    }
    
    public void decreaseHealth(int i)
    {
        healthPoints -= i;
        onDamaged();
        checkDead();
    }
    
    protected void setHealthPoints(int healthPoints)
    {
        this.healthPoints = healthPoints;
        checkDead();
    }
    
    private void checkSpiderCollision()
    {
        
           Actor sA = null;
           sA = getOneIntersectingObject(Spider.class);
           if(sA != null && sA instanceof Spider)
           {
               Spider s = (Spider) sA;
               if(!isStunned())
               {
    
                   if(enemyDamageTimer == 0)
                   {
                       s.decreaseHealth(damage);
                       enemyDamageTimer = getHitInterval();
                       s.knockback();
                   }
               
                }
                else
                {
                   if(spiderDamageTimer == 0)
                   {
                       decreaseHealth(s.getDamage());
                       spiderDamageTimer = s.getHitInterval();
                   }
                   

                }
           
 
        } 

          if(enemyDamageTimer > 0)
          {
               enemyDamageTimer--;
          }
          if(spiderDamageTimer > 0)
          {
               spiderDamageTimer--;
          }
    }
    
    public void checkStunned()
    {
        if(isStunned())
        {
            if(stunnedTicksLeft == 0)
            {
                spiderDamageTimer = 0;
                enemyDamageTimer = 0;
                onReleased();
            }
                
            else
                stunnedTicksLeft--;
        }
    }
    
    public void setStunned()
    {
        stunnedTicksLeft += 100;
    }
    
    /**
     * Called when the enemy is released from stunning.
     */
    public void onReleased()
    {
        
    }
    
    /**
     * Called when the enemy is damaged.
     */
    public void onDamaged()
    {
        
    }
    
    public boolean isStunned()
    {
        return stunnedTicksLeft != 0;
    }
    
    public void setActivated(boolean b)
    {
        this.activated = b;
    }
       
    public boolean isActivated()
    {
        return activated;
    }
    
    private void checkDead()
    {
        if(healthPoints <= 0)
        {
            Level l = (Level) getWorld();
            l.removeLevelActor(this);
            return;
        }
    }
    
        
    public int getHitInterval()
    {
        return hitInterval;
    }
}
