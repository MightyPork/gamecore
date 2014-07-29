package mightypork.gamecore.gui.components;


/**
 * Component whose width is derived from content.<br>
 * Used for Linear components.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public interface DynamicWidthComponent extends Component {
	
	/**
	 * Get current width, if the element has specified height
	 *
	 * @param height current height
	 * @return current width
	 */
	double computeWidth(double height);
}
