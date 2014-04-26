package com.moyamo.bfc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;

import com.moyamo.bfc.debug.ExceptionDialog;

public class InputEvent implements Serializable{
	private static final long serialVersionUID = -5585142106961981982L;
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
	public DatagramPacket toDatagramPacket(InetAddress server, int port){
		ByteArrayOutputStream obyte = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(obyte);
			out.writeObject(this);
		} catch (IOException e) {
			new ExceptionDialog(e);
		} finally {
			try {
				out.flush();
				out.close();
				obyte.flush();
				obyte.close();
			} catch (IOException e) {
				new ExceptionDialog(e);
			}
		}
		byte bytes[] = obyte.toByteArray();
		return new DatagramPacket(bytes, bytes.length, server, port);
	}
}
