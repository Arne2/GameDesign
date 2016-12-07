import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.GreenfootSound;

/**
 * Level to allow choosing a Level. Will have a wall on either side and a walkway between. Above the walkway there are Blocks that will load a Level, when shot with web.
 * 
 * @author maximilian-zollbrecht
 * @version 28.11.2016
 */
public class LevelSelection extends Level
{
	private static final int		SIDE_HEIGHT		= 20;
	private static final int		SIDE_WIDTH		= 10;
	private static final int		PLATFORM_DEPTH	= 12;
	private static final int		WIDTH_PER_LEVEL	= 9;

	private final List<LevelInfo>	levels			= new ArrayList<>();

	private int						currentLevel	= 0;
	private GreenfootImage			background		= new GreenfootImage("Sky_blue.png");
	private GreenfootSound			music			= new GreenfootSound("On My Way.wav");

	private class LevelInfo
	{
		private final Platform.Type		surfaceType;
		private final Platform.Type[]	types;
		private final SplorrtWorld		world;

		private LevelInfo(SplorrtWorld world, Platform.Type surfaceType, Platform.Type... types)
		{
			super();
			this.surfaceType = surfaceType;
			this.types = types;
			this.world = world;
		}

		private Platform.Type getType(boolean surface)
		{
			if (surface || types == null || types.length == 0)
			{
				return surfaceType;
			}
			else
			{
				return types[Greenfoot.getRandomNumber(types.length)];
			}
		}

		private Level getLevel()
		{
			SplorrtWorld l = world;
			while (l instanceof InfoScreen)
			{
				l = ((InfoScreen) l).getNext();
			}
			if (l instanceof Level)
			{
				return (Level) l;
			}
			else
			{
				return null;
			}
		}
	}

	public LevelSelection()
	{
		this(loadUnlockedAreas());
	}

	/**
	 * Constructor for objects of class LevelSelection.
	 * 
	 */
	public LevelSelection(int unlockedAreas)
	{
		super(null, false);

		setBackground(getBackgroundImage());

		setWorldHeight(SIDE_HEIGHT + PLATFORM_DEPTH);

		super.setSpawn((WIDTH_PER_LEVEL / 2 + WIDTH_PER_LEVEL % 2) * Platform.SIZE, (SIDE_HEIGHT - 2) * Platform.SIZE);

		// add all the levels, that are supposed to be selectable here.
		levels.add(new LevelInfo(new Level1_1(), Platform.Type.GRASS, Platform.Type.DIRT));
		levels.add(new LevelInfo(new Level1_2(), Platform.Type.GRASS, Platform.Type.DIRT, Platform.Type.SAND));
		levels.add(new LevelInfo(new Level1_3(), Platform.Type.GRASS, Platform.Type.DIRT, Platform.Type.SAND));
		levels.add(new LevelInfo(new Level1_4(), Platform.Type.GRASS, Platform.Type.DIRT, Platform.Type.SAND));
		levels.add(new LevelInfo(new Level1_5(), Platform.Type.GRASS, Platform.Type.DIRT, Platform.Type.SAND));

		// separator to create a bridge (DO NOT PUT BRIDGES AT THE FIRST AND LAST PLACE)
		levels.add(null);

		levels.add(new LevelInfo(new Level2_1(), Platform.Type.STONE, Platform.Type.STONE, Platform.Type.COBBLE));
		levels.add(new LevelInfo(new Level2_2(), Platform.Type.STONE, Platform.Type.STONE, Platform.Type.COBBLE));
		levels.add(new LevelInfo(new Level2_3(), Platform.Type.STONE, Platform.Type.STONE, Platform.Type.COBBLE));
		levels.add(new LevelInfo(new Level2_4(), Platform.Type.STONE, Platform.Type.STONE, Platform.Type.COBBLE));
		levels.add(new LevelInfo(new Level2_5(), Platform.Type.STONE, Platform.Type.STONE, Platform.Type.COBBLE));

		for (int y = 0; y < SIDE_HEIGHT + PLATFORM_DEPTH; y++)
		{
			for (int x = -SIDE_WIDTH + 1; x <= 0; x++)
			{
				Platform left = new Platform(levels.get(0).getType(false), x * Platform.SIZE, y * Platform.SIZE);
				addLevelActor(left);
			}
			for (int x = 0; x < SIDE_WIDTH; x++)
			{
				Platform right = new Platform(levels.get(levels.size() - 1).getType(false), (levels.size() * WIDTH_PER_LEVEL + 1 + x) * Platform.SIZE, y * Platform.SIZE);
				addLevelActor(right);
			}
		}

		int area = 0;
		int yStart = SIDE_HEIGHT * Platform.SIZE;
		for (int i = 0; i < levels.size(); i++)
		{
			LevelInfo info = levels.get(i);

			if (info != null)
			{
				for (int x = 0; x < WIDTH_PER_LEVEL; x++)
				{
					for (int y = 0; y < PLATFORM_DEPTH; y++)
					{
						Platform next = new Platform(info.getType(y == 0), WIDTH_PER_LEVEL * Platform.SIZE * i + (x + 1) * Platform.SIZE, y * Platform.SIZE + yStart);
						addLevelActor(next);
					}
				}

				int y = yStart - 3 * Platform.SIZE - Greenfoot.getRandomNumber(7) * Platform.SIZE;
				if (area <= unlockedAreas)
				{
					Platform selector = new LevelSelectorShootPlatform(info.surfaceType, WIDTH_PER_LEVEL * Platform.SIZE * i + Platform.SIZE * (WIDTH_PER_LEVEL / 2 + 1), y, levels.get(i).world);
					addLevelActor(selector);

					Level level = info.getLevel();

					if (level != null)
					{
						Label label = new Label(level.getName(), 32);
						label.setLevelX(selector.getLevelX());
						label.setLevelY(selector.getLevelY() - 96);
						addLevelActor(label);

						LevelRating rating = new LevelRating(level.getStars(), 32);
						rating.setLevelX(selector.getLevelX());
						rating.setLevelY(selector.getLevelY() - 54);
						addLevelActor(rating);
					}
				}
				else
				{
					Platform platform = new Platform(info.surfaceType, WIDTH_PER_LEVEL * Platform.SIZE * i + Platform.SIZE * (WIDTH_PER_LEVEL / 2 + 1), y);
					addLevelActor(platform);
				}
			}
			else
			{
				if (area++ < unlockedAreas)
				{
					for (int x = 0; x < WIDTH_PER_LEVEL; x++)
					{
						Platform next = new Platform(x < WIDTH_PER_LEVEL / 2 ? levels.get(i - 1).getType(true) : levels.get(i + 1).getType(true), WIDTH_PER_LEVEL * Platform.SIZE * i + (x + 1) * Platform.SIZE, yStart - (x == 0 || x == WIDTH_PER_LEVEL - 1 ? Platform.SIZE / 2 : Platform.SIZE));
						addLevelActor(next);
					}
				}
			}
		}
	}

	@Override
	public void act()
	{
		super.act();
		int index = (super.getXPosition() + getWidth() / 2) / (Platform.SIZE * WIDTH_PER_LEVEL);
		if (currentLevel != index && index >= 0 && index < levels.size() && levels.get(index) != null)
		{
			GreenfootSound newMusic = levels.get(index).getLevel().getBackgroundMusic();

			String newName = newMusic.toString().split(" ")[2];
			String oldName = music.toString().split(" ")[2];
			if (!newName.equals(oldName))
			{
				this.music.stop();
				this.music = newMusic;
				this.music.playLoop();
			}

			this.background = levels.get(index).getLevel().getBackgroundImage();
			this.setBackground(this.background);

			currentLevel = index;
		}
	}

	public static void unlockAreas(int area)
	{
		if (area > loadUnlockedAreas())
		{
			saveUnlockedAreas(area);
		}
	}

	public static void saveUnlockedAreas(int areas)
	{
		try (PrintWriter pw = new PrintWriter(new FileWriter("splorrt.areas.unlocked")))
		{
			pw.println(areas);
			pw.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int loadUnlockedAreas()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader("splorrt.areas.unlocked")))
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

	@Override
	public GreenfootSound getBackgroundMusic()
	{
		return music;
	}

	@Override
	public GreenfootImage getBackgroundImage()
	{
		return background;
	}

	@Override
	public int getStartingWeb()
	{
		return getSpider().getWebBar().getMaximumValue();
	}
}
