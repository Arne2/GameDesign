import greenfoot.Actor;

public class WebBlob extends LevelActor
{

	private final int	speed;
	private boolean		stationary	= false;
	private final int	damage;

	public WebBlob(int speed, int damage)
	{
		this.speed = speed;
		this.damage = damage;
		setImage("web.png");
	}

	@Override
	public void act()
	{
		if (!stationary)
		{
			move(speed);

			Actor platform = getOneObjectAtOffset(0, 0, Platform.class);
			if (platform != null)
			{
				 if(((Platform)platform).getType().isSticky()){
                    stationary = true;
                } else {
                     ((Level)getWorld()).removeLevelActor(this);
                     return;
                }
			}

			Actor enemyActor = getOneObjectAtOffset(0, 0, Enemy.class);
			if (enemyActor != null && enemyActor instanceof Enemy)
			{
				WebBar bar = ((Level) getWorld()).getSpider().getWebBar();
				if (bar.getValue() >= Spider.ENEMY_STUN_COST)
				{
					if(!Setting.isHaungsMode())
						bar.subtract(Spider.ENEMY_STUN_COST);

					Enemy enemy = (Enemy) enemyActor;
					enemy.setStunned();
					enemy.decreaseHealth(damage);
				}
				else
				{
					bar.flash(20);
				}

				((Level) getWorld()).removeLevelActor(this);
				return;
			}
		}

		if (isAtEdge())
		{
			((Level) getWorld()).removeLevelActor(this);
			return;
		}
		updatePosition();
	}

	public boolean isStationary()
	{
		return stationary;
	}
}
