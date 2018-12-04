package fr.alexandrebertrand.tetris;

import fr.alexandrebertrand.tetris.models.abstracts.Piece;
import fr.alexandrebertrand.tetris.models.*;
import fr.alexandrebertrand.tetris.utils.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javafx.scene.media.MediaPlayer;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Board of the game
 * @author Alexandre Bertrand
 */
public final class Board extends JPanel implements ActionListener {

    /*
     * Constants
     */
    
    /** Timer for game animations */
    private final Timer TIMER;
    
    /** Music reader of the game */
    private final SoundReader MUSIC_READER;
    
    /** Sound effect reader of the game */
    private final SoundReader EFFECT_READER;
    
    /** Dimensions of the Board */
    private final Dimension BOARD_DIM;
    
    /** Set of case colors */
    private final CaseColorSet CASE_COLOR_SET;
    
    /** Minimal box size */
    private static final int BOX_SIZE = 30;
    
    private static final long DELAY_BETWEN_MOVES = 200;
    
    private final KeysStateManager keysStateManager;
    
    /*
     * Attributes
     */
    
    /** Settings of the game */
    private Settings settings;
    
    /** Main font of the game */
    private Font font;
    
    /** Current piece of the game */
    private static Piece currentPiece;
    
    /** Next piece of the game */
    private static ArrayList<Piece> nextPiece;
    
    /** Stored piece of the game */
    private static Piece storedPiece;
    
    /** Grid of the game */
    private ArrayList<Line> grid;
    
    /** Number of lines compleate */
    private int nbLines;
    
    /** Current level */
    private int level;
    
    /** Score of the player */
    private int score;
    
    /** Has swiched piece */
    private boolean hasSwiched;
    
    private List<Supplier> suppliers;
    
    private boolean gameOver;
    
    private LocalTime lastLoopTime;
    
    private long timeBufer;
    
    private long fallingTime;
    
    private LocalTime lastDown;
    
    private LocalTime lastLeft;
    
    private LocalTime lastRight;
    
    private long bufferDown;
    
    private long bufferLeft;
    
    private long bufferRight;
    
    private long fixTime;
    
    private LocalTime sinceLastMove;
    
    private LocalTime sincePieceDown;
        
    /*
     * Constructors
     */
    
    /**
     * Default constructor of the snake
     * Initialise the board and the game
     */
    public Board() {
        getSettings();
        BOARD_DIM = new Dimension(
                Board.BOX_SIZE * settings.getXCases(),
                Board.BOX_SIZE * settings.getYCases()
        );
        MUSIC_READER = new SoundReader(settings.getMusicsVolume(), "musics/");
        EFFECT_READER = new SoundReader(settings.getEffectsVolume(), "effects/");
        loadSounds();
        CASE_COLOR_SET = new CaseColorSet();
        initRotationOperations();
        initInitialPositions();
        initSuppliers();
        initGame();
        initBoard();
        initFont();
        keysStateManager = new KeysStateManager();
        int ms = (int) Math.round(1000d / settings.getFrapsPerSeconds());
        TIMER = new Timer(ms, this);
        TIMER.start();
    }
    
    /*
     * Methods
     */
    
    /**
     * Get settings of the game
     */
    private void getSettings() {
        try {
            InputStream is = this.getClass().getClassLoader()
                    .getResourceAsStream("resources/datas/settings.json");
            int ch;
            StringBuilder sb = new StringBuilder();
            while((ch = is.read()) != -1)
                sb.append((char)ch);
            Gson gson = new Gson();
            settings = new Settings();
            settings = gson.fromJson(sb.toString(), Settings.class);
        } catch (JsonSyntaxException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Initialize suppliers
     */
    private void initSuppliers() {
        final int x = settings.getXCases();
        final int y = settings.getYCases();
        suppliers = new ArrayList<>();
        suppliers.add(() -> new PieceA(x, y));
        suppliers.add(() -> new PieceB(x, y));
        suppliers.add(() -> new PieceC(x, y));
        suppliers.add(() -> new PieceD(x, y));
        suppliers.add(() -> new PieceE(x, y));
        suppliers.add(() -> new PieceF(x, y));
        suppliers.add(() -> new PieceG(x, y));
    }
    
    private void initRotationOperations() {
        PieceA.initRotationOperations();
        PieceB.initRotationOperations();
        PieceC.initRotationOperations();
        PieceE.initRotationOperations();
        PieceF.initRotationOperations();
        PieceG.initRotationOperations();
    }
    
    private void initInitialPositions() {
        PieceA.initInitialPosition();
        PieceB.initInitialPosition();
        PieceC.initInitialPosition();
        PieceD.initInitialPosition();
        PieceE.initInitialPosition();
        PieceF.initInitialPosition();
        PieceG.initInitialPosition();
    }
    
    /**
     * Initialize the Board of the game
     */
    public void initBoard() {
        setBackground(new Color(36, 35, 35));
        Dimension d = new Dimension(this.BOARD_DIM);
        d.setSize(d.getWidth() + BOX_SIZE * 4 + 80, d.getHeight());
        setPreferredSize(d);
        setFocusable(true);
    }
    
    /**
     * Initialize the game
     */
    public void initGame() {
        gameOver = false;
        nbLines = 0;
        level = 1;
        score = 0;
        lastLoopTime = null;
        timeBufer = 0;
        fallingTime = 1000;
        lastDown = null;
        lastLeft = null;
        lastRight = null;
        bufferDown = 0;
        bufferLeft = 0;
        bufferRight = 0;
        fixTime = fallingTime * 2;
        sinceLastMove = null;
        sincePieceDown = null;
        MUSIC_READER.destroyAllPlayers();
        MUSIC_READER.play("theme.mp3", MediaPlayer.INDEFINITE);
        grid = new ArrayList<>();
        for (int i = 0; i < settings.getYCases(); i++) {
            grid.add(new Line(settings.getXCases()));
        }
        int r = (int) (Math.random() * suppliers.size());
        currentPiece = (Piece) suppliers.get(r).get();
        currentPiece.initPosition(grid);
        Board.nextPiece = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            r = (int) (Math.random() * suppliers.size());
            nextPiece.add((Piece) suppliers.get(r).get());
        }
    }
    
    /**
     * Set new current piece and initialize the third next piece
     */
    private void initNextPiece() {
        currentPiece = nextPiece.get(0);
        if (!currentPiece.initPosition(grid) && gameOver == false) {
            MUSIC_READER.destroyAllPlayers();
            MUSIC_READER.play("gameover.mp3", 1);
            gameOver = true;
        }
        nextPiece.remove(0);
        int r = (int) (Math.random() * suppliers.size());
        nextPiece.add((Piece) suppliers.get(r).get());
        sincePieceDown = null;
        sinceLastMove = null;
    }
    
    /**
     * Load all ressources
     */
    private void loadSounds() {
        MUSIC_READER.loadResource("theme.mp3");
        MUSIC_READER.loadResource("gameover.mp3");
        EFFECT_READER.loadResource("drop.mp3");
        EFFECT_READER.loadResource("move.mp3");
        EFFECT_READER.loadResource("rotate.mp3");
        EFFECT_READER.loadResource("hold.mp3");
        EFFECT_READER.loadResource("level-up.mp3");
        EFFECT_READER.loadResource("line.mp3");
        EFFECT_READER.loadResource("tetris.mp3");
    }
    
    private void initFont() {
        try {
            font = Font.createFont(0, getClass().getResourceAsStream(
                    "/resources/fonts/Tetris.ttf"));
        } catch(FontFormatException | IOException e){
            font = new Font ("Serif", Font.PLAIN, 15);
        }
    }
    
    /**
     * Get main font of the game
     * @param style Style of the text
     * @param size Size of the text
     * @return The font to use to write
     */
    public Font getFont(int style, float size) {
        return font.deriveFont(style, size);
    }
    
    /**
     * Paint the component
     * @param g Graphic context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Map<TextAttribute, Object> fontAttrs = new HashMap<>();
        fontAttrs.put(TextAttribute.TRACKING, 0.1);
        Font curFont = getFont(Font.PLAIN, 12f).deriveFont(fontAttrs);
        g.setFont(curFont);
        
        // Init grid cases
        g.setColor(Color.BLACK);
        for (int i = 0; i < settings.getXCases(); i++) {
            for (int j = 0; j < settings.getYCases(); j++) {
                fillBorderRect(g, new Point(i, j));
            }
        }
        
        // Paint grid lines
        for (int i = 0; i < settings.getYCases(); i++) {
            for (int j = 0; j < settings.getXCases(); j++) {
                if (grid.get(i).getPoints().get(j) != 0) {
                    g.setColor(CASE_COLOR_SET.getCaseColors().get(
                            grid.get(i).getPoints().get(j) - 1
                    ).getBorderColor());
                    fillBorderRect(g, new Point(j, i));
                    g.setColor(CASE_COLOR_SET.getCaseColors().get(
                            grid.get(i).getPoints().get(j) - 1
                    ).getColor());
                    fillRect(g, new Point(j, i));
                }
            }
        }
        
        if (!gameOver) {
            // Paint gost of current piece
            for (Point p: currentPiece.getGostPoints()) {
                g.setColor(CASE_COLOR_SET.getCaseColors().get(currentPiece
                        .getColorValue() - 1).getColor());
                fillBorderRect(g, p);
                g.setColor(Color.BLACK);
                fillRect(g, p);
            }

            // Paint current piece
            for (Point p: currentPiece.getPoints()) {
                g.setColor(CASE_COLOR_SET.getCaseColors().get(currentPiece
                        .getColorValue() - 1).getBorderColor());
                fillBorderRect(g, p);
                g.setColor(CASE_COLOR_SET.getCaseColors().get(currentPiece
                        .getColorValue() - 1).getColor());
                fillRect(g, p);
            }
        }
        
        int bs = Board.BOX_SIZE;
        int rb = (int) BOARD_DIM.getWidth();
        g.setColor(Color.BLACK);
        g.fillRoundRect(rb + 20, 25, bs * 4 + 40, 40, 40, 40);
        g.fillRoundRect(rb + 20, 75, bs * 4 + 40, 40, 40, 40);
        g.fillRoundRect(rb + 20, 125, bs * 4 + 40, 40, 40, 40);
        g.fillRoundRect(rb + 20, 210, bs * 4 + 40, bs * 2 + 20, 40, bs * 2 + 20);
        g.fillRoundRect(rb + 20, bs * 2 + 275, bs * 4 + 40, bs * 6 + 40, 40, bs * 6 + 40);
        g.setColor(Color.WHITE);
        g.drawString("Level : " + level, rb + 40, 50);
        g.drawString("Lines : " + nbLines, rb + 40, 100);
        g.drawString("Score : " + score, rb + 40, 150);
        g.drawString("Hold : ", rb + 40, 200);
        g.drawString("Next : ", rb + 40, bs * 2 + 265);

        if (Board.storedPiece != null) {
            // Paint stored piece
            Point sp = new Point();
            sp.setLocation(
                    settings.getXCases() * bs + 40d
                            + (4 - storedPiece.getPieceWidth()) * (bs / 2),
                    220d - (2 - storedPiece.getPieceHeight()) * (bs / 2)
            );
            for (Point p: storedPiece.getPoints()) {
                g.setColor(CASE_COLOR_SET.getCaseColors().get(storedPiece
                        .getColorValue() - 1).getBorderColor());
                fillBorderRect(g, p, sp);
                g.setColor(CASE_COLOR_SET.getCaseColors().get(storedPiece
                        .getColorValue() - 1).getColor());
                fillRect(g, p, sp);
            }
        }
        // Paint next pieces
        for (int i = 0; i < 3; i++) {
            Point np = new Point();
            np.setLocation(
                    settings.getXCases() * bs + 40d
                            + (4 - nextPiece.get(i).getPieceWidth()) * (bs / 2),
                    285d - (2 - nextPiece.get(i).getPieceHeight()) * (bs / 2)
                            + i * (bs * 2 + 10) + bs * 2
            );
            for (Point p: nextPiece.get(i).getPoints()) {
                g.setColor(CASE_COLOR_SET.getCaseColors().get(nextPiece
                        .get(i).getColorValue() - 1).getBorderColor());
                fillBorderRect(g, p, np);
                g.setColor(CASE_COLOR_SET.getCaseColors().get(nextPiece
                        .get(i).getColorValue() - 1).getColor());
                fillRect(g, p, np);
            }
        }
        
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.fillRoundRect(80, 40, BOARD_DIM.width + BOX_SIZE * 4 - 80,
                    BOARD_DIM.height - 80, 44, 44);
            g.setColor(Color.DARK_GRAY);
            g.fillRoundRect(84, 44, BOARD_DIM.width + BOX_SIZE * 4 - 88,
                    BOARD_DIM.height - 88, 40, 40);
            g.setColor(Color.WHITE);
            curFont = getFont(Font.PLAIN, 38f).deriveFont(fontAttrs);
            g.setFont(curFont);
            FontMetrics curFM = getFontMetrics(curFont);
            String t = "Game Over";
            g.drawString(t,
                    (getWidth() - curFM.stringWidth(t)) / 2,
                    100);
            g.fillRect(120, 130, getWidth() - 240, 3);
            curFont = getFont(Font.PLAIN, 24f).deriveFont(fontAttrs);
            g.setFont(curFont);
            curFM = getFontMetrics(curFont);
            t = "Score : " + this.score;
            g.drawString(t,
                    (getWidth() - curFM.stringWidth(t)) / 2,
                    300);
            curFont = getFont(Font.PLAIN, 20f).deriveFont(fontAttrs);
            g.setFont(curFont);
            curFM = getFontMetrics(curFont);
            t = "Press 'R' to restart";
            g.drawString(t,
                    (getWidth() - curFM.stringWidth(t)) / 2,
                    (getHeight() - 80));
        }
        
    }
    
    /**
     * Paint rectangle (box into the grid)
     * @param g Graphic context
     * @param p Position of the element
     */
    private void fillBorderRect(Graphics g, Point p) {
        g.fillRect(
                p.x * BOX_SIZE + 1, p.y * BOX_SIZE  + 1,
                BOX_SIZE - 2, BOX_SIZE - 2
        );
    }
    
    /**
     * Paint rectangle (box into the grid)
     * @param g Graphic context
     * @param p Position of the element
     */
    private void fillBorderRect(Graphics g, Point p, Point a) {
        g.fillRect(
                p.x * BOX_SIZE + 1 + a.x, p.y * BOX_SIZE  + 1 + a.y,
                BOX_SIZE - 2, BOX_SIZE - 2
        );
    }
    
    /**
     * Paint background rectangle
     * @param g Graphic context
     * @param p Position of the element
     */
    private void fillRect(Graphics g, Point p) {
        g.fillRect(
                p.x * BOX_SIZE + 2, p.y * BOX_SIZE + 2,
                BOX_SIZE - 4, BOX_SIZE - 4
        );
    }
    
    /**
     * Paint background rectangle
     * @param g Graphic context
     * @param p Position of the element
     */
    private void fillRect(Graphics g, Point p, Point a) {
        g.fillRect(
                p.x * BOX_SIZE + 2 + a.x, p.y * BOX_SIZE + 2 + a.y,
                BOX_SIZE - 4, BOX_SIZE - 4
        );
    }
    
    /**
     * Performed action
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        manageKeys();
        actions();
        repaint();
    }
    
    private void actions() {
        if (gameOver) {
            
        } else {
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
                    EFFECT_READER.play("drop.mp3", 1);
                    currentPiece.getPoints().stream().filter((p) -> (p.y >= 0))
                            .forEachOrdered((p) -> {
                        grid.get(p.y).updatePoint(
                                p.x, currentPiece.getColorValue()
                        );
                    });
                    int nbRemove = 0;
                    boolean newLevel = false;
                    for (int j = settings.getYCases() - 1; j >= 0; j--) {
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
                        EFFECT_READER.play("tetris.mp3", 1);
                    } else if (nbRemove > 0) {
                        EFFECT_READER.play("line.mp3", 1);
                    }
                    lineScore(nbRemove);
                    if (newLevel) {
                        EFFECT_READER.play("level-up.mp3", 1);
                        level++;
                        fallingTime = (int) (fallingTime * .8d);
                        fixTime = fallingTime * 2;
                    }
                    for (int j = 0; j < nbRemove; j++) {
                        grid.add(0, new Line(settings.getXCases()));
                    }
                    initNextPiece();
                    hasSwiched = false;
                }
            }
        }
    }
    
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
    
    /**
     * Key Listener
     */
    private void manageKeys() {
        if (!gameOver) {
            if (KeysStateManager.isUpPressed() && 
                    !KeysStateManager.isUpAlreadyPressed()) {
                KeysStateManager.setUpAlreadyPressed(true);
                if (currentPiece.rotateRight(grid)) {
                    EFFECT_READER.play("rotate.mp3", 1);
                    sinceLastMove = LocalTime.now();
                }
            }
            if (KeysStateManager.isDownPressed()) {
                if (lastDown != null) {
                    bufferDown += lastDown
                            .until(LocalTime.now(), ChronoUnit.MILLIS);
                }
                lastDown = LocalTime.now();
                if (bufferDown > DELAY_BETWEN_MOVES ||
                        !KeysStateManager.isDownAlreadyPressed()) {
                    bufferDown = 0;
                    if (currentPiece.canDown(grid)) {
                        currentPiece.down();
                        if (!KeysStateManager.isDownAlreadyPressed()) {
                            EFFECT_READER.play("move.mp3", 1);
                        }
                        timeBufer = 0;
                        score += 1;
                        KeysStateManager.setDownAlreadyPressed(true);
                    } else if (sincePieceDown == null) {
                        sincePieceDown = LocalTime.now();
                        sinceLastMove = LocalTime.now();
                    }
                }
            }
            if (KeysStateManager.isLeftPressed()) {
               if (lastLeft != null) {
                    bufferLeft += lastLeft
                            .until(LocalTime.now(), ChronoUnit.MILLIS);
                }
                lastLeft = LocalTime.now();
                if (bufferLeft > DELAY_BETWEN_MOVES ||
                        !KeysStateManager.isLeftAlreadyPressed()) {
                    bufferLeft = 0;
                    if (currentPiece.setXDirection(-1, grid)) {
                        EFFECT_READER.play("move.mp3", 1);
                        sinceLastMove = LocalTime.now();
                    }
                    KeysStateManager.setLeftAlreadyPressed(true);
                }
            }
            if (KeysStateManager.isRightPressed()) {
               if (lastRight != null) {
                    bufferRight += lastRight
                            .until(LocalTime.now(), ChronoUnit.MILLIS);
                }
                lastRight = LocalTime.now();
                if (bufferRight > DELAY_BETWEN_MOVES ||
                        !KeysStateManager.isRightAlreadyPressed()) {
                    bufferRight = 0;
                    if (currentPiece.setXDirection(1, grid)) {
                        EFFECT_READER.play("move.mp3", 1);
                        sinceLastMove = LocalTime.now();
                    }
                    KeysStateManager.setRightAlreadyPressed(true);
                }
            }
            if (KeysStateManager.isSpacePressed() && 
                    !KeysStateManager.isSpaceAlreadyPressed()) {
                KeysStateManager.setSpaceAlreadyPressed(true);
                if (currentPiece.canDown(grid)) {
                    score += (currentPiece.fix() * 2);
                    sincePieceDown = LocalTime.MIN;
                    sinceLastMove = LocalTime.MIN;
                }
            }
            if (KeysStateManager.isCPressed()) {
                if (!hasSwiched && !KeysStateManager.isCAlreadyPressed()) {
                    KeysStateManager.setCAlreadyPressed(true);
                    if (storedPiece == null) {
                        storedPiece = currentPiece;
                        storedPiece.setPointsAsInitial();
                        initNextPiece();
                    } else {
                        Piece tempPiece = storedPiece;
                        storedPiece = currentPiece;
                        storedPiece.setPointsAsInitial();
                        currentPiece = tempPiece;
                        currentPiece.initPosition(grid);
                        hasSwiched = true;
                        sincePieceDown = null;
                        sinceLastMove = null;
                    }
                    EFFECT_READER.play("hold.mp3", 1);
                }
            } 
            if (KeysStateManager.isZPressed() && 
                    !KeysStateManager.isZAlreadyPressed()) {
                KeysStateManager.setZAlreadyPressed(true);
                if (currentPiece.rotateLeft(grid)) {
                    EFFECT_READER.play("rotate.mp3", 1);
                    sinceLastMove = LocalTime.now();
                }
            }
        } else {
            if (KeysStateManager.isRPressed()) {
                initGame();
            }
        }
    }
    
}
