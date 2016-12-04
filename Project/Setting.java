import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Setting
{
	private static int	sfxvolume	= 100;

	private static int	musicvolume	= 100;

	public static int getMusicvolume()
	{
		return musicvolume;
	}

	public static int getSfxvolume()
	{
		return sfxvolume;
	}

	public static void setMusicvolume(int musicvolume)
	{
		Setting.musicvolume = musicvolume;
	}

	public static void setSfxvolume(int sfxvolume)
	{
		Setting.sfxvolume = sfxvolume;
	}

	public static void loadSettings()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader("splorrt.settings")))
		{
			setSfxvolume(Integer.valueOf(reader.readLine()));
			setMusicvolume(Integer.valueOf(reader.readLine()));
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
			pw.println(sfxvolume);
			pw.println(musicvolume);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
