package mightypork.gamecore.core.init;


import mightypork.gamecore.resources.loading.AsyncResourceLoader;
import mightypork.gamecore.resources.loading.ResourceLoader;


/**
 * Add Async resource loader.
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
		return new AsyncResourceLoader();
	}
}
