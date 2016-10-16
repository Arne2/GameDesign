import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

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

    /** whether the player has jumped and not released the button **/
    private boolean jumpButtonReady = true;
    
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

        // gravity
        if(inAir) {
            if(ySpeed<Y_SPEED_MAX){
                ySpeed++;
            }
        }
        // jumping
        if(Greenfoot.isKeyDown("space")){
            if(!inAir && jumpButtonReady){
                ySpeed = -JUMP_STRENGTH;
                inAir = true;
                jumpButtonReady = false;
            }
        } else {
            jumpButtonReady = true;
        }
        
        // horizontal movement
        if(Greenfoot.isKeyDown("right")){
            moveRight();
        } else if(Greenfoot.isKeyDown("left")){
            moveLeft();
        }
        
        moveVertically(ySpeed);
    }
    
    /**
     * Moves the spider downwards by ySpeed pixels.
     * Checks for collisions with platforms afterwards.
     */
    public void moveVertically(int ySpeed){
        setLocation(getX(), getY()+ySpeed);
        
        if(ySpeed != 0){
            List<Actor> intersecting = getIntersectingObjects(Actor.class);
            if(intersecting.size() > 0){
                if(ySpeed > 0){
                    Platform ground = null;
                    for(Actor next : intersecting){
                        if(next instanceof Platform &&
                            (ground == null || next.getY()-next.getImage().getHeight()/2 < ground.getY()-ground.getImage().getHeight()/2))
                            ground = (Platform)next;
                    }
                    setToGround(ground);
                } else {
                    Platform ceiling = null;
                    for(Actor next : intersecting){
                        if(next instanceof Platform &&
                            (ceiling == null || next.getY()+next.getImage().getHeight()/2 > ceiling.getY()+ceiling.getImage().getHeight()/2))
                            ceiling = (Platform)next;
                    }
                    setToCeiling(ceiling);
                }
            }
        }
    }
    
    /**
     * Moves the spider to the right by X_SPEED pixels.
     * Checks for collisions with platforms afterwards.
     */
    public void moveRight(){
        // set inAir to true to allow falling of platforms.
        inAir = true;
        setLocation(getX()+X_SPEED, getY());
        
        List<Actor> intersecting = getIntersectingObjects(Actor.class);
        if(intersecting.size() > 0) {
            Platform wall = null;
            for(Actor next : intersecting){
                if(next instanceof Platform &&
                    (wall == null || next.getX()-next.getImage().getWidth()/2 < wall.getX()-wall.getImage().getWidth()/2))
                    wall = (Platform)next;
            }
            setToRightWall(wall);
        }
    }
    
    /**
     * Moves the spider to the left by X_SPEED pixels.
     * Checks for collisions with platforms afterwards.
     */
    public void moveLeft(){
        // set inAir to true to allow falling of platforms.
        inAir = true;
        setLocation(getX()-X_SPEED, getY());
        
        List<Actor> intersecting = getIntersectingObjects(Actor.class);
        if(intersecting.size() > 0) {
            Platform wall = null;
            for(Actor next : intersecting){
                if(next instanceof Platform &&
                    (wall == null || next.getX()+next.getImage().getWidth()/2 < wall.getX()+wall.getImage().getWidth()/2))
                    wall = (Platform)next;
            }
            setToLeftWall(wall);
        }
    }

    /**
     * Places the spider above the given Actor. Does not move the spider horizontally.
     */
    public void setToGround(Actor ground){
        ySpeed = 0;
        inAir = false;
        setLocation(getX(), ground.getY() - (ground.getImage().getHeight()+getImage().getHeight())/2);
    }
    
    /**
     * Places the spider below the given Actor. Does not move the spider horizontally.
     */
    public void setToCeiling(Actor ceiling){
        ySpeed = 0;
        setLocation(getX(), ceiling.getY() + (ceiling.getImage().getHeight()+getImage().getHeight())/2);
    }
    
    /**
     * Places the spider left of the given Actor. Does not move the spider vertically.
     */
    public void setToRightWall(Actor wall){
        setLocation(wall.getX() - (wall.getImage().getWidth()+getImage().getWidth())/2, getY());
    }
    
    /**
     * Places the spider right of the given Actor. Does not move the spider vertically.
     */
    public void setToLeftWall(Actor wall){
        setLocation(wall.getX() + (wall.getImage().getWidth()+getImage().getWidth())/2, getY());
    }

    public boolean isDead(){
        return getY() >= getWorld().getHeight();
    }
}