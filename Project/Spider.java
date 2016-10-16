import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spider extends Actor
{
    private int ySpeed = 0;
    private final int Y_SPEED_MAX = 8;
    private final int JUMP_STRENGTH = 15;
    private boolean hasJumped = true;
    private final int X_SPEED = 4;
    
    /**
     * Act - do whatever the Spider wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(isDead()){
            Greenfoot.setWorld(new MyWorld());
        }
        if(onGround()){
            ySpeed = 0;
            
            if(Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("space")){
                hasJumped = true;
                jump();
            } else {
                hasJumped = false;
            }
        } else {
            if(ySpeed < Y_SPEED_MAX){
                ySpeed++;
            }
            setLocation(getX(), getY()+ySpeed);
        }

        if(Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")){
            moveHorizontally(X_SPEED);
        } else if(Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")){
            moveHorizontally(-X_SPEED);
        }
    }

    public void moveHorizontally(int amount){
        setLocation(getX()+amount, getY());
    }

    public void jump(){
        ySpeed = -JUMP_STRENGTH;
        setLocation(getX(), getY()+ySpeed);
    }
    
    public boolean onGround(){
        return getOneObjectAtOffset(0, getImage().getHeight()/2, Platform.class) != null;
    }

    public boolean isDead(){
        return getY() >= getWorld().getHeight();
    }
}
