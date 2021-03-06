package engine;

import java.io.Serializable;

public class Event implements Serializable{
	public Player p;//who triggered the event or who's minion
	public Player p2;//for targeting players for attacks or effects
	public Minion m;//minion that triggered event(such as summoned or activated effect)
	public Minion m2;//a minion that was affected by the event(such as m2 got destroyed by m)
	public int amount;//used for amount of damage or healing done etc.
	public int mana;
	public String eventType; //description of what happened
	
	public Event(String type){
		eventType = type;
		assert(eventType != null);
	}
	/* types include
	summon
	declaredAttack
	minionDestroyed(this minion was destroyed by m2)
	spentMana
	resumeProcessing - for affects that require user input for their target
	tookDamage
	wasHealed
	playerDamaged
	playerDrewCards
	*/
}
