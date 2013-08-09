package com.moyamo.bfc.entities;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.moyamo.bfc.events.InputEvent;
import com.moyamo.bfc.events.ProjectileEvent;
import com.moyamo.bfc.events.ProjectileEvent.projType;
import com.moyamo.bfc.logic.GameEngine;
import com.moyamo.bfc.utils.BasicPNG;


/**
 * This is a player for Chuck Norris.
 * 
 * @author Mohammed Yaseen Mowzer
 *
 */
public class ChuckNorris extends Player{
	private boolean SHOOTING;
	private final static int START_HEALTH = 1000;
	private static final int REACH = 130;
	private static final int BASIC_ATTACK_DAMAGE = 20;
	
	public ChuckNorris(int x, int y, byte direction, GameEngine parent) 
	       throws URISyntaxException, IOException {
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
		if(e.getInputString() == InputEvent.ATTACK2){
			shoot();
		}
	}
}
