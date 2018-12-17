package fr.alexandrebertrand.tetris.model.piece;

import fr.alexandrebertrand.tetris.model.grid.Line;
import fr.alexandrebertrand.tetris.util.graphic.*;
import fr.alexandrebertrand.tetris.util.settings.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexandre Bertrand
 */
public class Piece {
    
    /*
     * Attributes
     */
    
    /** Position of the piece */
    private final List<Point> points;
    
    /** Position of the ghost */
    private final List<Point> ghostPoints;
    
    /** Current rotation state of the piece */
    private int curRotation;
    
    /** Space between piece and left edge of the board */
    private final int leftSpace;
    
    /** Color type of the piece */
    private ColorType colorType;
            
    /*
     * Constructors
     */
    
    /**
     * Default constructor
     */
    public Piece(ColorType colorType) {
        this.colorType = colorType;
        points = new ArrayList<>();
        ghostPoints = new ArrayList<>();
        curRotation = 0;
        leftSpace = (int) Math.floor((Settings.getXCases() - 4d) / 2);
        setPointsAsInitial();
    }
    
    /*
     * Methods
     */
    
    /**
     * Get rotations operations of the piece
     * 
     * @return Rotations operations of the piece
     */
    protected List<List<Point>> getRotationOperations() {
        return null;
    }
    
    /**
     * Get initial position of the piece
     * 
     * @return Initial position of the piece
     */
    protected List<Point> getInitialPosition() {
        return null;
    }
    
    /**
     * Get inverted points from list of point
     * 
     * @param points Points to invert
     * @return Inverted points
     */
    protected static List<Point> getInversedPoint(List<Point> points) {
        List<Point> i = new ArrayList<>();
        for (Point p: points) {
            Point z = new Point(p.x, p.y);
            z.x = z.x *= -1;
            z.y = z.y *= -1;
            i.add(z);
        }
        return i;
    }
    
    /**
     * Initialize position of the piece
     * 
     * @param grid Grid of the game
     * @return true if piece has been initialized,
     *         else false
     */
    public boolean initPosition(ArrayList<Line> grid) {
        boolean firstFailled = false;
        points.clear();
        for (Point p : this.getInitialPosition()) {
            int x = leftSpace + p.x;
            if (grid.get(p.y).getPoints().get(x) != null) {
                firstFailled = true;
                break;
            } // else
            points.add(new Point(x, p.y));
        }
        if (firstFailled) {
            points.clear();
            for (Point p : getInitialPosition()) {
                int x = leftSpace + p.x;
                if (p.y - 1 >= 0 && grid.get(p.y - 1).getPoints().get(x) != null) {
                    return false;
                } // else
                points.add(new Point(x, p.y - 1));
            }
        }
        if (getPieceWidth() == 2) {
            points.forEach((p) -> p.setLocation(p.x + 1, p.y));
        }
        setGostPointsPosition(grid);
        return true;
    }
    
    /**
     * Check if the piece can down
     * 
     * @param grid Grid of the game
     * @return true if piece can down,
     *         else false
     */
    public boolean canDown(ArrayList<Line> grid) {
        return points.stream().noneMatch((p) -> (p.y + 1 >= Settings.getYCases() ||
                grid.get(p.y + 1).getPoints().get(p.x) != null));
    }
    
    /**
     * Down the piece
     */
    public void down() {
        points.forEach((p) -> p.y++);
    }
    
    /**
     * Close position of the piece
     * 
     * @return Number of cases between piece and ghost before close
     */
    public int fix() {
        int diff = this.ghostPoints.get(0).y - points.get(0).y;
        points.clear();
        ghostPoints.forEach((p) -> points.add(new Point(p)));
        return diff;
    }

    /**
     * Set the x direction of the piece
     * 
     * @param xDirection Desired direction
     * @param grid       Grid of the game
     * @return true if piece has been moved,
     *         else false
     */
    public boolean setXDirection(int xDirection, ArrayList<Line> grid) {
        boolean isMovable = true;
        for (Point p : points) {
            if (p.x <= 0 && xDirection <= -1 ||
                    p.x >= Settings.getXCases() - 1 && xDirection >= 1 ||
                    p.y >= 0 && grid.get(p.y).getPoints().get(p.x + xDirection) != null) {
                isMovable = false;
            }
        }
        if (isMovable) {
            points.forEach((p) -> p.x += xDirection);
            setGostPointsPosition(grid);
        }
        return isMovable;
    }
    
    /**
     * Rotate left the piece
     * 
     * @param grid Grid of the game
     * @return true if piece has been rotated,
     *         else false
     */
    public boolean rotateLeft(ArrayList<Line> grid) {
        if (getRotationOperations() == null) {
            return true;
        } // else
        int c = curRotation;
        int n = (curRotation + 1) % getRotationOperations().size();
        return rotate(grid, c, n);
    }
    
    /**
     * Rotate right the piece
     * 
     * @param grid Grid of the game
     * @return true if piece has been rotated,
     *         else false
     */
    public boolean rotateRight(ArrayList<Line> grid) {
        if (getRotationOperations() == null) {
            return true;
        } // else
        int c = (curRotation + 1) % getRotationOperations().size();
        int n = ((curRotation - 1) < 0) ? 3 : (curRotation - 1);
        return rotate(grid, c, n);
    }
    
    /**
     * Rotate the piece
     * 
     * @param grid Grid of the game
     * @param c Current rotation operation
     * @param n Next rotation operation
     * @return true if piece has been rotated,
     *         else false
     */
    private boolean rotate(ArrayList<Line> grid, int c, int n) {
        boolean failled = false;
        int xDec = 0;
        List<Point> newPoints = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            failled = false;
            newPoints.clear();
            for (int j = 0; j < points.size(); j++) {
                int x = points.get(j).x + getRotationOperations()
                        .get(c).get(j).x + xDec;
                int y = points.get(j).y + getRotationOperations()
                        .get(c).get(j).y;
                if (x < 0) {
                    xDec++;
                    failled = true;
                    break;
                } else if (x >= Settings.getXCases()) {
                    xDec--;
                    failled = true;
                    break;
                } else if (y >= Settings.getYCases() ||
                        y >= 0 && grid.get(y).getPoints().get(x) != null) {
                    if (i > 0) {
                        return false;
                    }
                    failled = true;
                    break;
                }
                newPoints.add(new Point(x, y));
            }
            if (!failled) {
                break;
            }
        }
        points.clear();
        newPoints.forEach((p) -> points.add(p));
        curRotation = n;
        setGostPointsPosition(grid);
        return true;
    }
    
    /**
     * Initialize points of the piece
     */
    public final void setPointsAsInitial() {
        curRotation = 0;
        points.clear();
        getInitialPosition().forEach((p) -> points.add(p));
    }
    
    /**
     * Set up ghost piece position
     * 
     * @param grid Grid of the game
     */
    private void setGostPointsPosition(ArrayList<Line> grid) {
        int h = Settings.getYCases() - getMinHeightPoint();
        boolean intersect = false;
        ghostPoints.clear();
        for (int i = 0; i <= h; i++) {
            for (Point p : points) {
                if (p.y + i >= Settings.getYCases() || p.y >= 0 &&
                        grid.get(p.y + i).getPoints().get(p.x) != null) {
                    intersect = true;
                    break;
                }
            }
            if (intersect) {
                for (Point p : points) {
                    ghostPoints.add(new Point(p.x, p.y + i - 1));
                }
                break;
            }
        }
    }
    
    /**
     * Get minimal height of the piece
     * 
     * @return Minimal height of the piece
     */
    private int getMinHeightPoint() {
        int m = points.get(0).y;
        for (Point p : points) {
            if (m > p.y) {
                m = p.y;
            }
        }
        return m;
    }
    
    /*
     * Getters & Setters
     */

    /**
     * Get points of the piece
     * 
     * @return Points of the piece
     */
    public List<Point> getPoints() {
        return points;
    }
    
    /**
     * Get width of the piece
     * 
     * @return Width of the piece
     */
    public int getPieceWidth() {
        int min = points.get(0).x;
        int max = points.get(0).x;
        for (Point p: points) {
            if (p.x > max) {
                max = p.x;
            } else if (p.x < min) {
                min = p.x;
            }
        }
        return max - min + 1;
    }
    
    /**
     * Get height of the piece
     * 
     * @return Height of the piece
     */
    public int getPieceHeight() {
        int min = points.get(0).y;
        int max = points.get(0).y;
        for (Point p: points) {
            if (p.y > max) {
                max = p.y;
            } else if (p.y < min) {
                min = p.y;
            }
        }
        return max - min + 1;
    }
    
    /**
     * Get points of the ghost piece
     * 
     * @return Points of the ghost piece
     */
    public List<Point> getGhostPoints() {
        return ghostPoints;
    }
    
    /**
     * Get color type of the piece
     * 
     * @return Color type of the piece
     */
    public ColorType getColorType() {
        return this.colorType;
    }

}
