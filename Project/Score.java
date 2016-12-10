import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Score {
	public static final double SCORE_PER_WEB = 0.1;
	
	private final String saveName;
	
	private final int consumableNumber;
	private final int consumableScore;
	private final int enemyNumber;
	private final int maxWeb;
	private final int fiveStarTime;
	private final int scorePerStar = 20;
	
	private int consumableLeftNumber;
	private int consumableLeftScore;
	private int enemyLeftNumber;
	private int leftWeb;
	
	private int deaths;
	private int frames;

	private int collectibleScore = 60;
	private int webScore = 30;
	private int timeScore = 10;
	
	public Score(String saveName, int consumableNumber, int consumableScore, int enemyNumber, int maxWeb, int bestTime) {
		this.saveName = saveName;
		this.consumableNumber = consumableNumber;
		this.consumableScore = consumableScore;
		this.enemyNumber = enemyNumber;
		this.maxWeb = maxWeb;
		this.fiveStarTime = bestTime;
		
		this.consumableLeftNumber = consumableNumber;
		this.consumableLeftScore = consumableScore;
		this.enemyLeftNumber = enemyNumber;
	}
	
	public int getScorePerStar() {
		return scorePerStar;
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
	
	public int getMaxWeb() {
		return maxWeb;
	}
	
	public int getMaxWebScore(){
		return webScore;
	}
	
	public int getReachedWebScore(){
		return (int) (webScore * (getLeftWeb()/(float)getMaxWeb()));
	}
	
	public int getMaxTimeScore(){
		return timeScore;
	}
	
	public int getReachedTimeScore(){
		return Math.min(timeScore, (int) (timeScore * (1/(frames/(float)fiveStarTime))));
	}
	
	public int getMaxConsumableScore(){
		return collectibleScore;
	}
	
	public int getReachedConsumableScore(){
		return (int) (collectibleScore * ((getConsumableScore() - getConsumableLeftScore())/(float)getConsumableScore()));
	}

	public int getScore(){
		return getReachedConsumableScore()+getReachedTimeScore()+getReachedWebScore();
//		return (int)(getConsumableScore() - getConsumableLeftScore() + getLeftWeb()*SCORE_PER_WEB) / (getDeaths()+1);
	}
	
	public int getMaxScore(){
		return collectibleScore+webScore+timeScore;
	}
	
	public void save(){
		int score = getScore();
    	
    	int oldScore = load();
    	
    	if(score>oldScore){
    		write(score);
    	}
	}
	
	public int load(){
		try (BufferedReader reader = new BufferedReader(new FileReader("splorrt.scores."+saveName)))
		{
			return Integer.valueOf(reader.readLine());
		}
		catch (NumberFormatException | FileNotFoundException e)
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
	
	public static int loadStars(Level level){
		return loadStars(level.getClass().getName());
	}
	
	public static int loadStars(String name){
		try (BufferedReader reader = new BufferedReader(new FileReader("splorrt.scores."+name)))
		{
			reader.readLine();
			return Integer.valueOf(reader.readLine());
		}
		catch (NumberFormatException | FileNotFoundException e)
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
		try (PrintWriter pw = new PrintWriter(new FileWriter("splorrt.scores."+saveName)))
		{
			pw.println(score);
			pw.println((score/scorePerStar)+1);
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
