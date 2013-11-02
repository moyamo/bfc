package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;


public interface IDrawable{
	public void draw(Graphics g, long timeDiff);
	public boolean destroyed();
}
