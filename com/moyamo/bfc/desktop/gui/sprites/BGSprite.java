package com.moyamo.bfc.desktop.gui.sprites;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URI;

import javax.swing.ImageIcon;

public class BGSprite implements IDrawable{
	Image image;
	ImageObserver observer;
	public BGSprite(URI image, ImageObserver observer){
		this.observer = observer;
		try {
			this.image = new ImageIcon(image.toURL()).getImage();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
