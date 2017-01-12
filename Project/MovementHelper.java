import java.util.List;
import greenfoot.Actor;

/**
 * Write a description of class MovementHelper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovementHelper  
{
    private final IMoveable moveable;
    
    private Platform            ground              = null;
    
    private boolean collision;
    
    private int maxFall = 1;
    
    private boolean canStep = true;
    
    public MovementHelper(IMoveable m)
    {
        this(m, true);
    }
    
    public MovementHelper(IMoveable m, boolean collision)
    {
        moveable = m;
        this.collision = collision;
    }
    
    public void moveX(int moveX)
    {
        
        boolean canMove = true;
        
 
        
        if(!hasGround())
        {
            moveable.moveToY(moveable.getLevelY() + 5);
            return;
        }

        if(detectCliff())
        {
           return;
        }
        
        Actor a = null;
        if(moveable.getFacingDirection() == Direction.RIGHT)
        {
            a = moveable.getOneObjectAtOffset(moveable.getWidth() / 2 + 2, 0, Actor.class);
   
        }
        else
        {
           a = moveable.getOneObjectAtOffset(-moveable.getWidth() / 2 - 2, 0, Actor.class); 
        }
        if(a != null && a instanceof Platform && canStep)
        {
                Platform p = (Platform) a;
                
                Actor s  = null;
                if(moveable.getFacingDirection() == Direction.RIGHT)
                {
                    s = moveable.getOneObjectAtOffset(moveable.getWidth() / 2 + 2, -p.getImage().getHeight(), Actor.class);
           
                }
                else
                {
                   s = moveable.getOneObjectAtOffset(-moveable.getWidth() / 2 - 2, -p.getImage().getHeight(), Actor.class); 
                }
                
                if(s == null)
                    step(p);
                else
                {
                   canMove = false;
                }
                    
        }
        
        if (canMove)
        {
          moveable.moveTo(moveable.getLevelX() + moveX, moveable.getLevelY());
                      
        }
        
    }
   
    public void moveY(int moveY)
    {

    }
    
    public void step(Platform p)
    {
        int mul = 1;
        if(moveable.getFacingDirection() == Direction.LEFT)
        mul = -1;
        
        moveable.moveTo(moveable.getLevelX() + mul * (moveable.getWidth() / 2  + 2), moveable.getLevelY() - p.getImage().getHeight());
    }
    
    public void move(int moveX, int moveY)
    {
        
        
            moveX(moveX);
            moveY(moveY);
            
 
    }
   
    
    public boolean hasGround()
    {
        List<Actor> actors =  moveable.getObjectsAtOffset(0, moveable.getHeight() / 2 + 2, Actor.class);
        for(Actor a : actors)
        {
            if(a != null)
            {
                if(a instanceof Spikes)
                    return false;
                if(a instanceof Platform)
                    return true;
            }

        }

        return false;
    }
    
    boolean detectCliff()
    {
 
        
        int mul = 1;
        if(moveable.getFacingDirection() == Direction.LEFT)
        mul = -1;
        
        for(int i = 0; i <= maxFall; i++)
        {
            List<Actor> actors =  moveable.getObjectsAtOffset(mul * moveable.getWidth() / 2, (i) * moveable.getHeight() + moveable.getHeight() / 2 + 2, Actor.class);
            for(Actor a : actors)
            {
                    if(a != null && a instanceof Platform)
                    {
                        return false;
                    }

            }
        }
        
        return true;
    }
}
