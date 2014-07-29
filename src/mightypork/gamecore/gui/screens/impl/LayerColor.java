package mightypork.gamecore.gui.screens.impl;


import mightypork.gamecore.gui.components.painters.QuadPainter;
import mightypork.gamecore.gui.screens.Screen;
import mightypork.gamecore.gui.screens.ScreenLayer;
import mightypork.utils.math.color.Color;


/**
 * Screen overlay with a given color.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class LayerColor extends ScreenLayer {

	private final int zIndex;


	/**
	 * Overlay with color
	 *
	 * @param screen the parent screen
	 * @param color the used color
	 * @param zIndex z-index in the screen
	 */
	public LayerColor(Screen screen, Color color, int zIndex)
	{
		super(screen);

		final QuadPainter qp = new QuadPainter(color);
		qp.setRect(root);
		root.add(qp);
		this.zIndex = zIndex;
	}


	@Override
	public int getZIndex()
	{
		return this.zIndex;
	}

}
