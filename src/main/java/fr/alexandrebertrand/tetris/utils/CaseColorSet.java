package fr.alexandrebertrand.tetris.utils;

import java.util.ArrayList;

/**
 * Colorset for cases and pieces
 *
 * @author Alexandre Bertrand
 */
public class CaseColorSet {

    /*
     * Attribute
     */

    /** List of cases colors */
    public ArrayList<CaseColor> caseColors;

    /*
     * Constructor
     */

    /**
     * Default constructor to define colorset
     */
    public CaseColorSet() {
        caseColors = new ArrayList<>();
        caseColors.add(CaseColor.CYAN);
        caseColors.add(CaseColor.BLUE);
        caseColors.add(CaseColor.ORANGE);
        caseColors.add(CaseColor.YELLOW);
        caseColors.add(CaseColor.RED);
        caseColors.add(CaseColor.GREEN);
        caseColors.add(CaseColor.MAGENTA);
    }

    /**
     * Method
     */

    /**
     * Add a color to colorset
     * 
     * @param caseColor Color to add
     */
    public void addCaseColor(CaseColor caseColor) {
        caseColors.add(caseColor);
    }

    /*
     * Getter
     */

    /**
     * Get colorset
     * 
     * @return Return list of colors of colorset
     */
    public ArrayList<CaseColor> getCaseColors() {
        return caseColors;
    }

}
