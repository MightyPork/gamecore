package mightypork.gamecore.audio;


import mightypork.gamecore.core.App;
import mightypork.gamecore.resources.BaseDeferredResource;
import mightypork.utils.annotations.Alias;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Abstract deferred audio, to be extended in backend.
 *
 * @author Ondřej Hruška (MightyPork)
 */
@Alias(name = "Audio")
public abstract class DeferredAudio extends BaseDeferredResource implements IAudio {
	
	/**
	 * Create audio
	 *
	 * @param resourceName resource to load (when needed)
	 */
	public DeferredAudio(String resourceName)
	{
		super(resourceName);
	}


	@Override
	public void play(double gain, double pitch, boolean loop)
	{
		play(gain, pitch, loop, App.sound().getListenerPos());
	}


	@Override
	public void play(double gain, double pitch, boolean loop, double x, double y)
	{
		play(gain, pitch, loop, x, y, App.sound().getListenerPos().z());
	}


	@Override
	public void play(double gain, double pitch, boolean loop, Vect pos)
	{
		play(gain, pitch, loop, pos.x(), pos.y(), pos.z());
	}
}
