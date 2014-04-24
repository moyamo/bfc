package com.moyamo.bfc.view.desktop.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
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
	public GameBoard(InetAddress serverAddress) {
		receiver = new Thread(new ViewReceiver());
		receiver.setName("viewerReceiverThread");
		viewerThread = new Thread(this);
		viewerThread.setName("viewerThread");
		addKeyListener(new GameBoardListener(serverAddress, FocusPlayer.PLAYER2,
				1));
		addKeyListener(new GameBoardListener(serverAddress, FocusPlayer.PLAYER1,
				0));
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setMinimumSize(getPreferredSize());
		RepeatingReleasedEventsFixer rref = new RepeatingReleasedEventsFixer();
		rref.install();
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
		System.out.println(timeDiff);
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