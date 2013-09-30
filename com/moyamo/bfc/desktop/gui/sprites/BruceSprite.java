package com.moyamo.bfc.desktop.gui.sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import com.moyamo.bfc.entities.BruceLee;
import com.moyamo.bfc.logic.EntityStore;

public class BruceSprite implements IDrawable{
	private int entityID;
	private static final int ANIM_DELAY = 75;
	private int animCount = ANIM_DELAY;
	private boolean attacking, flykicking;
	private ImageObserver observer;
	private ImageIcon leeImage = new ImageIcon(this.getClass().getResource("/com/moyamo/bfc/res/images/Bruce_Lee/BruceLee.png"));
	private ImageIcon leePunchImage = new ImageIcon(this.getClass().getResource("/com/moyamo/bfc/res/images/Bruce_Lee/BruceLeePunch.png"));
	private ImageIcon leeFlyKickImage = new ImageIcon(this.getClass().getResource("/com/moyamo/bfc/res/images/Bruce_Lee/BruceLeeFlyKick.png"));
	private ImageIcon lastImage = leeImage;
	private ImageIcon rightLeeImage;
	private ImageIcon rightLeePunchImage;
	private ImageIcon rightLeeFlyKickImage;
	private int x, y, width, height, xBounds, yBounds;
	
	public BruceSprite(int entityID, ImageObserver observer){
		this.entityID = entityID;
		this.observer = observer;
		makeRotatedImages();
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
	
	private void makeRotatedImages(){
		AffineTransform tx = AffineTransform.getScaleInstance(-1.0, 1.0);
		BufferedImage bI = new BufferedImage(leeImage.getIconWidth(), leeImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D gc = bI.createGraphics();
		gc.drawImage(leeImage.getImage(), 0, 0 , null);
		gc.dispose();
		tx.translate(-bI.getWidth(), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		rightLeeImage = new ImageIcon(op.filter(bI, null));
		
		tx = AffineTransform.getScaleInstance(-1.0, 1.0);
		bI = new BufferedImage(leePunchImage.getIconWidth(), leePunchImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		gc = bI.createGraphics();
		gc.drawImage(leePunchImage.getImage(), 0, 0 , null);
		gc.dispose();
		tx.translate(-bI.getWidth(), 0);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		rightLeePunchImage = new ImageIcon(op.filter(bI, null));
		
		tx = AffineTransform.getScaleInstance(-1.0, 1.0);
		bI = new BufferedImage(leeFlyKickImage.getIconWidth(), leeFlyKickImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		gc = bI.createGraphics();
		gc.drawImage(leeFlyKickImage.getImage(), 0, 0 , null);
		gc.dispose();
		tx.translate(-bI.getWidth(), 0);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		rightLeeFlyKickImage = new ImageIcon(op.filter(bI, null));
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
