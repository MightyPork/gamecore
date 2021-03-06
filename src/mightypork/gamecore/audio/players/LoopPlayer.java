package mightypork.gamecore.audio.players;


import mightypork.gamecore.audio.DeferredAudio;
import mightypork.gamecore.audio.Volume;
import mightypork.utils.interfaces.Pauseable;
import mightypork.utils.interfaces.Updateable;
import mightypork.utils.math.animation.NumAnimated;


/**
 * Audio loop player (with fading, for music)
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class LoopPlayer extends AudioPlayer implements Updateable, Pauseable {
	
	/** animator for fade in and fade out */
	private final NumAnimated fadeAnim = new NumAnimated(0);

	private double lastUpdateGain = 0;

	/** flag that track is paused */
	private boolean paused = true;

	/** Default fadeIn time */
	private double inTime = 1;

	/** Default fadeOut time */
	private double outTime = 1;


	/**
	 * @param track audio resource
	 * @param volume volume control
	 */
	public LoopPlayer(DeferredAudio track, Volume volume)
	{
		super(track, volume);

		paused = true;
	}
	
	
	/**
	 * Set fading duration (seconds)
	 *
	 * @param in duration of fade-in
	 * @param out duration of fade-out
	 */
	public void setFadeTimes(double in, double out)
	{
		inTime = in;
		outTime = out;
	}


	private void initLoop()
	{
		if (hasAudio() && !getAudio().isActive()) {
			getAudio().play(computeGain(1), computePitch(1), true);
			getAudio().pauseLoop();
		}
	}


	@Override
	public void pause()
	{
		if (!hasAudio() || paused) return;

		initLoop();

		getAudio().pauseLoop();
		paused = true;
	}


	@Override
	public boolean isPaused()
	{
		return paused;
	}


	/**
	 * Alias to resume (more meaningful name)
	 */
	public void play()
	{
		resume();
	}


	@Override
	public void resume()
	{
		if (!hasAudio() || !paused) return;

		initLoop();

		paused = false;

		getAudio().adjustGain(computeGain(fadeAnim.value()));
	}


	@Override
	public void update(double delta)
	{
		if (!hasAudio() || paused) return;

		initLoop();

		fadeAnim.update(delta);

		final double gain = computeGain(fadeAnim.value());
		if (!paused && gain != lastUpdateGain) {
			getAudio().adjustGain(gain);
			lastUpdateGain = gain;
		}

		if (gain == 0 && !paused) pause(); // pause on zero volume
	}


	/**
	 * Resume if paused, and fade in (pick up from current volume).
	 *
	 * @param fadeTime fade time (s)
	 */
	public void fadeIn(double fadeTime)
	{
		if (!hasAudio()) return;

		if (isPaused()) fadeAnim.setTo(0);
		resume();
		fadeAnim.fadeIn(fadeTime);
	}


	/**
	 * Fade out and pause when reached zero volume
	 *
	 * @param fadeTime fade time (s)
	 */
	public void fadeOut(double fadeTime)
	{
		if (!hasAudio()) return;
		if (isPaused()) return;
		fadeAnim.fadeOut(fadeTime);
	}


	/**
	 * Fade in with default duration
	 */
	public void fadeIn()
	{
		fadeIn(inTime);
	}


	/**
	 * Fade out with default duration
	 */
	public void fadeOut()
	{
		fadeOut(outTime);
	}

}
