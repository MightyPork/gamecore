package mightypork.gamecore.core.init;


import mightypork.gamecore.core.BasicMainLoop;
import mightypork.gamecore.core.MainLoop;
import mightypork.utils.annotations.Stub;


/**
 * Task to add a main loop.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class InitTaskMainLoop extends InitTask {
	
	/** The loop, can be accessed in the after() method. */
	protected MainLoop loop;
	
	
	@Override
	public void run()
	{
		loop = getLoopImpl();
		app.setMainLoop(loop);
	}
	
	
	/**
	 * Create a main loop
	 *
	 * @return loader
	 */
	@Stub
	protected MainLoop getLoopImpl()
	{
		return new BasicMainLoop();
	}
	
	
	@Override
	public String getName()
	{
		return "main_loop";
	}


	@Override
	public int getPriority()
	{
		return PRIO_EARLY;
	}
}
