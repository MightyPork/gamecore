package mightypork.gamecore.gui.components;


import mightypork.utils.eventbus.clients.ToggleableClient;


/**
 * Component used for user input, such as buttons.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InputComponent extends BaseComponent implements ToggleableClient {
	
	@Override
	public boolean isListening()
	{
		return isEnabled();
	}
}
