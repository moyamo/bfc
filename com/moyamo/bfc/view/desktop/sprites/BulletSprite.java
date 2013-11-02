package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Color;
import java.awt.Graphics;

import com.moyamo.bfc.model.EntityStore;
import com.moyamo.bfc.model.entities.Bullet;

public class BulletSprite implements IDrawable{
	private int entityID;
	private boolean destroyed;
	
	public BulletSprite (int id){
		this.entityID = id;
	}
	@Override
	public void draw(Graphics g, long timeDiff) {
		Bullet b = (Bullet) EntityStore.self().getEntity(entityID);
		processDrawEvents();
		g.setColor(Color.BLACK);
		g.fillOval(b.getX(), b.getY(), 3,5);
		g.setColor(Color.RED);
		g.fillRect(b.getX() - 5*b.getDirection(), b.getY(), 5, 5);
	}
	@Override
	public boolean destroyed() {
		return destroyed;
	}
	
	private void processDrawEvents() {
		Bullet b = (Bullet) EntityStore.self().getEntity(entityID);
		for (String e = b.nextDrawEvent(); e != null; e = b.nextDrawEvent()){
			if(e.equals("destroy")){
				destroyed = true;
			}
		}
	}
}
