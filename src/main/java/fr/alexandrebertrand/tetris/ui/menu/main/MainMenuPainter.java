package fr.alexandrebertrand.tetris.ui.menu.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * Main menu painter
 * 
 * @author Alexandre Bertrand
 */
public final class MainMenuPainter {

    /*
     * Constants
     */

    /** Name of the game logo file path */
    private static final String LOGO_PATH = "/tetris-logo.png";

    /** Name of the game logo file path */
    private static final String PLAY_BTN_PATH = "/play-btn.png";

    /** Name of the game logo file path */
    private static final String OPTIONS_BTN_PATH = "/options-btn.png";

    /** Name of the game logo file path */
    private static final String EXIT_BTN_PATH = "/exit-btn.png";

    /*
     * Attributes
     */

    /** Logo of the game */
    private static Image logoImage;

    /** Play button of the game */
    private static Image playBtnImage;

    /** Options button of the game */
    private static Image optionsBtnImage;

    /** Exit button of the game */
    private static Image exitBtnImage;

    /** Board dimensions */
    private static Dimension boardDim;

    /*
     * Constructor
     */

    /**
     * Empty private constructor
     */
    private MainMenuPainter() {
    }

    static {
        initialize();
    }

    /*
     * Method
     */

    /**
     * Initialize the main menu painter
     */
    private static void initialize() {
        String pref = "resources/dist/";
        logoImage = new ImageIcon(
            ClassLoader.getSystemResource(pref + LOGO_PATH)).getImage();
        playBtnImage = new ImageIcon(
            ClassLoader.getSystemResource(pref + PLAY_BTN_PATH)).getImage();
        optionsBtnImage = new ImageIcon(
            ClassLoader.getSystemResource(pref + OPTIONS_BTN_PATH)).getImage();
        exitBtnImage = new ImageIcon(
            ClassLoader.getSystemResource(pref + EXIT_BTN_PATH)).getImage();
    }

    /**
     * Paint the main menu
     * 
     * @param g        Graphic context
     * @param boardDim Bord dimension
     */
    public static void paint(Graphics g, Dimension boardDim) {
        MainMenuPainter.boardDim = boardDim;
        g.drawImage(logoImage, getLeftMargin(logoImage), 30, null);
        g.drawImage(playBtnImage, getLeftMargin(playBtnImage), 220, null);
        g.drawImage(optionsBtnImage, getLeftMargin(optionsBtnImage), 260, null);
        g.drawImage(exitBtnImage, getLeftMargin(exitBtnImage), 300, null);
    }

    /**
     * Get left margin of an image to center it
     * 
     * @param image Image to center
     * @return Left margin to add
     */
    public static int getLeftMargin(Image image) {
        return (int) boardDim.getWidth() - (image.getWidth(null) / 2);
    }

    /**
     * Get images of main menu buttons
     * 
     * @return List of main menu buttons images
     */
    public static ArrayList<Image> getButtonImages() {
        ArrayList<Image> images = new ArrayList<>();
        images.add(playBtnImage);
        images.add(optionsBtnImage);
        images.add(exitBtnImage);
        return images;
    }

}
