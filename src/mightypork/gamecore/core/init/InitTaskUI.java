package mightypork.gamecore.core.init;


import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.resources.Res;


/**
 * Task to init main renderable (UI).<br>
 * Resources are already registered in {@link Res}.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTaskUI extends InitTask {

	@Override
	public void run()
	{
		app.setMainRenderable(createMainRenderable());
	}
	
	
	/**
	 * Create a loader impl
	 *
	 * @return loader
	 */
	protected abstract Renderable createMainRenderable();
	
	
	@Override
	public String getName()
	{
		return "ui";
	}
	
	
	@Override
	public String[] getDependencies()
	{
		// main loop queues layout change events, would lose them otherwise
		return new String[] { "resources", "main_loop" };
	}
	
	
	@Override
	public int getPriority()
	{
		return PRIO_LAST;
	}
}
