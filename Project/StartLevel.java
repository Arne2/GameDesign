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
  
    @Override
    public void update()
    {
        super.update();
        
        //example world loading
        int x = getXPosition();     
        if(x > 200)
        {
            loadLevel(LevelID.LEVEL1);
        }
    }
}
