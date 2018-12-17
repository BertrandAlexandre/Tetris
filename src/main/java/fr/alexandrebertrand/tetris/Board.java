package fr.alexandrebertrand.tetris;

import fr.alexandrebertrand.tetris.model.grid.*;
import fr.alexandrebertrand.tetris.model.piece.*;
import fr.alexandrebertrand.tetris.model.piece.type.*;
import fr.alexandrebertrand.tetris.ui.gameover.*;
import fr.alexandrebertrand.tetris.ui.scoreboard.*;
import fr.alexandrebertrand.tetris.util.*;
import fr.alexandrebertrand.tetris.util.graphic.*;
import fr.alexandrebertrand.tetris.util.input.*;
import fr.alexandrebertrand.tetris.util.settings.*;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javafx.scene.media.MediaPlayer;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Board of the game
 * 
 * @author Alexandre Bertrand
 */
public final class Board extends JPanel implements ActionListener {

    /*
     * Constants
     */
	
	/** Unique serial version identifier */
	private static final long serialVersionUID = -873119713546307387L;
	
    /*
     * Attributes
     */

	/** Main timer of the game */
    private Timer timer;

    /** Music reader of the game */
    private SoundReader musicsReader;
    
    /** Sound effect reader of the game */
    private SoundReader effectsReader;

    /** Key state manager of the game */
    private KeysStateManager keyStateManager;
    
    /** Current piece of the game */
    private Piece currentPiece;
    
    /** Next piece of the game */
    private ArrayList<Piece> nextPieces;
    
    /** Hold piece of the game */
    private Piece holdPiece;
    
    /** Grid of the game */
    private ArrayList<Line> grid;
    
    /** Number of completed lines */
    private int nbLines;
    
    /** Current level */
    private int level;
    
    /** Score of the player */
    private int score;

    /** Has switched piece */
    private boolean hasSwiched;
    
    /** List of suppliers containing pieces types */
    @SuppressWarnings("rawtypes")
	private List<Supplier> suppliers;
    
    /** Game over status */
    private boolean gameOver;

    /** Time between now and the last loop */
    private LocalTime lastLoopTime;
    
    /** Time loop buffer */
    private long timeBufer;

    /** Falling piece time buffer */
    private long fallingTime;
    
    /** Fixed piece time buffer */
    private long fixTime;
    
    /** Time between now and last move */
    private LocalTime sinceLastMove;

    /** Time between now and last down of piece */
    private LocalTime sincePieceDown;
        
    /*
     * Constructors
     */
    
    /**
     * Initialize the board and the game
     */
    public Board() {
    	SettingsManager.readSettings();
        this.keyStateManager = KeysStateManager.getInstance();
        addMouseListener(MouseInputListener.getInstance());
        FontManager.initFont(this.getClass());
        initApp();
        initSounds();
        initSuppliers();
        initGame();
    }
    
    /*
     * Methods
     */

    /**
     * Initialize the window of the game
     */
    public void initApp() {
    	int ms = (int) Math.round(1000d / Settings.getFramesPerSecond());
    	timer = new Timer(ms, this);
    	timer.start();
        setBackground(new Color(35, 35, 35));
        Dimension d = new Dimension(Settings.getGridDimensions());
        d.setSize(d.getWidth() + Settings.getBoxSize() * 4 + 80, d.getHeight());
        setPreferredSize(d);
        setFocusable(true);
    }

    /**
     * Load sound resources
     */
    private void initSounds() {
        musicsReader = new SoundReader(Settings.getMusicsVolume(), "musics/");
        musicsReader.loadResource("theme.mp3");
        musicsReader.loadResource("gameover.mp3");
        effectsReader = new SoundReader(Settings.getEffectsVolume(), "effects/");
        effectsReader.loadResource("drop.mp3");
        effectsReader.loadResource("move.mp3");
        effectsReader.loadResource("rotate.mp3");
        effectsReader.loadResource("hold.mp3");
        effectsReader.loadResource("level-up.mp3");
        effectsReader.loadResource("line.mp3");
        effectsReader.loadResource("tetris.mp3");
    }
    
    /**
     * Initialize suppliers
     */
    private void initSuppliers() {
        suppliers = new ArrayList<>();
        suppliers.add(() -> new PieceA());
        suppliers.add(() -> new PieceB());
        suppliers.add(() -> new PieceC());
        suppliers.add(() -> new PieceD());
        suppliers.add(() -> new PieceE());
        suppliers.add(() -> new PieceF());
        suppliers.add(() -> new PieceG());
    }
    
    /**
     * Initialize the game
     */
    public void initGame() {
        keyStateManager.initialize();
        gameOver = false;
        nbLines = 0;
        level = 1;
        score = 0;
        lastLoopTime = null;
        timeBufer = 0;
        fallingTime = 1000;
        fixTime = fallingTime * 2;
        sinceLastMove = null;
        sincePieceDown = null;
        musicsReader.destroyAllPlayers();
        musicsReader.play("theme.mp3", MediaPlayer.INDEFINITE);
        grid = new ArrayList<>();
        for (int i = 0; i < Settings.getYCases(); i++) {
            grid.add(new Line());
        }
        int r = (int) (Math.random() * suppliers.size());
        currentPiece = (Piece) suppliers.get(r).get();
        currentPiece.initPosition(grid);
        nextPieces = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            r = (int) (Math.random() * suppliers.size());
            nextPieces.add((Piece) suppliers.get(r).get());
        }
    }
    
    /**
     * Set new current piece and initialize the third next piece
     */
    private void initNextPiece() {
        currentPiece = nextPieces.get(0);
        if (!currentPiece.initPosition(grid) && gameOver == false) {
            musicsReader.destroyAllPlayers();
            musicsReader.play("gameover.mp3", 1);
            gameOver = true;
        }
        nextPieces.remove(0);
        int r = (int) (Math.random() * suppliers.size());
        nextPieces.add((Piece) suppliers.get(r).get());
        sincePieceDown = null;
        sinceLastMove = null;
    }

    /**
     * Performed action
     * 
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        manageKeys();
        manageLoop();
        repaint();
    }

    /**
     * Manage key inputs and related actions
     */
    private void manageKeys() {
        if (!gameOver) {
            if (keyStateManager.isPressed(KeyEvent.VK_UP) && 
                    !keyStateManager.isAlreadyPressed(KeyEvent.VK_UP)) {
                keyStateManager.setAlreadyPressed(KeyEvent.VK_UP);
                if (currentPiece.rotateRight(grid)) {
                    effectsReader.play("rotate.mp3", 1);
                    sinceLastMove = LocalTime.now();
                }
            }
            if (keyStateManager.isPressed(KeyEvent.VK_DOWN)) {
                keyStateManager.increaseBuffer(KeyEvent.VK_DOWN);
                if (keyStateManager.canBeUsed(KeyEvent.VK_DOWN)) {
                    if (currentPiece.canDown(grid)) {
                        currentPiece.down();
                        if (!keyStateManager.isAlreadyPressed(KeyEvent.VK_DOWN)) {
                            effectsReader.play("move.mp3", 1);
                        }
                        timeBufer = 0;
                        score += 1;
                        keyStateManager.setAlreadyPressed(KeyEvent.VK_DOWN);
                    } else if (sincePieceDown == null) {
                        sincePieceDown = LocalTime.now();
                        sinceLastMove = LocalTime.now();
                    }
                }
            }
            if (keyStateManager.isPressed(KeyEvent.VK_LEFT)) {
                keyStateManager.increaseBuffer(KeyEvent.VK_LEFT);
                if (keyStateManager.canBeUsed(KeyEvent.VK_LEFT)) {
                    if (currentPiece.setXDirection(-1, grid)) {
                        effectsReader.play("move.mp3", 1);
                        sinceLastMove = LocalTime.now();
                    }
                    keyStateManager.setAlreadyPressed(KeyEvent.VK_LEFT);
                }
            }
            if (keyStateManager.isPressed(KeyEvent.VK_RIGHT)) {
                keyStateManager.increaseBuffer(KeyEvent.VK_RIGHT);
                if (keyStateManager.canBeUsed(KeyEvent.VK_RIGHT)) {
                    if (currentPiece.setXDirection(1, grid)) {
                        effectsReader.play("move.mp3", 1);
                        sinceLastMove = LocalTime.now();
                    }
                    keyStateManager.setAlreadyPressed(KeyEvent.VK_RIGHT);
                }
            }
            if (keyStateManager.isPressed(KeyEvent.VK_SPACE) && 
                    !keyStateManager.isAlreadyPressed(KeyEvent.VK_SPACE)) {
                keyStateManager.setAlreadyPressed(KeyEvent.VK_SPACE);
                if (currentPiece.canDown(grid)) {
                    score += (currentPiece.fix() * 2);
                    sincePieceDown = LocalTime.MIN;
                    sinceLastMove = LocalTime.MIN;
                }
            }
            if (keyStateManager.isPressed(KeyEvent.VK_C)) {
                if (!hasSwiched && !keyStateManager.isAlreadyPressed(KeyEvent.VK_C)) {
                    keyStateManager.setAlreadyPressed(KeyEvent.VK_C);
                    if (holdPiece == null) {
                        holdPiece = currentPiece;
                        holdPiece.setPointsAsInitial();
                        initNextPiece();
                    } else {
                        Piece tempPiece = holdPiece;
                        holdPiece = currentPiece;
                        holdPiece.setPointsAsInitial();
                        currentPiece = tempPiece;
                        currentPiece.initPosition(grid);
                        hasSwiched = true;
                        sincePieceDown = null;
                        sinceLastMove = null;
                    }
                    effectsReader.play("hold.mp3", 1);
                }
            } 
            if (keyStateManager.isPressed(KeyEvent.VK_Z) && 
                    !keyStateManager.isAlreadyPressed(KeyEvent.VK_Z)) {
                keyStateManager.setAlreadyPressed(KeyEvent.VK_Z);
                if (currentPiece.rotateLeft(grid)) {
                    effectsReader.play("rotate.mp3", 1);
                    sinceLastMove = LocalTime.now();
                }
            }
        } else {
            if (keyStateManager.isPressed(KeyEvent.VK_R)) {
                initGame();
            }
        }
    }

    /**
     * Manage current loop operations
     */
    private void manageLoop() {
        if (!gameOver) {
            if (lastLoopTime != null) {
                timeBufer += lastLoopTime
                        .until(LocalTime.now(), ChronoUnit.MILLIS);
            }
            lastLoopTime = LocalTime.now();

            if (timeBufer > fallingTime) {
                int nbFalling = (int) (timeBufer / fallingTime);
                timeBufer = timeBufer % fallingTime;
                for (int i = 0; i < nbFalling; i++) {
                    if (currentPiece.canDown(grid)) {
                        currentPiece.down();
                        sincePieceDown = null;
                    } else if (sincePieceDown == null) {
                        sincePieceDown = LocalTime.now();
                        sinceLastMove = LocalTime.now();
                    }
                }
            }

            if (sincePieceDown != null && sinceLastMove != null) {
                long a = sinceLastMove
                        .until(LocalTime.now(), ChronoUnit.MILLIS);
                long b = sincePieceDown
                        .until(LocalTime.now(), ChronoUnit.MILLIS);
                if (a >= fallingTime || b >= fixTime) {
                    effectsReader.play("drop.mp3", 1);
                    currentPiece.getPoints().stream().filter((p) -> (p.y >= 0))
                            .forEachOrdered((p) -> {
                        grid.get(p.y).updatePoint(
                                p.x, currentPiece.getColorType()
                        );
                    });
                    int nbRemove = 0;
                    boolean newLevel = false;
                    for (int j = Settings.getYCases() - 1; j >= 0; j--) {
                        if (grid.get(j).isClear()) {
                            grid.remove(j);
                            nbRemove++;
                            nbLines++;
                            if (nbLines % 10 == 0) {
                                newLevel = true;
                            }
                        }
                    }
                    if (nbRemove == 4) {
                        effectsReader.play("tetris.mp3", 1);
                    } else if (nbRemove > 0) {
                        effectsReader.play("line.mp3", 1);
                    }
                    lineScore(nbRemove);
                    if (newLevel) {
                        effectsReader.play("level-up.mp3", 1);
                        level++;
                        fallingTime = (int) (fallingTime * .8d);
                        fixTime = fallingTime * 2;
                    }
                    for (int j = 0; j < nbRemove; j++) {
                        grid.add(0, new Line());
                    }
                    initNextPiece();
                    hasSwiched = false;
                }
            }
        }
    }
    
    /**
     * Paint components
     * 
     * @param g Graphic context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        GridPainter.paintGrid(g);
        GridPainter.paintLines(g, grid);
        
        if (!gameOver) {
            PiecePainter.paintGhostPiece(g, currentPiece);
            PiecePainter.paintCurrentPiece(g, currentPiece);
        }

        ScoreBoardPainter.paint(g, level, nbLines, score);

        if (holdPiece != null) {
            PiecePainter.paintHoldPiece(g, holdPiece);
        }
        PiecePainter.paintNextPieces(g, nextPieces);
        
        if (gameOver) {
            GameOverPainter.paint(g, this, score);
        }
    }
    
    /**
     * Get score addition with number of line cleared and current level
     * 
     * @param nbLine Number of line cleared
     */
    private void lineScore(int nbLine) {
        switch (nbLine) {
            case 1:
                score += level * 100;
                break;
            case 2:
                score += level * 300;
                break;
            case 3:
                score += level * 500;
                break;
            case 4:
                score += level * 800;
                break;
        }
    }
    
}
