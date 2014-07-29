package mightypork.gamecore.gui.screens.impl;


import mightypork.gamecore.core.App;
import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.events.ScreenRequest;
import mightypork.gamecore.gui.screens.Overlay;
import mightypork.gamecore.gui.screens.ScreenRegistry;
import mightypork.utils.math.animation.Easing;
import mightypork.utils.math.animation.NumAnimated;
import mightypork.utils.math.color.pal.RGB;
import mightypork.utils.math.timing.TimedTask;


/**
 * Overlay used for cross-fading between screens in {@link ScreenRegistry}
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class CrossfadeOverlay extends Overlay {
	
	private static final double T_IN = 0.4;
	private static final double T_OUT = 0.6;
	
	NumAnimated alpha = new NumAnimated(0);
	String requestedScreenName;
	
	TimedTask revealTask = new TimedTask() {
		
		@Override
		public void run()
		{
			if (requestedScreenName == null) {
				App.shutdown();
			} else {
				App.bus().send(new ScreenRequest(requestedScreenName));
			}
			alpha.setEasing(Easing.SINE_OUT);
			alpha.fadeOut(T_OUT);
		}
	};
	
	
	/**
	 * Create new crossfade overlay
	 */
	public CrossfadeOverlay()
	{
		final QuadPainter qp = new QuadPainter(RGB.BLACK); // TODO allow custom colors
		qp.setRect(root);
		root.add(qp);
		
		updated.add(alpha);
		updated.add(revealTask);
		
		setAlpha(alpha);
	}
	
	
	@Override
	public int getZIndex()
	{
		return 10000; // not too high, so app can put something on top
	}
	
	
	/**
	 * Go to specified screen
	 *
	 * @param screen screen alias
	 * @param fromDark true to fade from dark (ie. first screen in application)
	 */
	public void goToScreen(String screen, boolean fromDark)
	{
		requestedScreenName = screen;
		
		if (screen == null) {
			// going for halt
			App.sound().fadeOutAllLoops();
		}
		
		if (fromDark) {
			alpha.setTo(1);
			revealTask.run();
		} else {
			revealTask.start(T_IN);
			
			alpha.setEasing(Easing.SINE_IN);
			alpha.fadeIn(T_IN);
		}
	}
	
}
