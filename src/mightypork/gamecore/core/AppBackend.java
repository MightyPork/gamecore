package mightypork.gamecore.core;


import mightypork.gamecore.audio.AudioModule;
import mightypork.gamecore.core.init.InitTaskBackend;
import mightypork.gamecore.graphics.GraphicsModule;
import mightypork.gamecore.input.InputModule;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;


/**
 * Application backend interface (set of core modules).<br>
 * The goal of this abstraction is to allow easy migration to different
 * environment with different libraries etc. It should be as simple as using
 * different backend.<br>
 * The backend is initialized using {@link InitTaskBackend}.
 *
 * @author MightyPork
 */
public abstract class AppBackend extends BusNode {
	
	/** App instance assigned using <code>bind()</code> */
	protected App app;
	
	
	/**
	 * Assign the initialized app instance to an "app" field.
	 *
	 * @param app app
	 */
	public void bind(App app)
	{
		if (this.app != null) {
			throw new IllegalStateException("App already set.");
		}
		this.app = app;
	}


	/**
	 * Add backend-specific init tasks or init task configurations.<br>
	 * This is run after default init tasks have been added, and before the init
	 * sequence is started.<br>
	 * The backend is already binded to the app.
	 */
	@Stub
	public void addInitTasks()
	{
		//
	}
	
	
	/**
	 * Initialize backend modules, add them to event bus.
	 */
	public abstract void initialize();
	
	
	/**
	 * Get graphics module (screen manager, texture and font loader, renderer)
	 *
	 * @return graphics module
	 */
	public abstract GraphicsModule getGraphics();
	
	
	/**
	 * Get audio module (
	 *
	 * @return audio module
	 */
	public abstract AudioModule getAudio();
	
	
	/**
	 * Get input module
	 *
	 * @return input module
	 */
	public abstract InputModule getInput();
}
