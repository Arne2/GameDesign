import java.util.ArrayList;
import java.util.List;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Level to allow choosing a Level.
 * Will have a wall on either side and a walkway between.
 * Above the walkway there are Blocks that will load a Level, when clicked on.
 * 
 * @author maximilian-zollbrecht
 * @version 16.11.2016
 */
public class LevelSelection extends Level
{
	private static final int SIDE_HEIGHT = 20;
	private static final int SIDE_WIDTH = 10;
	private static final int PLATFORM_DEPTH = 12;
	private static final int WIDTH_PER_LEVEL = 9;
	
	private final List<LevelInfo> levels = new ArrayList<>();
	private final int unlockedAreas;
    
	private int currentLevel = 0;
    private GreenfootImage background = new GreenfootImage("Sky_blue.png");
	private GreenfootSound music = new GreenfootSound("Constance.mp3");
	
	private class LevelInfo{
		private final Platform.Type surfaceType;
		private final Platform.Type[] types;
		private final SplorrtWorld world;
		
		private LevelInfo(SplorrtWorld world, Platform.Type surfaceType, Platform.Type... types) {
			super();
			this.surfaceType = surfaceType;
			this.types = types;
			this.world = world;
		}
		
		private Platform.Type getType(boolean surface){
			if(surface || types==null || types.length==0){
				return surfaceType;
			} else {
				return types[Greenfoot.getRandomNumber(types.length)];
			}
		}
	}
	
    /**
     * Constructor for objects of class LevelSelection.
     * 
     */
    public LevelSelection(int unlockedAreas)
    {
    	super(null, false);
    	
    	setBackground(getBackgroundImage());
    	
    	this.unlockedAreas = unlockedAreas;
    	
    	setWorldHeight(SIDE_HEIGHT+PLATFORM_DEPTH);
    	getSpider().getWebBar().add(getSpider().getWebBar().getMaximumValue());
    	
    	// add all the levels, that are supposed to be selectable here.
    	levels.add(new LevelInfo(new Level1_1(), Platform.Type.GRASS, Platform.Type.DIRT));
    	levels.add(new LevelInfo(new Level1_2(), Platform.Type.GRASS, Platform.Type.DIRT, Platform.Type.SAND));
    	levels.add(null);
    	
    	
    	
    	levels.add(new LevelInfo(new Level2_1(), Platform.Type.STONE));
    	
    	for(int y = 0; y<SIDE_HEIGHT+PLATFORM_DEPTH; y++){
    		for(int x = -SIDE_WIDTH+1; x<=0; x++){
    			Platform left = new Platform(levels.get(0).getType(false), x*Platform.SIZE, y*Platform.SIZE);
	    		addLevelActor(left);
    		}
    		for(int x = 0; x<SIDE_WIDTH; x++){
    			Platform right = new Platform(levels.get(levels.size()-1).getType(false), (levels.size()*WIDTH_PER_LEVEL + 1 + x)*Platform.SIZE, y*Platform.SIZE);
	    		addLevelActor(right);
    		}
    	}
    	
    	int area = 0;
    	int yStart = SIDE_HEIGHT*Platform.SIZE;
    	for(int i = 0; i<levels.size(); i++){
    		LevelInfo info = levels.get(i);
    		
    		if(info!=null){
	    		for(int x = 0; x<WIDTH_PER_LEVEL; x++){
	    			for(int y = 0; y<PLATFORM_DEPTH; y++){
		    			Platform next = new Platform(info.getType(y==0),
		    					WIDTH_PER_LEVEL*Platform.SIZE*i+(x+1)*Platform.SIZE, 
		    					y*Platform.SIZE+yStart);
		    			addLevelActor(next);
	    			}
	    		}
	    		
		    	int y = yStart-3*Platform.SIZE-Greenfoot.getRandomNumber(7)*Platform.SIZE;
	    		if(area <= unlockedAreas){
		    		Platform selector = new LevelSelectorShootPlatform(info.surfaceType, WIDTH_PER_LEVEL*Platform.SIZE*i+Platform.SIZE*(WIDTH_PER_LEVEL/2+1), y, levels.get(i).world.getClass());
		    		addLevelActor(selector);
	    		} else {
	    			Platform platform = new Platform(info.surfaceType, WIDTH_PER_LEVEL*Platform.SIZE*i+Platform.SIZE*(WIDTH_PER_LEVEL/2+1), y);
		    		addLevelActor(platform);
	    		}
    		} else {
    			if(area++ < unlockedAreas) {
    				for(int x = 0; x<WIDTH_PER_LEVEL; x++){
		    			Platform next = new Platform(x<WIDTH_PER_LEVEL/2 ? levels.get(i-1).getType(true) : levels.get(i+1).getType(true),
		    					WIDTH_PER_LEVEL*Platform.SIZE*i+(x+1)*Platform.SIZE, 
		    					yStart-(x==0 || x==WIDTH_PER_LEVEL-1 ? Platform.SIZE/2 : Platform.SIZE));
		    			addLevelActor(next);
    	    		}
    			}
    		}
    	}
    	
    	super.load();
    }
    
    @Override
    public void act() {
    	super.act();
    	
    	int index = (super.getXPosition()+getWidth()/2)/(Platform.SIZE*WIDTH_PER_LEVEL);
    	if(currentLevel != index && levels.get(index)!=null){
	    	GreenfootSound newMusic = ((Level)levels.get(index).world).getBackgroundMusic();
	    	
	    	String newName = newMusic.toString().split(" ")[2];
	    	String oldName = music.toString().split(" ")[2];
	    	if(!newName.equals(oldName)){
	    		this.music.stop();
	    		this.music = newMusic;
		    	this.music.playLoop();
	    	}
    		
    		this.background = ((Level)levels.get(index).world).getBackgroundImage();
	    	this.setBackground(this.background);
	    	
	    	currentLevel = index;
    	}
    }

	@Override
	public GreenfootSound getBackgroundMusic() {
		return music;
	}

	@Override
	public GreenfootImage getBackgroundImage() {
		return background;
	}
}
