package mightypork.gamecore.core.init;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mightypork.utils.Reflect;
import mightypork.utils.logging.Log;


/**
 * initialization sequence that takes care of task dependencies and ordering.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class InitSequence {

	private final Map<String, InitTask> taskMap = new HashMap<>();


	/**
	 * Add a task. If a task with the name already exists, replace it.
	 *
	 * @param task task to add
	 */
	public void addTask(InitTask task)
	{
		final String name = task.getName();
		
		// detailed logging
//		if (taskMap.containsKey(name)) {
//			Log.f3("REPL init " + Str.pad("\"" + name + "\"", 20) + " <" + Str.val(task) + ">");
//		} else {
//			Log.f3("ADD  init " + Str.pad("\"" + name + "\"", 20) + " <" + Str.val(task) + ">");
//		}
		
		taskMap.put(name, task);
	}
	
	
	/**
	 * Get task sequence in proper order.
	 *
	 * @return initialization sequence
	 */
	public List<InitTask> getSequence()
	{
		final List<InitTask> remainingTasks = new ArrayList<>(taskMap.values());

		final List<InitTask> orderedTasks = new ArrayList<>();
		final Set<String> loadedTaskNames = new HashSet<>();
		
		// resolve task order
		InitTask taskToAdd = null;
		do {
			taskToAdd = null;
			
			for (final InitTask task : remainingTasks) {

				String[] deps = task.getDependencies();
				if (deps == null) deps = new String[] {};
				
				int missingDeps = deps.length;
				
				for (final String d : deps) {
					if (loadedTaskNames.contains(d)) missingDeps--;
				}
				
				if (missingDeps == 0) {
					if (taskToAdd == null || taskToAdd.getPriority() < task.getPriority()) {
						taskToAdd = task;
					}
				}
			}

			if (taskToAdd != null) {
				orderedTasks.add(taskToAdd);
				loadedTaskNames.add(taskToAdd.getName());
				remainingTasks.remove(taskToAdd);
			}

		} while (taskToAdd != null);
		
		checkLeftovers(loadedTaskNames, remainingTasks);

		return orderedTasks;
	}
	
	
	public List<InitTask> getSequenceOldImpl()
	{
		final List<InitTask> remainingTasks = new ArrayList<>(taskMap.values());

		final List<InitTask> orderedTasks = new ArrayList<>();
		final Set<String> loadedTaskNames = new HashSet<>();
		
		// resolve task order
		int addedThisIteration = 0;
		do {
			addedThisIteration = 0;
			for (final Iterator<InitTask> i = remainingTasks.iterator(); i.hasNext();) {
				final InitTask task = i.next();
				
				String[] deps = task.getDependencies();
				if (deps == null) deps = new String[] {};
				
				int missingDeps = deps.length;
				
				for (final String d : deps) {
					if (loadedTaskNames.contains(d)) missingDeps--;
				}
				
				if (missingDeps == 0) {
					orderedTasks.add(task);
					loadedTaskNames.add(task.getName());
					i.remove();
					addedThisIteration++;
				}
			}
		} while (addedThisIteration > 0);
		
		checkLeftovers(loadedTaskNames, remainingTasks);

		return orderedTasks;
	}
	
	
	private void checkLeftovers(Collection<String> loadedTaskNames, Collection<InitTask> remainingTasks)
	{
		// check if any tasks are left out
		if (remainingTasks.size() > 0) {
			
			// build error message for each bad task
			int badInitializers = 0;
			for (final InitTask task : remainingTasks) {
				
				if (Reflect.hasAnnotation(task.getClass(), OptionalInitTask.class)) {
					continue;
				}
				
				badInitializers++;
				
				String notSatisfied = "";
				
				for (final String d : task.getDependencies()) {
					if (!loadedTaskNames.contains(d)) {
						
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
	}
}
