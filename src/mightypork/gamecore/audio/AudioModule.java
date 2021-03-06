package mightypork.gamecore.audio;


import java.util.ArrayList;
import java.util.List;

import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;
import mightypork.gamecore.core.App;
import mightypork.gamecore.core.BackendModule;
import mightypork.gamecore.resources.loading.ResourceLoadRequest;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Abstract audio module.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class AudioModule extends BackendModule implements Updateable {
	
	/**
	 * Set listener position
	 *
	 * @param pos listener position
	 */
	public abstract void setListenerPos(Vect pos);
	
	
	/**
	 * Get current listener position
	 *
	 * @return listener position
	 */
	public abstract Vect getListenerPos();
	
	// -- instance --
	
	private final Volume masterVolume = new Volume(1D);
	private final Volume effectsVolume = new JointVolume(masterVolume);
	private final Volume loopsVolume = new JointVolume(masterVolume);
	
	private final List<LoopPlayer> loopPlayers = new ArrayList<>();
	private final List<DeferredAudio> resources = new ArrayList<>();
	
	
	@Override
	public void destroy()
	{
		for (final DeferredAudio r : resources) {
			r.destroy();
		}
		
		deinitSoundSystem();
	}
	
	
	/**
	 * Deinitialize the soud system, release resources etc.<br>
	 * Audio resources are already destroyed.
	 */
	protected abstract void deinitSoundSystem();
	
	
	@Override
	public void update(double delta)
	{
		for (final Updateable lp : loopPlayers) {
			lp.update(delta);
		}
	}
	
	
	/**
	 * Create effect resource
	 *
	 * @param resource resource path
	 * @return player
	 */
	public EffectPlayer createEffect(String resource)
	{
		return new EffectPlayer(createAudioResource(resource), effectsVolume);
	}
	
	
	/**
	 * Register loop resource (music / effect loop)
	 *
	 * @param resource resource path
	 * @return player
	 */
	public LoopPlayer createLoop(String resource)
	{
		final LoopPlayer p = new LoopPlayer(createAudioResource(resource), loopsVolume);
		loopPlayers.add(p);
		return p;
	}
	
	
	/**
	 * Create {@link DeferredAudio} for a resource, request deferred load and
	 * add to the resources list.<br>
	 * The method is accessed only from within this class, since the sound
	 * registry holds players, not the resources themselves.
	 *
	 * @param res a resource name
	 * @return the resource
	 * @throws IllegalArgumentException if resource is already registered
	 */
	protected final DeferredAudio createAudioResource(String res)
	{
		final DeferredAudio a = doCreateResource(res);
		App.bus().send(new ResourceLoadRequest(a));
		resources.add(a);
		return a;
	}
	
	
	/**
	 * Create a backend-specific deferred audio resource.<br>
	 * The actual resource instance should be created here. Registering, loading
	 * etc. is handled higher.
	 *
	 * @param res resource path
	 * @return Deferred Audio
	 */
	protected abstract DeferredAudio doCreateResource(String res);
	
	
	/**
	 * Fade out all loops (= fade out the currently playing loops)
	 */
	public void fadeOutAllLoops()
	{
		for (final LoopPlayer p : loopPlayers) {
			p.fadeOut();
		}
	}
	
	
	/**
	 * Pause all loops (leave volume unchanged)
	 */
	public void pauseAllLoops()
	{
		for (final LoopPlayer p : loopPlayers) {
			p.pause();
		}
	}
	
	
	/**
	 * Set level of master volume (volume multiplier)
	 *
	 * @param volume level (0..1)
	 */
	public void setMasterVolume(double volume)
	{
		masterVolume.set(volume);
	}
	
	
	/**
	 * Set level of effects volume (volume multiplier)
	 *
	 * @param volume level (0..1)
	 */
	public void setEffectsVolume(double volume)
	{
		effectsVolume.set(volume);
	}
	
	
	/**
	 * Set level of loops volume (volume multiplier)
	 *
	 * @param volume level (0..1)
	 */
	public void setLoopsVolume(double volume)
	{
		loopsVolume.set(volume);
	}
	
	
	/**
	 * Get level of master volume (volume multiplier)
	 *
	 * @return level (0..1)
	 */
	public double getMasterVolume()
	{
		return masterVolume.get();
	}
	
	
	/**
	 * Get level of effects volume (volume multiplier)
	 *
	 * @return level (0..1)
	 */
	public double getEffectsVolume()
	{
		return effectsVolume.get();
	}
	
	
	/**
	 * Get level of loops volume (volume multiplier)
	 *
	 * @return level (0..1)
	 */
	public double getLoopsVolume()
	{
		return loopsVolume.get();
	}
}
