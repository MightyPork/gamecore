package mightypork.gamecore.audio.players;


import mightypork.gamecore.audio.IAudio;
import mightypork.gamecore.audio.Volume;
import mightypork.utils.interfaces.Destroyable;


/**
 * Base of an audio player
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class AudioPlayer implements Destroyable {
	
	/** the track */
	private final IAudio audio;
	
	/** base gain for sfx */
	private double baseGain;
	
	/** base pitch for sfx */
	private double basePitch;
	
	/** dedicated volume control */
	private final Volume gainMultiplier;
	
	
	/**
	 * @param track audio resource
	 * @param volume colume control
	 */
	public AudioPlayer(IAudio track, Volume volume)
	{
		this.audio = track;
		
		if (volume == null) volume = new Volume(1D);
		
		this.gainMultiplier = volume;
	}
	
	
	@Override
	public void destroy()
	{
		audio.destroy();
	}
	
	
	/**
	 * @return audio resource
	 */
	protected IAudio getAudio()
	{
		return audio;
	}
	
	
	/**
	 * Get play gain, computed based on volume and given multiplier
	 *
	 * @param multiplier extra volume adjustment
	 * @return computed gain
	 */
	protected double computeGain(double multiplier)
	{
		return baseGain * gainMultiplier.get() * multiplier;
	}
	
	
	/**
	 * Get pitch
	 *
	 * @param multiplier pitch adjustment
	 * @return computed pitch
	 */
	protected double computePitch(double multiplier)
	{
		return basePitch * multiplier;
	}
	
	
	/**
	 * Get if audio is valid
	 *
	 * @return is valid
	 */
	protected boolean hasAudio()
	{
		return (audio != null);
	}
	
	
	/**
	 * Set base gain. 1 is original volume, 0 is silence.
	 *
	 * @param gain base gain
	 */
	public void setGain(double gain)
	{
		this.baseGain = gain;
	}
	
	
	/**
	 * Set base pitch. 1 is original pitch, less is deeper, more is higher.
	 *
	 * @param pitch base pitch
	 */
	public void setPitch(double pitch)
	{
		this.basePitch = pitch;
	}

}
