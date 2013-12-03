package com.moyamo.bfc.view;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;

import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.view.desktop.sprites.BulletSprite;

public class ViewReceiver implements Runnable {
	private DatagramSocket serverSocket;
	private HashMap<Integer,Integer> entityToSprite;
	
	public ViewReceiver () {
		try {
			this.serverSocket = new DatagramSocket(1730);
		} catch (SocketException e) {
			new ExceptionDialog(e);
		}
		entityToSprite = new HashMap<Integer, Integer>();
		entityToSprite.put(Integer.valueOf(0), Integer.valueOf(1));
		entityToSprite.put(Integer.valueOf(1), Integer.valueOf(2));
	}
	@Override
	public void run() {
		byte buf[] = new byte[256];
		ByteBuffer buffer;
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		while (true) {
			try {
				serverSocket.receive(packet);
				buffer = ByteBuffer.wrap(packet.getData());
				Integer id = Integer.valueOf(buffer.getInt());
				if (entityToSprite.containsKey(id)) {
					buffer.getInt();
					buffer.getInt(); // Move pointer 8 bytes forward
					SpriteManager.self().getSprite(entityToSprite.get(id).intValue()).update(buffer);
				} else {
					byte name[] = new byte[8];
					buffer.get(name, 0, 8);
					String sname = Charset.availableCharsets().get("UTF-8").decode(ByteBuffer.wrap(name)).toString();
					if (sname.equals("BULLET  ")){
						BulletSprite bullet = new BulletSprite();
						bullet.update(buffer);
						entityToSprite.put(id, Integer.valueOf(SpriteManager.self().addSprite(bullet)));
					}
				}
			} catch (IOException e) {
				new ExceptionDialog(e);
			}
		}
	}
	
}
