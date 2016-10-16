import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spider extends Actor
{
    /** Speed when moving in X-direction in pixels per frame. **/
    private final int X_SPEED = 4;

    /** Maximum falling speed in pixels per frame. **/
    private final int Y_SPEED_MAX = 8;

    /** Jumping acceleration in pixels per frame. **/
    private final int JUMP_STRENGTH = 13;

    /** current speed in the Y-direction in pixels per frame. **/
    private int ySpeed = 0;

    /** whether the spider is in the air or standing on something. Used to prevent jumping in mid-air. **/
    private boolean inAir = true;
    
    /**
     * Act - do whatever the Spider wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // TODO: implement death and game-over mechanic.
        if(isDead()){
            Greenfoot.setWorld(new MyWorld());
        }

        // jumping and falling
        Actor ground = getGround();
        if(ground != null){
            // stop falling
            ySpeed = 0;
            setToGround(ground);

            // allow jumping
            inAir = false;
        } else {
            // fall when in the air
            if(ySpeed < Y_SPEED_MAX){
                ySpeed++;
            }
        }
        if(!inAir && Greenfoot.isKeyDown("space")){
            inAir = true;
            ySpeed = -JUMP_STRENGTH;
        }
        setLocation(getX(), getY()+ySpeed);

        // horizontal movement
        if(Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")){
            setLocation(getX()+X_SPEED, getY());
        } else if(Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")){
            setLocation(getX()-X_SPEED, getY());
        }
    }
    
    /**
     * Returns a Platform underneath the spider or null, if there is none. 
     */
    public Actor getGround(){
        return getOneObjectAtOffset(0, getImage().getHeight()/2, Platform.class);
    }

    /**
     * Places the spider above the given Actor. Does not move the spider horizontally.
     */
    public void setToGround(Actor ground){
        setLocation(getX(), ground.getY() - (ground.getImage().getHeight()+getImage().getHeight())/2);
    }

    public boolean isDead(){
        return getY() >= getWorld().getHeight();
    }
}