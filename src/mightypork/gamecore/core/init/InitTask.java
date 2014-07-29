package mightypork.gamecore.core.init;


import mightypork.gamecore.core.App;
import mightypork.utils.annotations.Stub;


/**
 * App initializer. A sequence of initializers is executed once the start()
 * method on App is called. Adding initializers is one way to customize the App
 * behavior and features.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTask {
	
	protected static final int PRIO_FIRST = Integer.MAX_VALUE;
	protected static final int PRIO_EARLY = 9000;
	protected static final int PRIO_DEFAULT = 0;
	protected static final int PRIO_LATE = -9000;
	protected static final int PRIO_LAST = Integer.MIN_VALUE;
	
	/** App instance assigned using <code>bind()</code> */
	protected App app;


	/**
	 * Assign the initialized app instance to an "app" field.
	 *
	 * @param app app
	 */
	public final void bind(App app)
	{
		this.app = app;
	}


	/**
	 * An init method that is called before the <code>run()</code> method.<br>
	 * This method should be left unimplemented in the task, and can be used to
	 * configure the init task when using it as anonymous inner type.
	 */
	@Stub
	public void init()
	{
		//
	}


	/**
	 * Hook for extra action before the main task action.<br>
	 * Can be overridden during app configuration to "bake-in" extra actions.
	 */
	@Stub
	public void before()
	{
		//
	}


	/**
	 * Run the initializer on app.
	 */
	public abstract void run();


	/**
	 * Hook executed after the "run()" method.<br>
	 * Can be overridden during app configuration to "bake-in" extra actions.
	 */
	@Stub
	public void after()
	{
		//
	}


	/**
	 * Get name of this initializer (for dependency resolver).<br>
	 * The name should be short, snake_case and precise.
	 *
	 * @return name
	 */
	public abstract String getName();


	/**
	 * Get what other initializers must be already loaded before this can load.<br>
	 * Depending on itself or creating a circular dependency will cause error.<br>
	 * If the dependencies cannot be satisfied, the initialization sequence will
	 * be aborted.
	 *
	 * @return array of names of required initializers.
	 */
	@Stub
	public String[] getDependencies()
	{
		return new String[] {};
	}


	/**
	 * Get priority in the init sequence. Tasks with higher priority are loaded
	 * earlier (but only after their dependencies are loaded).
	 *
	 * @return priority, higher = runs earlier
	 */
	@Stub
	public int getPriority()
	{
		return PRIO_DEFAULT;
	}
}
