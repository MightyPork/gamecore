package mightypork.gamecore.graphics.textures;


/**
 * Texture wrap mode (policy when rendered on larger area than can be covered by
 * the texture)
 *
 * @author Ondřej Hruška (MightyPork)
 */
public enum WrapMode
{
	/** transparent in the overlap area */
	CLAMP,
	/** repeat the texture (tiling) */
	REPEAT;
}
