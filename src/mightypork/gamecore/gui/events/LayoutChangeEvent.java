package mightypork.gamecore.gui.events;


import mightypork.utils.eventbus.BusEvent;
import mightypork.utils.eventbus.events.flags.DirectEvent;
import mightypork.utils.eventbus.events.flags.NonConsumableEvent;
import mightypork.utils.eventbus.events.flags.NonRejectableEvent;


/**
 * Intended use is to notify UI component sub-clients that they should poll
 * their cached constraints.<br>
 * Is {@link NonRejectableEvent} to force update even of hidden screens and
 * layers.
 *
 * @author Ondřej Hruška (MightyPork)
 */
@DirectEvent
@NonConsumableEvent
@NonRejectableEvent
public class LayoutChangeEvent extends BusEvent<LayoutChangeListener> {

	@Override
	public void handleBy(LayoutChangeListener handler)
	{
		handler.onLayoutChanged();
	}
}
