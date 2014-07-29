package mightypork.gamecore.core.init;


import mightypork.gamecore.resources.Res;
import mightypork.gamecore.resources.ResourceInitializer;


/**
 * Task to initialize resources
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTaskResources extends InitTask implements ResourceInitializer {

	@Override
	public void run()
	{
		Res.load(this);
	}


	@Override
	public String getName()
	{
		return "resources";
	}


	@Override
	public String[] getDependencies()
	{
		// main loop handles resource load rewuests that run in rendering context
		// must be before, otherwise the requests would get lost.
		return new String[] { "resource_loader", "main_loop" };
	}


}
