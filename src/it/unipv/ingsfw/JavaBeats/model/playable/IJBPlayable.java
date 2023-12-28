package it.unipv.ingsfw.javabeats.model.playable;

import it.unipv.ingsfw.javabeats.model.user.IJBProfile;

public interface IJBPlayable {
	public int PlayableID;
	public String playableName;
	public IJBProfile creator;
}
