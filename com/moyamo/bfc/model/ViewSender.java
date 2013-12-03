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
import com.moyamo.bfc.model.entities.Entity;

public class ViewSender implements Runnable, AddressBook{
	private InetAddress viewAddress[];
	private int viewPort[];
	private DatagramSocket viewSocket;
	private Queue<Entity> entityQueue = new LinkedList<Entity>();
	private Queue<Integer> idQueue = new LinkedList<Integer>();
	
	ViewSender(){
		this.viewAddress = new InetAddress[2];
		this.viewPort = new int[2];
		try {
			this.viewSocket = new DatagramSocket();
		} catch (SocketException e) {
			new ExceptionDialog(e);
		}
	}
	
	synchronized void sendEntity(Entity ent, int id) {
		if (viewAddress == null){
			return;
		}
		DatagramPacket packet;
		ByteBuffer firstbuff = ent.getByteBuffer();
		ByteBuffer buff = ByteBuffer.allocate(256);
		buff.putInt(id);
		buff.put(ent.getUniqueName());
		buff.put(firstbuff);
		for (int i = 0; i < free; ++i) {
			try {
				packet = new DatagramPacket(buff.array(), buff.limit(), viewAddress[i], viewPort[i]);
				viewSocket.send(packet);
			} catch (IOException e) {
				new ExceptionDialog(e);
			}
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
	private int free = 0;
	public void addAddress(InetAddress address, int port) {
		this.viewAddress[free] = address;
		this.viewPort[free] = port;
		++free;
	}

	@Override
	public boolean isAddressIn(InetAddress address) {
		if (free >= 2) {
			return true;
		} else if (free == 0) {
			return false;
		} else if (viewAddress[0].equals(address)) {
			return true;
		}
		return false;
	}
}
