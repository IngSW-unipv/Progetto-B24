package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;

/**
 * Interface that every playable object must implement.
 * Implemented by {@link JBAudio}.
 *
 * @author Giorgio Giacomotti
 * @see JBAudio
 * @see Song
 * @see Episode
 */
public interface IJBPlayable extends IJBResearchable {

    /**
     * Abstract method to be implemented.
     * Is expected to play the audio file of playable object.
     */
    void playFX();
}
