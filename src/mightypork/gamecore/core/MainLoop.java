package mightypork.gamecore.core;


import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.utils.interfaces.Destroyable;


/**
 * A main loop of the app.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public interface MainLoop extends Destroyable {
	
	/**
	 * Set primary renderable
	 *
	 * @param rootRenderable main {@link Renderable}, typically a
	 *            {@link ScreenRegistry}
	 */
	public abstract void setRootRenderable(Renderable rootRenderable);
	
	
	/**
	 * Start the loop. The loop should be terminated when the destroy() method
	 * is called.
	 */
	public abstract void start();
	
	
	@Override
	public abstract void destroy();
	
	
	/**
	 * Add a task to queue to be executed in the main loop (in rendering
	 * context). This may be eg. loading textures.
	 *
	 * @param task task
	 * @param skipQueue true to skip the queue
	 */
	public abstract void queueTask(Runnable task, boolean skipQueue);
	
}
