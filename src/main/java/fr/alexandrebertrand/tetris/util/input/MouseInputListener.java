package fr.alexandrebertrand.tetris.util.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.alexandrebertrand.tetris.ui.menu.main.MainMenuInputManager;
import fr.alexandrebertrand.tetris.util.GameStateManager;

/**
 * Mouse input listener
 * 
 * @author Alexandre Bertrand
 */
public class MouseInputListener extends MouseAdapter {

    /*
     * Attributes
     */

	/** Unique instance of mouse input listener */
    private static MouseInputListener instance;

    /*
     * Constructors
     */

    /**
     * Empty private constructor
     */
    private MouseInputListener() {
    }

    /*
     * Methods
     */

    /**
     * Get the unique instance of the class
     * 
     * @return Unique instance of the class
     */
    public static MouseInputListener getInstance() {
        if (instance == null) {
            instance = new MouseInputListener();
        }
        return instance;
    }

    /**
     * Manage mouse operations
     * 
     * @param e Mouse events
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStateManager.getState()) {
            case GameStateManager.INTO_MAIN_MENU:
                switch (MainMenuInputManager.mouseReleased(e)) {
                    case MainMenuInputManager.PLAY_GAME:
                        playGame();
                        break;
                    case MainMenuInputManager.OPEN_OPTIONS:
                        openOptionsPanel();
                        break;
                    case MainMenuInputManager.EXIT:
                        exitGame();
                        break;
                }
                break;
            case GameStateManager.INTO_OPTIONS_MENU:
                // TODO Add options menu options
                break;
            case GameStateManager.INTO_PAUSE:
            	// TODO Add pause menu options
                break;
        }
    }

    /**
     * Play the game
     */
    private void playGame() {
    }

    /**
     * Open option panel
     */
    private void openOptionsPanel() {
    }

    /**
     * Exit the game
     */
    private void exitGame() {
        // TODO Stop sound readers ?
        System.exit(0);
    }

}
