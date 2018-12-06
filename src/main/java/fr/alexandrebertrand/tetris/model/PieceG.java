package fr.alexandrebertrand.tetris.model;

import fr.alexandrebertrand.tetris.model.abstracts.Piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * G piece type
 *
 * @author Alexandre Bertrand
 */
public class PieceG extends Piece {

    /*
     * Attributes
     */

    /** Rotations of the G piece type */
    private static List<List<Point>> rotationOperations;

    /** Initial position of the G piece type */
    private static List<Point> initialPoints;

    /*
     * Constructor
     */

    /**
     * Main constructor of the G piece type
     * 
     * @param xCases Width of the grid (number of cases on the x axe)
     * @param yCases Height of the grid (number of cases on the y axe)
     */
    public PieceG(int xCases, int yCases) {
        super(xCases, yCases);
        this.colorValue = 7;
    }

    /*
     * Methods
     */

    /**
     * Initialize rotation operations of the G piece type
     */
    public static void initRotationOperations() {
        PieceG.rotationOperations = new ArrayList<>();
        final List<Point> a = new ArrayList<>();
        a.add(new Point(1, 1));
        a.add(new Point(0, 0));
        a.add(new Point(-1, 1));
        a.add(new Point(-1, -1));

        final List<Point> b = new ArrayList<>();
        b.add(new Point(1, -1));
        b.add(new Point(0, 0));
        b.add(new Point(1, 1));
        b.add(new Point(-1, 1));

        PieceG.rotationOperations.add(a);
        PieceG.rotationOperations.add(b);
        PieceG.rotationOperations.add(Piece.getInversedPoint(a));
        PieceG.rotationOperations.add(Piece.getInversedPoint(b));
    }

    /**
     * Initialize the initial position of the G piece type
     */
    public static void initInitialPosition() {
        PieceG.initialPoints = new ArrayList<>();
        PieceG.initialPoints.add(new Point(0, 1));
        PieceG.initialPoints.add(new Point(1, 1));
        PieceG.initialPoints.add(new Point(1, 0));
        PieceG.initialPoints.add(new Point(2, 1));
    }

    /*
     * Getters
     */

    /**
     * Get rotation operations of the G piece type
     * 
     * @return Rotation operation
     */
    @Override
    protected List<List<Point>> getRotationOperations() {
        return PieceG.rotationOperations;
    }

    /**
     * Get the initial position of the G piece type
     * 
     * @return Initial position
     */
    @Override
    protected List<Point> getInitialPosition() {
        return PieceG.initialPoints;
    }

}
