package mightypork.gamecore.audio;


import java.util.HashMap;
import java.util.Map;

import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;


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
	 * @param effect the effect to add (Obtained from audio module)
	 */
	public void addEffect(String key, EffectPlayer effect)
	{
		effects.put(key, effect);
	}
	
	
	/**
	 * Register loop resource (music / effect loop)
	 *
	 * @param key sound key
	 * @param loop the loop to add (Obtained from audio module)
	 */
	public void addLoop(String key, LoopPlayer loop)
	{
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
