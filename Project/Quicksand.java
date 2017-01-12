import greenfoot.GreenfootImage;

public class Quicksand extends Enemy
{
	private AnimationSet images = new AnimationSet(20);
	

	
	public Quicksand(int x, int y)
	{
		super(x, y, false, false);
		setImage("Quicksand1(32x32).png");
		images.addImage(new GreenfootImage("Quicksand2(32x32).png"));
		images.addImage(new GreenfootImage("Quicksand3(32x32).png"));
	}
	
	@Override
	public void act() {
		super.act();
		images.next();
		if(images.hasChanged()){
            setImage(images.getImage());
        }
	}
	
	@Override
	public void damageSpider(Spider s)
	{
	    s.instantKill();
	}
}
