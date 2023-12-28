package it.unipv.ingsfw.JavaBeats.model.playable;

import java.util.List;
import it.unipv.ingsfw.JavaBeats.model.EJBMODE;
import it.unipv.ingsfw.JavaBeats.model.user.IJBProfile;

public class Podcast implements IJBCollection {
	private List<Episode> trackList;

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
