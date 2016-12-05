import java.awt.Color;

/**
 * Write a description of class LevelActorLoader here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelActorLoader  
{

    private static final Color EMPTY = Color.WHITE;
    
    private static final Color CONSUMEABLE_BUG = new Color(128,0,0);
    
    private static final Color PLATFORM_BRICK = new Color(246,150,121);
    
    private static final Color PLATFORM_GRASS = Color.GREEN;
    
    private static final Color PLATFORM_DIRT = Color.LIGHT_GRAY;
    
    private static final Color PLATFORM_SAND = new Color(151,149,92);
    
    private static final Color ENEMY_SPIKES = new Color(246,49,121);
    
    private static final Color PLATFORM_CACTUS = new Color(17,149,92);
    
    private static final Color PLATFORM_COBBLE = new Color(125,125,125);
    
    private static final Color PLATFORM_STONE = new Color(81,81,81);
    
    private static final Color PLATFORM_LEAVES = new Color(0,78,0);
    
    private static final Color SPAWN = Color.YELLOW;
    
    private static final Color ENEMY_WASP_SHOOTING = new Color(245,0,0);
    
    private static final Color ENEMY_WASP_MOVEABLE = new Color(250,0,0);
    
    private static final Color ENEMY_WASP = new Color(255,0,0);
    
    private static final Color ENEMY_SCORPION = new Color(240, 0, 0);
    
    private static final Color ENEMY_SCORPION_SHOOTING = new Color(230, 0, 0);
    
    private static final Color ENEMY_SCORPION_MOVEABLE = new Color(235, 0, 0);
    
    private static final Color GOAL = Color.ORANGE;
    
    private static final Color LARVA = new Color(71,130,32);
    
    private static final Color WASP = new Color(200,200,20);
    
    private static final Color COIN = new Color(61,192,192);
    
    private final Level level;
    
    public LevelActorLoader(Level l)
    {
        this.level = l;
    }
    
    
    public LevelActor loadLevelActor(Color color, int x, int y)
    {
        if(color.equals(EMPTY))
        {
            return null;
        } 
        
        else if(color.equals(CONSUMEABLE_BUG))
        {
            return new Consumable(Consumable.Type.BUG, x, y);
        } 
        
        else if(color.equals(PLATFORM_BRICK))
        {
            return new Platform(Platform.Type.BRICK, x, y);
        } 
        
        else if(color.equals(PLATFORM_GRASS))
        {
            return new Platform(Platform.Type.GRASS, x, y);
        } 
        
        else if(color.equals(PLATFORM_DIRT))
        {
            return new Platform(Platform.Type.DIRT, x, y);
        } 
        
        else if(color.equals(PLATFORM_SAND))
        {
            return new Platform(Platform.Type.SAND, x, y);
        } 
        
        else if(color.equals(ENEMY_SPIKES))
        {
            return new EnemySpawner(EnemyID.SPIKES, x, y);
        } 
        
        else if(color.equals(PLATFORM_CACTUS))
        {
            return new Platform(Platform.Type.CACTUS, x, y);
        } 
        
        else if(color.equals(PLATFORM_COBBLE))
        {
            return new Platform(Platform.Type.COBBLE, x, y);
        } 
        
        else if(color.equals(PLATFORM_STONE))
        {
            return new Platform(Platform.Type.STONE, x, y);
        } 
        
        else if(color.equals(PLATFORM_LEAVES))
        {
            return new Platform(Platform.Type.LEAVES, x, y);
        } 
        
        else if(color.equals(SPAWN))
        {
            level.setSpawn(x, y);
        } 
        
        else if(color.equals(ENEMY_WASP_MOVEABLE))
        {
        	return new EnemySpawner(EnemyID.WASP_MOVEABLE, x, y);
        } 
        
        else if(color.equals(ENEMY_WASP))
        {
        	return new EnemySpawner(EnemyID.WASP, x, y);
        } 
        
        else if(color.equals(ENEMY_SCORPION))
        {
            return new EnemySpawner(EnemyID.SCORPION, x, y);
        }
        
        else if(color.equals(ENEMY_SCORPION_MOVEABLE))
        {
            return new EnemySpawner(EnemyID.SCORPION_MOVEABLE, x, y);
        }  
        
        else if(color.equals(ENEMY_SCORPION_SHOOTING))
        {
            return new EnemySpawner(EnemyID.SCORPION_SHOOTING, x, y);
        }
        
        else if(color.equals(GOAL))
        {
        	return new Goal(x, y, level.getNextLevel());
        }
        
        else if(color.equals(LARVA))
        {
            return new Consumable(Consumable.Type.LARVA, x, y);
        }
        
        else if(color.equals(WASP))
        {
            return new Consumable(Consumable.Type.WASP, x, y); 
        }
        
        else if(color.equals(COIN))
        {
            return new Consumable(Consumable.Type.COIN, x, y); 
        }
        
        else 
        {
            throw new IllegalArgumentException("Unknown Color" + color.toString() + " " + x + " " + y + "     " + ENEMY_WASP.toString());
        }
        
        return null;
        
    }
    
    
   
}
