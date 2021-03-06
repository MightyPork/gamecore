package mightypork.gamecore.audio;


import mightypork.utils.interfaces.Destroyable;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Audio resource interface (backend independent)
 *
 * @author Ondřej Hruška (MightyPork)
 */
public interface IAudio extends Destroyable {
	
	/**
	 * Pause loop (remember position and stop playing) - if was looping
	 */
	void pauseLoop();
	
	
	/**
	 * Resume loop (if was looping and paused)
	 */
	void resumeLoop();
	
	
	/**
	 * Adjust gain for the currently playing effect (can be used for fading
	 * music)
	 *
	 * @param gain gain to set 0..1
	 */
	void adjustGain(double gain);
	
	
	/**
	 * Stop audio playback, free source. Meaningful for loops, may not work
	 * properly for effects.
	 */
	void stop();
	
	
	/**
	 * @return true if the audio is playing
	 */
	boolean isPlaying();
	
	
	/**
	 * @return trie if the audio is paused
	 */
	boolean isPaused();


	/**
	 * Play as sound effect at listener position
	 *
	 * @param gain gain
	 * @param pitch pitch
	 * @param loop looping
	 */
	void play(double gain, double pitch, boolean loop);
	
	
	/**
	 * Play as sound effect at given position
	 *
	 * @param gain gain
	 * @param pitch pitch
	 * @param loop looping
	 * @param x
	 * @param y
	 * @param z
	 */
	void play(double gain, double pitch, boolean loop, double x, double y, double z);
	
	
	/**
	 * Play as sound effect at given X-Y position
	 *
	 * @param gain gain
	 * @param pitch pitch
	 * @param loop looping
	 * @param x
	 * @param y
	 */
	void play(double gain, double pitch, boolean loop, double x, double y);
	
	
	/**
	 * Play as sound effect at given position
	 *
	 * @param gain gain
	 * @param pitch pitch
	 * @param loop looping
	 * @param pos coord
	 */
	void play(double gain, double pitch, boolean loop, Vect pos);
	
	
	/**
	 * Check if this audio is currently active (ie. playing or paused, not
	 * stopped)
	 *
	 * @return is active
	 */
	boolean isActive();
	
}
