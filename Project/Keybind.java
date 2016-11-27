
public enum Keybind
{
	MOVE_LEFT("left"), MOVE_RIGHT("right"), JUMP("space"), SHOOT("LMB"), PULL_UP("w"), ROPE_DOWN("s"), CANCEL_WEB("space");

	private String key;

	private Keybind(String key)
	{
		this.key = key;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String newKey)
	{
		this.key = newKey;
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
}
