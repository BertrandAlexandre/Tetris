package fr.alexandrebertrand.tetris.utils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Sounds reader and manager to play sounds
 * 
 * @author Alexandre Bertrand
 */
public final class SoundReader {

    /*
     * Constants
     */

    /** Max number of players playing simultaneously */
    private static final int NB_PLAYERS_MAX = 10;

    /*
     * Attributes
     */

    /** Sound volume of the reader */
    private final double volume;

    /** Path of the sound ressources directory */
    private final String path;

    /** Medias collection */
    private final HashMap<String, Media> medias;

    /* List of players */
    private final List<MediaPlayer> players;

    /** Number of media players currently playing */
    private int nbPlayers;

    /*
     * Constructor
     */

    /**
     * Default constructor to initialise medias and players
     * @param volume Volume of sounds
     * @param prefix Prefix of the sound ressources directory
     */
    public SoundReader(double volume, String prefix) {
        this.volume = volume;
        path = "/resources/sounds/" + prefix;
        medias = new HashMap<>();
        players = new ArrayList<>();
        nbPlayers = 0;
    }

    /*
     * Methods
     */

    /**
     * Read a sound with loop
     * @param fileName Filename of the sound
     * @param nbLoop   Number of loop to play sound
     */
    public void play(String fileName, int nbLoop) {
        if (nbPlayers >= SoundReader.NB_PLAYERS_MAX) {
            return;
        } // else
        final MediaPlayer player = new MediaPlayer(medias.get(fileName));
        player.setCycleCount(nbLoop);
        player.setVolume(volume);
        player.play();
        nbPlayers++;
        player.setOnEndOfMedia(() -> {
            if (player.getCycleCount() <= player.getCurrentCount() &&
                    player.getCycleCount() != MediaPlayer.INDEFINITE) {
                player.dispose();
                nbPlayers--;
            }
        });
        players.add(player);
    }

    /**
     * Load a sound resource
     * @param fileName File name of the sound
     */
    public void loadResource(String fileName) {
        try {
            Media med = new Media(
                    getClass().getResource(path + fileName)
                            .toURI().toString()
            );
            medias.put(fileName, med);
        } catch (URISyntaxException ex) {
            Logger.getLogger(SoundReader.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Destroy all current sound players
     */
    public void destroyAllPlayers() {
        players.forEach((p) -> p.dispose());
        players.clear();
    }

}
