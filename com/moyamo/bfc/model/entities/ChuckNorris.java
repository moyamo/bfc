package com.moyamo.bfc.model.entities;

import java.nio.ByteBuffer;

import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.InputEvent.InputKey;
import com.moyamo.bfc.UTF8;
import com.moyamo.bfc.model.events.ProjectileEvent;
import com.moyamo.bfc.model.events.ProjectileEvent.projType;


/**
 * This is a player for Chuck Norris.
 * 
 * @author Mohammed Yaseen Mowzer
 *
 */
public class ChuckNorris extends Player{
	private static final int START_HEALTH = 1000;
	private static final int REACH = 130;
	private static final int BASIC_ATTACK_DAMAGE = 20;
	
	public ChuckNorris(int x, int y, int direction) {
		super(x, y, 140 , 260, BASIC_ATTACK_DAMAGE, direction, START_HEALTH,
				REACH);
		setSpeed(300);
		setJumpspeed(30);
		setMaxMomentum(200);
		setMomentumRegen(10);
	}
	
	@Override 
	protected int getBasicYContact() {
		return getY() + 60;
	}
	
	private void shoot(){
		if (getMomentum() >= 50){
			setMomentum(getMomentum() - 50); 
			addDrawEvent("shoot");
			int xOrigin = 0;
			if (getDirection() == 1){
				xOrigin = getX() + getReach();
			} else {
				xOrigin = getX() + getWidth() - getReach();
			}
			addEvent(new ProjectileEvent(xOrigin, getBasicYContact(), getDirection()*800, 0, projType.BULLET));
		}
	}
	
	@Override
	public void pressEvent(InputEvent e) {
		super.pressEvent(e);
		if(e.getInputString() == InputKey.ATTACK2){
			shoot();
		}
	}

	@Override
	protected int getAttackDelay() {
		return 100;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(128);
		buffer.putInt(getX());
		buffer.putInt(getY());
		buffer.putInt(getDirection());
		buffer.putInt(getHealth());
		buffer.putInt((int)getMomentum());
		buffer.putInt(isMoving() ? 1 : 0);
		for (String e = nextDrawEvent(); e != null; e = nextDrawEvent()){
			e += " ";
			buffer.put(UTF8.encode(e));
		}
		buffer.rewind();
		return buffer;
	}
	@Override
	public ByteBuffer getUniqueName() {
		return UTF8.encode("CHUCKNRS");
	}
}
