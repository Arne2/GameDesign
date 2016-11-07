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
    
    
    public EnemyWasp(int x, int y)
    {
        super(x,y);
        
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
    
    private void animate()    
    {
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
