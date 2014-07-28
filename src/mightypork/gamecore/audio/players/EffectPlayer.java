package mightypork.gamecore.audio.players;


import mightypork.gamecore.audio.IAudio;
import mightypork.gamecore.audio.Volume;
import mightypork.utils.math.constraints.vect.Vect;


/**
 * Player for one-off effects
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class EffectPlayer extends AudioPlayer {
	
	/**
	 * @param track audio resource
	 * @param volume volume control
	 */
	public EffectPlayer(IAudio track, Volume volume)
	{
		super(track, volume);
	}
	
	
	/**
	 * Play at listener
	 *
	 * @param gain play gain
	 * @param pitch play pitch
	 */
	public void play(double gain, double pitch)
	{
		if (!hasAudio()) return;
		
		getAudio().play(computeGain(gain), computePitch(pitch), false);
	}
	
	
	/**
	 * Play at listener
	 *
	 * @param gain play gain
	 */
	public void play(double gain)
	{
		play(gain, 1);
	}
	
	
	/**
	 * Play at given position
	 *
	 * @param gain play gain
	 * @param pos play position
	 */
	public void play(double gain, Vect pos)
	{
		play(gain, 1, pos);
	}
	
	
	/**
	 * Play at given position
	 *
	 * @param gain play gain
	 * @param pitch play pitch
	 * @param pos play position
	 */
	public void play(double gain, double pitch, Vect pos)
	{
		if (!hasAudio()) return;
		
		getAudio().play(computeGain(gain), computePitch(pitch), false, pos);
	}
	
}
