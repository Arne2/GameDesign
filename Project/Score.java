import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.sun.javafx.geom.transform.GeneralTransform3D;


public class Score {
	public static final double SCORE_PER_WEB = 0.1;
	
	private final String saveName;
	
	private final int consumableNumber;
	private final int consumableScore;
	private final int enemyNumber;
	
	private int consumableLeftNumber;
	private int consumableLeftScore;
	private int enemyLeftNumber;
	
	private int leftWeb;
	
	private int deaths;
	private int frames;
	
	public Score(String saveName, int consumableNumber, int consumableScore, int enemyNumber) {
		this.saveName = saveName;
		this.consumableNumber = consumableNumber;
		this.consumableScore = consumableScore;
		this.enemyNumber = enemyNumber;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void sincreaseDeaths(int deaths) {
		this.deaths += deaths;
	}
	
	public int getFrames() {
		return frames;
	}
	
	public void increaseFrames(int frames) {
		this.frames += frames;
	}
	
	public int getConsumableNumber() {
		return consumableNumber;
	}
	
	public int getConsumableScore() {
		return consumableScore;
	}
	
	public int getEnemyNumber() {
		return enemyNumber;
	}

	public int getConsumableLeftNumber() {
		return consumableLeftNumber;
	}

	public void setConsumableLeftNumber(int consumableLeftNumber) {
		this.consumableLeftNumber = consumableLeftNumber;
	}

	public int getConsumableLeftScore() {
		return consumableLeftScore;
	}

	public void setConsumableLeftScore(int consumableLeftScore) {
		this.consumableLeftScore = consumableLeftScore;
	}

	public int getEnemyLeftNumber() {
		return enemyLeftNumber;
	}

	public void setEnemyLeftNumber(int enemyLeftNumber) {
		this.enemyLeftNumber = enemyLeftNumber;
	}

	public int getLeftWeb() {
		return leftWeb;
	}

	public void setLeftWeb(int leftWeb) {
		this.leftWeb = leftWeb;
	}

	public String getSaveName() {
		return saveName;
	}
	
	private int calculateScore(){
		int score = 0;
		
		score = getConsumableScore()-getConsumableLeftScore();
    	
    	score += getLeftWeb()*SCORE_PER_WEB;
    	
    	score /= getDeaths()+1;
    	
    	return score;
	}
	
	public void save(){
		int score = calculateScore();
    	
    	int oldScore = load();
    	
    	if(score>oldScore){
    		write(score);
    	}
	}
	
	public int load(){
		try (BufferedReader reader = new BufferedReader(new FileReader("splorrt.scores."+getClass().getName())))
		{
			return Integer.valueOf(reader.readLine());
		}
		catch (FileNotFoundException e)
		{
			return 0;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	private void write(int score){
		try (PrintWriter pw = new PrintWriter(new FileWriter("splorrt.scores."+getClass().getName())))
		{
			pw.println(score);
			pw.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "Took "+getFrames()+" ticks to complete the Level.\n";
		s += "Died "+getDeaths()+" times.\n";
		s += "Collected consumables worth "+(getConsumableScore()-getConsumableLeftScore())+". ("+getConsumableScore()+" available)\n";
		s += "Collected "+(getConsumableNumber()-getConsumableLeftNumber())+" consumables. ("+getConsumableNumber()+" available)\n";
		s += "Killed "+(getEnemyNumber()-getEnemyLeftNumber())+" enemies. ("+getEnemyNumber()+" available)\n";
		return s;
	}
}
