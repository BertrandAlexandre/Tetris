package fr.alexandrebertrand.tetris.util.settings;

import java.awt.Dimension;

/**
 * Main settings of the game
 * 
 * @author Alexandre Bertrand
 */
public final class Settings {

    /*
     * Attributes
     */

    /** Number of X cases of the grid */
    private static int xCases;

    /** Number of Y cases of the grid */
    private static int yCases;

    /** Initial musics volume */
    private static double musicsVolume;

    /** Initial effects volume */
    private static double effectsVolume;
    
    /** Preferred number of frames per second */
    private static int framesPerSecond;

    /** Size of a box on the grid */
    private static int boxSize;

    /** Delay between too moves */
    private static int delayBetweenMoves;

    /*
     * Constructors
     */

    /**
     * Empty private constructor
     */
    private Settings() {
    }

    /*
     * Methods
     */

    /**
     * Get grid dimension from settings
     * 
     * @return Grid dimension
     */
    public static Dimension getGridDimensions() {
        return new Dimension(
            Settings.boxSize * Settings.xCases,
            Settings.boxSize * Settings.yCases
        );
    }

    /*
     * Getters & Setters
     */

    /**
     * Get x case number of the grid
     * 
     * @return x case number of the grid
     */
    public static int getXCases() {
        return Settings.xCases;
    }

    /**
     * Set x case number of the grid
     * 
     * @param xCases New x case number of the grid
     */
    public static void setXCases(int xCases) {
        Settings.xCases = xCases;
    }

    /**
     * Get y case number of the grid
     * 
     * @return y case number of the grid
     */
    public static int getYCases() {
        return Settings.yCases;
    }

    /**
     * Set y case number of the grid
     * 
     * @param yCases New y case number of the grid
     */
    public static void setYCases(int yCases) {
        Settings.yCases = yCases;
    }

    /**
     * Get musics volume of the game
     * 
     * @return Musics volume of the game
     */
    public static double getMusicsVolume() {
        return Settings.musicsVolume;
    }

    /**
     * Set musics volume of the game
     * 
     * @param musicsVolume New musics volume of the game
     */
    public static void setMusicsVolume(double musicsVolume) {
        Settings.musicsVolume = musicsVolume;
    }

    /**
     * Get effects volume of the game
     * 
     * @return Effects volume of the game
     */
    public static double getEffectsVolume() {
        return Settings.effectsVolume;
    }

    /**
     * Set effects volume of the game
     * 
     * @param effectsVolume New effects volume of the game
     */
    public static void setEffectsVolume(double effectsVolume) {
        Settings.effectsVolume = effectsVolume;
    }
    
    /**
     * Get preferred number of frames per second
     * 
     * @return Preferred number of frames per second
     */
    public static double getFramesPerSecond() {
        return Settings.framesPerSecond;
    }

    /**
     * Set preferred number of frames per second
     * 
     * @param framesPerSecond New preferred number of frames per second
     */
    public static void setFramesPerSecond(int framesPerSecond) {
        Settings.framesPerSecond = framesPerSecond;
    }

    /**
     * Get grid box size
     * 
     * @return Grid box size
     */
    public static int getBoxSize() {
        return Settings.boxSize;
    }

    /**
     * Set grid box size
     * 
     * @param boxSize New grid box size
     */
    public static void setBoxSize(int boxSize) {
        Settings.boxSize = boxSize;
    }

    /**
     * Get delay between too moves
     * 
     * @return Delay between too moves
     */
    public static int getDelayBetweenMoves() {
        return Settings.delayBetweenMoves;
    }

    /**
     * Set delay between too moves
     * 
     * @param delayBetweenMoves New delay between too moves
     */
    public static void setDelayBetweenMoves(int delayBetweenMoves) {
        Settings.delayBetweenMoves = delayBetweenMoves;
    }

}
