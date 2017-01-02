import greenfoot.GreenfootImage;

/**
 * Write a description of class EnemyWasp here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyWasp extends ProjectileEnemy{

    private static final int DAMAGE = 2;
    
    private static final int MAX_HEALTH = 3;
    
    private static final int MOVEMENT_SPEED = 1;
    
    private static final int FRAMES_PER_PICTURE = 5;
    
    private static final int SIGHT_RADIUS = 500;
    
    private int ticksShowHit    = -1;
    
    private int targetX;
    
    private int targetY;

    private int movementSpeed = MOVEMENT_SPEED;
    
    private AnimationSet images;

    private final AnimationSet imagesLeft;
    
    private final AnimationSet imagesRight;
    
    private boolean moveable;
    
    private boolean shootable;
    
    public EnemyWasp(int x, int y)
    {
     this(x,y,false, false);   
    }
    
    public EnemyWasp(int x, int y, boolean moveable, boolean shootable)
    {
        super(x, y, true, true, new Consumable(Consumable.Type.WASP, x, y));

        imagesLeft = new AnimationSet(FRAMES_PER_PICTURE);
        imagesRight = new AnimationSet(FRAMES_PER_PICTURE);
        
        images = imagesLeft;
        
        imagesLeft.addImage(new GreenfootImage("wasp1_64_left.png"));
        imagesLeft.addImage(new GreenfootImage("wasp2_64_left.png"));
        
        imagesRight.addImage(new GreenfootImage("wasp1_64_right.png"));
        imagesRight.addImage(new GreenfootImage("wasp2_64_right.png"));
        
        setDamage(DAMAGE);
        setHealth(MAX_HEALTH);
        setImage("wasp1_64_left.png");
        setSightRadius(SIGHT_RADIUS);
        
        targetX = x;
        targetY = y;
        
        this.moveable = moveable;
        this.shootable = shootable;
    }

    
    @Override
    public int getMaxHealth()
    {
        return MAX_HEALTH;
    }
    
    /**
     * Act - do whatever the EnemyWasp wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
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
                images.addImageForFrames(new GreenfootImage("wasp_stunned_64_left.png"), 100, false);
            else
                images.addImageForFrames(new GreenfootImage("wasp_stunned_64_right.png"), 100, false);
        }
    }

    @Override
    public void onDamaged()
    {
        if(getFacingDirection() == Direction.LEFT)
            images.addImageForFrames(new GreenfootImage("wasp_hit_64_left.png"), 40, false);
        else
            images.addImageForFrames(new GreenfootImage("wasp_hit_64_right.png"), 40, false); 
    }

    private void animate()
    {
        images.next();
        
        if(images.hasChanged()){
            setImage(images.getImage());
        }
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
        int y = getLevelY();
        
        int dX = targetX - x;
        int dY = targetY - y;
        
        int offset = movementSpeed * 2;
        
        if(Math.abs(dX) > offset)
        {
            int mulX = 1;
            if(dX < 0)
                mulX = -1;
                
            int mX = mulX * movementSpeed;
            setLevelX(getLevelX() + mX);

        }

        if(Math.abs(dY) > offset)
        {
            int mulY = 1;
            if(dY < 0)
                mulY = -1;
                
            int mY = mulY * movementSpeed;
            setLevelY(getLevelY() + mY);

        }


    }
    
    private void checkSpiderTarget()
    {
        Spider s = ((Level)getWorld()).getSpider();
        targetX = s.getLevelX();
        targetY = s.getLevelY();    
        
        Level l = (Level) getWorld();
        
    }
  
    @Override
    public Projectile getProjectile()
    {
        return new WaspProjectile(this);
    }
    
    @Override
    public boolean canShoot()
    {
        return shootable;
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
