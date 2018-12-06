package fr.alexandrebertrand.tetris.util;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Colorset for cases and pieces
 *
 * @author Alexandre Bertrand
 */
public final class ColorManager {

    /*
     * Attribute
     */

    /** List of cases colors */
    private static ArrayList<ColorType> colorSet;

    /*
     * Constructor
     */

    /**
     * Default constructor to define colorset
     */
    private ColorManager() {
    }

    /*
     * Method
     */

    /**
     * Initialize the color set
     */
    public static void initialize() {
        colorSet = new ArrayList<>();
        colorSet.add(ColorType.CYAN);
        colorSet.add(ColorType.BLUE);
        colorSet.add(ColorType.ORANGE);
        colorSet.add(ColorType.YELLOW);
        colorSet.add(ColorType.RED);
        colorSet.add(ColorType.GREEN);
        colorSet.add(ColorType.MAGENTA);
    }

    /*
     * Getters
     */

    /**
     * Get color from identifiant
     * 
     * @param colorNumber Identifiant of the color
     * @return Searched color
     */
    public static Color getColor(int colorNumber) {
        if (colorNumber <=0) {
            return Color.BLACK;
        }
        return colorSet.get(colorNumber - 1).getColor();
    }

    /**
     * Get border color from identifiant
     * 
     * @param colorNumber Identifiant of the border color
     * @return Searched border color
     */
    public static Color getBorderColor(int colorNumber) {
        if (colorNumber <=0) {
            return Color.BLACK;
        }
        return colorSet.get(colorNumber - 1).getBorderColor();
    }

}
