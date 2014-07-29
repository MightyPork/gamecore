package mightypork.gamecore.resources;


import mightypork.gamecore.audio.SoundRegistry;
import mightypork.gamecore.audio.players.EffectPlayer;
import mightypork.gamecore.audio.players.LoopPlayer;
import mightypork.gamecore.graphics.fonts.FontRegistry;
import mightypork.gamecore.graphics.fonts.IFont;
import mightypork.gamecore.graphics.textures.ITexture;
import mightypork.gamecore.graphics.textures.TextureRegistry;
import mightypork.gamecore.graphics.textures.TxQuad;
import mightypork.gamecore.graphics.textures.TxSheet;


/**
 * Static resource repository
 *
 * @author Ondřej Hruška (MightyPork)
 */
public final class Res {
	
	private static TextureRegistry textures = new TextureRegistry();
	private static SoundRegistry sounds = new SoundRegistry();
	private static FontRegistry fonts = new FontRegistry();
	
	
	/**
	 * Get a texture by key
	 *
	 * @param key the key
	 * @return texture
	 */
	public static ITexture texture(String key)
	{
		return textures.getTexture(key);
	}
	
	
	/**
	 * Get a texture sheet by key
	 *
	 * @param key the key
	 * @return sheet
	 */
	public static TxSheet txSheet(String key)
	{
		return textures.getSheet(key);
	}
	
	
	/**
	 * Get a texture quad by key
	 *
	 * @param key the key
	 * @return quad
	 */
	public static TxQuad txQuad(String key)
	{
		return textures.getQuad(key);
	}
	
	
	/**
	 * Get a sound loop player by key
	 *
	 * @param key the key
	 * @return loop player
	 */
	public static LoopPlayer loop(String key)
	{
		return sounds.getLoop(key);
	}
	
	
	/**
	 * Get a sound effect player by key
	 *
	 * @param key the key
	 * @return effect player
	 */
	public static EffectPlayer sound(String key)
	{
		return sounds.getEffect(key);
	}


	/**
	 * Get a font by key
	 *
	 * @param key the key
	 * @return font
	 */
	public static IFont font(String key)
	{
		return fonts.getFont(key);
	}
	
	
	/**
	 * Get internal texture registry
	 *
	 * @return registry
	 */
	public static TextureRegistry getTextureRegistry()
	{
		return textures;
	}


	/**
	 * Get internal font registry
	 *
	 * @return registry
	 */
	public static FontRegistry getFontRegistry()
	{
		return fonts;
	}
	
	
	/**
	 * Get internal sound registry
	 *
	 * @return registry
	 */
	public static SoundRegistry getSoundRegistry()
	{
		return sounds;
	}


	/**
	 * Load resources by a resource initializer.
	 *
	 * @param initializer the resource initializer
	 */
	public static void load(ResourceInitializer initializer)
	{
		initializer.addFonts(fonts);
		initializer.addTextures(textures);
		initializer.addSounds(sounds);
	}
	
}
