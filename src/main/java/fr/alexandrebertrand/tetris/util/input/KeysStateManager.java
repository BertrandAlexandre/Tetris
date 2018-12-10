package fr.alexandrebertrand.tetris.util.input;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import fr.alexandrebertrand.tetris.util.settings.Settings;

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

    /** Last used time of keys */
    private HashMap<Integer, LocalTime> lastUsedKeys;

    /** Keys use buffers */
    private HashMap<Integer, Long> keyUseBuffer;

    /*
     * Constructor
     */

    /**
     * Default constructor to detect currently pressed or released keys
     */
    private KeysStateManager() {
        pressedKeys = new HashMap<>();
        alreadyPressedKeys = new HashMap<>();
        lastUsedKeys = new HashMap<>();
        keyUseBuffer = new HashMap<>();
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
    public static KeysStateManager getInstance() {
        if (instance == null) {
            instance = new KeysStateManager();
        }
        return instance;
    }

    /**
     * Initialize key maps
     */
    public void initialize() {
        pressedKeys.clear();
        alreadyPressedKeys.clear();
        lastUsedKeys.clear();
        keyUseBuffer.clear();
    }
    
    /*
    public void init() {
        initHashMap(pressedKeys, false);
        initHashMap(alreadyPressedKeys, false);
        initHashMap(lastUsedKeys, null);
        initHashMap(keyUseBuffer, 0l);
    }

    private <T extends Object & Comparable<? super T>> void initHashMap(HashMap<Integer, T> map, T val) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            map.put((int) pair.getKey(), val);
        }
    }
    */

    /**
     * Indicates if the key is pressed or released
     * 
     * @param  key Key to analyse
     * @return true if key is pressed,
     *         else false
     */
    public boolean isPressed(int key) {
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
    public boolean isAlreadyPressed(int key) {
        synchronized (KeysStateManager.class) {
            return alreadyPressedKeys.get(key);
        }
    }

    /**
     * Set status of the key to indicate if this key
     * has already been pressed
     * 
     * @param key Key to update
     */
    public void setAlreadyPressed(int key) {
        alreadyPressedKeys.put(key, true);
    }

    /**
     * Increase buffer between last move
     * 
     * @param key Key to update
     */
    public void increaseBuffer(int key) {
        if (lastUsedKeys.get(key) != null) {
            long timeBetween = lastUsedKeys.get(key)
                    .until(LocalTime.now(), ChronoUnit.MILLIS);
            keyUseBuffer.put(key, keyUseBuffer.get(key) + timeBetween);
        }
        lastUsedKeys.put(key, LocalTime.now());
    }

    /**
     * Check if key can be used by the player
     * 
     * @param key Key to update and analyse
     * @return true if key can be used,
     *         else false
     */
    public boolean canBeUsed(int key) {
        boolean b = keyUseBuffer.get(key) > Settings.getDelayBetweenMoves() ||
                !isAlreadyPressed(key);
        if (b)
            keyUseBuffer.put(key, 0l);
        return b;
    }

}
