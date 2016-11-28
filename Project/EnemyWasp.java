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
    
    private static final int FRAMES_PER_PICTURE = 5;
    
    private static final int SIGHT_RADIUS = 500;
    
    private int ticksShowHit    = -1;
    
    private int targetX;
    
    private int targetY;

    private int movementSpeed = 2;
    
    private final AnimationSet  images = new AnimationSet(FRAMES_PER_PICTURE);

    private boolean moveable;
    
    public EnemyWasp(int x, int y)
    {
     this(x,y,false);   
    }
    
    public EnemyWasp(int x, int y, boolean moveable)
    {
        super(x, y, true, true, new Consumable(Consumable.Type.WASP, x, y));

        images.addImage(new GreenfootImage("wasp1_64.png"));
        images.addImage(new GreenfootImage("wasp2_64.png"));
        
        setDamage(DAMAGE);
        setHealth(MAX_HEALTH);
        setImage("wasp1_64.png");
        setSightRadius(SIGHT_RADIUS);
        
        targetX = x;
        targetY = y;
        
        this.moveable = moveable;
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
        if(!isStunned()){
            super.setStunned();
            images.addImageForFrames(new GreenfootImage("wasp_stunned_64.png"), 100, false);
        }
    }

    @Override
    public void onDamaged()
    {
        images.addImageForFrames(new GreenfootImage("wasp_hit_64.png"), 40, false);
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
        if(moveable && !isStunned() && isSpiderInSight())
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
  

}
