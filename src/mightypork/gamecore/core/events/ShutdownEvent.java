package mightypork.gamecore.core.events;


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
public class ShutdownEvent extends BusEvent<ShutdownListener> {
	
	private final Runnable shutdownTask;
	
	
	/**
	 * Make a shutdown event
	 *
	 * @param doShutdown Task that does the actual shutdown
	 */
	public ShutdownEvent(Runnable doShutdown)
	{
		this.shutdownTask = doShutdown;
	}
	
	
	@Override
	protected void handleBy(ShutdownListener handler)
	{
		handler.onShutdown(this);
	}
	
	
	@Override
	public void onDispatchComplete(EventBus bus)
	{
		if (!isConsumed()) {
			Log.i("Shutting down...");
			shutdownTask.run();
		} else {
			Log.i("Shutdown aborted.");
		}
	}
	
}
