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

    /** Main menu state */
    public static final int INTO_MAIN_MENU = 0;

    /** Options menu state */
    public static final int INTO_OPTIONS_MENU = 1;

    /** Into game state */
    public static final int INTO_GAME = 2;

    /** Pause menu state */
    public static final int INTO_PAUSE = 3;

    /*
     * Attributes
     */

    /** Current state of the game */
    private static int state;

    /*
     * Constructors
     */

    /**
     * Empty private constructor
     */
    private GameStateManager() {
    }

    /*
     * Getters & Setters
     */

    /**
     * Get current state of the game
     * 
     * @return State of the game
     */
    public static int getState() {
        return GameStateManager.state;
    }

    /**
     * Set state of the game
     * 
     * @param state New state of the game
     */
    public static void setState(int state) {
        GameStateManager.state = state;
    }

}
