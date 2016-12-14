import java.awt.Color;

import greenfoot.GreenfootImage;

public class ScoreScreen extends InfoScreen
{
	private final ScoreBar		collectibleBar;
	private final ScoreBar		enemyBar;

	private final ScoreBar		consumableBar;
	private final ScoreBar		webBar;
	private final ScoreBar		timeBar;
	private final ScoreBar		scoreBar;

	private final Label			separatorLabel;
	private final Label			deathLabel;

	private final LevelRating	rating;

	public static final int		X_BASE	= 600;
	public static final int		Y_BASE	= 250;

	public ScoreScreen()
	{
		this(new Score("", 0, 5, 4, 450, 3000), null);
	}
	
	public ScoreScreen(Score score, SplorrtWorld next)
	{
		super(new GreenfootImage("ScoreScreen.jpg"), next);

		this.enemyBar = new ScoreBar("Enemies      ", score.getEnemyNumber() - score.getEnemyLeftNumber(), score.getEnemyNumber());
		enemyBar.setDangerColor(Color.ORANGE);
		enemyBar.setSafeColor(Color.ORANGE);

		this.collectibleBar = new ScoreBar("Collectibles", score.getConsumableNumber() - score.getConsumableLeftNumber(), score.getConsumableNumber());
		collectibleBar.setDangerColor(Color.ORANGE);
		collectibleBar.setSafeColor(Color.ORANGE);

		this.consumableBar = new ScoreBar("Collected  ", score.getReachedConsumableScore(), score.getMaxConsumableScore());
		consumableBar.setDangerColor(Color.ORANGE);
		consumableBar.setSafeColor(Color.ORANGE);
		this.webBar = new ScoreBar("Web          ", score.getReachedWebScore(), score.getMaxWebScore());
		webBar.setDangerColor(Color.LIGHT_GRAY);
		webBar.setSafeColor(Color.LIGHT_GRAY);
		this.timeBar = new ScoreBar("Time          ", score.getReachedTimeScore(), score.getMaxTimeScore());
		timeBar.setDangerColor(Color.YELLOW);
		timeBar.setSafeColor(Color.YELLOW);

		this.scoreBar = new ScoreBar("Score        ", score.getScore(), score.getMaxScore());
		scoreBar.setSafeColor(Color.GREEN);
		scoreBar.setDangerColor(Color.GREEN);

		this.separatorLabel = new Label("Additional information:", 18);
		this.separatorLabel.setFillColor(Color.BLACK);
		this.separatorLabel.setLineColor(new Color(0, 0, 0, 0));

		this.deathLabel = new Label("Died " + score.getDeaths() + " times", 18);
		this.deathLabel.setFillColor(Color.BLACK);
		this.deathLabel.setLineColor(new Color(0, 0, 0, 0));

		this.rating = new LevelRating(score.getScore() / score.getScorePerStar() + 1);

		this.addObject(rating, X_BASE, Y_BASE);
		this.addObject(scoreBar, X_BASE, Y_BASE + 80);
		this.addObject(consumableBar, X_BASE, Y_BASE + 110);
		this.addObject(webBar, X_BASE, Y_BASE + 140);
		this.addObject(timeBar, X_BASE, Y_BASE + 170);

		this.addObject(separatorLabel, X_BASE, Y_BASE + 250);
		
		this.addObject(deathLabel, X_BASE, Y_BASE+280);
		this.addObject(enemyBar, X_BASE, Y_BASE + 310);
		this.addObject(collectibleBar, X_BASE, Y_BASE + 340);
	}

}
