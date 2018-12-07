package fr.alexandrebertrand.tetris.util.graphic;

import java.awt.Color;

/**
 * Case color for cases and pieces
 *
 * @author Alexandre Bertrand
 */
public enum ColorType {

    /*
     * Constants
     */

    /** Cyan coor */
    CYAN(new Color(43, 172, 226), new Color(0, 112, 202)),

    /** Blue color */
    BLUE(new Color(0, 90, 157), new Color(0, 0, 107)),

    /** Orange color */
    ORANGE(new Color(248, 150, 34), new Color(167, 75, 0)),

    /** Yellow color */
    YELLOW(new Color(253, 255, 0), new Color(235, 160, 0)),

    /** Red color */
    RED(new Color(238, 39, 51), new Color(138, 0, 0)),

    /** Green color */
    GREEN(new Color(78, 183, 72), new Color(0, 146, 0)),

    /** Magenta color */
    MAGENTA(new Color(146, 43, 140), new Color(94, 0, 94));

    /*
     * Attributes
     */

    /** Color of the case */
    private final Color color;

    /** Border color of the case */
    private final Color borderColor;

    /*
     * Constructor
     */

    /**
     * Initialize new constant representing a set of
     * color for a case
     * 
     * @param color       Color of the case
     * @param borderColor Border color of the case
     */
    ColorType(Color color, Color borderColor) {
        this.color = color;
        this.borderColor = borderColor;
    }

    /*
     * Getters
     */

    /**
     * Get the main color of the casecolor
     * 
     * @return Main color of the casecolor
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the border color of the casecolor
     * 
     * @return Border color of the casecolor
     */
    public Color getBorderColor() {
        return borderColor;
    }

}
