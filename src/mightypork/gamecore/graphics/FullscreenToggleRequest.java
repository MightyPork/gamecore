package mightypork.gamecore.graphics;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.SingleReceiverEvent;


/**
 * Event that will request fullscreen toggle in the graphics module.<br>
 * FIXME the usefullness of this event is dubious.
 *
 * @author Ondřej Hruška (MightyPork)
 */
@SingleReceiverEvent
public class FullscreenToggleRequest extends BusEvent<GraphicsModule> {
	
	@Override
	protected void handleBy(GraphicsModule handler)
	{
		handler.switchFullscreen();
	}
}
