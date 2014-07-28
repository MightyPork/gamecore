package mightypork.gamecore.core;


import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.eventbus.events.UpdateEvent;
import mightypork.utils.interfaces.Destroyable;
import mightypork.utils.logging.Log;
import mightypork.utils.math.timing.Profiler;
import mightypork.utils.math.timing.TimerDelta;


/**
 * Delta-timed game loop with task queue etc.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class MainLoop extends BusNode implements Destroyable {

	/**
	 * Max time spent on main loop tasks per cycle (s)
	 */
	protected double MAX_TIME_TASKS = 1 / 30D;

	/**
	 * Max delta time (s) per frame.<br>
	 * If delta is larger than this, it's clamped to it.
	 */
	protected double MAX_DELTA = 1 / 20D;

	private final Deque<Runnable> tasks = new ConcurrentLinkedDeque<>();
	private TimerDelta timer;
	private Renderable rootRenderable;
	private volatile boolean running = true;


	/**
	 * Set primary renderable
	 *
	 * @param rootRenderable main {@link Renderable}, typically a
	 *            {@link ScreenRegistry}
	 */
	public void setRootRenderable(Renderable rootRenderable)
	{
		this.rootRenderable = rootRenderable;
	}


	/**
	 * Start the loop
	 */
	public void start()
	{
		timer = new TimerDelta();

		while (running) {
			App.gfx().beginFrame();

			double delta = timer.getDelta();
			if (delta > MAX_DELTA) {
				Log.f3("(timing) Clamping delta: was " + delta + " s, MAX_DELTA = " + MAX_DELTA + " s");
				delta = MAX_DELTA;
			}

			// dispatch update event
			App.bus().sendDirect(new UpdateEvent(delta));

			// run main loop tasks
			Runnable r;
			final long t = Profiler.begin();

			while ((r = tasks.poll()) != null) {
				Log.f3(" * Main loop task.");
				r.run();

				if (Profiler.end(t) > MAX_TIME_TASKS) {
					Log.f3("! Time's up, postponing task to next cycle.");
					break;
				}
			}

			beforeRender();

			if (rootRenderable != null) {
				rootRenderable.render();
			}

			afterRender();

			App.gfx().endFrame();
		}
	}


	/**
	 * Called before render
	 */
	@Stub
	protected void beforeRender()
	{
		//
	}


	/**
	 * Called after render
	 */
	@Stub
	protected void afterRender()
	{
		//
	}


	@Override
	public void destroy()
	{
		running = false;
	}


	/**
	 * Add a task to queue to be executed in the main loop (in rendering
	 * context)
	 *
	 * @param task task
	 * @param skipQueue true to skip the queue
	 */
	public synchronized void queueTask(Runnable task, boolean skipQueue)
	{
		if (skipQueue) {
			tasks.addFirst(task);
		} else {
			tasks.addLast(task);
		}
	}

}
