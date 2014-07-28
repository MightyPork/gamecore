package mightypork.gamecore.core.init;


import mightypork.gamecore.resources.loading.ResourceLoader;


/**
 * Task to add a resource loader.<br>
 * By default the async resource loader is used
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTaskResourceLoader extends InitTask {

	/** The loader. */
	protected ResourceLoader loader;


	@Override
	public void run()
	{
		loader = getLoaderImpl();
		if (loader != null) loader.init();
	}


	/**
	 * Create a loader impl
	 *
	 * @return loader
	 */
	protected abstract ResourceLoader getLoaderImpl();


	@Override
	public String getName()
	{
		return "resource_loader";
	}
}
