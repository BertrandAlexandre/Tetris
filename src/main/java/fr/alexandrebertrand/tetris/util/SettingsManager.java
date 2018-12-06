package fr.alexandrebertrand.tetris.util;

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
            
            Settings.xCases = Integer.parseInt(p.getProperty("xCases"));
            Settings.yCases = Integer.parseInt(p.getProperty("yCases"));
            Settings.musicsVolume = Double.parseDouble(p.getProperty("musicsVolume"));
            Settings.effectsVolume = Double.parseDouble(p.getProperty("effectsVolume"));
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
                "xCases=" + Settings.xCases + "\r\n" +
                "yCases=" + Settings.yCases + "\r\n" +
                "musicsVolume=" + Settings.musicsVolume + "\r\n" +
                "effectsVolume=" + Settings.effectsVolume
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
