/**
 * Write a description of class DeathField here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class DeathField extends Enemy
{

	public DeathField(int x, int y)
	{
		super(x, y, false, false);
		setImage("empty.png");
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
