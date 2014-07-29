package mightypork.gamecore.core;


import java.io.File;
import java.util.Arrays;
import java.util.List;

import mightypork.gamecore.audio.AudioModule;
import mightypork.gamecore.core.config.Config;
import mightypork.gamecore.core.events.MainLoopRequest;
import mightypork.gamecore.core.events.ShutdownRequest;
import mightypork.gamecore.core.init.InitSequence;
import mightypork.gamecore.core.init.InitTask;
import mightypork.gamecore.core.init.InitTaskBackend;
import mightypork.gamecore.core.init.InitTaskCrashHandler;
import mightypork.gamecore.core.init.InitTaskIonizables;
import mightypork.gamecore.core.init.InitTaskLog;
import mightypork.gamecore.core.init.InitTaskLogHeader;
import mightypork.gamecore.core.init.InitTaskMainLoop;
import mightypork.gamecore.core.init.InitTaskResourceLoaderAsync;
import mightypork.gamecore.core.init.InitTaskWorkdir;
import mightypork.gamecore.core.plugins.AppPlugin;
import mightypork.gamecore.graphics.GraphicsModule;
import mightypork.gamecore.graphics.Renderable;
import mightypork.gamecore.input.InputModule;
import mightypork.utils.Str;
import mightypork.utils.annotations.Stub;
import mightypork.utils.eventbus.EventBus;
import mightypork.utils.eventbus.clients.BusNode;
import mightypork.utils.eventbus.clients.DelegatingList;
import mightypork.utils.eventbus.events.DestroyEvent;
import mightypork.utils.logging.Log;


/**
 * Game base class & static subsystem access
 *
 * @author MightyPork
 */
public class App extends BusNode {

	private static App instance;

	private final AppBackend backend;
	private final EventBus eventBus = new EventBus();
	private boolean started = false;
	private boolean inited = false;

	/** List of installed App plugins */
	protected final DelegatingList plugins = new DelegatingList();
	/** List of initializers */
	protected final InitSequence initTasks = new InitSequence();

	/** The used main loop instance */
	protected MainLoop mainLoop;

	private Renderable mainRenderable;


	/**
	 * Create an app with given backend.
	 *
	 * @param backend the backend to use
	 */
	public App(AppBackend backend)
	{
		if (App.instance != null) {
			throw new IllegalStateException("App already initialized");
		}

		// store current instance in static field
		App.instance = this;

		// join the bus
		this.eventBus.subscribe(this);

		// create plugin registry attached to bus
		addChildClient(this.plugins);

		// initialize and use backend
		this.backend = backend;
		this.backend.bind(this);
		addChildClient(backend);
		
		addDefaultInitTasks();
		this.backend.addInitTasks();
	}


	private void addDefaultInitTasks()
	{
		addInitTask(new InitTaskCrashHandler());
		addInitTask(new InitTaskWorkdir(new File("."), true));
		addInitTask(new InitTaskLog());
		addInitTask(new InitTaskBackend());
		addInitTask(new InitTaskIonizables());
		addInitTask(new InitTaskMainLoop());
		addInitTask(new InitTaskResourceLoaderAsync());
		addInitTask(new InitTaskLogHeader());
	}


	/**
	 * Add a plugin to the app. Plugins can eg. listen to bus events and react
	 * to them.
	 *
	 * @param plugin the added plugin.
	 */
	public void addPlugin(AppPlugin plugin)
	{
		// attach to event bus
		plugins.add(plugin);
		plugin.bind(this);
		plugin.initialize();
	}


	/**
	 * Add an initializer to the app.
	 *
	 * @param initializer the added init task
	 */
	public void addInitTask(InitTask initializer)
	{
		if (started) {
			throw new IllegalStateException("App already started, cannot add initializers.");
		}

		initTasks.addTask(initializer);
	}


	/**
	 * Set the main loop implementation
	 *
	 * @param loop main loop impl
	 */
	public void setMainLoop(MainLoop loop)
	{
		this.mainLoop = loop;
		addChildClient(loop); // ?
	}


	/**
	 * Set the main renderable
	 *
	 * @param renderable the main renderable
	 */
	public void setMainRenderable(Renderable renderable)
	{
		this.mainRenderable = renderable;
	}


	/**
	 * Get current backend
	 *
	 * @return the backend
	 */
	public AppBackend getBackend()
	{
		return backend;
	}


	/**
	 * Initialize the App and start operating.<br>
	 * This method should be called after adding all required initializers and
	 * plugins.
	 */
	public final void start()
	{
		if (started) {
			throw new IllegalStateException("Already started.");
		}
		started = true;
		
		Log.i("Starting init...");
		init();

		if (mainLoop == null) {
			throw new IllegalStateException("The app has no main loop assigned.");
		}

		Log.i("Starting main loop...");
		mainLoop.setRootRenderable(mainRenderable);
		mainLoop.start();
	}


	private void init()
	{
		
		if (inited) {
			throw new IllegalStateException("Already inited.");
		}
		inited = true;

		// pre-init hook, just in case anyone wanted to have one.
		Log.f2("Calling pre-init hook...");
		preInit();

		Log.f2("Running init tasks...");

		// sort initializers based on dependencies
		final List<InitTask> orderedInitializers = initTasks.getSequence();
		
		// detailed logging
		Log.f3("=== Task overview ===");
		for (final InitTask t : orderedInitializers) {
			Log.f3("Task " + Str.pad(t.getName(), 20) + " class = " + Str.pad(Str.val(t), 30) + " prio = " + Str.pad("" + t.getPriority(), 12) + " deps = "
					+ Arrays.toString(t.getDependencies()));
		}

		for (final InitTask initTask : orderedInitializers) {
			Log.f1("Running init task \"" + initTask.getName() + "\"...");

			initTask.bind(this);

			// set the task options
			initTask.init();

			initTask.before();

			// main task action
			initTask.run();

			// after hook for extra actions immeditaely after the task completes
			initTask.after();
		}

		// user can now start the main loop etc.
		Log.f2("Calling post-init hook...");
		postInit();
	}


	/**
	 * Hook called before the initialization sequence starts.
	 */
	@Stub
	protected void preInit()
	{
	}


	/**
	 * Hook called after the initialization sequence is finished.
	 */
	@Stub
	protected void postInit()
	{
	}


	/**
	 * Shut down the running instance.<br>
	 * Deinitialize backend modules and terminate the JVM.
	 */
	public static void requestShutdown()
	{
		if (instance == null) {
			Log.w("App is not running.");
			System.exit(0);
		}

		Log.i("Sending a shutdown request...");
		bus().send(new ShutdownRequest());
	}


	/**
	 * Shut down the running instance.<br>
	 * Deinitialize backend modules and terminate the JVM.
	 */
	public static void shutdown()
	{
		if (instance == null) {
			Log.w("App is not running.");
			System.exit(0);
		}
		
		// It's safer to shutdown in rendering context
		// (LWJGL backend has problems otherwise)
		
		App.bus().send(new MainLoopRequest(new Runnable() {

			@Override
			public void run()
			{
				try {
					final EventBus bus = bus();
					if (bus != null) {
						bus.send(new DestroyEvent());
					}
				} catch (final Throwable e) {
					Log.e(e);
				}
			}
		}, true));
		
		
		Log.i("Shutdown completed.");
		System.exit(0);
	}


	/**
	 * Get the currently running App instance.
	 *
	 * @return app instance
	 */
	public static App instance()
	{
		return instance;
	}


	/**
	 * Get graphics module from the running app's backend
	 *
	 * @return graphics module
	 */
	public static GraphicsModule gfx()
	{
		return instance.backend.getGraphics();
	}


	/**
	 * Get audio module from the running app's backend
	 *
	 * @return audio module
	 */
	public static AudioModule sound()
	{
		return instance.backend.getAudio();
	}


	/**
	 * Get input module from the running app's backend
	 *
	 * @return input module
	 */
	public static InputModule input()
	{
		return instance.backend.getInput();
	}


	/**
	 * Get event bus instance.
	 *
	 * @return event bus
	 */
	public static EventBus bus()
	{
		return instance.eventBus;
	}


	/**
	 * Get the main config, if initialized.
	 *
	 * @return main config
	 * @throws IllegalArgumentException if there is no such config.
	 */
	public static Config cfg()
	{
		return cfg("main");
	}


	/**
	 * Get a config by alias.
	 *
	 * @param alias config alias
	 * @return the config
	 * @throws IllegalArgumentException if there is no such config.
	 */
	public static Config cfg(String alias)
	{
		return Config.forAlias(alias);
	}
}
