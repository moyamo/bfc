package com.moyamo.bfc;

public class InputEvent {
	private InputKey inputKey;
	private FocusPlayer focus;
	private boolean press;
	public enum InputKey {LEFT, RIGHT, UP, DOWN, ATTACK1, ATTACK2};
	public enum FocusPlayer {PLAYER1, PLAYER2};
	
	public InputEvent (InputKey inputKey, FocusPlayer focus, boolean press) {
		this.inputKey = inputKey;
		this.focus = focus;
		this.press = press;
	}
	
	public InputKey getInputString() {
		return inputKey;
	}
	
	public FocusPlayer getFocus(){
		return focus;
	}
	public boolean isPress(){
		return press;
	}
}
