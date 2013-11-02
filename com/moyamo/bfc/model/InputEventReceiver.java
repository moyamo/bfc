package com.moyamo.bfc.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.debug.ExceptionDialog;

public class InputEventReceiver implements Runnable{
	private DatagramSocket socket;
	
	public InputEventReceiver() {
		try {
			socket = new DatagramSocket(1729);
		} catch (SocketException e) {
			new ExceptionDialog(e);
		}
	}
	@Override
	public void run() {
		byte buffer[] = new byte[12];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		InputEvent iE;
		while (true){
			try {
				socket.receive(packet);
				iE = new InputEvent(packet);
				if (iE.isPress()){
					EventProcessor.self().pressEvent(iE);
				} else {
					EventProcessor.self().releaseEvent(iE);
				}
			} catch (IOException e) {
				new ExceptionDialog(e);
			}
		}
	}

}
