package com.moyamo.bfc.desktop.gui.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;


public interface IDrawable{
	public void draw(Graphics g, long timeDiff, ImageObserver observer);
}
