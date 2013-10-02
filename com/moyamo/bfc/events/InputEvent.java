package com.moyamo.bfc.events;

public class InputEvent {
	private String inputString;
	private String focus;
	private boolean press;
	public final static String LEFT    = "l ";
	public final static String RIGHT   = "r ";
	public final static String UP      = "u ";
	public final static String DOWN    = "d ";
	public final static String PLAYER1 = "p1";
	public final static String PLAYER2 = "p2";
	public final static String ATTACK1 = "a1";
	public final static String ATTACK2 = "a2";
	
	public InputEvent (String inputString, String focus, boolean press) {
		this.inputString = inputString;
		this.focus = focus;
		this.press = press;
	}
	
	public String getInputString() {
		return inputString;
	}
	
	public String getFocus(){
		return focus;
	}
	public boolean isPress(){
		return press;
	}
}
