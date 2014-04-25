package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import com.moyamo.bfc.UTF8;
import com.moyamo.bfc.res.ImageStore;

public class ChuckSprite implements IDrawablePlayer{
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
	private boolean isMoving;
	private int direction;
	private int posX, posY, health, momentum;
	private ImageObserver observer;
	
	public ChuckSprite(int entityID, ImageObserver observer){
		this.observer = observer;
		chuckImages = ImageStore.getChuckImageIcons();
		xBounds = chuckImages[0].getIconWidth();
		yBounds = chuckImages[0].getIconHeight();
	}
	@Override
	public void draw(Graphics g, long timeDiff) {
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
		int facing = direction;
		boolean moving = isMoving;
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
				if (direction == -1) imageIndex = 10;
				else imageIndex = 11;
				shooting = false;
			}
			animCount = ANIM_DELAY;
		}else{
			animCount-=timeDiff;
		}
		
		width = chuckImages[imageIndex].getIconWidth();
		height = chuckImages[imageIndex].getIconHeight();

		if (direction == - 1){
			x = (posX - (width  - xBounds));
			y = (posY - (height - yBounds));
		} else {
			x = posX;
			y = posY;
		}
		
		g.drawImage(chuckImages[imageIndex].getImage(),x,y,observer);
	}
	
	private void setDrawFlags(StringTokenizer tokens){
		while(tokens.hasMoreTokens()){
			String e = tokens.nextToken();
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
	@Override
	public void update(ByteBuffer buffer){
		posX = buffer.getInt();
		posY = buffer.getInt();
		direction = buffer.getInt();
		health = buffer.getInt();
		momentum = buffer.getInt();
		isMoving = buffer.getInt() != 0 ? true : false;
		CharBuffer charbuff = UTF8.decode(buffer);
		StringTokenizer tokens = new StringTokenizer(charbuff.toString());
		setDrawFlags(tokens);
	}
	@Override
	public int getHealth() {
		return health;
	}
	@Override
	public int getMomentum() {
		return momentum;
	}
}