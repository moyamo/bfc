package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import com.moyamo.bfc.UTF8;
import com.moyamo.bfc.res.ImageStore;

public class BruceSprite implements IDrawablePlayer{
	private static final int ANIM_DELAY = 75;
	private int animCount = ANIM_DELAY;
	private boolean attacking, flykicking, onGround;
	private ImageObserver observer;
	private ImageIcon leeImage;
	private ImageIcon leePunchImage;
	private ImageIcon leeFlyKickImage;
	private ImageIcon lastImage;
	private ImageIcon rightLeeImage;
	private ImageIcon rightLeePunchImage;
	private ImageIcon rightLeeFlyKickImage;
	private int x, y, direction, width, height, xBounds, yBounds;
	private int posX, posY, health, momentum;
	
	public BruceSprite(int entityID, ImageObserver observer){
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
		
		if(animCount < 0){
			if (attacking){
				if (direction == -1){
					lastImage = leePunchImage;
				}else{
					lastImage = rightLeePunchImage;
				}
				attacking = false;
			}else if (flykicking){
				if (direction == -1){
					lastImage = leeFlyKickImage;
				}else{
					lastImage = rightLeeFlyKickImage;
				}
				if (onGround){
					flykicking = false;
				}
			}else{
				if (direction == -1){
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
		if (direction == - 1){
			x = (posX - (width  - xBounds));
			y = (posY - (height - yBounds));
		} else {
			x = posX;
			y = posY;
		}
		g.drawImage(lastImage.getImage(), x, y, observer);
	}
	
	@Override
	public boolean destroyed() {
		return false;
	}
	
	private void setDrawFlags(StringTokenizer token){
		while (token.hasMoreTokens()) {
			String e = token.nextToken();
			if (e.equals("attack")){
				attacking = true;
			}else if (e.equals("flykick")){
				flykicking = true;
			}
		}
	}
	
	public void update(ByteBuffer buffer){	
		posX = buffer.getInt();
		posY = buffer.getInt();
		direction = buffer.getInt();
		health = buffer.getInt();
		momentum = buffer.getInt();
		onGround = buffer.getInt() != 0 ? true : false;
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
