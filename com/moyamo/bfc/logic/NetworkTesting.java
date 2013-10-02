package com.moyamo.bfc.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import com.moyamo.bfc.events.InputEvent;

public class NetworkTesting {
	public static void sendPacket(InputEvent e, String saddress){
		byte buffer[] = new byte[16];
		DatagramSocket socket = null;
		try {
			InetAddress address = InetAddress.getByName(saddress);
			socket = new DatagramSocket();
			ByteBuffer bbuffer =  ByteBuffer.wrap(buffer);
			bbuffer.put((e.getInputString() + e.getFocus()).getBytes());
			bbuffer.put(e.isPress() ? (byte)1 : (byte)0);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1729);
			socket.send(packet);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			socket.close();
		}
	}
}
