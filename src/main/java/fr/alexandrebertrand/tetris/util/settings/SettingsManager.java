package fr.alexandrebertrand.tetris.util.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Settings manager to read and update settings
 * 
 * @author Alexandre Bertrand
 */
public final class SettingsManager {

    /*
     * Attributes
     */

    /** Name of the game properties file */
    private final static String propertyFileName =
            "resources/app.properties";

    /** Name of the private game properties file */
    private final static String privatePropertyFileName =
            "resources/app-private.properties";

    /*
     * Constructor
     */
    
    /**
     * Empty private constructor
     */
    private SettingsManager() {
    }

    /**
     * Read current settings
     */
    public static void readSettings() {
        try {
            Properties p = new Properties();
            
            FileInputStream fis = new FileInputStream(propertyFileName);
            if (fis.available() > 0) {
                p.load(fis);
                fis.close();
            } else {
                p.load(ClassLoader.getSystemResourceAsStream(propertyFileName));
            }
            
            Settings.setXCases(Integer.parseInt(p.getProperty("xCases")));
            Settings.setYCases(Integer.parseInt(p.getProperty("yCases")));
            Settings.setMusicsVolume(Double.parseDouble(p.getProperty("musicsVolume")));
            Settings.setEffectsVolume(Double.parseDouble(p.getProperty("effectsVolume")));

            Properties pp = new Properties();
            pp.load(ClassLoader.getSystemResourceAsStream(privatePropertyFileName));

            Settings.setBoxSize(Integer.parseInt(pp.getProperty("boxSize")));
            Settings.setDelayBetweenMoves(Integer.parseInt(pp.getProperty("delayBetweenMoves")));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Save current settings
     */
    public static void saveSettings() {
        try {
            File f = new File(propertyFileName);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write(
                "xCases=" + Settings.getXCases() + "\r\n" +
                "yCases=" + Settings.getYCases() + "\r\n" +
                "musicsVolume=" + Settings.getMusicsVolume() + "\r\n" +
                "effectsVolume=" + Settings.getEffectsVolume()
            );
            fw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } 
    }

    /**
     * Initialize settings
     */
    public static void initializeSettings() {
        File f = new File(propertyFileName);
        if (f.exists()) {
            f.delete();
        }
        readSettings();
    }

}
