package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;

import com.moyamo.bfc.model.entities.Entity;

public interface IDrawable{
	public void draw(Graphics g, long timeDiff);
	public boolean destroyed();
	public void update(Entity entity);
}
