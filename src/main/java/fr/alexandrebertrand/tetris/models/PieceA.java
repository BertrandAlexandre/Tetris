package fr.alexandrebertrand.tetris.models;

import fr.alexandrebertrand.tetris.models.abstracts.Piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A piece type
 *
 * @author Alexandre Bertrand
 */
public class PieceA extends Piece {

    /*
     * Attributes
     */

    /** Rotations of the A piece type */
    private static List<List<Point>> rotationOperations;

    /** Initial position of the A piece type */
    private static List<Point> initialPoints;

    /*
     * Constructor
     */

    /**
     * Main constructor of the A piece type
     * @param xCases Width of the grid (number of cases on the x axe)
     * @param yCases Height of the grid (number of cases on the y axe)
     */
    public PieceA(int xCases, int yCases) {
        super(xCases, yCases);
        this.colorValue = 1;
    }

    /*
     * Methods
     */

    /**
     * Initialize rotation operations of the A piece type
     */
    public static void initRotationOperations() {
        PieceA.rotationOperations = new ArrayList<>();
        List<Point> a = new ArrayList<>();
        a.add(new Point(1, 2));
        a.add(new Point(0, 1));
        a.add(new Point(-1, 0));
        a.add(new Point(-2, -1));

        List<Point> b = new ArrayList<>();
        b.add(new Point(2, -1));
        b.add(new Point(1, 0));
        b.add(new Point(0, 1));
        b.add(new Point(-1, 2));

        PieceA.rotationOperations.add(a);
        PieceA.rotationOperations.add(b);
        PieceA.rotationOperations.add(Piece.getInversedPoint(a));
        PieceA.rotationOperations.add(Piece.getInversedPoint(b));
    }

    /**
     * Initialize the initial position of the A piece type
     */
    public static void initInitialPosition() {
        PieceA.initialPoints = new ArrayList<>();
        PieceA.initialPoints.add(new Point(0, 1));
        PieceA.initialPoints.add(new Point(1, 1));
        PieceA.initialPoints.add(new Point(2, 1));
        PieceA.initialPoints.add(new Point(3, 1));
    }

    /*
     * Getters
     */

    /**
     * Get rotation operations of the A piece type
     * @return Rotation operation
     */
    @Override
    protected List<List<Point>> getRotationOperations() {
        return PieceA.rotationOperations;
    }

    /**
     * Get the initial position of the A piece type
     * @return Initial position
     */
    @Override
    protected List<Point> getInitialPosition() {
        return PieceA.initialPoints;
    }

}
