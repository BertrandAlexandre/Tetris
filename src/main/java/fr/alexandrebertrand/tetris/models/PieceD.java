package fr.alexandrebertrand.tetris.models;

import fr.alexandrebertrand.tetris.models.abstracts.Piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * D piece type
 *
 * @author Alexandre Bertrand
 */
public class PieceD extends Piece {

    /*
     * Attribute
     */

    /** Initial position of the D piece type */
    private static List<Point> initialPoints;

    /*
     * Constructor
     */

    /**
     * Main constructor of the D piece type
     * @param xCases Width of the grid (number of cases on the x axe)
     * @param yCases Height of the grid (number of cases on the y axe)
     */
    public PieceD(int xCases, int yCases) {
        super(xCases, yCases);
        this.colorValue = 4;
    }

    /*
     * Method
     */

    /**
     * Initialize the initial position of the F piece type
     */
    public static void initInitialPosition() {
        PieceD.initialPoints = new ArrayList<>();
        PieceD.initialPoints.add(new Point(0, 0));
        PieceD.initialPoints.add(new Point(1, 0));
        PieceD.initialPoints.add(new Point(0, 1));
        PieceD.initialPoints.add(new Point(1, 1));
    }

    /*
     * Getters
     */

    /**
     * Get the initial position of the F piece type
     * @return Initial position
     */
    @Override
    protected List<Point> getInitialPosition() {
        return PieceD.initialPoints;
    }

}
