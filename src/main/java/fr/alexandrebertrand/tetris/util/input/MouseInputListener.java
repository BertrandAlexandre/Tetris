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

    private static MouseInputListener instance;

    /*
     * Constructor
     */

    /**
     * Empty private constructor
     */
    private MouseInputListener() {
    }

    /*
     * Method
     */

    public static MouseInputListener getInstance() {
        if (instance == null) {
            instance = new MouseInputListener();
        }
        return instance;
    }

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
                
                break;
            case GameStateManager.INTO_PAUSE:
                
                break;
        }
    }

    private void playGame() {
    }

    private void backToMainMenu() {
    }

    private void openOptionsPanel() {
    }

    private void exitGame() {
        // Stop sound readers ?
        System.exit(0);
    }

}
