package mightypork.gamecore.core.init;


import mightypork.gamecore.resources.loading.ResourceLoader;


/**
 * Add no resource loader. That will cause resources to be loaded on-demand. May
 * cause lag if the resources are too large.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskResourceLoaderNone extends InitTaskResourceLoader {
	
	
	@Override
	protected ResourceLoader getLoaderImpl()
	{
		return null;
	}
}
