package com.moyamo.bfc.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.controller.HandShake;
import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.debug.Out;

public class InputEventReceiver implements Runnable {
	private DatagramSocket socket;
	private AddressBook addresses;
	
	public InputEventReceiver(AddressBook addresses) {
		this.addresses = addresses;
		try {
			socket = new DatagramSocket(1729);
		} catch (SocketException e) {
			new ExceptionDialog(e);
		}
	}
	@Override
	public void run() {
		byte buffer[] = new byte[4096];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		InputEvent iE;
		while (true){
			try {
				socket.receive(packet);
				ByteArrayInputStream bytes = new ByteArrayInputStream(buffer);
				ObjectInputStream in = new ObjectInputStream(bytes);
				Object object = in.readObject();
				in.close();
				if (object instanceof HandShake) {
					if (!addresses.isAddressIn(packet.getAddress())){
						Out.print("addresses " + packet.getAddress());
						addresses.addAddress(packet.getAddress(), 1730);
					}
				} else if (object instanceof InputEvent){
					iE = (InputEvent) object;
					if (iE.isPress()){
						EventProcessor.self().pressEvent(iE);
					} else {
						EventProcessor.self().releaseEvent(iE);
					}
				} else {
					Out.print("Unkown packet type");
				}
			} catch (IOException | ClassNotFoundException e) {
				new ExceptionDialog(e);
			}
		}
	}
}
