package com.moyamo.bfc.view.desktop.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.GameHolder;
import com.moyamo.bfc.controller.desktop.GameBoardListener;
import com.moyamo.bfc.controller.desktop.RepeatingReleasedEventsFixer;
import com.moyamo.bfc.model.EntityStore;
import com.moyamo.bfc.model.EventProcessor;
import com.moyamo.bfc.model.GameLoop;
import com.moyamo.bfc.model.entities.Bullet;
import com.moyamo.bfc.model.entities.Entity;
import com.moyamo.bfc.res.ImageStore;
import com.moyamo.bfc.view.SpriteManager;
import com.moyamo.bfc.view.desktop.sprites.BGSprite;
import com.moyamo.bfc.view.desktop.sprites.BruceSprite;
import com.moyamo.bfc.view.desktop.sprites.BulletSprite;
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
public class GameBoard extends JComponent implements GameHolder, Constants{
	GameLoop engine;
	long timeSince;
	public GameBoard() {
		engine = new GameLoop(this);
		addKeyListener(new GameBoardListener(EventProcessor.self()));
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
		engine.start();
		timeSince = System.currentTimeMillis();
		SpriteManager.self().addSprite(new BGSprite(ImageStore.getBGImage(), this));
		SpriteManager.self().addSprite(new ChuckSprite(0, this));
		SpriteManager.self().addSprite(new BruceSprite(1, this));
		SpriteManager.self().addSprite(new PlayerStatBar(0));
		SpriteManager.self().addSprite(new PlayerStatBar(1));


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
			entity.draw(g, timeDiff);
		}
		timeSince = System.currentTimeMillis();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
			drawSprites(g);
		if (!engine.isGameRunning()){
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", 390, 290);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	@Override
	public void notifyDraw() {
		repaint();
	}


	@Override
	public void addSprite(int id) {
		Entity entity = EntityStore.self().getEntity(id);
		if (entity instanceof Bullet){
			SpriteManager.self().addSprite(new BulletSprite(id));
		}

	}
}