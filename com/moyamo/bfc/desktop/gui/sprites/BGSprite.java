package com.moyamo.bfc.desktop.gui.sprites;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URI;

import javax.swing.ImageIcon;

public class BGSprite implements IDrawable{
	Image image;
	
	public BGSprite(URI image){
		try {
			this.image = new ImageIcon(image.toURL()).getImage();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Graphics g, long timeDiff, ImageObserver observer) {
			g.drawImage(image, 0, 0, observer);
	}
	
}
