import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.Collection;
import java.util.ArrayList;
import java.awt.Color;


public class StartLevel extends Level
{
    
    public StartLevel(Spider spider)
    {
        super(spider);
    }
    
    public StartLevel()
    {    
        this(null);
    }

	@Override
	public Class<? extends SplorrtWorld> getNextLevel() {
		return Level1_1.class;
	}
    
}
