package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import com.moyamo.bfc.model.EntityStore;
import com.moyamo.bfc.model.entities.BruceLee;
import com.moyamo.bfc.res.ImageStore;

public class BruceSprite implements IDrawable{
	private int entityID;
	private static final int ANIM_DELAY = 75;
	private int animCount = ANIM_DELAY;
	private boolean attacking, flykicking;
	private ImageObserver observer;
	private ImageIcon leeImage;
	private ImageIcon leePunchImage;
	private ImageIcon leeFlyKickImage;
	private ImageIcon lastImage;
	private ImageIcon rightLeeImage;
	private ImageIcon rightLeePunchImage;
	private ImageIcon rightLeeFlyKickImage;
	private int x, y, width, height, xBounds, yBounds;
	
	public BruceSprite(int entityID, ImageObserver observer){
		this.entityID = entityID;
		this.observer = observer;
		ImageIcon limage[] = ImageStore.getBruceImages();
		leeImage = limage[0];
		leePunchImage = limage[1];
		leeFlyKickImage = limage[2];
		rightLeeImage = limage[3];
		rightLeePunchImage = limage[4];
		rightLeeFlyKickImage = limage[5];
		lastImage = leeImage;
		xBounds = leeImage.getIconWidth();
		yBounds = leeImage.getIconHeight();
	}
	
	@Override
	public void draw(Graphics g, long timeDiff) {
		BruceLee lee = (BruceLee)EntityStore.self().getCombatant(entityID);
		setDrawFlags(lee);
		x = lee.getX();
		y = lee.getY();
		if(animCount < 0){
			if (attacking){
				if (lee.getDirection() == -1){
					lastImage = leePunchImage;
				}else{
					lastImage = rightLeePunchImage;
				}
				attacking = false;
			}else if (flykicking){
				if (lee.getDirection() == -1){
					lastImage = leeFlyKickImage;
				}else{
					lastImage = rightLeeFlyKickImage;
				}
				if (lee.onGround()){
					flykicking = false;
				}
			}else{
				if (lee.getDirection() == -1){
					lastImage = leeImage;
				}else{
					lastImage = rightLeeImage;
				}
			}
			animCount = ANIM_DELAY;
		}else {
			animCount -= timeDiff;
		}
		width = lastImage.getIconWidth();
		height = lastImage.getIconHeight();
		if (lee.getDirection() == - 1){
			x = (lee.getX() - (width  - xBounds));
			y = (lee.getY() - (height - yBounds));
		} else {
			x = lee.getX();
			y = lee.getY();
		}
		g.drawImage(lastImage.getImage(), x, y, observer);
	}
	
	@Override
	public boolean destroyed() {
		return false;
	}
	
	private void setDrawFlags(BruceLee lee){
		for(String e = lee.nextDrawEvent(); e != null; e = lee.nextDrawEvent()){
			if (e.equals("attack")){
				attacking = true;
			}else if (e.equals("flykick")){
				flykicking = true;
			}
		}
	}		
}
