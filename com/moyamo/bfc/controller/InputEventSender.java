package com.moyamo.bfc.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.debug.ExceptionDialog;

public class InputEventSender {
	private DatagramSocket serverSocket;
	private InetAddress serverAddress;
	
	public InputEventSender (InetAddress serverAddress){
		this.serverAddress = serverAddress;
		try {
			this.serverSocket = new DatagramSocket();
		} catch (SocketException e) {
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
