package mightypork.gamecore.core.init;


import mightypork.gamecore.core.App;


/**
 * Initialize backend. The main point of postponing this is to make sure the
 * init is logged properly.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskBackend extends InitTask {
	
	@Override
	public void run()
	{
		App.instance().getBackend().initialize();
	}
	
	
	@Override
	public String getName()
	{
		return "backend";
	}


	@Override
	public String[] getDependencies()
	{
		return new String[] { "log" };
	}


	@Override
	public int getPriority()
	{
		return PRIO_EARLY;
	}
}
