import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Setting
{
	private static int		SFXVolume	= 100;

	private static int		musicVolume	= 100;

	private static boolean	haungsMode	= false;

	public static int getMusicVolume()
	{
		return musicVolume;
	}

	public static int getSFXVolume()
	{
		return SFXVolume;
	}

	public static boolean isHaungsMode()
	{
		return haungsMode;
	}

	public static void setMusicVolume(int musicvolume)
	{
		Setting.musicVolume = musicvolume;
	}

	public static void setSFXVolume(int sfxvolume)
	{
		Setting.SFXVolume = sfxvolume;
	}

	public static void setHaungsMode(boolean haungsMode)
	{
		Setting.haungsMode = haungsMode;
	}

	public static void loadSettings()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader("splorrt.settings")))
		{
			setSFXVolume(Integer.valueOf(reader.readLine()));
			setMusicVolume(Integer.valueOf(reader.readLine()));
		}
		catch (FileNotFoundException e)
		{
			saveSettings();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void saveSettings()
	{
		try (PrintWriter pw = new PrintWriter(new FileWriter("splorrt.settings")))
		{
			pw.println(SFXVolume);
			pw.println(musicVolume);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
