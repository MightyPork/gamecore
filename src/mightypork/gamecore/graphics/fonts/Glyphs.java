package mightypork.gamecore.graphics.fonts;


/**
 * Glyph tables, can be used for font loading.<br>
 * The font should also always add a space glyph.
 *
 * @author Ondřej Hruška (MightyPork)
 */
public class Glyphs {
	
	/** A-Z a-z */
	public static final String latin = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	/** Extra variants of latin glyphs */
	public static final String latin_extra = "ŒÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜŸÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿĚŠČŘŽŤŇĎŮěščřžťňďůŁłđ";
	
	/** 0-9 */
	public static final String numbers = "0123456789";
	
	/** Commonly used punctuation symbols */
	public static final String punctuation = ".-,.?!:;\"'";
	
	/** Less common punctuation symbols */
	public static final String punctuation_extra = "()¿¡»«›‹“”‘’„…";

	/** Commonly used symbols (that are not included in punctuation) */
	public static final String symbols = "[]{}#$%&§*+/<=>@\\^_|~°";
	
	/** Less common symbols */
	public static final String symbols_extra = "¥€£¢`ƒ†‡ˆ‰•¤¦¨ªº¹²³¬­¯±´µ¶·¸¼½¾×÷™©­®→↓←↑";
	
	/** Latin, numbers, punctuation and symbols */
	public static final String basic = latin + numbers + punctuation + symbols;
	
	/** Extra glyphs to accompany "basic" */
	public static final String extra = latin_extra + punctuation_extra + symbols_extra;
	
	/** Basic + Extra */
	public static final String all = basic + extra;
	
}
