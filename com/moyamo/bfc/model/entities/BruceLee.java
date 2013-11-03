package com.moyamo.bfc.model.entities;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.InputEvent.InputKey;
import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.model.events.AttackEvent;


public class BruceLee extends Player{
	private static final int START_HEALTH = 800;
	private static final int REACH = 130;
	private static final int BASIC_ATTACK_DAMAGE = 15;
	private static final int FLY_KICK_DAMAGE = 40;
	private boolean flyKicking = false;
	public BruceLee(int x, int y, int direction) {
		super(x, y, 167, 250, BASIC_ATTACK_DAMAGE, direction, START_HEALTH,
				REACH);
		setSpeed(400);
		setJumpspeed(35);
		setMaxMomentum(250);
		setMomentumRegen(15);
	}
	
	private void flyKick(){
		if (getMomentum() >= 45){
			setMomentum(getMomentum() - 40); 
			addDrawEvent("flykick");
			setYSpeed(-15);
			setXSpeed(50*getDirection());
			flyKicking = true;
		}
	}
	
	@Override
	public void move(long timeDiff) {
		super.move(timeDiff);
		AttackEvent ae;
		if (flyKicking){
			if (onGround()){
				flyKicking = false;
			}
			if (getDirection() == 1){
				ae = new AttackEvent(FLY_KICK_DAMAGE, getX() + REACH, getBasicYContact(), getDirection());
				
			} else {
					ae  = new AttackEvent(FLY_KICK_DAMAGE ,getX() + getWidth() - REACH, getBasicYContact(), getDirection());
			}
			ae.setAttackType("flykick");
			addEvent(ae);
		}
	}
	@Override
	protected int getBasicYContact() {
		return getY()+60;
	}

	@Override
	protected int getAttackDelay() {
		return 75;
	}
	@Override
	public void pressEvent(InputEvent e) {
		super.pressEvent(e);
		if(e.getInputString() == InputKey.ATTACK2){
			flyKick();
		}
	}
	@Override
	public void onHit(AttackEvent e){
		if(e.getAttackType().equals("flykick")) {
			setY(Constants.GROUND);
			setXSpeed(0);
		}
	}

	@Override
	public ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(128);
		buffer.putInt(getX());
		buffer.putInt(getY());
		buffer.putInt(getDirection());
		buffer.putInt(getHealth());
		buffer.putInt((int)getMomentum());
		buffer.putInt(onGround() ? 1 : 0);
		CharsetEncoder encoder = Charset.availableCharsets().get("UTF-8").newEncoder();
		for (String e = nextDrawEvent(); e != null; e = nextDrawEvent()){
			e += " ";
			try {
				buffer.put(encoder.encode(CharBuffer.wrap(e.toCharArray())));
			} catch (CharacterCodingException e1) {
				new ExceptionDialog(e1);
			}
		}
		buffer.rewind();
		return buffer;
	}

	@Override
	public ByteBuffer getUniqueName() {
		return Charset.availableCharsets().get("UTF-8").encode("BRUCELEE");
	}
}
