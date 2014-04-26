package com.moyamo.bfc.controller;

import java.io.Serializable;

import com.moyamo.bfc.InputEvent.FocusPlayer;

public class ControllerHandShake implements Serializable{
	private static final long serialVersionUID = 3087173358628793311L;
	public FocusPlayer player;
	
	public ControllerHandShake(FocusPlayer player) {
		this.player = player;
	}
}
