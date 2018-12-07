package fr.alexandrebertrand.tetris.model.piece.type;

import fr.alexandrebertrand.tetris.model.piece.Piece;
import fr.alexandrebertrand.tetris.util.graphic.*;

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
     */
    public PieceA() {
        super(ColorType.CYAN);
    }

    /**
     * Initialize the A piece type
     */
    static {
        // Initialize rotation operations of the A piece type
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

        // Initialize the initial position of the A piece type
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
     * 
     * @return Rotation operation
     */
    @Override
    protected List<List<Point>> getRotationOperations() {
        return PieceA.rotationOperations;
    }

    /**
     * Get the initial position of the A piece type
     * 
     * @return Initial position
     */
    @Override
    protected List<Point> getInitialPosition() {
        return PieceA.initialPoints;
    }

}
