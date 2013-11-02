package com.moyamo.bfc;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class InputEvent {
	private InputKey inputKey;
	private FocusPlayer focus;
	private boolean press;
	public enum InputKey {LEFT, RIGHT, UP, DOWN, ATTACK1, ATTACK2};
	private InputKey InputKeysValues[] = InputKey.values();
	public enum FocusPlayer {PLAYER1, PLAYER2};
	private FocusPlayer FocusPlayerValues[] = FocusPlayer.values();

	public InputEvent (InputKey inputKey, FocusPlayer focus, boolean press) {
		this.inputKey = inputKey;
		this.focus = focus;
		this.press = press;
	}
	
	public InputEvent(DatagramPacket packet){
		ByteBuffer buf = ByteBuffer.wrap(packet.getData());
		inputKey = InputKeysValues[buf.getInt()];
		focus = FocusPlayerValues[buf.getInt()];
		press = buf.getInt() == 1 ? true : false;
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
	public DatagramPacket toDatagramPacket(InetAddress server, int port){
		byte bytes[] = new byte[12];
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		buf.putInt(inputKey.ordinal());
		buf.putInt(focus.ordinal());
		buf.putInt(press ? 1 : 0);
		return new DatagramPacket(bytes, bytes.length, server, port);
	}
}
