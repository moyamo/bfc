package com.moyamo.bfc.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetEncoder;

import com.moyamo.bfc.events.InputEvent;

public class NetworkTesting {
	public static void sendPacket(InputEvent e, String saddress){
		byte buffer[] = new byte[16];
		DatagramSocket socket = null;
		try {
			InetAddress address = InetAddress.getByName(saddress);
			System.out.println(address.getCanonicalHostName());
			socket = new DatagramSocket();
			if (socket == null){
				return;
			}
			ByteBuffer bbuffer =  ByteBuffer.wrap(buffer);
			bbuffer.put(e.getInputString().getBytes());
			bbuffer.put(e.getFocus().getBytes());
			bbuffer.put(e.isPress() ? (byte)1 : (byte)0);
			for(int i = 0; i < buffer.length; ++i){
				System.out.print(buffer[i] + " ");
			}
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1729);
			socket.send(packet);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			socket.close();
		}
	}
}
