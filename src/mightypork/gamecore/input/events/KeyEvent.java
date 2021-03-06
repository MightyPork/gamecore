package mightypork.gamecore.input.events;


import mightypork.gamecore.input.Keys;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.NotLoggedEvent;


/**
 * A keyboard event FIXME Should use Key class, not keycode.
 *
 * @author Ondřej Hruška (MightyPork)
 */
@NotLoggedEvent
public class KeyEvent extends BusEvent<KeyEventHandler> {

	private final int key;
	private final boolean down;
	private final char c;


	/**
	 * @param key key that triggered the event. Can be KEY_NONE.
	 * @param c typed char (can be zero char)
	 * @param down true = pressed, false = released.
	 */
	public KeyEvent(int key, char c, boolean down)
	{
		this.key = key;
		this.c = c;
		this.down = down;
	}


	/**
	 * @return key code
	 */
	public int getKey()
	{
		return key;
	}


	/**
	 * @return true if key was just pressed
	 */
	public boolean isDown()
	{
		return down;
	}


	/**
	 * @return true if key was just released
	 */
	public boolean isUp()
	{
		return !down;
	}


	/**
	 * @return event character (if any)
	 */
	public char getChar()
	{
		return c;
	}


	@Override
	public void handleBy(KeyEventHandler keh)
	{
		keh.receive(this);
	}


	@Override
	public String toString()
	{
		// FIXME
		return Keys.codeToKey(key).getName() + ":" + (down ? "DOWN" : "UP");
	}

}
