package affects;

import engine.GameState;

//this is the interface for affects
//an affect is part of a monsters effect
//an affect makes changes to the game
public interface Affect {
	public void applyAffect();
	public String getDescription();
}
