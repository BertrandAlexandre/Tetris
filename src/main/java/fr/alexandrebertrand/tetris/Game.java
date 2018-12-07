package fr.alexandrebertrand.tetris;

import java.awt.EventQueue;
import java.awt.Image;

import javafx.embed.swing.JFXPanel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Tetris game
 * 
 * @author Alexandre Bertrand
 */
public final class Game extends JFrame {

    private static final long serialVersionUID = -4134577864201810993L;
    
    /*
     * Constructor
     */

    /**
     * Constructor to initialize the UI view
     */
    public Game() {
        initUI();
    }

    /*
     * Methods
     */

    /**
     * Initialize the UI view
     */
    public void initUI() {
        final JFXPanel fxPanel = new JFXPanel();
        add(new Board());
        
        // setUndecorated(true);
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/icons/tetris.png"));
        Image image = icon.getImage();
        setIconImage(image);
        
        setResizable(false);
        pack();
        
        setTitle("Tetris");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Launcher of the game
     * 
     * @param args Main arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new Game();
                ex.setVisible(true);
            }
        });
    }

}
