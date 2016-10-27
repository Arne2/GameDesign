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
	/** Threshold for playing sound after landing. In frames spent in the air. **/
	private static final int FALLING_SOUND_THRESHOLD = 20;

	/** Pixels the spider can walk up, when colliding right or left. **/
	private final int WALK_UP_LIMIT = 25;
	
    /** Maximum speed when moving in X-direction (by pressing a/d or left/right) in pixels per frame. 
     *  The spider can move faster, but the player cannot accelerate once that speed is reached. **/
    private final int X_SPEED_MAX = 4;

    /** Maximum falling speed in pixels per frame. **/
    private final int Y_SPEED_MAX = 10;

    /** Jumping acceleration in pixels per frame. **/
    private final int JUMP_STRENGTH = 13;

    /** Current speed in the X-direction in pixels per frame. **/
    private int xSpeed = 0;
    
    /** Current speed in the Y-direction in pixels per frame. **/
    private int ySpeed = 0;

    /** The platform the spider is currently standing on. Null if the player is in the air. **/
    private Platform ground = null;
    
    /** How many frames the spider has been in the air. **/
    private int inAir = 0;

    /** Whether the player has jumped and not released the button. **/
    private boolean jumpButtonReady = true;
    
    /**
     * Act - do whatever the Spider wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // TODO: implement death and game-over mechanic.
        if(isDead()){
            ySpeed = 0;
            // Game mechanic won't work this way. It leaves all items like music intact and unaccessible.
            Greenfoot.setWorld(new StartLevel());
        }
        
        // --- INPUTS
        // horizontal movement
        if(Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")){
        	if(xSpeed < X_SPEED_MAX){
        		xSpeed++;
        	}
        } else if(Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")){
        	if(xSpeed > -X_SPEED_MAX){
        		xSpeed--;
        	}
        } else {
        	if(xSpeed > 0){
        		xSpeed--;
        	} else if(xSpeed<0){
        		xSpeed++;
        	}
        }

        // gravity
        if(ground == null) {
            if(ySpeed<Y_SPEED_MAX){
                ySpeed++;
            }
        }
        // jumping
        if(Greenfoot.isKeyDown("space")){
            if(ground != null && jumpButtonReady){
                Greenfoot.playSound("jump02.wav");
                ySpeed = -JUMP_STRENGTH;
                jumpButtonReady = false;
            }
        } else {
            jumpButtonReady = true;
        }
        
        // --- MOVEMENT
        moveHorizontally(xSpeed);
        
        moveVertically(ySpeed);
        
        updateWorld();
    }
    
    /**
     * Updates the world. Centers the view around the spider and shifts the surrounding world accordingly.
     */
    public void updateWorld(){
        int dx = getX() - getWorld().getWidth()/2;
        int dy = getY() - getWorld().getHeight()/2;
        
        setLocation(getX()-dx, getY()-dy);
        ((Level)getWorld()).movePosition(dx, dy);
        ((Level)getWorld()).update();
    }
    
    /**
     * Moves the spider downwards by ySpeed pixels.
     * Checks for collisions with platforms afterwards.
     */
    public void moveVertically(int ySpeed){
        if(ySpeed != 0){
	        inAir++;
        	
        	// the actual movement
	    	setLocation(getX(), getY()+ySpeed);
	        
	    	if(ySpeed<0){
	    		// no longer on the ground
	         	ground = null;
	    	}
    	
            List<Actor> intersecting = getIntersectingObjects(Actor.class);
            if(intersecting.size() > 0){
                if(ySpeed > 0){
                	// moving down
                    Platform ground = null;
                    
                    // find the highest intersecting platform
                    for(Actor next : intersecting){
                        if(next instanceof Platform &&
                            (ground == null || next.getY()-next.getImage().getHeight()/2 < ground.getY()-ground.getImage().getHeight()/2))
                            ground = (Platform)next;
                    }
                    
                    if(ground==null){
                		// no collision detected.
                		return;
                	}
                    
                    // react to collision
                    setToGround(ground);
                } else {
                	// moving up
                    Platform ceiling = null;
                    
                    // find the lowest intersecting platform
                    for(Actor next : intersecting){
                        if(next instanceof Platform &&
                            (ceiling == null || next.getY()+next.getImage().getHeight()/2 > ceiling.getY()+ceiling.getImage().getHeight()/2))
                            ceiling = (Platform)next;
                    }
                    
                    if(ceiling==null){
                		// no collision detected.
                		return;
                	}
                    
                    // react to collision
                    setToCeiling(ceiling);
                }
            }
        }
    }
    
    /**
     * Moves the spider right by xSpeed pixels.
     * Checks for collisions with platforms afterwards.
     */
    public void moveHorizontally(int xSpeed){
        if(xSpeed!=0){
	        // Check, if the spider has moved off its platform and is falling
	        if(ground != null &&
	        		(getX()-ground.getX() > (ground.getImage().getWidth()+getImage().getWidth())/2 ||
	        		 ground.getX()-getX() > (ground.getImage().getWidth()+getImage().getWidth())/2)){
	            ground = null;
	        }
	        
	        // the actual movement
	        setLocation(getX()+xSpeed, getY());
	        
	        // collision detection
	        List<Actor> intersecting = getIntersectingObjects(Actor.class);
	        if(intersecting.size() > 0) {
	            Platform wall = null;
            
	            if(xSpeed>0){
	            	// move right
	            	// find the leftmost highest wall
	            	for(Actor next : intersecting){
		                if(next instanceof Platform &&
		                    (wall == null || next.getX()-next.getImage().getWidth()/2 < wall.getX()-wall.getImage().getWidth()/2) ||
		                    (next.getX()-next.getImage().getWidth()/2 == wall.getX()-wall.getImage().getWidth()/2) && next.getY()-next.getImage().getHeight()/2 < wall.getY()-wall.getImage().getHeight()/2)
		                    wall = (Platform)next;
		            }
	            	
	            	if(wall==null){
	            		// no collision detected.
	            		return;
	            	}
	            	
	            	// react to collision
		            if(ground!=null && (getY()+getImage().getHeight()/2) - (wall.getY()-wall.getImage().getHeight()/2) < WALK_UP_LIMIT){
		            	walkUpRightWall(wall);
		            } else {
		            	setToRightWall(wall);
		            }
	            } else {
	            	// move left
	            	// find the rightmost highest wall
	            	for(Actor next : intersecting){
	                    if(next instanceof Platform &&
	                        (wall == null || next.getX()+next.getImage().getWidth()/2 < wall.getX()+wall.getImage().getWidth()/2) ||
	                        (next.getX()+next.getImage().getWidth()/2 == wall.getX()+wall.getImage().getWidth()/2) && next.getY()-next.getImage().getHeight()/2 < wall.getY()-wall.getImage().getHeight()/2)
	                        wall = (Platform)next;
	                }
	            	
	            	if(wall==null){
	            		// no collision detected.
	            		return;
	            	}
	            	
	            	// react to collision
	                if(ground!=null && (getY()+getImage().getHeight()/2) - (wall.getY()-wall.getImage().getHeight()/2) < WALK_UP_LIMIT){
	                	walkUpLeftWall(wall);
	                } else {
	                	setToLeftWall(wall);
	                }
	            }
	        }
        }
    }
    
    /**
     * Places the Spider on top of the given wall while being as far left as possible.
     */
    private void walkUpRightWall(Platform wall) {
    	setLocation(wall.getX() - (wall.getImage().getWidth()+getImage().getWidth())/2 -1, wall.getY()-(wall.getImage().getHeight()+getImage().getHeight())/2);
    	ground = wall;
    }

    /**
     * Places the Spider on top of the given wall while being as far right as possible.
     */
    private void walkUpLeftWall(Platform wall) {
        setLocation(wall.getX() + (wall.getImage().getWidth()+getImage().getWidth())/2 -1, wall.getY()-(wall.getImage().getHeight()+getImage().getHeight())/2);
    	ground = wall;
	}

	/**
     * Places the spider above the given Actor. Does not move the spider horizontally.
     * This also stops the spider from falling.
     * When the spider is falling this is the only method that will stop it from falling.
     */
    public void setToGround(Actor ground){
    	System.out.println(inAir);
    	if(inAir > FALLING_SOUND_THRESHOLD){
    		Greenfoot.playSound("landing01.wav");
    	}
    	
    	inAir = 0;
    	
        ySpeed = 0;
        this.ground = (Platform)ground;
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
        setLocation(wall.getX() - (wall.getImage().getWidth()+getImage().getWidth())/2 - 1, getY());
    }
    
    /**
     * Places the spider right of the given Actor. Does not move the spider vertically.
     */
    public void setToLeftWall(Actor wall){
        setLocation(wall.getX() + (wall.getImage().getWidth()+getImage().getWidth())/2, getY());
    }

    // TODO
    public boolean isDead(){
        return ((Level)getWorld()).hasSpiderFallen();
    }
}