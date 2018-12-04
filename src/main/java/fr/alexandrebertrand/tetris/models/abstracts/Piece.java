package fr.alexandrebertrand.tetris.models.abstracts;

import fr.alexandrebertrand.tetris.models.Line;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexandre Bertrand
 */
public class Piece {
    
    /*
     * Constants
     */

    /** Number of X cases */
    private final int X_CASES;
    
    /** Number of Y cases */
    private final int Y_CASES;
    
    /*
     * Attributes
     */
    
    /** Position of the piece */
    private final List<Point> points;
    
    /** Position of the gost */
    private final List<Point> gostPoints;
        
    protected int curRotation;
    
    private final int leftSpace;
    
    protected int colorValue;
            
    /*
     * Constructors
     */
    
    /**
     * Default constructor
     * @param xCases Number of X cases
     * @param yCases Number of Y cases
     */
    public Piece(int xCases, int yCases) {
        X_CASES = xCases;
        Y_CASES = yCases;
        points = new ArrayList<>();
        gostPoints = new ArrayList<>();
        curRotation = 0;
        leftSpace = (int) Math.floor((X_CASES - 4d) / 2);
        setPointsAsInitial();
    }
    
    /*
     * Methods
     */
    
    protected List<List<Point>> getRotationOperations() {
        return null;
    }
    
    protected List<Point> getInitialPosition() {
        return null;
    }
    
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
    
    public boolean initPosition(ArrayList<Line> grid) {
        boolean firstFailled = false;
        points.clear();
        for (Point p : this.getInitialPosition()) {
            int x = leftSpace + p.x;
            if (grid.get(p.y).getPoints().get(x) != 0) {
                firstFailled = true;
                break;
            } // else
            points.add(new Point(x, p.y));
        }
        if (firstFailled) {
            points.clear();
            for (Point p : getInitialPosition()) {
                int x = leftSpace + p.x;
                if (p.y - 1 >= 0 && grid.get(p.y - 1).getPoints().get(x) != 0) {
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
    
    public boolean canDown(ArrayList<Line> grid) {
        return points.stream().noneMatch((p) -> (p.y + 1 >= Y_CASES ||
                grid.get(p.y + 1).getPoints().get(p.x) != 0));
    }
    
    public void down() {
        points.forEach((p) -> p.y++);
    }
    
    public int fix() {
        int diff = this.gostPoints.get(0).y - points.get(0).y;
        points.clear();
        gostPoints.forEach((p) -> points.add(new Point(p)));
        return diff;
    }

    public boolean setXDirection(int xDirection, ArrayList<Line> grid) {
        boolean isMovable = true;
        for (Point p : points) {
            if (p.x <= 0 && xDirection <= -1 ||
                    p.x >= X_CASES - 1 && xDirection >= 1 ||
                    p.y >= 0 && grid.get(p.y).getPoints().get(p.x + xDirection) != 0) {
                isMovable = false;
            }
        }
        if (isMovable) {
            points.forEach((p) -> p.x += xDirection);
            setGostPointsPosition(grid);
        }
        return isMovable;
    }
    
    public boolean rotateLeft(ArrayList<Line> grid) {
        if (getRotationOperations() == null) {
            return true;
        } // else
        int c = curRotation;
        int n = (curRotation + 1) % getRotationOperations().size();
        return rotate(grid, c, n);
    }
    
    public boolean rotateRight(ArrayList<Line> grid) {
        if (getRotationOperations() == null) {
            return true;
        } // else
        int c = (curRotation + 1) % getRotationOperations().size();
        int n = ((curRotation - 1) < 0) ? 3 : (curRotation - 1);
        return rotate(grid, c, n);
    }
    
    private boolean rotate(ArrayList<Line> grid, int c, int n) {
        boolean failled = false;
        int xDec = 0;
        int yDec = 0;
        List<Point> newPoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).x + getRotationOperations()
                    .get(c).get(i).x;
            int y = points.get(i).y + getRotationOperations()
                    .get(c).get(i).y;
            if (x < 0) {
                xDec++;
                failled = true;
                break;
            } else if (x >= this.X_CASES) {
                xDec--;
                failled = true;
                break;
            }
            if (y >= this.Y_CASES ||
                    y >= 0 && grid.get(y).getPoints().get(x) != 0) {
                failled = true;
                break;
            }
            newPoints.add(new Point(x, y));
        }
        if (failled) {
            failled = false;
            newPoints.clear();
            for (int i = 0; i < points.size(); i++) {
                int x = points.get(i).x + getRotationOperations()
                        .get(c).get(i).x + xDec;
                int y = points.get(i).y + getRotationOperations()
                        .get(c).get(i).y - yDec;
                if (x < 0) {
                    xDec++;
                    failled = true;
                    break;
                } else if (x >= this.X_CASES) {
                    xDec--;
                    failled = true;
                    break;
                }
                if (y >= this.Y_CASES ||
                        y >= 0 && grid.get(y).getPoints().get(x) != 0) {
                    return false;
                }
                newPoints.add(new Point(x, y));
            }
        }
        if (failled) {
            newPoints.clear();
            for (int i = 0; i < points.size(); i++) {
                int x = points.get(i).x + getRotationOperations()
                        .get(c).get(i).x + xDec;
                int y = points.get(i).y + getRotationOperations()
                        .get(c).get(i).y - yDec;
                if (x >= X_CASES || x < 0 ||
                        y >= 0 && grid.get(y).getPoints().get(x) != 0) {
                    return false;
                }
                newPoints.add(new Point(x, y));
            }
        }
        points.clear();
        newPoints.forEach((p) -> points.add(p));
        curRotation = n;
        setGostPointsPosition(grid);
        return true;
    }
    
    public final void setPointsAsInitial() {
        curRotation = 0;
        points.clear();
        getInitialPosition().forEach((p) -> points.add(p));
    }
    
    private void setGostPointsPosition(ArrayList<Line> grid) {
        int h = Y_CASES - getMinHeightPoint();
        boolean intersect = false;
        gostPoints.clear();
        for (int i = 0; i <= h; i++) {
            for (Point p : points) {
                if (p.y + i >= Y_CASES || p.y >= 0 &&
                        grid.get(p.y + i).getPoints().get(p.x) != 0) {
                    intersect = true;
                    break;
                }
            }
            if (intersect) {
                for (Point p : points) {
                    gostPoints.add(new Point(p.x, p.y + i - 1));
                }
                break;
            }
        }
    }
    
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

    public List<Point> getPoints() {
        return points;
    }
    
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
    
    public List<Point> getGostPoints() {
        return gostPoints;
    }
    
    public int getColorValue() {
        return colorValue;
    }

}
