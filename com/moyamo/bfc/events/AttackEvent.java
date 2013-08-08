package com.moyamo.bfc.events;

public class AttackEvent {
	private int damage, direction;
	private int x, y;
	private int defenderID;
	public AttackEvent(int damage, int x, int y, int direction){
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.direction = direction;
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
