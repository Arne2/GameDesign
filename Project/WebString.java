import greenfoot.Actor;

public class WebString extends Web
{

	private Actor anchor1, anchor2;

	public WebString(Actor anchor1, Actor anchor2)
	{
		this.anchor1 = anchor1;
		this.anchor2 = anchor2;
		setImage("web.png");
	}

	@Override
	public void act()
	{
		if (anchor1.getWorld() == null || anchor2.getWorld() == null)
		{
			((Level) getWorld()).removeLevelActor(this);
			return;
		}

		super.act();
	}

	@Override
	protected int getX1()
	{
		return anchor1.getX();
	}

	@Override
	protected int getY1()
	{
		return anchor1.getY();
	}

	@Override
	protected int getX2()
	{
		return anchor2.getX();
	}

	@Override
	protected int getY2()
	{
		return anchor2.getY();
	}

	public Actor getAnchor1()
	{
		return anchor1;
	}

	public Actor getAnchor2()
	{
		return anchor2;
	}

	public void setAnchor1(Actor anchor1)
	{
		this.anchor1 = anchor1;
	}

	public void setAnchor2(Actor anchor2)
	{
		this.anchor2 = anchor2;
	}

}
