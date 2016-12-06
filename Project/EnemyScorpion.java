import greenfoot.GreenfootImage;

/**
 * Write a description of class EnemyScorpion here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyScorpion extends ProjectileEnemy
{
    private static final int MAX_HEALTH = 5;
   
    private static final int FRAMES_PER_PICTURE = 25;
    
    private static final int DAMAGE = 2;
        
    private final AnimationSet  images = new AnimationSet(FRAMES_PER_PICTURE);

        
	public EnemyScorpion(int x, int y)
	{
		super(x, y, true, true, new Consumable(Consumable.Type.SCORPION, x, y));

		setImage("scorp1.png");
		
		images.addImage(new GreenfootImage("scorp1.png"));
        images.addImage(new GreenfootImage("scorp2.png"));
        
		setDamage(DAMAGE);
		setHealth(MAX_HEALTH);
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
	}
	
	@Override
    public void setStunned()
    {
        if(!isStunned()){
            super.setStunned();
            images.addImageForFrames(new GreenfootImage("scorp_stunned.png"), 100, false);
        }
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
}
