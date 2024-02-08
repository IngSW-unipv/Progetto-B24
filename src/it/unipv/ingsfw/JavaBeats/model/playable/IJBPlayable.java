package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;

/**
 * Interface that every playable object must implement.
 * Implemented by {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio}.
 *
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Song
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode
 */
public interface IJBPlayable extends IJBResearchable {

    /**
     * Abstract method to be implemented.
     * Is expected to play the audio file of playable object.
     */
    void playFX();
}
