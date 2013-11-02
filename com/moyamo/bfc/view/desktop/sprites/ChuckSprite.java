package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import com.moyamo.bfc.model.EntityStore;
import com.moyamo.bfc.model.entities.Player;
import com.moyamo.bfc.res.ImageStore;

public class ChuckSprite implements IDrawable{
	private int entityID;
	private int x;
	private int y;
	private final int ANIM_DELAY = 170;
	private ImageIcon chuckImages[];
	private long animCount= ANIM_DELAY;
	private int animStep = 1;
	private int imageIndex = 0;
	private int xBounds;
	private int yBounds;
	private int width, height;
	private boolean attacking;
	private boolean shooting;
	private ImageObserver observer;
	
	public ChuckSprite(int entityID, ImageObserver observer){
		this.entityID = entityID;
		this.observer = observer;
		chuckImages = ImageStore.getChuckImageIcons();
		xBounds = chuckImages[0].getIconWidth();
		yBounds = chuckImages[0].getIconHeight();
	}
	@Override
	public void draw(Graphics g, long timeDiff) {
		setDrawFlags();
		doAnim(g,timeDiff,observer);
	}
	
	/**
	 * This method changes the picture according to the appropriate action,
	 * causing the image to animate.
	 * 
	 * If the direction is 0 (i.e. Not moving). It will assign a stationary
	 * image. If the direction is 1 or -1. It will flip between two image to
	 * emulate walking.
	 * 
	 * @param timeDiff - time in milliseconds since doAnim was called.
	 */
	private void doAnim(Graphics g, double timeDiff, ImageObserver observer) {
		Player c = EntityStore.self().getCombatant(entityID);
		int facing = c.getDirection();
		boolean moving = c.isMoving();
		if(animCount <= 0){ //Slows down animation
			if (!moving) { //If he is standing still
				imageIndex = (facing + 3) / 2 - 1;
			}else if (moving) { //If he is moving
				imageIndex = (facing + 3)  / 2 + (animStep * 2 - 1);
				animStep = (animStep == 1) ? 2 : 1;
			}if (attacking){
				imageIndex = (facing + 3) / 2 + (animStep * 2 - 1) + 4;
				animStep = (animStep == 1) ? 2 : 1;
				attacking = false;
			}else if(shooting){
				if (c.getDirection() == -1) imageIndex = 10;
				else imageIndex = 11;
				shooting = false;
			}
			animCount = ANIM_DELAY;
		}else{
			animCount-=timeDiff;
		}
		
		width = chuckImages[imageIndex].getIconWidth();
		height = chuckImages[imageIndex].getIconHeight();

		if (c.getDirection() == - 1){
			x = (c.getX() - (width  - xBounds));
			y = (c.getY() - (height - yBounds));
		} else {
			x = c.getX();
			y = c.getY();
		}
		
		g.drawImage(chuckImages[imageIndex].getImage(),x,y,observer);
	}
	
	private void setDrawFlags(){
		Player c = EntityStore.self().getCombatant(entityID);
		for(String e = c.nextDrawEvent(); e != null; e = c.nextDrawEvent()) {
			if (e.equals("attack")){
				attacking = true;
			} else if (e.equals("shoot")){
				shooting = true;
			}
		}
	}
	@Override
	public boolean destroyed() {
		return false;
	}
}