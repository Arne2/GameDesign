import java.util.List;

// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

/**
 * Write a description of class Spider here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spider extends Actor
{
    private static boolean DEBUG = false;
    
    /** Width of the spider in pixels **/
    private final int           WIDTH               = 64;
    /** Height of the spider in pixels **/
    private final int           HEIGHT              = 23;

    /** Downwards acceleration in pixels per frame per frame. **/
    public static final double  GRAVITY             = 1.5f;

    /** Pixels the spider can walk up, when colliding right or left. **/
    private static final int    WALK_UP_LIMIT       = Platform.SIZE + 4;

    /**
     * Maximum speed when moving in X-direction (by pressing a/d or left/right) in pixels per frame. The spider can move faster, but the player cannot accelerate once that speed is reached.
     **/
    private static final int    X_SPEED_MAX         = 6;

    /** Maximum falling speed in pixels per frame. **/
    private static final int    Y_SPEED_MAX         = 16;

    /** Jumping acceleration in pixels per frame. **/
    private static final int    JUMP_STRENGTH       = 30;

    /** Current speed in the X-direction in pixels per frame. **/
    private double              xSpeed              = 0;
    /** Movement in x direction, that was not executed the last frame due to pixel precision. **/
    private double              xMoveRemaining;

    /** Current speed in the Y-direction in pixels per frame. **/
    private double              ySpeed              = 0;
    /** Movement in y direction, that was not executed the last frame due to pixel precision. **/
    private double              yMoveRemaining;

    /** The platform the spider is currently standing on. Null if the player is in the air. **/
    private Platform            ground              = null;

    /** Whether the player has jumped and not released the button. **/
    private boolean             jumpButtonReady     = true;

    private WebBlob             blob                = null;
    private double              webLength           = -1;

    private int                 movementFrame;
    private int                 currentMovementFrame;
    private static final int    FRAMES_PER_PICTURE  = 10;
    private static final int    FRAMES_BEFORE_IDLE  = 25;
    
    private static final double WEB_LENGTH_CHANGE = 2;

    private int healthPoints = 5;
    
    private int damage = 1;
    
    /** After how many ticks can the spider attack again. */
    private int hitInterval = 50;
    
    private int knockbackX = 0;
    
    private int knockbackY = 0;
    
    private int knockbackCounter = 0;
    
    public Spider()
    {
        GreenfootImage image = getImage();
        image.scale(WIDTH, HEIGHT);
        setImage(image);
    }

    /**
     * Act - do whatever the Spider wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // TODO: implement death and game-over mechanic.
        if (isDead())
        {
            ySpeed = 0;
            // Game mechanic won't work this way. It leaves all items like music intact and unaccessible.
            Greenfoot.setWorld(new StartLevel());
        }

       
        
        // --- INPUTS
        // horizontal movement
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d"))
        {
            if (xSpeed < X_SPEED_MAX)
            {
                xSpeed = Math.min(X_SPEED_MAX, xSpeed + X_SPEED_MAX);
            }
        }
        else if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a"))
        {
            if (xSpeed > -X_SPEED_MAX)
            {
                xSpeed = Math.max(-X_SPEED_MAX, xSpeed - X_SPEED_MAX);
            }
        }
        else
        {
            lowerXSpeed(GRAVITY);
        }
        
        
        // gravity
        if (ground == null)
        {
            if (ySpeed < Y_SPEED_MAX)
            {
                ySpeed += GRAVITY;
            }
        }
        // jumping
        if (Greenfoot.isKeyDown("space"))
        {
            if (ground != null && jumpButtonReady)
            {
                Greenfoot.playSound("jump02.wav");
                ySpeed = -JUMP_STRENGTH;
                jumpButtonReady = false;
            }
        }
        else
        {
            jumpButtonReady = true;
        }

        // friction
        if (xSpeed > X_SPEED_MAX || xSpeed < -X_SPEED_MAX)
        {
            xSpeed *= 0.9;
        }
        if (ySpeed > Y_SPEED_MAX || ySpeed < -Y_SPEED_MAX)
        {
            ySpeed *= 0.9;
        }

        // web
        if (blob != null)
        {
            if (Greenfoot.mouseClicked(null))
            {
                ((Level) getWorld()).removeLevelActor(blob);
                removeBlob();
            }
            else if (blob.getWorld() == null)
            {
                removeBlob();
            } 
            else if(blob.isStationary() && webLength<0)
            {
                webLength = (int)Math.hypot((double)(getX()-blob.getX()), (double)(getY()-blob.getY()))+5;
            } 
            else if(webLength>0)
            {
                adjustWebLength();
                calculateWebForce();
            }
        }
        else if (Greenfoot.mouseClicked(null))
        {
            MouseInfo mi = Greenfoot.getMouseInfo();

            blob = new WebBlob(20);

            ((Level) getWorld()).addLevelActor(blob, getX(), getY());
            ((Level) getWorld()).addLevelActor(new WebString(this, blob), getX(), getY());
            blob.turnTowards(mi.getX(), mi.getY());
        }

        int xMove = (int) (xSpeed+xMoveRemaining);
        int yMove = (int) (ySpeed+yMoveRemaining);
        xMoveRemaining = (xSpeed+xMoveRemaining) - xMove;
        yMoveRemaining = (ySpeed+yMoveRemaining) - yMove;

        // --- MOVEMENT


        if(knockbackCounter > 0)
        {
            if(DEBUG){
                System.out.println(getLevelX() + " " + getLevelY());
            }
            moveHorizontally(knockbackX);
            moveVertically(knockbackY);
            knockbackCounter--;
            if(DEBUG){
                System.out.println(getLevelX() + " " + getLevelY());
            }
        }
        else
        {
                    moveHorizontally(xMove);

        moveVertically(yMove);
        }
        
        
        updateWorld();

        if (xMove > 0 && ((movementFrame = movementFrame % FRAMES_PER_PICTURE) == 0) && yMove == 0)
        {
            if (currentMovementFrame == 1)
            {
                currentMovementFrame = 0;
                // set image to image 1
                GreenfootImage image = new GreenfootImage("side1_64x23.png");
                image.mirrorHorizontally();
                setImage(image);
            }
            else if (currentMovementFrame == 0)
            {
                currentMovementFrame = 1;
                // set image to image 2
                GreenfootImage image = new GreenfootImage("side2_64x23.png");
                image.mirrorHorizontally();
                setImage(image);
            }
        }
        else if (xMove < 0 && ((movementFrame = movementFrame % FRAMES_PER_PICTURE) == 0) && yMove == 0)
        {
            if (currentMovementFrame == 1)
            {
                currentMovementFrame = 0;
                // set image to image 1
                GreenfootImage image = new GreenfootImage("side1_64x23.png");
                setImage(image);
            }
            else if (currentMovementFrame == 0)
            {
                currentMovementFrame = 1;
                // set image to image 2
                GreenfootImage image = new GreenfootImage("side2_64x23.png");
                setImage(image);
            }
        }
        else if (xMove == 0 && yMove == 0 && ((movementFrame = movementFrame % FRAMES_BEFORE_IDLE) == 0))
        {
            if (currentMovementFrame == 1)
            {
                currentMovementFrame = 0;
                // set image to image 1
                GreenfootImage image = new GreenfootImage("front1_64x23.png");
                setImage(image);
            }
            else if (currentMovementFrame == 0)
            {
                currentMovementFrame = 1;
                // set image to image 2
                GreenfootImage image = new GreenfootImage("front2_64x23.png");
                setImage(image);
            }
        }

        movementFrame++;
    }    
    
    /**
     * Lets the User adjust the web length.
     */
    private void adjustWebLength() {
        if(Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up")){
            if(webLength > WEB_LENGTH_CHANGE){
                webLength -= WEB_LENGTH_CHANGE;
            }
        } else if(Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down")){
            webLength += WEB_LENGTH_CHANGE;
        }
    }

    /**
     * Removes its webblob from this spider.
     */
    private void removeBlob(){
        blob = null;
        webLength = -1;
    }
    
    /**
     * If the web is taut, sets the speed accordingly.
     */
    private void calculateWebForce() {
        double nextX = getX()+xSpeed;
        double nextY = getY()+ySpeed;

        double nextDistance = Math.hypot(blob.getX()-nextX, blob.getY()-nextY);
        if(nextDistance>webLength){
            nextX = blob.getX()*(1-webLength/nextDistance)+nextX*(webLength/nextDistance);
            nextY = blob.getY()*(1-webLength/nextDistance)+nextY*(webLength/nextDistance);

            double remainingSpeed =  Math.hypot(xSpeed,  ySpeed) - Math.hypot(getX()-nextX, getY()-nextY);
            
            double prevAngle = Math.atan2(blob.getY()-getY(), blob.getX()-getX());
            double nextAngle = Math.atan2(blob.getY()-nextY, blob.getX()-nextX);
            
            double remainingAngle = remainingSpeed/webLength;
            
            if(prevAngle>nextAngle){
                nextAngle -= remainingAngle;
            } else {
                nextAngle += remainingAngle;
            }
            nextX = blob.getX()-Math.cos(nextAngle)*webLength;
            nextY = blob.getY()-Math.sin(nextAngle)*webLength;
            
            nextDistance = Math.hypot(blob.getX()-nextX, blob.getY()-nextY);
                
            xSpeed = nextX-getX();
            ySpeed = nextY-getY();
            
            if(Math.abs(xSpeed)<GRAVITY && Math.abs(blob.getX()-getX())<GRAVITY){
                xSpeed = blob.getX()-getX();
            }
        }
    }

    /**
     * Lowers the speed in x-direction by amount.
     */
    private void lowerXSpeed(double amount)
    {
        if (xSpeed > 0)
        {
            xSpeed -= amount;
            if (xSpeed < 0)
            {
                xSpeed = 0;
            }
        }
        else if (xSpeed < 0)
        {
            xSpeed += amount;
            if (xSpeed > 0)
            {
                xSpeed = 0;
            }
        }
    }

    /**
     * Updates the world. Centers the view around the spider and shifts the surrounding world accordingly.
     */
    public void updateWorld()
    {
        int dx = getX() - getWorld().getWidth() / 2;
        int dy = getY() - getWorld().getHeight() / 2;

        setLocation(getX() - dx, getY() - dy);
        ((Level) getWorld()).movePosition(dx, dy);
        ((Level) getWorld()).update();
    }

    /**
     * Moves the spider downwards by ySpeed pixels. Checks for collisions with platforms afterwards.
     */
    public void moveVertically(int ySpeed)
    {
        if (ySpeed != 0)
        {
            // the actual movement
            setLocation(getX(), getY() + ySpeed);

            if (ySpeed < 0)
            {
                // no longer on the ground
                ground = null;
            }

            List<Actor> intersecting = getIntersectingObjects(Actor.class);
            if (intersecting.size() > 0)
            {
                if (ySpeed > 0)
                {
                    // moving down
                    Platform ground = null;

                    // find the highest intersecting platform
                    for (Actor next : intersecting)
                    {
                        if (next instanceof Platform && (ground == null || next.getY() - next.getImage().getHeight() / 2 < ground.getY() - ground.getImage().getHeight() / 2))
                            ground = (Platform) next;
                    }

                    if (ground == null)
                    {
                        // no collision detected.
                        return;
                    }

                    // react to collision
                    setToGround(ground);
                }
                else
                {
                    // moving up
                    Platform ceiling = null;

                    // find the lowest intersecting platform
                    for (Actor next : intersecting)
                    {
                        if (next instanceof Platform && (ceiling == null || next.getY() + next.getImage().getHeight() / 2 > ceiling.getY() + ceiling.getImage().getHeight() / 2))
                            ceiling = (Platform) next;
                    }

                    if (ceiling == null)
                    {
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
     * Moves the spider right by xSpeed pixels. Checks for collisions with platforms afterwards.
     */
    public void moveHorizontally(int xSpeed)
    {
        if (xSpeed != 0)
        {
            // Check, if the spider has moved off its platform and is falling
            if (ground != null && (getX() - ground.getX() > (ground.getImage().getWidth() + getImage().getWidth()) / 2 || ground.getX() - getX() > (ground.getImage().getWidth() + getImage().getWidth()) / 2))
            {
                ground = null;
            }

            // the actual movement
            setLocation(getX() + xSpeed, getY());

            // collision detection
            List<Actor> intersecting = getIntersectingObjects(Actor.class);
            if (intersecting.size() > 0)
            {
                Platform wall = null;

                if (xSpeed > 0)
                {
                    // move right
                    // find the leftmost wall
                    for (Actor next : intersecting)
                    {
                        if (next instanceof Platform && ((wall == null || next.getX() - next.getImage().getWidth() / 2 < wall.getX() - wall.getImage().getWidth() / 2)))
                            wall = (Platform) next;
                    }

                    if (wall == null)
                    {
                        // no collision detected.
                        return;
                    }

                    // react to collision

                    if(ground==null || !trySetToGround(wall)){
                        setToRightWall(wall);
                    }
                }
                else
                {
                    // move left
                    // find the rightmost highest wall
                    for (Actor next : intersecting)
                    {

                        if (next instanceof Platform && ((wall == null || next.getX() + next.getImage().getWidth() / 2 < wall.getX() + wall.getImage().getWidth() / 2)))
                            wall = (Platform) next;
                    }

                    if (wall == null)
                    {
                        // no collision detected.
                        return;
                    }

                    // react to collision
                    
                    if(ground==null || !trySetToGround(wall)){
                        setToLeftWall(wall);
                    }
                }
            }
        }
    }
    
    /**
     * Places the spider above the given Actor if it does fit there. Does not move the spider horizontally. This also stops the spider from falling. When the spider is falling this is the only method that will stop it from falling.
     */
    public boolean trySetToGround(Actor ground)
    {
        if (ySpeed >= Y_SPEED_MAX)
        {
            Greenfoot.playSound("landing01.wav");
        }

        int yOld = getY();
        setLocation(getX(), ground.getY() - (ground.getImage().getHeight() + getImage().getHeight()) / 2 -1);
        if(!getIntersectingObjects(Platform.class).isEmpty()){
            setLocation(getX(), yOld);
            return false;
        } else {
            ySpeed = 0;
            this.ground = (Platform) ground;
            return true;
        }
    }

    /**
     * Places the spider above the given Actor. Does not move the spider horizontally. This also stops the spider from falling. When the spider is falling this is the only method that will stop it from falling.
     */
    public void setToGround(Actor ground)
    {
        if (ySpeed >= Y_SPEED_MAX)
        {
            Greenfoot.playSound("landing01.wav");
        }

        ySpeed = 0;
        this.ground = (Platform) ground;
        setLocation(getX(), ground.getY() - (ground.getImage().getHeight() + getImage().getHeight()) / 2 -1);
    }

    /**
     * Places the spider below the given Actor. Does not move the spider horizontally.
     */
    public void setToCeiling(Actor ceiling)
    {
        ySpeed = 0;
        setLocation(getX(), ceiling.getY() + (ceiling.getImage().getHeight() + getImage().getHeight()) / 2);
    }

    /**
     * Places the spider left of the given Actor. Does not move the spider vertically.
     */
    public void setToRightWall(Actor wall)
    {
        setLocation(wall.getX() - (wall.getImage().getWidth() + getImage().getWidth()) / 2 - 1, getY());
    }

    /**
     * Places the spider right of the given Actor. Does not move the spider vertically.
     */
    public void setToLeftWall(Actor wall)
    {
        setLocation(wall.getX() + (wall.getImage().getWidth() + getImage().getWidth()) / 2, getY());
    }

    // TODO
    public boolean isDead()
    {
        return ((Level) getWorld()).hasSpiderFallen() || healthPoints <= 0;
    }
    
    public int getHealth()
    {
      return healthPoints;  
    }
    
    public void decreaseHealth(int count)
    {
        this.healthPoints -= count;
        if(this.healthPoints <= 0)
        {
            Greenfoot.setWorld(new StartLevel());
            return;
        }
        
    }
    
    public int getDamage()
    {
        return damage;
    }

    
    public int getHitInterval()
    {
        return hitInterval;
    }
    
    public void knockback(int x, int y)
    {
        int spiderX = getLevelX();
        int spiderY = getLevelY();
        
        int diffX = x - spiderX;
        int diffY = y - spiderY;
        
        
        knockbackX = -diffX / 5;
        knockbackY = -diffY / 2;

        knockbackCounter = 10;
    }
    
    public int getLevelX()
    {
        Level l = getCurrentLevel();
        return l.getXPosition() + l.getWidth() / 2;
    }
    
    public int getLevelY()
    {
        Level l = getCurrentLevel();
        return l.getYPosition() + l.getHeight() / 2;
    }
    
    public Level getCurrentLevel()
    {
        return (Level) getWorld();
    }
}