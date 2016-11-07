public abstract class Enemy extends LevelActor
{
    private boolean stunned;
    private int     healthPoints;

    private int damage = 1;
    
    private boolean activated = false;

    private int damageTimer= 0;
    
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
            

        }
        
    }
    
    
    private void checkSpiderCollision()
    {
       Spider s = null;
       s = (Spider) getOneIntersectingObject(Spider.class);
       if(s != null)
       {
           if(damageTimer == 0)
           {
               s.decreaseHealth(damage);
               damageTimer = 100;
           }
           
       }
       
       if(damageTimer > 0)
       {
           damageTimer--;
        }
    }
    
    public void setActivated(boolean b)
    {
        this.activated = b;
    }
       
    public boolean isActivated()
    {
        return activated;
    }
    
}
