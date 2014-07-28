package mightypork.gamecore.gui.events;


/**
 * Receives notifications about layout change
 *
 * @author Ondřej Hruška (MightyPork)
 */
public interface LayoutChangeListener {
	
	/**
	 * Triggered when display size changed and GUI should be recalculated.
	 */
	public void onLayoutChanged();
}
