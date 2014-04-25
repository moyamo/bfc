package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Color;
import java.awt.Graphics;

import com.moyamo.bfc.model.entities.Bullet;
import com.moyamo.bfc.model.entities.Entity;

public class BulletSprite implements IDrawable{
	private boolean destroyed;
	private int x, y, direction;
		
	@Override
	public void draw(Graphics g, long timeDiff) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, 3, 5);
		g.setColor(Color.RED);
		g.fillRect(x - 5*direction, y, 5, 5);
	}
	
	@Override
	public boolean destroyed() {
		return destroyed;
	}
	
	private void setDrawFlags(Bullet bullet) {
		for (String e = bullet.nextDrawEvent(); e != null; e = bullet.nextDrawEvent()){
			if(e.equals("destroy")){
				destroyed = true;
			}
		}
	}

	@Override
	public void update(Entity entity) {
		Bullet bullet = (Bullet) entity;
		x = bullet.getX();
		y = bullet.getY();
		direction = bullet.getDirection();
		setDrawFlags(bullet);
	}
}
