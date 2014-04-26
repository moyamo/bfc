package com.moyamo.bfc.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.model.entities.Entity;

public class ViewSender implements Runnable {
	private DatagramSocket viewSocket;
	private Queue<Entity> entityQueue = new LinkedList<Entity>();
	private Queue<Integer> idQueue = new LinkedList<Integer>();
	private AddressBook addrBook;
	
	ViewSender(AddressBook addressBook){
		this.addrBook = addressBook;
		try {
			this.viewSocket = new DatagramSocket();
		} catch (SocketException e) {
			new ExceptionDialog(e);
		}
	}
	
	synchronized void sendEntity(Entity ent, int id) {
		DatagramPacket packet;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(bytes);
			out.writeInt(id);
			out.writeObject(ent);
			ent.clearDrawEvents(); // FIXME Change draw events into flags
		} catch (IOException e1) {
			new ExceptionDialog(e1);
		} finally {
			try {
				out.flush();
				bytes.flush();
				out.close();
			} catch (IOException e) {
				new ExceptionDialog(e);
			}
		}
		byte data [] = bytes.toByteArray();
		for (int i = 0; i < addrBook.viewLength(); ++i) {
			try {
				packet = new DatagramPacket(data, data.length, addrBook.getViewAddress(i));
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
}
