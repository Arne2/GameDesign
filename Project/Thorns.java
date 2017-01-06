/**
 * Write a description of class Spikes here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Thorns extends Enemy
{

    public Thorns(int x, int y, boolean reverse)
    {
        super(x, y, false, false);
        if(reverse){
            setImage("Thorns2(32x32).png");
        } else {
            setImage("Thorns(32x32).png");
        }
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
