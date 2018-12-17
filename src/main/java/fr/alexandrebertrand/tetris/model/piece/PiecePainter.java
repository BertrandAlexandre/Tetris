package fr.alexandrebertrand.tetris.model.piece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import fr.alexandrebertrand.tetris.util.graphic.*;
import fr.alexandrebertrand.tetris.util.settings.*;

/**
 * Piece painter
 * 
 * @author Alexandre Bertrand
 */
public final class PiecePainter {

    /*
     * Constructors
     */

    /**
     * Empty private constructor
     */
    private PiecePainter() {
    }

    /*
     * Methods
     */

    /**
     * Paint current piece
     * 
     * @param g     Graphic context
     * @param piece Current piece
     */
    public static void paintCurrentPiece(Graphics g, Piece piece) {
        for (Point p: piece.getPoints()) {
            g.setColor(piece.getColorType().getBorderColor());
            PaintManager.fillBorderRect(g, p);
            g.setColor(piece.getColorType().getColor());
            PaintManager.fillRect(g, p);
        }
    }

    /**
     * Paint ghost piece of current piece
     * 
     * @param g     Graphic context
     * @param piece Ghost piece
     */
    public static void paintGhostPiece(Graphics g, Piece piece) {
        for (Point p: piece.getGhostPoints()) {
            g.setColor(piece.getColorType().getColor());
            PaintManager.fillBorderRect(g, p);
            g.setColor(Color.BLACK);
            PaintManager.fillRect(g, p);
        }
    }

    /**
     * Paint hold piece
     * 
     * @param g     Graphic context
     * @param piece Hold piece
     */
    public static void paintHoldPiece(Graphics g, Piece piece) {
        Point offset = getHoldPieceOffset(piece);
        for (Point p: piece.getPoints()) {
            g.setColor(piece.getColorType().getBorderColor());
            PaintManager.fillBorderRect(g, p, offset);
            g.setColor(piece.getColorType().getColor());
            PaintManager.fillRect(g, p, offset);
        }
    }

    /**
     * Paint next pieces
     * 
     * @param g      Graphic context
     * @param pieces Next pieces
     */
    public static void paintNextPieces(Graphics g, ArrayList<Piece> pieces) {
        for (int i = 0; i < 3; i++) {
            Point np = getNextPieceOffset(pieces.get(i), i);
            for (Point p: pieces.get(i).getPoints()) {
                g.setColor(pieces.get(i).getColorType().getBorderColor());
                PaintManager.fillBorderRect(g, p, np);
                g.setColor(pieces.get(i).getColorType().getColor());
                PaintManager.fillRect(g, p, np);
            }
        }
    }

    /**
     * Get hold piece offset
     * 
     * @param piece Hold piece
     */
    private static Point getHoldPieceOffset(Piece piece) {
        int x = (int) (Settings.getXCases() * Settings.getBoxSize() + 40d
                + (4 - piece.getPieceWidth()) * (Settings.getBoxSize() / 2));
        int y = (int) (220d - (2 - piece.getPieceHeight())
                * (Settings.getBoxSize() / 2));
        return new Point(x, y);
    }

    /**
     * Get next piece offset
     * 
     * @param piece Next piece
     */
    private static Point getNextPieceOffset(Piece piece, int index) {
        int x = (int) (Settings.getXCases() * Settings.getBoxSize() + 40d
                + (4 - piece.getPieceWidth()) * (Settings.getBoxSize() / 2));
        int y = (int) (285d - (2 - piece.getPieceHeight())
                * (Settings.getBoxSize() / 2) + index
                * (Settings.getBoxSize() * 2 + 10) + Settings.getBoxSize() * 2);
        return new Point(x, y);
    }

}
