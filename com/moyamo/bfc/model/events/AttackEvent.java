package com.moyamo.bfc.model.events;

public class AttackEvent {
	private int damage, direction;
	private int x, y;
	private int defenderID;
	private String attackType;
	public AttackEvent(int damage, int x, int y, int direction){
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.attackType = "";
	}
	
	public void setAttackType(String type){
		attackType = type;
	}
	public String getAttackType(){
		return attackType;
	}
	public int getDamage(){
		return damage;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setDefenderID(int ID){
		this.defenderID = ID;
	}
	public int getDefenderID(){
		return defenderID;
	}
	public int getDirection(){
		return direction;
	}
}
