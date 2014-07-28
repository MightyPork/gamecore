package mightypork.gamecore.core;


import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.interfaces.Destroyable;


/**
 * Abstract application backend module.
 *
 * @author MightyPork
 */
public abstract class BackendModule extends BusNode implements Destroyable {

	/**
	 * Initialize the backend module.<br>
	 * Any initialization that would normally be done in constructor shall be
	 * done here, to avoid pitfalls with
	 * "call to overridable method from constructor"
	 */
	public abstract void init();


	@Override
	@Stub
	public void destroy()
	{
	}
}
