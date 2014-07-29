package mightypork.gamecore.core.init;


import mightypork.gamecore.core.MainLoop;


/**
 * Task to add a resource loader.<br>
 * By default the async resource loader is used
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTaskMainLoop extends InitTask {
	
	/** The loader. */
	protected MainLoop loop;
	
	
	@Override
	public void run()
	{
		loop = getLoopImpl();
		app.setMainLoop(loop);
	}
	
	
	/**
	 * Create a loader impl
	 *
	 * @return loader
	 */
	protected abstract MainLoop getLoopImpl();
	
	
	@Override
	public String getName()
	{
		return "resource_loader";
	}
}
