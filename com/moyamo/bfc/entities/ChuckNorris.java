package com.moyamo.bfc.entities;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.moyamo.bfc.logic.GameEngine;
import com.moyamo.bfc.utils.BasicPNG;


/**
 * This is a player for Chuck Norris.
 * 
 * @author Mohammed Yaseen Mowzer
 *
 */
public class ChuckNorris extends Player{
	
	private final static int START_HEALTH = 1000;
	private static final int REACH = 130;
	private static final int BASIC_ATTACK_DAMAGE = 20;
	
	public ChuckNorris(int x, int y, byte direction, GameEngine parent) 
	       throws URISyntaxException, IOException {
		super(x, y, 140 , 260, BASIC_ATTACK_DAMAGE, direction, START_HEALTH,
				REACH);
		setSpeed(300);
		setJumpspeed(30);
	}
	
	@Override 
	protected int getBasicYContact() {
		return getY() + 60;
	}
}
