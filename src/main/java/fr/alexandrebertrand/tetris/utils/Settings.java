package fr.alexandrebertrand.tetris.utils;

/**
 * Main settings of the game
 * 
 * @author Alexandre Bertrand
 */
public class Settings {

    /*
     * Attributes
     */

    /** Number of fraps per seconds of the renderer */
    private int frapsPerSeconds;

    /** Number of X cases of the grid */
    private int xCases;

    /** Number of Y cases of the grid */
    private int yCases;

    /** Initial musics volume */
    private double musicsVolume;

    /** Initial effects volume */
    private double effectsVolume;

    /*
     * Getters
     */

    /**
     * Get the number of fraps per seconds for the animation
     * @return Number of fraps per seconds
     */
    public int getFrapsPerSeconds() {
        return (frapsPerSeconds > 60) ? 60 : frapsPerSeconds;
    }

    /**
     * Get number of X cases of the grid
     * @return Number of X cases
     */
    public int getXCases() {
        return xCases;
    }

    /**
     * Get number of Y cases of the grid
     * @return Number of Y cases
     */
    public int getYCases() {
        return yCases;
    }

    /**
     * Get initial musics volume
     * @return Initial musics volume
     */
    public double getMusicsVolume() {
        return musicsVolume;
    }

    /**
     * Get initial effects volume
     * @return Initial effects volume
     */
    public double getEffectsVolume() {
        return effectsVolume;
    }

}
