import greenfoot.GreenfootImage;
import greenfoot.Actor;

/**
 * Write a description of class EnemyScorpion here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyScorpion extends ProjectileEnemy implements IMoveable
{
    private static final int MAX_HEALTH = 5;
   
    private static final int FRAMES_PER_PICTURE = 25;
    
    private static final int MOVEMENT_SPEED = 1;
    
    private static final int DAMAGE = 2;
        
    private AnimationSet images;

    private final AnimationSet imagesLeft;
    
    private final AnimationSet imagesRight;
    
    private boolean moveable;
    
    private boolean shootable;
        
    private int targetX;
    
    private int movementSpeed = MOVEMENT_SPEED;
    
    private MovementHelper movementHelper;
    
    public EnemyScorpion(int x, int y)
    {
        this(x,y, false, false);
    }
    
    public EnemyScorpion(int x, int y, boolean moveable, boolean shootable)
    {
        super(x, y, true, true, new Consumable(Consumable.Type.SCORPION, x, y));

        imagesLeft = new AnimationSet(FRAMES_PER_PICTURE);
        imagesRight = new AnimationSet(FRAMES_PER_PICTURE);
        
        images = imagesLeft;
        
        setImage("scorp1_left.png");
        
        imagesLeft.addImage(new GreenfootImage("scorp1_left.png"));
        imagesLeft.addImage(new GreenfootImage("scorp2_left.png"));
        
        imagesRight.addImage(new GreenfootImage("scorp1_right.png"));
        imagesRight.addImage(new GreenfootImage("scorp2_right.png"));
        
        setDamage(DAMAGE);
        setHealth(MAX_HEALTH);
        
        this.moveable = moveable;
        this.shootable = shootable;
        
        movementHelper = new MovementHelper(this);
    }
    
    @Override
    public int getMaxHealth()
    {
        return MAX_HEALTH;
       }

    /**
     * Act - do whatever the EnemyScorpion wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        animate();
        movement();
    }
    
    @Override
    public void setStunned()
    {
        if(!isStunned())
        {
            super.setStunned();
            if(getFacingDirection() == Direction.LEFT)
                images.addImageForFrames(new GreenfootImage("scorp_stunned_left.png"), 100, false);
            else
                images.addImageForFrames(new GreenfootImage("scorp_stunned_right.png"), 100, false);
        }
    }

    @Override
    public void onDamaged()
    {
        if(getFacingDirection() == Direction.LEFT)
            images.addImageForFrames(new GreenfootImage("scorp_hit_left.png"), 40, false);
        else
            images.addImageForFrames(new GreenfootImage("scorp_hit_right.png"), 40, false); 
    }

    private void animate()
    {
        images.next();
        
        if(images.hasChanged()){
            setImage(images.getImage());
        }
    }
    
    @Override
    public Projectile getProjectile()
    {
        return new ScorpionProjectile(this);
    }
    
    @Override
    public boolean canShoot()
    {
        return shootable;
    }
    
    private void movement()
    {
       
        if(moveable && !isStunned() && canSeeSpider())
        {
            checkSpiderTarget();
            move();
        }
    }
    
    
    private void move()
    {
        
        int x = getLevelX();
        
        int dX = targetX - x;
        
        int offset = movementSpeed * 2;
        
        
        if(Math.abs(dX) > offset)
        {
            int mulX = 1;
            if(dX < 0)
                mulX = -1;
                
            int mX = mulX * movementSpeed;
            
            movementHelper.moveX(mX);
        }



    }
    
    private void checkSpiderTarget()
    {
        Spider s = ((Level)getWorld()).getSpider();
        targetX = s.getLevelX();
    
        
        
    }
    
    public void moveTo(int x, int y)
    {
       moveToX(x);
       moveToY(y);
        
    }
    
    public void moveToX(int x)
    {
       super.setLevelX(x);
    }
    
    public void moveToY(int y)
    {
        super.setLevelY(y);
    }
    
    
    public <A> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls)
    {
        return super.getIntersectingObjects(cls);
    }
    
    public Actor getOneObjectAtOffset(int dx,
                                     int dy,
                                     java.lang.Class<?> cls)
     {
           return super.getOneObjectAtOffset(dx, dy, cls);
        }
                                     
    public int getLevelX()
    {
        return super.getLevelX();
    }
    
    public int getLevelY()
    {
        return super.getLevelY();
    }
    
    public int getWidth()
    {
        return getImage().getWidth();
    }
    
    public int getHeight()
    {
        return getImage().getHeight();
    }
    
    public <A> java.util.List<A>    getObjectsAtOffset(int dx, int dy, java.lang.Class<A> cls)
    {
        return super.getObjectsAtOffset(dx, dy, cls);
    }
    
    @Override
    public void onPlayerSee()
    {
        super.onPlayerSee();
        if(getFacingDirection() == Direction.LEFT)
            images = imagesLeft;
        else
            images = imagesRight;
    }
    

    
}
