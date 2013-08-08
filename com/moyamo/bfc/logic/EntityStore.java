package com.moyamo.bfc.logic;

import com.moyamo.bfc.entities.Entity;
import com.moyamo.bfc.entities.Player;

/**
 * Stores all game entities
 * 
 * @author Mohammed Yaseen Mowzer
 */
public class EntityStore{
	private Player combatants[];
	private static EntityStore instance;
	private Entity entities[];
	
	private EntityStore (){}
	
	public static EntityStore self() {
		if (instance == null) {
			instance = new EntityStore();
		}
		return instance;
	}
	
	public Player getCombatant(int playerNo){
		return combatants[playerNo];
	}
	
	public Entity getEntity(int entityNo){
		return entities[entityNo];
	}
	
	Player[] getCombatants(){
		return combatants;
	}
	
	void addCombatant(Player combatant){
		if (combatants == null){
			combatants = new Player[2];
			combatants[0] = combatant;
		} else {
			combatants[1] = combatant;
		}
	}
}
