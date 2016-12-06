import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public enum Keybind
{
	MOVE_LEFT("a", "Move left"), MOVE_RIGHT("d", "Move right"), JUMP("space", "Jump"), SHOOT("LMB", "Shoot web"), PULL_UP("w", "Pull up on web"), ROPE_DOWN("s", "Rope down on web"), CANCEL_WEB("LMB", "Cancel web");

	private String	key;
	private String	text;

	private Keybind(String key, String text)
	{
		this.key = key;
		this.text = text;
	}

	public String getKey()
	{
		return key;
	}

	public String getText()
	{
		return text;
	}

	public void setKey(String newKey)
	{
		this.key = newKey;
		saveKeybinds();
	}

	public boolean isMouseAction()
	{
		if (key.equals("LMB") || key.equals("RMB"))
			return true;
		return false;
	}

	public int getMouseButton()
	{
		if (key.equals("LMB"))
			return 1;
		else if (key.equals("RMB"))
			return 3;
		else
			return 0;
	}

	public static void saveKeybinds()
	{
		try (PrintWriter pw = new PrintWriter(new FileWriter("splorrt.keybinds")))
		{
			pw.println(MOVE_LEFT.key);
			pw.println(MOVE_RIGHT.key);
			pw.println(JUMP.key);
			pw.println(SHOOT.key);
			pw.println(PULL_UP.key);
			pw.println(ROPE_DOWN.key);
			pw.println(CANCEL_WEB.key);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadKeybinds()
	{
		try (BufferedReader reader = new BufferedReader(new FileReader("splorrt.keybinds")))
		{
			MOVE_LEFT.setKey(reader.readLine());
			MOVE_RIGHT.setKey(reader.readLine());
			JUMP.setKey(reader.readLine());
			SHOOT.setKey(reader.readLine());
			PULL_UP.setKey(reader.readLine());
			ROPE_DOWN.setKey(reader.readLine());
			CANCEL_WEB.setKey(reader.readLine());
		}
		catch (FileNotFoundException e)
		{
			saveKeybinds();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
