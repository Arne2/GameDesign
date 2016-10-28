import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Web here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Web extends LevelActor
{
	protected abstract int getX1(); 
	protected abstract int getY1(); 
	
	protected abstract int getX2(); 
	protected abstract int getY2(); 
	
    /**
     * Act - do whatever the Web wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setLocation((getX1()+getX2())/2, (getY1()+getY2())/2);
        turnTowards(getX1(), getY1());
        
        GreenfootImage image = getImage();
        try{
        	image.scale((int)Math.hypot((double)(getX1()-getX2()), (double)(getY1()-getY2())), image.getHeight());
        } catch(IllegalArgumentException e){
        	image.scale(1, image.getHeight());
        }
        setImage(image);
        
        updatePosition();
    }
}
