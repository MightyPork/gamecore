package mightypork.gamecore.core.events;


import mightypork.gamecore.core.App;
import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.EventBus;
import mightypork.utils.logging.Log;


/**
 * Shutdown event.<br>
 * This event is dispatched when the <code>App.shutdown()</code> method is
 * called. If no client consumes it, the shutdown will immediately follow.<br>
 * This is a way to allow clients to abort the shutdown (ie. ask to save game).
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class ShutdownRequest extends BusEvent<ShutdownRequestListener> {
	
	
	@Override
	protected void handleBy(ShutdownRequestListener handler)
	{
		handler.onShutdownRequested(this);
	}
	
	
	@Override
	public void onDispatchComplete(EventBus bus)
	{
		if (!isConsumed()) {
			Log.i("Shutting down...");

			App.shutdown();

		} else {
			Log.i("Shutdown aborted.");
		}
	}
	
}
