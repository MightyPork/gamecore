package mightypork.gamecore.audio;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;
import mightypork.gamecore.core.App;
import mightypork.utils.exceptions.KeyAlreadyExistsException;


/**
 * Audio resource storage
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class SoundRegistry {

	private final Map<String, EffectPlayer> effects = new HashMap<>();
	private final Map<String, LoopPlayer> loops = new HashMap<>();


	/**
	 * Register effect resource
	 *
	 * @param key sound key
	 * @param resourcePath path to the effect resource
	 * @param gain gain adjustment
	 * @param pitch pitch adjustment
	 * @return the just created effect player
	 */
	public EffectPlayer addEffect(String key, String resourcePath, double gain, double pitch)
	{
		final EffectPlayer effect = App.sound().createEffect(resourcePath);

		effect.setPitch(pitch);
		effect.setGain(gain);

		addEffect(key, effect);

		return effect;
	}


	/**
	 * Register effect resource
	 *
	 * @param key sound key
	 * @param effect the effect to add (Obtained from audio module)
	 */
	public void addEffect(String key, EffectPlayer effect)
	{
		if (effects.containsKey(key)) throw new KeyAlreadyExistsException();

		effects.put(key, effect);
	}


	/**
	 * Register loop resource (music / effect loop)
	 *
	 * @param key sound key
	 * @param resourcePath path to the effect resource
	 * @param gain gain adjustment
	 * @param pitch pitch adjustment
	 * @param fadeIn fadeIn time (s)
	 * @param fadeOut fadeOut time (s)
	 * @return the just created loop player
	 */
	public LoopPlayer addLoop(String key, String resourcePath, double gain, double pitch, double fadeIn, double fadeOut)
	{
		final LoopPlayer loop = App.sound().createLoop(resourcePath);
		
		loop.setPitch(pitch);
		loop.setGain(gain);
		loop.setFadeTimes(fadeIn, fadeOut);
		
		addLoop(key, loop);
		
		return loop;
	}


	/**
	 * Register loop resource (music / effect loop)
	 *
	 * @param key sound key
	 * @param loop the loop to add (Obtained from audio module)
	 */
	public void addLoop(String key, LoopPlayer loop)
	{
		if (loops.containsKey(key)) throw new KeyAlreadyExistsException();

		loops.put(key, loop);
	}


	/**
	 * Get a loop player for key
	 *
	 * @param key sound key
	 * @return loop player
	 */
	public LoopPlayer getLoop(String key)
	{
		final LoopPlayer p = loops.get(key);
		if (p == null) {
			throw new RuntimeException("Unknown sound loop \"" + key + "\".");
		}
		return p;
	}


	/**
	 * Get a effect player for key
	 *
	 * @param key sound key
	 * @return effect player
	 */
	public EffectPlayer getEffect(String key)
	{
		final EffectPlayer p = effects.get(key);
		if (p == null) {
			throw new RuntimeException("Unknown sound effect \"" + key + "\".");
		}
		return p;
	}
}
