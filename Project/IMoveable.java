import greenfoot.Actor;

/**
 * Write a description of class IMoveable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface IMoveable
{
    public void moveTo(int x, int y);
    
    public void moveToX(int x);
    
    public void moveToY(int y);
    
    public <A> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls);
    
    public Actor getOneObjectAtOffset(int dx,
                                     int dy,
                                     java.lang.Class<?> cls);
                                     public <A> java.util.List<A>	getObjectsAtOffset(int dx, int dy, java.lang.Class<A> cls);
    public Direction getFacingDirection();
    
    public int getLevelX();
    
    public int getLevelY();
    
    public int getWidth();
    
    public int getHeight();
    
}
