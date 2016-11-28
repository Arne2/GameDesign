import java.awt.Color;
import java.util.List;


// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import greenfoot.World;

/**
 * Write a description of class Spider here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spider extends Actor implements IDamageable
{
    private static boolean      DEBUG               = false;

    /** The max Health of the spider. */
    private static final int MAX_HEALTH = 5;
    
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
    private final WebBar        webBar              = new WebBar(1000, 1000);

    public static final int     ENEMY_STUN_COST     = 50;
    public static final double  WEB_COST_PER_LENGTH = 0.5;

    private static final int    FRAMES_PER_PICTURE_MOVE = 8;
    private static final int    FRAMES_BEFORE_IDLE      = 24;
    private static final int    FRAMES_PER_PICTURE_IDLE = 24;
    private int                 idleFrames          = 0;        
    private final AnimationSet  images              = new AnimationSet(FRAMES_PER_PICTURE_IDLE);

    private static final double WEB_LENGTH_CHANGE   = 4;

    private final Bar           healthBar           = new Bar("Health", "", MAX_HEALTH, MAX_HEALTH);

    private int                 damage              = 1;

    /** After how many ticks can the spider attack again. */
    private int                 hitInterval         = 100;

    private int                 knockbackX          = 0;

    private int                 knockbackY          = 0;

    private int                 knockbackCounter    = 0;

    public Spider()
    {
        loadKeybinds();

        healthBar.setTextColor(Color.WHITE);
        healthBar.setSafeColor(Color.RED);
        healthBar.setDangerColor(Color.YELLOW);
        healthBar.setBreakValue(2);
    
        images.addImage(new GreenfootImage("front1_64x23.png"));
        images.addImage(new GreenfootImage("front2_64x23.png"));
        
        // for front view as start image
        GreenfootImage right1 = new GreenfootImage("side1_64x23.png");
        right1.mirrorHorizontally();
        GreenfootImage right2 = new GreenfootImage("side2_64x23.png");
        right2.mirrorHorizontally();
        
        images.useSet("right");
        images.setFramesPerImage(FRAMES_PER_PICTURE_MOVE);
        images.addImage(right1);
        images.addImage(right2);
        
        images.useSet("left");
        images.setFramesPerImage(FRAMES_PER_PICTURE_MOVE);
        images.addImage(new GreenfootImage("side1_64x23.png"));
        images.addImage(new GreenfootImage("side2_64x23.png"));
        
        images.useSet(AnimationSet.DEFAULT_SET);
        setImage(images.getImage());
    }

    private void loadKeybinds()
    {
        // if we want to load keybinds from file
        // TODO: load keybinds from file
    }

    /**
     * Act - do whatever the Spider wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        // TODO: implement death and game-over mechanic.
        if (isDead())
        {
            ySpeed = 0;
            // Game mechanic won't work this way. It leaves all items like music intact and unaccessible.
            World world = getWorld();
            if (world != null && world instanceof SplorrtWorld)
            {
                ((SplorrtWorld) world).loadWorld(SplorrtWorld.getWorld(EndScreen.class));
            }
            return;
        }

        // --- INPUTS
        // horizontal movement

        if (isKeyPressed(Keybind.MOVE_RIGHT))
        {
            if (xSpeed < X_SPEED_MAX)
            {
                xSpeed = Math.min(X_SPEED_MAX, xSpeed + X_SPEED_MAX);
            }
        }
        else if (isKeyPressed(Keybind.MOVE_LEFT))
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
        if (isKeyPressed(Keybind.JUMP))
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

        webMechanics();

        int xMove = (int) (xSpeed + xMoveRemaining);
        int yMove = (int) (ySpeed + yMoveRemaining);
        xMoveRemaining = (xSpeed + xMoveRemaining) - xMove;
        yMoveRemaining = (ySpeed + yMoveRemaining) - yMove;

        // --- MOVEMENT

        if (knockbackCounter > 0)
        {
            if (DEBUG)
            {
                System.out.println(getLevelX() + " " + getLevelY());
            }
            moveHorizontally(knockbackX);
            moveVertically(knockbackY);
            knockbackCounter--;
            if (DEBUG)
            {
                System.out.println(getLevelX() + " " + getLevelY());
            }
        }
        else
        {
            moveHorizontally(xMove);

            moveVertically(yMove);
        }

        updateWorld();
        
        animate();
    }
    
    private void animate() {
        if(xSpeed > 1)
        {
            images.useSet("right");
            idleFrames = 0;
        }
        else if (xSpeed<-1)
        {
            images.useSet("left");
            idleFrames = 0;
        } 
        else
        {
            if(idleFrames>FRAMES_BEFORE_IDLE){
                images.useSet(AnimationSet.DEFAULT_SET);
            } else {
                idleFrames++;
            }
        }
        
        images.next();
        if(images.hasChanged()){
            setImage(images.getImage());
        }
    }

    private void webMechanics()
    {
        // web
        MouseInfo mi = Greenfoot.getMouseInfo();
        Platform mouseOverP = mi != null ? getWorld().getObjectsAt(mi.getX(), mi.getY(), Platform.class).stream().findFirst().orElse(null) : null;
        Enemy mouseOverE = mi != null ? getWorld().getObjectsAt(mi.getX(), mi.getY(), Enemy.class).stream().findFirst().orElse(null) : null;

        if (mouseOverP != null)
        {
            int distance = (int) Math.hypot(getX() - mouseOverP.getX(), getY() - mouseOverP.getY());
            webBar.setPreviewDelta((int) (distance * WEB_COST_PER_LENGTH));
        }
        else if (mouseOverE != null)
        {
            webBar.setPreviewDelta(ENEMY_STUN_COST);
        }
        else
        {
            webBar.setPreviewDelta(0);
        }

        if (blob != null)
        {
            if (isKeyPressed(Keybind.CANCEL_WEB))
            {
                // clicking again removes blob
                ((Level) getWorld()).removeLevelActor(blob);
                removeBlob();
            }
            else if (blob.getWorld() == null)
            {
                // if the blob reached the edge, or an enemy it will remove itself from the world.
                removeBlob();
            }
            else if (blob.isStationary() && webLength < 0)
            {
                double distance = Math.hypot((double) (getX() - blob.getX()), (double) (getY() - blob.getY()));

                // cost to connect
                int cost = (int) (distance * WEB_COST_PER_LENGTH);
                if (cost <= webBar.getValue())
                {
                    // set webLength -> swing around blob.
                    webBar.subtract(cost);
                    webLength = (int) distance + 5;
                }
                else
                {
                    webBar.flash(20);
                    removeBlob();
                }
            }
            else if (webLength > 0)
            {
                adjustWebLength();
                calculateWebForce();
            }
        }
        else if (isKeyPressed(Keybind.SHOOT))
        {
            // shoot a new blob
            blob = new WebBlob(25, damage);

            ((Level) getWorld()).addLevelActor(blob, getX(), getY());
            ((Level) getWorld()).addLevelActor(new WebString(this, blob), getX(), getY());
            blob.turnTowards(mi.getX(), mi.getY());
        }
    }

    /**
     * Lets the User adjust the web length.
     */
    private void adjustWebLength()
    {
        if (isKeyPressed(Keybind.PULL_UP))
        {
            if (webLength > WEB_LENGTH_CHANGE)
            {
                webLength -= WEB_LENGTH_CHANGE;
            }
        }
        else if (isKeyPressed(Keybind.ROPE_DOWN))
        {
            webLength += WEB_LENGTH_CHANGE;
        }
    }

    /**
     * Removes its webblob from this spider.
     */
    private void removeBlob()
    {
        if(blob.getWorld()!=null){
            ((Level)getWorld()).removeLevelActor(blob);
        }
        
        blob = null;
        webLength = -1;
    }

    /**
     * If the web is taut, sets the speed accordingly.
     */
    private void calculateWebForce()
    {
        double nextX = getX() + xSpeed;
        double nextY = getY() + ySpeed;

        double nextDistance = Math.hypot(blob.getX() - nextX, blob.getY() - nextY);
        if (nextDistance > webLength)
        {
            nextX = blob.getX() * (1 - webLength / nextDistance) + nextX * (webLength / nextDistance);
            nextY = blob.getY() * (1 - webLength / nextDistance) + nextY * (webLength / nextDistance);

            double remainingSpeed = Math.hypot(xSpeed, ySpeed) - Math.hypot(getX() - nextX, getY() - nextY);

            double prevAngle = Math.atan2(blob.getY() - getY(), blob.getX() - getX());
            double nextAngle = Math.atan2(blob.getY() - nextY, blob.getX() - nextX);

            double remainingAngle = remainingSpeed / webLength;

            if (prevAngle > nextAngle)
            {
                nextAngle -= remainingAngle;
            }
            else
            {
                nextAngle += remainingAngle;
            }
            nextX = blob.getX() - Math.cos(nextAngle) * webLength;
            nextY = blob.getY() - Math.sin(nextAngle) * webLength;

            nextDistance = Math.hypot(blob.getX() - nextX, blob.getY() - nextY);

            xSpeed = nextX - getX();
            ySpeed = nextY - getY();

            if (Math.abs(xSpeed) < GRAVITY && Math.abs(blob.getX() - getX()) < GRAVITY)
            {
                xSpeed = blob.getX() - getX();
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

                    if (ground == null || !trySetToGround(wall))
                    {
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

                    if (ground == null || !trySetToGround(wall))
                    {
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
        setLocation(getX(), ground.getY() - (ground.getImage().getHeight() + getImage().getHeight()) / 2 - 1);
        if (!getIntersectingObjects(Platform.class).isEmpty())
        {
            setLocation(getX(), yOld);
            return false;
        }
        else
        {
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
        setLocation(getX(), ground.getY() - (ground.getImage().getHeight() + getImage().getHeight()) / 2 - 1);
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
    @Override
    public boolean isDead()
    {
        return ((Level) getWorld()).hasSpiderFallen() || healthBar.getValue() <= 0;
    }

    @Override
    public int getHealth()
    {
        return healthBar.getValue();
    }

    @Override
    public void decreaseHealth(int count)
    {
        this.healthBar.subtract(count);
    }

    @Override
    public void decreaseHealth()
    {
        int h = this.healthBar.getValue() - 1;
        this.healthBar.subtract(h);
    }
    
    @Override
    public void setHealth(int h)
    {
        this.healthBar.setValue(h);
    }
    
    @Override
    public void instantKill()
    {
        setHealth(0);
    }
    
    @Override
    public int getMaxHealth()
    {
        return MAX_HEALTH;
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

    public WebBar getWebBar()
    {
        return webBar;
    }

    public Bar getHealthBar()
    {
        return healthBar;
    }

    public boolean isKeyPressed(Keybind key)
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        if ((key.isMouseAction() && mi != null && Greenfoot.mousePressed(null) && mi.getButton() == key.getMouseButton()) || (!key.isMouseAction() && Greenfoot.isKeyDown(key.getKey())))
            return true;
        return false;
    }
}