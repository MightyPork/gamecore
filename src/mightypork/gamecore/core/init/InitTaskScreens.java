package mightypork.gamecore.core.init;


import mightypork.gamecore.graphics.Renderable;


/**
 * Task to init renderable screens (part of the main loop).<br>
 * Resources must already be ready.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTaskScreens extends InitTask {

	@Override
	public void run()
	{
		app.setMainRenderable(getMainRenderableImpl());
	}
	
	
	/**
	 * Create a loader impl
	 *
	 * @return loader
	 */
	protected abstract Renderable getMainRenderableImpl();
	
	
	@Override
	public String getName()
	{
		return "renderables";
	}
	
	
	@Override
	public String[] getDependencies()
	{
		return new String[] { "resources", "main_loop" };
	}
}
