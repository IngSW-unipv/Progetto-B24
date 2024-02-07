package it.unipv.ingsfw.JavaBeats.model;

import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

/**
 * Interface that every researchable object must implement (or extend if is an interface).
 * Extended by {@link IJBPlayable}.
 * Implemented by {@link JBCollection} and {@link JBProfile}.
 *
 * @author Giorgio Giacomotti
 * @see IJBPlayable
 * @see JBCollection
 * @see JBProfile
 */
public interface IJBResearchable {
}
