package com.moyamo.bfc.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * An untested (and hopefully) platform independent way of representing PNG 
 * images.
 *  
 * @author  Mohammed Yaseen Mowzer
 * @version 0.0.1
 * 
 */
public class BasicPNG {
	byte [] image;
	
	public BasicPNG(URI imageURI) throws URISyntaxException, IOException {
		image = readImage(imageURI);
	}
	
	private byte[] readImage(URI imageURI) throws URISyntaxException,
	                                              IOException{
		File imageFile = new File(imageURI);
		FileInputStream fileInput = new FileInputStream(imageFile);
		BufferedInputStream bInput = new BufferedInputStream(fileInput);
		byte [] image = new byte[(int)imageFile.length()];
		bInput.read(image);
		bInput.close();
		fileInput.close();
		return image;
	}
	
	/**
	 * Returns the width of the PNG image.
	 * 
	 * @return image width
	 */
	public int getWidth () {
		//The width of the PNG is stored in byte 17 - 20
		//the 0xff is to unsign the byte.
		int width = 0;
		width += (image[16] & 0xff) * 256 * 256 * 256;
		width += (image[17] & 0xff) * 256 * 256;
		width += (image[18] & 0xff) * 256;
		width += (image[19] & 0xff);
		
		return width;
	}
	
	/**
	 * Returns the height of the PNG image.
	 * 
	 * @return image height
	 */
	public int getHeight () {
		//The height of the PNG is stored in byte 21 - 24
		//the 0xff is to unsign the byte.
		int height = 0;
		height += (image[20] & 0xff) * 256 * 256 * 256;
		height += (image[21] & 0xff) * 256 * 256;
		height += (image[22] & 0xff) * 256;
		height += (image[23] & 0xff);
		
		return height;
	}
}
