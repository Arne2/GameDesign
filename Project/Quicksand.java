public class Quicksand extends Enemy
{

	public Quicksand(int x, int y)
	{
		super(x, y, false, false);
		setImage("Quicksand1(32x32).png");
	}
	
	@Override
	public void act() {
		super.act();
	}
	
	@Override
	public void damageSpider(Spider s)
	{
	    s.instantKill();
	}
}
