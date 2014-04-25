package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import com.moyamo.bfc.model.entities.Entity;

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

	@Override
	public void update(Entity entity) {};
}
