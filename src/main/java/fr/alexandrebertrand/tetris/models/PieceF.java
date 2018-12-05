package fr.alexandrebertrand.tetris.models;

import fr.alexandrebertrand.tetris.models.abstracts.Piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * F piece type
 *
 * @author Alexandre Bertrand
 */
public class PieceF extends Piece {

    /*
     * Attributes
     */

    /** Rotations of the F piece type */
    private static List<List<Point>> rotationOperations;

    /** Initial position of the F piece type */
    private static List<Point> initialPoints;

    /*
     * Constructor
     */

    /**
     * Main constructor of the F piece type
     * 
     * @param xCases Width of the grid (number of cases on the x axe)
     * @param yCases Height of the grid (number of cases on the y axe)
     */
    public PieceF(int xCases, int yCases) {
        super(xCases, yCases);
        this.colorValue = 6;
    }

    /*
     * Methods
     */

    /**
     * Initialize rotation operations of the F piece type
     */
    public static void initRotationOperations() {
        PieceF.rotationOperations = new ArrayList<>();
        final List<Point> a = new ArrayList<>();
        a.add(new Point(1, 1));
        a.add(new Point(0, 0));
        a.add(new Point(-1, 1));
        a.add(new Point(-2, 0));

        final List<Point> b = new ArrayList<>();
        b.add(new Point(1, -1));
        b.add(new Point(0, 0));
        b.add(new Point(1, 1));
        b.add(new Point(0, 2));

        PieceF.rotationOperations.add(a);
        PieceF.rotationOperations.add(b);
        PieceF.rotationOperations.add(Piece.getInversedPoint(a));
        PieceF.rotationOperations.add(Piece.getInversedPoint(b));
    }

    /**
     * Initialize the initial position of the F piece type
     */
    public static void initInitialPosition() {
        PieceF.initialPoints = new ArrayList<>();
        PieceF.initialPoints.add(new Point(0, 1));
        PieceF.initialPoints.add(new Point(1, 1));
        PieceF.initialPoints.add(new Point(1, 0));
        PieceF.initialPoints.add(new Point(2, 0));
    }

    /*
     * Getters
     */

    /**
     * Get rotation operations of the F piece type
     * 
     * @return Rotation operation
     */
    @Override
    protected List<List<Point>> getRotationOperations() {
        return PieceF.rotationOperations;
    }

    /**
     * Get the initial position of the F piece type
     * 
     * @return Initial position
     */
    @Override
    protected List<Point> getInitialPosition() {
        return PieceF.initialPoints;
    }

}
