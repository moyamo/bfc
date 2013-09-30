package com.moyamo.bfc.desktop.gui.sprites;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

import com.moyamo.bfc.entities.Player;
import com.moyamo.bfc.logic.EntityStore;
import com.moyamo.bfc.utils.BasicPNG;

public class ChuckSprite implements IDrawable{
	private int entityID;
	private int x;
	private int y;
	private final int ANIM_DELAY = 170;
	private URI chuckImages[];
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
		try {
			initImages();
			BasicPNG png = new BasicPNG(chuckImages[0]);
			xBounds = png.getWidth();
			yBounds = png.getHeight();
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void draw(Graphics g, long timeDiff) {
		try {
			setDrawFlags();
			doAnim(g,timeDiff,observer);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initImages() throws URISyntaxException {
		URI chuckLeft   = this.getClass().getResource(
                          "/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft.png").toURI();
		URI chuckLeft1  = this.getClass().getResource(
                          "/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft1.png").toURI();
		URI chuckLeft2  = this.getClass().getResource(
		                  "/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft2.png").toURI();
		URI chuckRight  = this.getClass().getResource(
		                  "/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight.png").toURI();
		URI chuckRight1 = this.getClass().getResource(
				          "/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight1.png").toURI();
		URI chuckRight2 = this.getClass().getResource(
		                  "/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight2.png").toURI();
		URI chuckLeftP1 = this.getClass().getResource(
		                  "/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftP1.png").toURI();
		URI chuckRightP1 = this.getClass().getResource(
		                 "/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightP1.png").toURI();
		URI chuckLeftP2 = this.getClass().getResource(
		                  "/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftP2.png").toURI();
		URI chuckRightP2 = this.getClass().getResource(
		                 "/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightP2.png").toURI();
		URI chuckRightGun = this.getClass().getResource(
                "/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightGun.png").toURI();
		URI chuckLeftGun = this.getClass().getResource(
                "/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftGun.png").toURI();
		
		chuckImages = new URI [12];
		chuckImages[0] = chuckLeft;
		chuckImages[1] = chuckRight;
		chuckImages[2] = chuckLeft1;
		chuckImages[3] = chuckRight1;
		chuckImages[4] = chuckLeft2;
		chuckImages[5] = chuckRight2;
		chuckImages[6] = chuckLeftP1;
		chuckImages[7] = chuckRightP1;
		chuckImages[8] = chuckLeftP2;
		chuckImages[9] = chuckRightP2;
		chuckImages[10] = chuckLeftGun;
		chuckImages[11] = chuckRightGun;
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
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	private void doAnim(Graphics g, double timeDiff, ImageObserver observer) throws URISyntaxException, IOException{
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
		BasicPNG png = new BasicPNG(chuckImages[imageIndex]);
		width = png.getWidth();
		height = png.getHeight();
		if (c.getDirection() == - 1){
			x = (c.getX() - (width  - xBounds));
			y = (c.getY() - (height - yBounds));
		} else {
			x = c.getX();
			y = c.getY();
		}
		
		g.drawImage(new ImageIcon(chuckImages[imageIndex].toURL()).getImage(),x,y,observer);
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