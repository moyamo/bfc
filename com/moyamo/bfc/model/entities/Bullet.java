package com.moyamo.bfc.model.entities;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import com.moyamo.bfc.UTF8;
import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.model.events.DestroyedEvent;

public class Bullet extends Projectile{

	public Bullet(int x, int y, int speedX, int speedY) {
		super(x, y, 0, 0, speedX > 0 ? 1 : -1);
		this.setXSpeed(speedX);
		this.setYSpeed(speedY);
	}
	
	public void destroy(){
		addEvent(new DestroyedEvent());
		addDrawEvent("destroy");
	}
	
	public int getDamage(){
		return 50;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(64);
		buffer.putInt(getX());
		buffer.putInt(getY());
		buffer.putInt(getDirection());
		for (String e = nextDrawEvent(); e != null; e = nextDrawEvent()){
			e += " ";
			buffer.put(UTF8.encode(e));
		}
		buffer.rewind();
		return buffer;
	}

	@Override
	public ByteBuffer getUniqueName() {
		return UTF8.encode("BULLET  ");
	}
}
