import greenfoot.GreenfootImage;

/**
 * Write a description of class EnemyWasp here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyWasp extends Enemy {

	private int	ticksShowHit	= -1;
	
	
	private static final int   	FRAMES_PER_PICTURE	= 5;
	private final SetAnimation 	images = new SetAnimation(FRAMES_PER_PICTURE);

	public EnemyWasp(int x, int y)
	{
		super(x, y, true, true);

		images.addImage(new GreenfootImage("wasp1_64.png"));
		images.addImage(new GreenfootImage("wasp2_64.png"));
		images.addImage("stunned", new GreenfootImage("wasp_stunned_64.png"));
		
		setHealthPoints(3);
		setImage("wasp1_64.png");
	}

	/**
	 * Act - do whatever the EnemyWasp wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
	 */
	public void act()
	{
		super.act();

		animate();
	}

	@Override
	public void setStunned()
	{
		super.setStunned();
		images.useSet("stunned");
	}

	@Override
	public void onDamaged()
	{
		images.addImageForFrames(new GreenfootImage("wasp_hit_64.png"), 40);
	}

	private void animate()
	{
		if (!isStunned()) {
			images.useSet(SetAnimation.DEFAULT_SET);
		}
		
		images.next();
		
		if(images.hasChanged()){
			setImage(images.getImage());
		}
	}

}
