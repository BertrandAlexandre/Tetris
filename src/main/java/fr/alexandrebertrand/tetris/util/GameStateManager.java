package fr.alexandrebertrand.tetris.util;

/**
 * Game state manager
 * 
 * @author Alexandre Bertrand
 */
public class GameStateManager {

    /*
     * Constants
     */

    /**  */
    public static final int INTO_MAIN_MENU = 0;

    public static final int INTO_OPTIONS_MENU = 1;

    public static final int INTO_GAME = 2;

    public static final int INTO_PAUSE = 3;

    /*
     * Attribute
     */

    /** Current state of the game */
    private static int state;

    /*
     * Constructor
     */

    /**
     * Empty private constructor
     */
    private GameStateManager() {
    }

    /*
     * Method
     */

    public static int getState() {
        return GameStateManager.state;
    }

    public static void setState(int state) {
        GameStateManager.state = state;
    }

}
