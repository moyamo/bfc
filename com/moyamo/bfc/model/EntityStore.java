package com.moyamo.bfc.model;

import java.util.ArrayList;
import java.util.List;

import com.moyamo.bfc.model.entities.Entity;
import com.moyamo.bfc.model.entities.Player;

/**
 * Stores all game entities
 * 
 * @author Mohammed Yaseen Mowzer
 */
public class EntityStore{
	private Player combatants[];
	private static EntityStore instance;
	private List<Entity>entities;
	
	private EntityStore (){
		entities = new ArrayList<Entity>();
	}
	
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
		return entities.get(entityNo);
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
		entities.add(combatant);
	}
	
	void delEntity(int id){
		entities.set(id, null);
	}
	
	int addEntity(Entity e){
		entities.add(e);
		return entities.size() - 1;
	}
	int totalEntites(){
		return entities.size();
	}
}
