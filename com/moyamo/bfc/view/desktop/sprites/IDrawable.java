package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;
import java.nio.ByteBuffer;


public interface IDrawable{
	public void draw(Graphics g, long timeDiff);
	public boolean destroyed();
	public void update(ByteBuffer buffer);
}
