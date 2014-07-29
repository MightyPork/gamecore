package mightypork.gamecore.core.events;


/**
 * Quit request listener; implementing client can abort shutdown.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public interface ShutdownRequestListener {
	
	/**
	 * Intercept shutdown request.<br>
	 * Consume the event to abort shutdown (ie. ask user to save).
	 *
	 * @param event quit request event.
	 */
	void onShutdownRequested(ShutdownRequest event);
}
