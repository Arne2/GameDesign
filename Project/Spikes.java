/**
 * Write a description of class Spikes here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spikes extends Enemy
{

	public Spikes(int x, int y)
	{
		super(x, y, false, false);
		setImage("Spikes32x32.png");
	}
	
	@Override
	public void damageSpider(Spider s)
	{
	    s.instantKill();
	   }
}
