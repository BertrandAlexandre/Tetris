package fr.alexandrebertrand.tetris.utils;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Detect and indicates keys status (currently pressed or released,
 * already released)
 * 
 * @author Alexandre Bertrand
 */
public class KeysStateManager {

    /*
     * Attributes
     */

    private static HashMap<Integer, Boolean> pressedKeys;

    private static HashMap<Integer, Boolean> alreadyPressedKeys;

    /*
     * Constructor
     */

    /**
     * Default constructor to detect currently pressed or released keys
     */
    public KeysStateManager() {
        pressedKeys = new HashMap<>();
        alreadyPressedKeys = new HashMap<>();
        for (int i = 0; i < 124; i++) {
            pressedKeys.put(i, false);
            alreadyPressedKeys.put(i, false);
        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher((KeyEvent ke) -> {
            synchronized (KeysStateManager.class) {
                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        pressedKeys.replace(ke.getKeyCode(), true);
                        break;
                    case KeyEvent.KEY_RELEASED:
                        pressedKeys.replace(ke.getKeyCode(), false);
                        alreadyPressedKeys.replace(ke.getKeyCode(), false);
                        break;
                }
                return false;
            }
        });
    }

    /*
     * Getters & Setters
     */

    /**
     * Indicates if the key is pressed or released
     * 
     * @param  key Key to analyse
     * @return true if key is pressed,
     *         else false
     */
    public static boolean isPressed(int key) {
        synchronized (KeysStateManager.class) {
            return pressedKeys.get(key);
        }
    }

    /**
     * Indicates if the key has already been pressed or not
     * 
     * @param  key Key to analyse
     * @return true if key has already been pressed,
     *         else false
     */
    public static boolean isAlreadyPressed(int key) {
        synchronized (KeysStateManager.class) {
            return alreadyPressedKeys.get(key);
        }
    }

    /**
     * Set status of the key to indicate if this key
     * has already been pressed
     * 
     * @param key            Key to update
     * @param alreadyPressed Indicates if the key
     *                       has already been pressed
     */
    public static void setAlreadyPressed(int key, boolean alreadyPressed) {
        alreadyPressedKeys.replace(key, alreadyPressed);
    }

}
