package com.moyamo.bfc.entities;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.events.AttackEvent;
import com.moyamo.bfc.events.InputEvent;


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
		if(e.getInputString() == InputEvent.ATTACK2){
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
}
