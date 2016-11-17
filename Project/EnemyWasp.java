import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyWasp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyWasp extends Enemy
{
    
        
    private int tickCounter = 0;
    
    private int currentFrame = 1;
    
    private int ticksShowHit = -1;
    
    
    public EnemyWasp(int x, int y)
    {
        super(x,y,true,true);
        
        setHealthPoints(3);
        
    }  
    
    /**
     * Act - do whatever the EnemyWasp wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        
        animate();
    }    
    
    @Override
    public void setStunned()
    {
        super.setStunned();
        setImage("wasp_stunned_64.png");  
    }
    
    @Override
    public void onDamaged()
    {
        ticksShowHit = 40;
        setImage("wasp_hit_64.png");
    }
    
    private void animate()    
    {
        
        if(isStunned())
        {
            if(ticksShowHit > 0)
            {
                ticksShowHit--;
                if(ticksShowHit == 0)
                {
                    setImage("wasp_stunned_64.png");  
                }
                
            }

               return;
        }
            
            
       tickCounter++;
       if(tickCounter > 5)
       {
           
           tickCounter = 0;
           currentFrame++;
           if(currentFrame > 2)
           {
               currentFrame = 1;
           }
           switch(currentFrame)
           {
               case 1:
               setImage("wasp1_64.png");
               break;
               case 2:
               setImage("wasp2_64.png");
               break;
           }
       }
    }
            
}
