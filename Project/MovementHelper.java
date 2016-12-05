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

        Actor a = null;
        if(moveable.getFacingDirection() == Direction.RIGHT)
        {
            a = moveable.getOneObjectAtOffset(moveable.getWidth() / 2 + 2, 0, Actor.class);
   
        }
        else
        {
           a = moveable.getOneObjectAtOffset(-moveable.getWidth() / 2 - 2, 0, Actor.class); 
        }
        if(a != null && a instanceof Platform)
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
                   System.out.println(s.getX());
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
        moveable.moveTo(moveable.getLevelX() - moveable.getWidth() / 2  - 2, moveable.getLevelY() - p.getImage().getHeight());
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
                    if(a != null && a instanceof Platform)
            {
            return true;
        }

        }

        return false;
    }
}
