package com.moyamo.bfc.desktop.gui.sprites;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class BGSprite implements IDrawable{
	Image image;
	ImageObserver observer;
	public BGSprite(Image image, ImageObserver observer){
		this.observer = observer;
		this.image = image;
	}
	
	@Override
	public void draw(Graphics g, long timeDiff) {
			g.drawImage(image, 0, 0, observer);
	}

	@Override
	public boolean destroyed() {
		return false;
	}
	
}
