/**
 * Write a description of class EnemyScorpion here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyScorpion extends Enemy
{
	public EnemyScorpion(int x, int y)
	{
		super(x, y, true, true);
		// TODO: Set image, as soon as we have a scorpion image
		// setImage("scorpion.png");

		// replacement until image is available:
		setImage("skull.png");
	}

	/**
	 * Act - do whatever the EnemyScorpion wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
	 */
	public void act()
	{
		// Add your action code here.
	}
}
