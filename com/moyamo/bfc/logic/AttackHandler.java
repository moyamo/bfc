package com.moyamo.bfc.logic;

import com.moyamo.bfc.events.AttackEvent;

/**
 * This class processes all attacks that occur during play.
 *
 * @author Mohammed Yaseen Mowzer
 * @version  0.0.1
 *
 */
public class AttackHandler {
	
	public void processAttack(AttackEvent e) {
		EntityStore.self().getCombatant(e.getDefenderID()).applyAttack(e);
	}
} 
