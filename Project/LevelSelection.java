import java.util.ArrayList;
import java.util.List;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LevelSelection here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelSelection extends Level
{
	private static final int SIDE_HEIGHT = 10;
	private static final int PLATFORMS_PER_LEVEL = 7;
	private final List<Class<? extends SplorrtWorld>> levels = new ArrayList<>();
	
    /**
     * Constructor for objects of class LevelSelection.
     * 
     */
    public LevelSelection()
    {
    	super(null, false);
    	
    	// add all the levels, that are supposed to be selectable here.
    	levels.add(Level1.class);
    	levels.add(StartLevel.class);
    	
    	for(int i = 0; i<SIDE_HEIGHT; i++){
    		Platform left = new Platform(Platform.Type.BRICK, 0, i*Platform.SIZE);
    		Platform right = new Platform(Platform.Type.BRICK, levels.size()*PLATFORMS_PER_LEVEL*Platform.SIZE+Platform.SIZE, i*Platform.SIZE);
    		addLevelActor(left);
    		addLevelActor(right);
    	}
    	
    	int y = SIDE_HEIGHT*Platform.SIZE;
    	for(int i = 0; i<levels.size(); i++){
    		for(int j = 0; j<PLATFORMS_PER_LEVEL; j++){
    			Platform next = new Platform(Platform.Type.BRICK, PLATFORMS_PER_LEVEL*Platform.SIZE*i+(j+1)*Platform.SIZE, y);
    			addLevelActor(next);
    		}
    		LevelSelector selector = new LevelSelector(PLATFORMS_PER_LEVEL*Platform.SIZE*i+Platform.SIZE*(PLATFORMS_PER_LEVEL/2+1), 0, levels.get(i));
    		addLevelActor(selector);
    	}
    }
}
