package com.moyamo.bfc.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.model.entities.Bullet;
import com.moyamo.bfc.model.entities.Entity;
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
		byte buf[] = new byte[4096];
		ByteArrayInputStream byteIn = null;
		ObjectInputStream in = null;
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		while (true) {
			try {
				serverSocket.receive(packet);
				byteIn = new ByteArrayInputStream(packet.getData());
				in = new ObjectInputStream(byteIn);
				Integer id = Integer.valueOf(in.readInt());
				Object entity = in.readObject();
				if (entityToSprite.containsKey(id)) {
					SpriteManager.self().updateSprite(entityToSprite.get(id).intValue(), (Entity) entity);
				} else if (entity instanceof Bullet){
						BulletSprite bullet = new BulletSprite();
						bullet.update((Bullet) entity);
						entityToSprite.put(id, Integer.valueOf(SpriteManager.self().addSprite(bullet)));
				}
			} catch (IOException | ClassNotFoundException e) {
				new ExceptionDialog(e);
			} finally {
				try {
					in.close();
					byteIn.close();
				} catch (IOException e) {
					new ExceptionDialog(e);
				}
			}
		}
	}
	
}
