package com.moyamo.bfc.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.events.InputEvent;

public class NetworkServerTesting implements Runnable{
	private DatagramSocket socket;
	public NetworkServerTesting() {
		try {
			socket = new DatagramSocket(1729);
		} catch (SocketException e) {
			new ExceptionDialog(e);
		}
		new Thread(this).start();
	}
	@Override
	public void run() {
		while (true) {
			byte buffer[] = new byte[16];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				new ExceptionDialog(e);
			}
			buffer = packet.getData();
			byte key[] = new byte[2];
			byte focus[] = new byte[2];
			byte press[] = new byte[1];
			System.arraycopy(buffer, 0, key, 0, 2);
			System.arraycopy(buffer, 2, focus, 0, 2);
			System.arraycopy(buffer, 4, press, 0, 1);
			System.out.println(new String(key));
			System.out.println(new String(focus));
			System.out.println(press[0] == 1 ? true : false);
			String skey=null;
			String sfocus=null;
			boolean bpress;
			if (new String(focus).equals(InputEvent.PLAYER1)){
				sfocus = InputEvent.PLAYER1;
			}else if (new String(focus).equals(InputEvent.PLAYER2)){
				sfocus = InputEvent.PLAYER2;
			}
			
			if (new String(key).equals(InputEvent.DOWN)){
				skey = InputEvent.DOWN;
			}else if (new String(key).equals(InputEvent.UP)){
				skey = InputEvent.UP;
			}else if (new String(key).equals(InputEvent.LEFT)){
				skey = InputEvent.LEFT;
			}else if (new String(key).equals(InputEvent.RIGHT)){
				skey = InputEvent.RIGHT;
			}else if (new String(key).equals(InputEvent.ATTACK1)){
				skey = InputEvent.ATTACK1;
			}else if (new String(key).equals(InputEvent.ATTACK2)){
				skey = InputEvent.ATTACK2;
			}else{
				System.out.println("ABORT");
			}
			bpress = press[0] == 1 ? true : false;
			EventProcessor.self().addToQueue(new InputEvent(skey, sfocus, bpress));
		}
	}
}
