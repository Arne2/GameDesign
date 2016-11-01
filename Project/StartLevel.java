import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collection;
import java.util.ArrayList;
import java.awt.Color;


public class StartLevel extends Level
{
    
    public StartLevel(Spider spider)
    {
        super("start.png", spider);
    }
    
    public StartLevel()
    {    
        this(null);
    }
    
    public LevelID getNextLevel()
    {
        return LevelID.LEVEL1;
    }
    
}
