package fr.alexandrebertrand.tetris.model.piece.type;

import fr.alexandrebertrand.tetris.model.piece.Piece;
import fr.alexandrebertrand.tetris.util.graphic.*;

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
     */
    public PieceD() {
        super(ColorType.YELLOW);
    }
    
    /**
     * Initialize the F piece type
     */
    static {
        // Initialize the initial position of the F piece type
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
     * 
     * @return Initial position
     */
    @Override
    protected List<Point> getInitialPosition() {
        return PieceD.initialPoints;
    }

}
