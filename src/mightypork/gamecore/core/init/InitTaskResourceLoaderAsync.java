package mightypork.gamecore.core.init;


import mightypork.gamecore.resources.loading.AsyncResourceLoader;
import mightypork.gamecore.resources.loading.ResourceLoader;


/**
 * Task to add a resource loader.<br>
 * By default the async resource loader is used
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskResourceLoaderAsync extends InitTaskResourceLoader {

	/**
	 * Create a loader impl
	 *
	 * @return loader
	 */
	@Override
	protected ResourceLoader getLoaderImpl()
	{
		final AsyncResourceLoader loader = new AsyncResourceLoader();
		
		// could now configure the loader
		
		return loader;
	}
}
