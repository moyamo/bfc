package com.moyamo.bfc.events;

public class ProjectileEvent {
	public int origX;
	public int origY;
	public int speedX;
	public int speedY;
	public enum projType { BULLET };
	public projType type;
	public ProjectileEvent(int origX, int origY, int speedX, int speedY, projType type){
		this.origX = origX;
		this.origY = origY;
		this.speedX = speedX;
		this.speedY = speedY;
		this.type = type;
	}
}
