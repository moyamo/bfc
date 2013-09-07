package com.moyamo.bfc.entities;


public class BruceLee extends Player{
	private static final int START_HEALTH = 800;
	private static final int REACH = 130;
	private static final int BASIC_ATTACK_DAMAGE = 15;
	
	public BruceLee(int x, int y, int direction) {
		super(x, y, 167, 250, BASIC_ATTACK_DAMAGE, direction, START_HEALTH,
				REACH);
		setSpeed(400);
		setJumpspeed(35);
		setMaxMomentum(250);
		setMomentumRegen(15);
	}

	@Override
	protected int getBasicYContact() {
		return getY()+60;
	}

	@Override
	protected int getAttackDelay() {
		return 75;
	}

}
