package com.moyamo.bfc.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.debug.ExceptionDialog;

public class InputEventSender {
	private DatagramSocket serverSocket;
	private InetAddress serverAddress;
	
	public InputEventSender (InetAddress serverAddress){
		this.serverAddress = serverAddress;
		try {
			this.serverSocket = new DatagramSocket();
			CharsetEncoder coder = Charset.availableCharsets().get("UTF-8").newEncoder();
			ByteBuffer buffer = coder.encode(CharBuffer.wrap(Constants.HAND_SHAKE));
			DatagramPacket packet =  new DatagramPacket(buffer.array(), buffer.limit(), serverAddress, 1729);
			serverSocket.send(packet);
		} catch (IOException  e) {
			new ExceptionDialog(e);
			System.exit(1);
		}
	}
	
	public void sendInputEvent(InputEvent iE){
		DatagramPacket packet = iE.toDatagramPacket(serverAddress, 1729);
		try {
			serverSocket.send(packet);
		} catch (IOException e) {
			new ExceptionDialog(e);
		}
		
	}
}
