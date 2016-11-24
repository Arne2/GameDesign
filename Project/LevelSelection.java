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
	private static final int SIDE_HEIGHT = 10;
	private static final int PLATFORMS_PER_LEVEL = 7;
	private final List<LevelInfo> levels = new ArrayList<>();
	
	private class LevelInfo{
		private final Platform.Type type;
		private final Class<? extends SplorrtWorld> world;
		
		private LevelInfo(Platform.Type type, Class<? extends SplorrtWorld> world) {
			super();
			this.type = type;
			this.world = world;
		}
	}
	
    /**
     * Constructor for objects of class LevelSelection.
     * 
     */
    public LevelSelection()
    {
    	super(null, false);
    	
    	// add all the levels, that are supposed to be selectable here.
    	levels.add(new LevelInfo(Platform.Type.GRASS, Level1_1.class));
    	levels.add(new LevelInfo(Platform.Type.GRASS, Level1_2.class));
    	
    	for(int i = 0; i<SIDE_HEIGHT; i++){
    		Platform left = new Platform(Platform.Type.BRICK, 0, i*Platform.SIZE);
    		Platform right = new Platform(Platform.Type.BRICK, levels.size()*PLATFORMS_PER_LEVEL*Platform.SIZE+Platform.SIZE, i*Platform.SIZE);
    		addLevelActor(left);
    		addLevelActor(right);
    	}
    	
    	int y = SIDE_HEIGHT*Platform.SIZE;
    	for(int i = 0; i<levels.size(); i++){
    		Platform.Type type = levels.get(i).type;
    		for(int j = 0; j<PLATFORMS_PER_LEVEL; j++){
    			Platform next = new Platform(type, PLATFORMS_PER_LEVEL*Platform.SIZE*i+(j+1)*Platform.SIZE, y);
    			addLevelActor(next);
    		}
    		LevelSelectorClickPlatform selector = new LevelSelectorClickPlatform(type, PLATFORMS_PER_LEVEL*Platform.SIZE*i+Platform.SIZE*(PLATFORMS_PER_LEVEL/2+1), 0, levels.get(i).world);
    		addLevelActor(selector);
    	}
    }
}
