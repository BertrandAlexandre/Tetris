package fr.alexandrebertrand.tetris.util.graphic;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.HashMap;

/**
 * Font manager
 * 
 * @author Alexandre Bertrand
 */
public final class FontManager {

    /*
     * Constants
     */

    /** Path of the main font of the game */
    private static final String PATH =
            "/resources/fonts/Tetris.ttf";

    /** Spacing between characters */
    private static final double SPACING = 0.1d;

    /*
     * Attributes
     */

    /** Main font of the game */
    private static Font font;

    /** Current font attributes */
    private static HashMap<TextAttribute, Object> fontAttributes;

    /*
     * Constructors
     */

    /**
     * Empty private constructor
     */
    private FontManager() {
    }

    /*
     * Methods
     */

    /**
     * Initialize game main font
     * 
     * @param board Board class
     */
    public static void initFont(@SuppressWarnings("rawtypes") Class main) {
        try {
            fontAttributes = new HashMap<>();
            fontAttributes.put(TextAttribute.TRACKING, SPACING);
            font = Font.createFont(0, main.getResourceAsStream(PATH));
        } catch(FontFormatException | IOException e){
            font = new Font ("Serif", Font.PLAIN, 15);
        }
    }

    /**
     * Get main font of the game
     * 
     * @param size  Size of the text
     * @return Font of the game
     */
    public static Font getFont(float size) {
        return font.deriveFont(Font.PLAIN, size).deriveFont(fontAttributes);
    }

}
