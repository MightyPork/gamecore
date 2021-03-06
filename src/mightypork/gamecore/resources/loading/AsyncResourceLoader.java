package mightypork.gamecore.resources.loading;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import mightypork.gamecore.core.App;
import mightypork.gamecore.core.events.MainLoopRequest;
import mightypork.gamecore.resources.DeferredResource;
import mightypork.utils.Reflect;
import mightypork.utils.Str;
import mightypork.utils.interfaces.Destroyable;
import mightypork.utils.logging.Log;


/**
 * Asynchronous resource loading thread.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class AsyncResourceLoader extends Thread implements ResourceLoader, Destroyable {

	private final ExecutorService exs = Executors.newFixedThreadPool(2);

	private final LinkedBlockingQueue<DeferredResource> toLoad = new LinkedBlockingQueue<>();
	private volatile boolean stopped;
	private volatile boolean mainLoopQueuing = true;


	/**
	 * Create a resource loader.
	 */
	public AsyncResourceLoader()
	{
		super("Deferred loader");
	}


	@Override
	public synchronized void init()
	{
		setDaemon(true);
		super.start();
	}


	/**
	 * True to queue resources that must load in rendering context to main loop.
	 * May cause lag at the beginning, but results in smoother performance later
	 * (better than load-on-demand)
	 *
	 * @param yes true to allow queuing
	 */
	public void enableMainLoopQueuing(boolean yes)
	{
		mainLoopQueuing = yes;
	}


	@Override
	public void loadResource(final DeferredResource resource)
	{
		if (resource.isLoaded()) return;

		// textures & fonts needs to be loaded in main thread
		if (Reflect.hasAnnotation(resource, MustLoadInRenderingContext.class)) {

			if (!mainLoopQueuing) {
				// just let it be
			} else {
				Log.f3("(loader) Delegating to main thread: " + Str.val(resource));

				App.bus().send(new MainLoopRequest(new Runnable() {

					@Override
					public void run()
					{
						resource.load();
					}
				}, false));
			}

			return;
		}

		toLoad.add(resource);
	}


	@Override
	public void run()
	{
		Log.f3("Asynchronous resource loader started.");

		while (!stopped) {

			try {
				final DeferredResource def = toLoad.take();
				if (def == null) continue;

				if (!def.isLoaded()) {

					Log.f3("(loader) Scheduling... " + Str.val(def));

					exs.submit(new Runnable() {

						@Override
						public void run()
						{
							if (!def.isLoaded()) {
								def.load();
							}
						}
					});
				}

			} catch (final InterruptedException ignored) {
				//
			}

		}
	}


	// apparently, destroy method exists on thread :/
	@SuppressWarnings("deprecation")
	@Override
	public void destroy()
	{
		Log.f3("Stopping resource loader thread.");
		stopped = true;
		exs.shutdownNow();
	}

}
