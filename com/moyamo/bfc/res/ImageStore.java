package com.moyamo.bfc.res;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;

import com.moyamo.bfc.debug.ExceptionDialog;

public class ImageStore {

	public static URI getBGURI() {
		try {
			return ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Back/desert.png").toURI();
		} catch (URISyntaxException e) {
			new ExceptionDialog(e);
			return null;
		}
	}

	public static URI[] getChuckImage() {
		try {
			URI chuckLeft = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft.png")
					.toURI();

			URI chuckLeft1 = ImageStore.class
					.getClass()
					.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft1.png")
					.toURI();
			URI chuckLeft2 = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeft2.png")
					.toURI();
			URI chuckRight = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight.png")
					.toURI();
			URI chuckRight1 = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight1.png")
					.toURI();
			URI chuckRight2 = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRight2.png")
					.toURI();
			URI chuckLeftP1 = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftP1.png")
					.toURI();
			URI chuckRightP1 = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightP1.png")
					.toURI();
			URI chuckLeftP2 = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftP2.png")
					.toURI();
			URI chuckRightP2 = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightP2.png")
					.toURI();
			URI chuckRightGun = ImageStore.class
					.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckRightGun.png")
					.toURI();
			URI chuckLeftGun = ImageStore.class.getResource(
					"/com/moyamo/bfc/res/images/Chuck_Norris/chuckLeftGun.png")
					.toURI();
			URI chuckImages[] = new URI[12];
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
		} catch (URISyntaxException e) {
			new ExceptionDialog(e);
			return null;
		}
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
