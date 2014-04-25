package com.moyamo.bfc.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.UTF8;
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
		byte buffer[] = new byte[12];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		InputEvent iE;
		while (true){
			try {
				socket.receive(packet);
				String packetString = UTF8
						.decode((ByteBuffer) ByteBuffer.wrap(packet.getData())
								.limit(UTF8.encode(Constants.HAND_SHAKE)
										.capacity())).toString();
				if (packetString.equals(Constants.HAND_SHAKE)) {
					if (!addresses.isAddressIn(packet.getAddress())){
						Out.print("addresses " + packet.getAddress());
						addresses.addAddress(packet.getAddress(), 1730);
					}
					continue;
				}
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
