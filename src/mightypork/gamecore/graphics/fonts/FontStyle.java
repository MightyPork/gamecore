package mightypork.gamecore.graphics.fonts;

/**
 * Font style enum
 */
public enum FontStyle
{
	/** Plan style */
	PLAIN(0),
	/** Bold style */
	BOLD(1),
	/** Italic style */
	ITALIC(2),
	/** Bond and italic together */
	BOLD_ITALIC(1 + 2);
	
	/** Number associated with the style */
	public int numval;
	
	
	/**
	 * Font style
	 *
	 * @param style style index as in awt Font. Not using constants to be
	 *            independent on awt.
	 */
	private FontStyle(int style)
	{
		this.numval = style;
	}
}