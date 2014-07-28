package mightypork.gamecore.gui.events;


import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Request to change screen in {@link ScreenRegistry}
 *
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
public class ScreenRequest extends BusEvent<ScreenRequestListener> {
	
	private final String scrName;
	
	
	/**
	 * Create a request to change screen
	 *
	 * @param screenKey screen name
	 */
	public ScreenRequest(String screenKey)
	{
		scrName = screenKey;
	}
	
	
	@Override
	public void handleBy(ScreenRequestListener handler)
	{
		handler.showScreen(scrName);
	}
	
}
