package com.moyamo.bfc.desktop.gui.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import com.moyamo.bfc.entities.BruceLee;
import com.moyamo.bfc.logic.EntityStore;

public class BruceSprite implements IDrawable{
	private int entityID;
	private ImageObserver observer;
	private ImageIcon leeImage = new ImageIcon(this.getClass().getResource("/com/moyamo/bfc/res/images/Bruce_Lee/BruceLee.png"));
	public BruceSprite(int entityID, ImageObserver observer){
		this.entityID = entityID;
		this.observer = observer;
	}
	
	@Override
	public void draw(Graphics g, long timeDiff) {
		BruceLee lee = (BruceLee)EntityStore.self().getCombatant(entityID);
		g.drawImage(leeImage.getImage(), lee.getX(),lee.getY(), observer);
	}
	
	@Override
	public boolean destroyed() {
		return false;
	}

}
