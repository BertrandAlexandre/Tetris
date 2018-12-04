package fr.alexandrebertrand.tetris.utils;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

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

    /** Indicates if the key "Up" is pressed */
    private static volatile boolean upPressed = false;

    /** Indicates if the key "Up" has already been pressed */
    private static volatile boolean upAlreadyPressed = false;

    /** Indicates if the key "Down" is pressed */
    private static volatile boolean downPressed = false;

    /** Indicates if the key "Down" has already been pressed */
    private static volatile boolean downAlreadyPressed = false;

    /** Indicates if the key "Left" is pressed */
    private static volatile boolean leftPressed = false;

    /** Indicates if the key "Left" has already been pressed */
    private static volatile boolean leftAlreadyPressed = false;

    /** Indicates if the key "Right" is pressed */
    private static volatile boolean rightPressed = false;

    /** Indicates if the key "Right" has already been pressed */
    private static volatile boolean rightAlreadyPressed = false;

    /** Indicates if the key "Space" is pressed */
    private static volatile boolean spacePressed = false;

    /** Indicates if the key "Space" has already been pressed */
    private static volatile boolean spaceAlreadyPressed = false;

    /** Indicates if the key "C" is pressed */
    private static volatile boolean cPressed = false;

    /** Indicates if the key "C" has already been pressed */
    private static volatile boolean cAlreadyPressed = false;

    /** Indicates if the key "R" is pressed */
    private static volatile boolean rPressed = false;

    /** Indicates if the key "Z" is pressed */
    private static volatile boolean zPressed = false;

    /** Indicates if the key "Z" has already been pressed */
    private static volatile boolean zAlreadyPressed = false;

    /** Indicates if the key "Esc" is pressed */
    private static volatile boolean escapePressed = false; 

    /*
     * Constructor
     */

    /**
     * Default constructor to detect currently pressed or released keys
     */
    public KeysStateManager() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((KeyEvent ke) -> {
            synchronized (KeysStateManager.class) {
                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        setKeysPressed(ke.getKeyCode());
                        break;
                    case KeyEvent.KEY_RELEASED:
                        setKeysReleased(ke.getKeyCode());
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
     * Set up keys status of pressed keys
     * @param keyCode Code of the key currently pressed
     */
    private void setKeysPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                break;
            case KeyEvent.VK_C:
                cPressed = true;
                break;
            case KeyEvent.VK_R:
                rPressed = true;
                break;
            case KeyEvent.VK_Z:
                zPressed = true;
                break;
            case KeyEvent.VK_ESCAPE:
                escapePressed = true;
                break;   
        }
    }

    /**
     * Set up keys status of released keys
     * @param keyCode Code of the key currently released
     */
    private void setKeysReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                upAlreadyPressed = false;
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downAlreadyPressed = false;
                downPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftAlreadyPressed = false;
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightAlreadyPressed = false;
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spaceAlreadyPressed = false;
                spacePressed = false;
                break;
            case KeyEvent.VK_C:
                cAlreadyPressed = false;
                cPressed = false;
            case KeyEvent.VK_R:
                rPressed = false;
                break;
            case KeyEvent.VK_Z:
                zAlreadyPressed = false;
                zPressed = false;
                break;
            case KeyEvent.VK_ESCAPE:
                escapePressed = false;
                break;   
        }
    }

    /*
     * Getters & Setters
     */

    /**
     * Indicates if the key "Up" is pressed or released.
     * @return true if "Up" key is pressed,
     *         else false.
     */
    public static boolean isUpPressed() {
        synchronized (KeysStateManager.class) {
            return upPressed;
        }
    }

    /**
     * Indicates if the key "Up" has already been pressed or not.
     * @return true if "Up" key has already been pressed,
     *         else false.
     */
    public static boolean isUpAlreadyPressed() {
        synchronized (KeysStateManager.class) {
            return upAlreadyPressed;
        }
    }

    /**
     * Set status of the "Up" key to indicate if this key
     * has already been pressed.
     * @param upAlreadyPressed Indicates if the key "Up"
     *                         has already been pressed.
     */
    public static void setUpAlreadyPressed(boolean upAlreadyPressed) {
        KeysStateManager.upAlreadyPressed = upAlreadyPressed;
    }

    /**
     * Indicates if the key "Down" is pressed or released.
     * @return true if "Down" key is pressed,
     *         else false.
     */
    public static boolean isDownPressed() {
        synchronized (KeysStateManager.class) {
            return downPressed;
        }
    }

    /**
     * Indicates if the key "Down" has already been pressed or not.
     * @return true if "Down" key has already been pressed,
     *         else false.
     */
    public static boolean isDownAlreadyPressed() {
        synchronized (KeysStateManager.class) {
            return downAlreadyPressed;
        }
    }

    /**
     * Set status of the "Down" key to indicate if this key
     * has already been pressed.
     * @param downAlreadyPressed Indicates if the key "Down"
     *                           has already been pressed.
     */
    public static void setDownAlreadyPressed(boolean downAlreadyPressed) {
        KeysStateManager.downAlreadyPressed = downAlreadyPressed;
    }

    /**
     * Indicates if the key "Left" is pressed or released.
     * @return true if "Left" key is pressed,
     *         else false.
     */
    public static boolean isLeftPressed() {
        synchronized (KeysStateManager.class) {
            return leftPressed;
        }
    }

    /**
     * Indicates if the key "Left" has already been pressed or not.
     * @return true if "Left" key has already been pressed,
     *         else false.
     */
    public static boolean isLeftAlreadyPressed() {
        synchronized (KeysStateManager.class) {
            return leftAlreadyPressed;
        }
    }

    /**
     * Set status of the "Left" key to indicate if this key
     * has already been pressed.
     * @param leftAlreadyPressed Indicates if the key "Left"
     *                           has already been pressed.
     */
    public static void setLeftAlreadyPressed(boolean leftAlreadyPressed) {
        KeysStateManager.leftAlreadyPressed = leftAlreadyPressed;
    }

    /**
     * Indicates if the key "Right" is pressed or released.
     * @return true if "Right" key is pressed,
     *         else false.
     */
    public static boolean isRightPressed() {
        synchronized (KeysStateManager.class) {
            return rightPressed;
        }
    }

    /**
     * Indicates if the key "Right" has already been pressed or not.
     * @return true if "Right" key has already been pressed,
     *         else false.
     */
    public static boolean isRightAlreadyPressed() {
        synchronized (KeysStateManager.class) {
            return rightAlreadyPressed;
        }
    }

    /**
     * Set status of the "Right" key to indicate if this key
     * has already been pressed.
     * @param rightAlreadyPressed Indicates if the key "Right"
     *                            has already been pressed.
     */
    public static void setRightAlreadyPressed(boolean rightAlreadyPressed) {
        KeysStateManager.rightAlreadyPressed = rightAlreadyPressed;
    }

    /**
     * Indicates if the key "Space" is pressed or released.
     * @return true if "Space" key is pressed,
     *         else false.
     */
    public static boolean isSpacePressed() {
        synchronized (KeysStateManager.class) {
            return spacePressed;
        }
    }

    /**
     * Indicates if the key "Space" has already been pressed or not.
     * @return true if "Space" key has already been pressed,
     *         else false.
     */
    public static boolean isSpaceAlreadyPressed() {
        synchronized (KeysStateManager.class) {
            return spaceAlreadyPressed;
        }
    }

    /**
     * Set status of the "Space" key to indicate if this key
     * has already been pressed.
     * @param spaceAlreadyPressed Indicates if the key "Space"
     *                            has already been pressed.
     */
    public static void setSpaceAlreadyPressed(boolean spaceAlreadyPressed) {
        KeysStateManager.spaceAlreadyPressed = spaceAlreadyPressed;
    }

    /**
     * Indicates if the key "C" is pressed or released.
     * @return true if "C" key is pressed,
     *         else false.
     */
    public static boolean isCPressed() {
        synchronized (KeysStateManager.class) {
            return cPressed;
        }
    }

    /**
     * Indicates if the key "C" has already been pressed or not.
     * @return true if "C" key has already been pressed,
     *         else false.
     */
    public static boolean isCAlreadyPressed() {
        synchronized (KeysStateManager.class) {
            return cAlreadyPressed;
        }
    }

    /**
     * Set status of the "C" key to indicate if this key
     * has already been pressed.
     * @param cAlreadyPressed Indicates if the key "C"
     *                        has already been pressed.
     */
    public static void setCAlreadyPressed(boolean cAlreadyPressed) {
        KeysStateManager.cAlreadyPressed = cAlreadyPressed;
    }

    /**
     * Indicates if the key "R" is pressed or released.
     * @return true if "R" key is pressed,
     *         else false.
     */
    public static boolean isRPressed() {
        synchronized (KeysStateManager.class) {
            return rPressed;
        }
    }

    /**
     * Indicates if the key "Z" is pressed or released.
     * @return true if "Z" key is pressed,
     *         else false.
     */
    public static boolean isZPressed() {
        synchronized (KeysStateManager.class) {
            return zPressed;
        }
    }

    /**
     * Indicates if the key "Z" has already been pressed or not.
     * @return true if "Z" key has already been pressed,
     *         else false.
     */
    public static boolean isZAlreadyPressed() {
        synchronized (KeysStateManager.class) {
            return zAlreadyPressed;
        }
    }

    /**
     * Set status of the "Z" key to indicate if this key
     * has already been pressed.
     * @param zAlreadyPressed Indicates if the key "Z"
     *                        has already been pressed.
     */
    public static void setZAlreadyPressed(boolean zAlreadyPressed) {
        KeysStateManager.zAlreadyPressed = zAlreadyPressed;
    }

    /**
     * Indicates if the key "Esc" is pressed or released.
     * @return true if "Esc" key is pressed,
     *         else false.
     */
    public static boolean isEscapePressed() {
        synchronized (KeysStateManager.class) {
            return escapePressed;
        }
    }

}
