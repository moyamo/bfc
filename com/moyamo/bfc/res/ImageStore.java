package com.moyamo.bfc.res;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageStore {

	public static Image getBGImage() {
		return new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Back/desert.png")).getImage();
		
	}

	public static ImageIcon[] getChuckImageIcons() {
		ImageIcon chuckLeft = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft.png"));

		ImageIcon chuckLeft1 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft1.png"));
		ImageIcon chuckLeft2 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft2.png"));
		ImageIcon chuckRight = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight.png"));
		ImageIcon chuckRight1 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight1.png"));
		ImageIcon chuckRight2 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight2.png"));
		ImageIcon chuckLeftP1 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftP1.png"));
		ImageIcon chuckRightP1 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightP1.png"));
		ImageIcon chuckLeftP2 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftP2.png"));
		ImageIcon chuckRightP2 = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightP2.png"));
		ImageIcon chuckRightGun = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightGun.png"));
		ImageIcon chuckLeftGun = new ImageIcon(ImageStore.class.getResource(
				"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftGun.png"));
		ImageIcon chuckImages[] = new ImageIcon[12];
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
		return chuckImages;
	}
	
	public static ImageIcon[] getBruceImages(){
		ImageIcon iconArray[] = new ImageIcon[6];
		iconArray[0] = new ImageIcon(ImageStore.class.getResource("/com/moyamo/bfc/res/images/Bruce_Lee/BruceLee.png"));
		iconArray[1] = new ImageIcon(ImageStore.class.getResource("/com/moyamo/bfc/res/images/Bruce_Lee/BruceLeePunch.png"));
		iconArray[2] = new ImageIcon(ImageStore.class.getResource("/com/moyamo/bfc/res/images/Bruce_Lee/BruceLeeFlyKick.png"));	
		iconArray[3] = reflectImage(iconArray[0]);
		iconArray[4] = reflectImage(iconArray[1]);
		iconArray[5] = reflectImage(iconArray[2]);
		return iconArray;
	}
	
	private static ImageIcon reflectImage(ImageIcon image) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1.0, 1.0);
		BufferedImage bI = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D gc = bI.createGraphics();
		gc.drawImage(image.getImage(), 0, 0 , null);
		gc.dispose();
		tx.translate(-bI.getWidth(), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return new ImageIcon(op.filter(bI, null));
	}
}
