package com.moyamo.bfc.entities;

import com.moyamo.bfc.events.DestroyedEvent;

public class Bullet extends Projectile{

	public Bullet(int x, int y, int speedX, int speedY) {
		super(x, y, 0, 0, speedX > 0 ? 1 : -1);
		this.setXSpeed(speedX);
		this.setYSpeed(speedY);
	}
	
	public void destroy(){
		addEvent(new DestroyedEvent());
		addDrawEvent("destroy");
	}
	
	public int getDamage(){
		return 50;
	}
}
