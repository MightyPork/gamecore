package mightypork.gamecore.core.init;


import mightypork.gamecore.resources.loading.ResourceLoader;


/**
 * Task to add a resource loader.<br>
 * By default the async resource loader is used
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
