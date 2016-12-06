import java.awt.Color;

import greenfoot.GreenfootImage;


public class ScoreScreen extends InfoScreen {
	
	private final ScoreBar consumableBar;
	private final ScoreBar enemyBar;
	private final ScoreBar webBar;
	private final ScoreBar scoreBar;
	
	private final Label deathLabel;
	private final Label timeLabel;
	
	private final LevelRating rating;
	
	public static final int X_BASE = 600;
	public static final int Y_BASE = 200;
	
	public ScoreScreen(Score score, SplorrtWorld next) {
		super(new GreenfootImage("ScoreScreen.png"), next);
		
		this.consumableBar = new ScoreBar("Collected", score.getConsumableNumber()-score.getConsumableLeftNumber(), score.getConsumableNumber());
		this.enemyBar = new ScoreBar("Enemies  ", score.getEnemyNumber()-score.getEnemyLeftNumber(), score.getEnemyNumber());
		this.webBar = new ScoreBar("Web         ", score.getLeftWeb(), score.getMaxWeb());
		this.scoreBar = new ScoreBar("Score      ", score.getScore(), score.getMaxScore());
		
		this.deathLabel = new Label("Died "+score.getDeaths()+" times", 18);
		this.deathLabel.setFillColor(Color.BLACK);
		this.deathLabel.setLineColor(new Color(0,0,0,0));
		this.timeLabel = new Label("Took "+score.getFrames()+" frames", 18);
		this.timeLabel.setFillColor(Color.BLACK);
		this.timeLabel.setLineColor(new Color(0,0,0,0));
		
		this.rating = new LevelRating(score.getScore()/score.getScorePerStar()+1);
		
		this.addObject(deathLabel, X_BASE, Y_BASE);
		this.addObject(timeLabel, X_BASE, Y_BASE+30);
		
		this.addObject(consumableBar, X_BASE, Y_BASE+100);
		this.addObject(enemyBar, X_BASE, Y_BASE+150);
		this.addObject(webBar, X_BASE, Y_BASE+200);
		this.addObject(scoreBar, X_BASE, Y_BASE+250);
		this.addObject(rating, X_BASE, Y_BASE+320);
	}
	
}
