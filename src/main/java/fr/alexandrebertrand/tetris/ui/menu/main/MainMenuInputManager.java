package fr.alexandrebertrand.tetris.ui.menu.main;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Main menu input manager
 * 
 * @author Alexandre Bertrand
 */
public final class MainMenuInputManager {

    /*
     * Constants
     */

    /* Action : play the game */
    public static final int PLAY_GAME = 1;

    /* Action : play the game */
    public static final int OPEN_OPTIONS = 2;

    /* Action : play the game */
    public static final int EXIT = 3;

    /* Action : play the game */
    public static final int NOTHING = 0;

    /*
     * Constructors
     */

    /**
     * Empty private constructor
     */
    private MainMenuInputManager() {
    }

    /*
     * Methods
     */

    /**
     * Main menu mouse released listener
     * 
     * @param e Events of mouse listener
     * @return Value of the action to do
     */
    public static int mouseReleased(MouseEvent e) {
        ArrayList<Image> images = MainMenuPainter.getButtonImages();
        for (int i = 0; i < images.size(); i++) {
            if (collide(e, images.get(i), i)) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * Check collision between cursor and button
     * 
     * @param e     Events of mouse listener
     * @param image Image of the button
     * @param index Index of the button
     * @return true if cursor collide the button,
     *         else false
     */
    private static boolean collide(MouseEvent e, Image image, int index) {
        Point cursor = new Point(e.getX(), e.getY());
        Rectangle inputZone = new Rectangle(
            MainMenuPainter.getLeftMargin(image),
            220 + 40 * index,
            image.getWidth(null),
            image.getHeight(null)
        );
        if (inputZone.contains(cursor)) {
            return true;
        } // else
        return false;
    }

}
