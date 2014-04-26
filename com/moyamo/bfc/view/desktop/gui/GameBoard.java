package com.moyamo.bfc.view.desktop.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.InputEvent.FocusPlayer;
import com.moyamo.bfc.controller.desktop.GameBoardListener;
import com.moyamo.bfc.controller.desktop.RepeatingReleasedEventsFixer;
import com.moyamo.bfc.debug.ExceptionDialog;
import com.moyamo.bfc.res.ImageStore;
import com.moyamo.bfc.view.SpriteManager;
import com.moyamo.bfc.view.ViewReceiver;
import com.moyamo.bfc.view.desktop.sprites.BGSprite;
import com.moyamo.bfc.view.desktop.sprites.BruceSprite;
import com.moyamo.bfc.view.desktop.sprites.ChuckSprite;
import com.moyamo.bfc.view.desktop.sprites.IDrawable;
import com.moyamo.bfc.view.desktop.sprites.PlayerStatBar;

/**
 * This is the component that runs the game.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.1
 * 
 */
public class GameBoard extends JComponent implements Constants, Runnable{
	Thread receiver;
	Thread viewerThread;
	long timeSince;
	public GameBoard(InetAddress serverAddress, KeyListener listeners[]) {
		receiver = new Thread(new ViewReceiver());
		receiver.setName("viewerReceiverThread");
		viewerThread = new Thread(this);
		viewerThread.setName("viewerThread");
		sendViewHandshake(serverAddress);
		for (int i = 0; i < listeners.length; ++i) {
			addKeyListener(listeners[i]);
		}
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setMinimumSize(getPreferredSize());
		RepeatingReleasedEventsFixer rref = new RepeatingReleasedEventsFixer();
		rref.install();
	}
	
	private void sendViewHandshake(InetAddress serverAddress) {
		ByteArrayOutputStream obyte = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(obyte);
			out.writeObject(new ViewerHandShake(1730));
			out.flush();
			out.close();
			DatagramSocket socket = new DatagramSocket();
			byte bytes[] = obyte.toByteArray();
			socket.send(new DatagramPacket(bytes, bytes.length, serverAddress, 1729));
			socket.close();
		} catch (IOException e) {
			new ExceptionDialog(e);
		}
	}

	/**
	 * Begins running the game.
	 */
	public void start(){
		requestFocus();
		timeSince = System.currentTimeMillis();
		SpriteManager.self().addSprite(new BGSprite(ImageStore.getBGImage(), this));
		SpriteManager.self().addSprite(new ChuckSprite(0, this));
		SpriteManager.self().addSprite(new BruceSprite(1, this));
		SpriteManager.self().addSprite(new PlayerStatBar(1));
		SpriteManager.self().addSprite(new PlayerStatBar(2));
		receiver.start();
		viewerThread.start();
	}

	/**
	 * Draws sprites.
	 * 
	 * @param g - Graphics
	 */
	private void drawSprites(Graphics g){
		List <IDrawable>entities = SpriteManager.self().getSprites();
		Iterator<IDrawable> iterator = entities.iterator();
		IDrawable entity;
		long timeDiff = System.currentTimeMillis() - timeSince;
		while (iterator.hasNext()){
			entity = iterator.next();
			if (entity != null){
				entity.draw(g, timeDiff);
			}
		}
		timeSince = System.currentTimeMillis();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawSprites(g);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void run() {
		while(true){
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				new ExceptionDialog(e);
			}
		}
	}
}