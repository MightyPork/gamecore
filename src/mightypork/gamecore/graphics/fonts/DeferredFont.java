package mightypork.gamecore.graphics.fonts;


import mightypork.gamecore.graphics.textures.FilterMode;
import mightypork.gamecore.resources.BaseDeferredResource;


/**
 * Deferred font stub.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public abstract class DeferredFont extends BaseDeferredResource implements IFont {
	
	/**
	 * Requested font size. For bitmap fonts, this should match the actual font
	 * size (in pixels). The font can be scaled after loaded, but it may be
	 * cached with this size.
	 */
	protected double size = 12;
	
	/** Requested font style. If not applicable, fall back to PLAIN */
	protected FontStyle style = FontStyle.PLAIN;
	
	/**
	 * Chars that are required to be loaded in the font. A space glyph must be
	 * also added when loading.
	 */
	protected String chars = Glyphs.basic;
	
	/** Requested filtering mode */
	protected FilterMode filter = FilterMode.NEAREST;
	
	/** Whether to use anti-aliasing for the font. */
	protected boolean antialias = false;
	
	/**
	 * Ratio of the font to discard at the top (how much of the glyphs height is
	 * blank from top)
	 */
	protected double discardTop = 0;
	
	/**
	 * Ratio of the font to discard at the bottom (how much of the glyphs height
	 * is blank from bottom)
	 */
	protected double discardBottom = 0;
	
	
	/**
	 * Make a font from resource
	 *
	 * @param resource the font resource
	 */
	public DeferredFont(String resource)
	{
		super(resource);
	}
	
	
	/**
	 * Set font size. If the font is backed by a texture, this is the size at
	 * which the font is rendered to the texture. For bitmap fonts, this should
	 * match the font height in px.
	 *
	 * @param size font size
	 */
	public final void setSize(double size)
	{
		this.size = size;
	}
	
	
	/**
	 * Set desired font style
	 *
	 * @param style style
	 */
	public final void setStyle(FontStyle style)
	{
		this.style = style;
	}
	
	
	/**
	 * Set what chars are to be loaded. The space glyph will be loaded always.
	 *
	 * @param chars String containing chars to load (duplicates are ignored)
	 */
	public final void setChars(String chars)
	{
		this.chars = chars;
	}
	
	
	/**
	 * Set texture filtering mode. For bitmap fonts, set to NEAREST.
	 *
	 * @param filter filter mode.
	 */
	public final void setFilter(FilterMode filter)
	{
		this.filter = filter;
	}
	
	
	/**
	 * Set whether to use antialiasing.
	 *
	 * @param antialias antialias
	 */
	public final void setAntialias(boolean antialias)
	{
		this.antialias = antialias;
	}
	
	
	@Override
	public final void setDiscardRatio(double top, double bottom)
	{
		discardTop = top;
		discardBottom = bottom;
	}
	
	
	@Override
	public final double getTopDiscardRatio()
	{
		return discardTop;
	}
	
	
	@Override
	public final double getBottomDiscardRatio()
	{
		return discardBottom;
	}
	
}
