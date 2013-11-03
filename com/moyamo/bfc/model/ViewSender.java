package com.moyamo.bfc.model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.debug.Out;
import com.moyamo.bfc.model.entities.Entity;

public class ViewSender implements Runnable{
	private InetAddress viewAddress;
	private DatagramSocket viewSocket;
	private Queue<Entity> entityQueue = new LinkedList<Entity>();
	private Queue<Integer> idQueue = new LinkedList<Integer>();
	
	ViewSender(InetAddress address){
		this.viewAddress = address;
		try {
			this.viewSocket = new DatagramSocket();
		} catch (SocketException e) {
			new ExceptionDialog(e);
		}
	}
	
	synchronized void sendEntity(Entity ent, int id) {
		DatagramPacket packet;
		ByteBuffer firstbuff = ent.getByteBuffer();
		ByteBuffer buff = ByteBuffer.allocate(256);
		buff.putInt(id);
		buff.put(ent.getUniqueName());
		buff.put(firstbuff);
		packet = new DatagramPacket(buff.array(), buff.limit(), viewAddress, 1730);
		try {
			viewSocket.send(packet);
		} catch (IOException e) {
			new ExceptionDialog(e);
		}
	}
	synchronized void addEntityToQueue(Entity entity, int id){
		entityQueue.add(entity);
		idQueue.add(id);
		notifyAll();
	}

	@Override
	public synchronized void run() {
		while (true) {
			while (entityQueue.isEmpty()){
				try {
					wait();
				} catch (InterruptedException e) {
					new ExceptionDialog(e);
				}
			}
			sendEntity(entityQueue.remove(), idQueue.remove());
		}
	}
}
