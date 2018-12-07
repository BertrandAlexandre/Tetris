package fr.alexandrebertrand.tetris.util;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Detect and indicates keys status (currently pressed or released,
 * already released)
 * 
 * @author Alexandre Bertrand
 */
public final class KeysStateManager {

    /*
     * Attributes
     */

    /** Unique instance of the class */
    private static KeysStateManager instance = null;

    /** Pressed keys */
    private HashMap<Integer, Boolean> pressedKeys;

    /** Already pressed keys */
    private HashMap<Integer, Boolean> alreadyPressedKeys;

    /*
     * Constructor
     */

    /**
     * Default constructor to detect currently pressed or released keys
     */
    public KeysStateManager() {
        pressedKeys = new HashMap<>();
        alreadyPressedKeys = new HashMap<>();
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher((KeyEvent ke) -> {
            synchronized (KeysStateManager.class) {
                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        pressedKeys.put(ke.getKeyCode(), true);
                        break;
                    case KeyEvent.KEY_RELEASED:
                        pressedKeys.put(ke.getKeyCode(), false);
                        alreadyPressedKeys.put(ke.getKeyCode(), false);
                        break;
                }
                return false;
            }
        });
    }

    /*
     * Methods
     */

    /**
     * Implements an unique instance of the color manager
     */
    private static void setInstance() {
        if (instance == null) {
            instance = new KeysStateManager();
        }
    }

    /**
     * Indicates if the key is pressed or released
     * 
     * @param  key Key to analyse
     * @return true if key is pressed,
     *         else false
     */
    public static boolean isPressed(int key) {
        setInstance();
        synchronized (instance) {
            return instance.getPressedKeys().get(key);
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
        setInstance();
        synchronized (instance) {
            return instance.getAlreadyPressedKeys().get(key);
        }
    }

    /**
     * Set status of the key to indicate if this key
     * has already been pressed
     * 
     * @param key            Key to update
     * @param alreadyPressed Indicates if the key has already been pressed
     */
    public static void setAlreadyPressed(int key, boolean alreadyPressed) {
        setInstance();
        instance.setAlreadyPressedKey(key, alreadyPressed);
    }

    /*
     * Getters & Setters
     */

    /**
     * Get pressed keys of the key state manager
     * 
     * @return Pressed keys of the key state manager
     */
    public HashMap<Integer, Boolean> getPressedKeys() {
        return this.pressedKeys;
    }

    /**
     * Get already pressed keys of the key state manager
     * 
     * @return Already pressed keys of the key state manager
     */
    public HashMap<Integer, Boolean> getAlreadyPressedKeys() {
        return this.alreadyPressedKeys;
    }

    /**
     * Set an already pressed key of the key state manager
     * 
     * @param key            Key to update
     * @param alreadyPressed Indicates if the key has already been pressed
     */
    public void setAlreadyPressedKey(int key, boolean alreadyPressed) {
        this.alreadyPressedKeys.put(key, alreadyPressed);
    }

}
