package it.unipv.ingsfw.javabeats.model.playable;

import java.util.List;
import it.unipv.ingsfw.javabeats.model.EJBMODE;
import it.unipv.ingsfw.javabeats.model.user.IJBProfile;

public class Playlist implements IJBCollection {
	private List<IJBAudio> trackList;
	
	
	@Override
	public void addItem(IJBProfile activeJBProfile, IJBItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeItem(IJBProfile activeJBProfile, IJBItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void play(EJBMODE mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

}
