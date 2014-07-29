package mightypork.gamecore.core.init;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mightypork.gamecore.core.App;
import mightypork.utils.Reflect;
import mightypork.utils.annotations.Stub;
import mightypork.utils.logging.Log;


/**
 * App initializer. A sequence of initializers is executed once the start()
 * method on App is called. Adding initializers is one way to customize the App
 * behavior and features.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTask {
	
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
	 * Order init tasks so that all dependencies are loaded before thye are
	 * needed by the tasks.
	 *
	 * @param tasks task list
	 * @return task list ordered
	 */
	public static List<InitTask> inOrder(List<InitTask> tasks)
	{
		final List<InitTask> remaining = new ArrayList<>(tasks);

		final List<InitTask> ordered = new ArrayList<>();
		final Set<String> loaded = new HashSet<>();

		// resolve task order
		int addedThisIteration = 0;
		do {
			for (final Iterator<InitTask> i = remaining.iterator(); i.hasNext();) {
				final InitTask task = i.next();

				String[] deps = task.getDependencies();
				if (deps == null) deps = new String[] {};

				int unmetDepsCount = deps.length;

				for (final String d : deps) {
					if (loaded.contains(d)) unmetDepsCount--;
				}

				if (unmetDepsCount == 0) {
					ordered.add(task);
					loaded.add(task.getName());
					i.remove();
					addedThisIteration++;
				}
			}
		} while (addedThisIteration > 0);

		// check if any tasks are left out
		if (remaining.size() > 0) {

			// build error message for each bad task
			int badInitializers = 0;
			for (final InitTask task : remaining) {
				if (Reflect.hasAnnotation(task.getClass(), OptionalInitTask.class)) {
					continue;
				}

				badInitializers++;

				String notSatisfied = "";

				for (final String d : task.getDependencies()) {
					if (!loaded.contains(d)) {

						if (!notSatisfied.isEmpty()) {
							notSatisfied += ", ";
						}

						notSatisfied += d;
					}
				}

				Log.w("InitTask \"" + task.getName() + "\" - missing dependencies: " + notSatisfied);
			}

			if (badInitializers > 0) throw new RuntimeException("Some InitTask dependencies could not be satisfied.");
		}

		return ordered;
	}
}
