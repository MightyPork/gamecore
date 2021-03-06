package mightypork.gamecore.core.plugins;


import mightypork.gamecore.core.App;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;


/**
 * App plugin. Plugins are an easy way to extend app functionality.<br>
 * Typically, a plugin waits for trigger event(s) and performs some action upon
 * receiving them.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class AppPlugin extends BusNode {
	
	/** App instance assigned using <code>bind()</code> */
	protected App app;
	
	
	/**
	 * Assign the initialized app instance to an "app" field.
	 *
	 * @param app app
	 */
	public void bind(App app)
	{
		this.app = app;
	}
	
	
	/**
	 * Initialize the plugin for the given App.<br>
	 * The plugin is already attached to the event bus.
	 */
	@Stub
	public void initialize()
	{
	}
}
