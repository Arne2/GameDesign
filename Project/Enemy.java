import greenfoot.Actor;

public abstract class Enemy extends LevelActor implements IDamageable
{

    protected int stunnedTicksLeft = 0;
    
    protected int healthPoints = 1;

    protected int damage = 1;
    
    protected boolean activated = true;

    /** timer for the damage interval which the enemy deals*/
    protected int enemyDamageTimer = 0;
    
    protected int spiderDamageTimer = 0;
    
    /** After how many ticks can the enemy attack again. */
    private int hitInterval = 50;
    
    private int spiderKnockbackTimer = 0;
    
    /** Boolean if enemy is affected by web*/
    protected boolean stunnable;
    
    /** Boolean if Enemy is defeatable*/
    protected boolean defeatable;
    
    private final LevelActor spawnOnDeath;
    
    private int sightRadius = 300;
    
    private boolean seeSpider = false;
        
    private Direction facingDirection = Direction.LEFT;
    
    public Enemy(int x, int y, boolean stunnable, boolean defeatable)
    {
        this(x, y, stunnable, defeatable, null);
    }
    
    public Enemy(int x, int y, boolean stunnable, boolean defeatable, LevelActor spawnOnDeath)
    {
        super(x, y);
        this.stunnable = stunnable;
        this.defeatable = defeatable;
        this.spawnOnDeath = spawnOnDeath;
    }

    protected void setDamage(int d)
    {
        this.damage = d;
    }
    
    public int getSightRadius()
    {
        return this.sightRadius;
    }
    
    protected void setSightRadius(int r)
    {
        this.sightRadius = r;
    }
       
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(activated)     
        {
            lookForSpider();
            checkSpiderCollision();
            if(stunnable){
                checkStunned();
            }
            //checkMovement();
        }
        
    }
    
    @Override
    public int getHealth()
    {
        return healthPoints;
                               
    }
    
    @Override
    public void decreaseHealth()
    {
        if(defeatable){
            healthPoints--;
            onDamaged();
        }
    }
    
    @Override
    public void decreaseHealth(int i)
    {
        if(defeatable){
            healthPoints -= i;
            onDamaged();
            checkDead();
        }
    }
    
    @Override
    public void setHealth(int healthPoints)
    {
        if(defeatable){
            this.healthPoints = healthPoints;
            checkDead();
        }
    }
    
    private void checkSpiderCollision()
    {
           Actor sA = null;
           sA = getOneIntersectingObject(Spider.class);
           if(sA != null && sA instanceof Spider)
           {
               Spider s = (Spider) sA;
               if(!stunnable || !isStunned())
               {
    
                   if(enemyDamageTimer == 0)
                   {
                       
                       damageSpider(s);
                       enemyDamageTimer = getHitInterval();
                   }
               
                }
                else
                {
                   
                   if(spiderDamageTimer == 0)
                   {
                       
                       decreaseHealth(s.getDamage());
                       
                       spiderDamageTimer = s.getHitInterval();
                       spiderKnockbackTimer = 10;
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
    
    public void damageSpider(Spider s)
    {
        s.decreaseHealth(damage);              
        s.knockback(getLevelX(), getLevelY());
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
    
    private void checkMovement()
    {
        if(spiderKnockbackTimer > 0)
        {
            spiderKnockbackTimer--;
            setLevelX(getLevelX() + 5);
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
    
    /**
     * Called when the enemy can see the player;
     */
    public void onPlayerSee()
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
            if(spawnOnDeath!=null){
                l.addLevelActor(spawnOnDeath, getX(), getY());
            }
            l.removeLevelActor(this);
            return;
        }
    }
        
    public LevelActor getSpawnOnDeath() {
		return spawnOnDeath;
	}

	public int getHitInterval()
    {
        return hitInterval;
    }
    
    
    @Override
    public int getMaxHealth()
    {
        return 1;
    }
    @Override
    public void instantKill()
    {
        
        setHealth(0);
    }
    
    @Override
    public boolean isDead()
    {
        return getHealth() <= 0;
    }
    
    public boolean isSpiderInSight()
    {
        int distance = getSpiderDistance();

        if(distance < sightRadius)
            return true;         
        else
            return false;
    }
    
    public int getSpiderDistance()
    {
        Spider s = ((Level)getWorld()).getSpider();
        if(s == null)
        return 0;
        
        int sX = s.getLevelX();
        int sY = s.getLevelY();
        
        int x = getLevelX();
        int y = getLevelY();
        
        double dX = sX -x;
        double dY = sY - y;
        
        double a = Math.pow(dX, 2.0);
        double b = Math.pow(dY , 2.0);
        int distance = (int) Math.sqrt(a + b);
       
        return distance;
    }
    
    public void lookForSpider()
    {
        if(! isStunned() && isSpiderInSight())
        {
            onPlayerSee();
            seeSpider = true;
            checkDirection();
        }
        else
        {
            seeSpider = false;
        }
    }
    
    public void checkDirection()
    {
        Spider s = ((Level)getWorld()).getSpider();
        int spiderX = s.getLevelX();
        int x = getLevelX();
        
        int diff = x - spiderX;
        if(diff >= 0)
        {
            setFacingDirection(Direction.LEFT);
        }
        else
        {
            setFacingDirection(Direction.RIGHT);
        }
    }
    
    public boolean canSeeSpider()
    {
        return seeSpider;
    }
    
    public Direction getFacingDirection()
    {
        return facingDirection;
    }
    
    public void setFacingDirection(Direction d)
    {
        this.facingDirection = d;
    }
}
