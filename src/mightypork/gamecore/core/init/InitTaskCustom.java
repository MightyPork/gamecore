package mightypork.gamecore.core.init;


/**
 * Init task that can be extended to initialize additional stuff in the
 * application.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class InitTaskCustom extends InitTask {

	private final String name;
	private final String[] deps;


	public InitTaskCustom(String name)
	{
		this(name, null);
	}


	public InitTaskCustom(String name, String[] dependencies)
	{
		this.name = name;
		this.deps = dependencies;
	}


	@Override
	public abstract void run();


	@Override
	public String getName()
	{
		return name;
	}


	@Override
	public String[] getDependencies()
	{
		return deps;
	}
	
}
