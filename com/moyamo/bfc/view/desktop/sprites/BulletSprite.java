package com.moyamo.bfc.view.desktop.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.StringTokenizer;

import com.moyamo.bfc.debug.ExceptionDialog;

public class BulletSprite implements IDrawable{
	private boolean destroyed;
	private int x, y, direction;
		
	@Override
	public void draw(Graphics g, long timeDiff) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, 3, 5);
		g.setColor(Color.RED);
		g.fillRect(x - 5*direction, y, 5, 5);
	}
	
	@Override
	public boolean destroyed() {
		return destroyed;
	}
	
	private void setDrawFlags(StringTokenizer tokens) {
		while (tokens.hasMoreTokens()){
			String e = tokens.nextToken();
			if(e.equals("destroy")){
				destroyed = true;
			}
		}
	}

	@Override
	public void update(ByteBuffer buffer) {
		x = buffer.getInt();
		y = buffer.getInt();
		direction = buffer.getInt();
		CharsetDecoder decoder = Charset.availableCharsets().get("UTF-8").newDecoder();
		try {
			CharBuffer charbuff = decoder.decode(buffer);
			StringTokenizer tokens = new StringTokenizer(charbuff.toString());
			setDrawFlags(tokens);
		} catch (CharacterCodingException e) {
			new ExceptionDialog(e);
			return;
		}
	}
}
