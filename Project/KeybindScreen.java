/**
 * Write a description of class KeybindScreen here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class KeybindScreen extends SplorrtWorld
{

	/**
	 * Constructor for objects of class KeybindScreen.
	 * 
	 */
	public KeybindScreen()
	{
		setBackground("KeybindScreen.png");

		prepare();
	}

	private void prepare()
	{
		KeybindingActor kb = new KeybindingActor(Keybind.MOVE_LEFT, "Move left", Keybind.MOVE_LEFT.getKey(), Keybind.MOVE_LEFT.isMouseAction());
		addObject(kb, 250, 200);

		kb = new KeybindingActor(Keybind.MOVE_RIGHT, "Move right", Keybind.MOVE_RIGHT.getKey(), Keybind.MOVE_RIGHT.isMouseAction());
		addObject(kb, 250, 250);

		kb = new KeybindingActor(Keybind.JUMP, "Jump", Keybind.JUMP.getKey(), Keybind.JUMP.isMouseAction());
		addObject(kb, 273, 300);

		kb = new KeybindingActor(Keybind.SHOOT, "Shoot web", Keybind.SHOOT.getKey(), Keybind.SHOOT.isMouseAction());
		addObject(kb, 250, 350);

		kb = new KeybindingActor(Keybind.CANCEL_WEB, "Cancel web", Keybind.CANCEL_WEB.getKey(), Keybind.CANCEL_WEB.isMouseAction());
		addObject(kb, 250, 400);

		kb = new KeybindingActor(Keybind.PULL_UP, "Pull up on web", Keybind.PULL_UP.getKey(), Keybind.PULL_UP.isMouseAction());
		addObject(kb, 227, 450);

		kb = new KeybindingActor(Keybind.ROPE_DOWN, "Rope down on web", Keybind.ROPE_DOWN.getKey(), Keybind.ROPE_DOWN.isMouseAction());
		addObject(kb, 213, 500);
	}
}
