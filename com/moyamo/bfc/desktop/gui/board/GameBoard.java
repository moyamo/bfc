package com.moyamo.bfc.desktop.gui.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.GameHolder;
import com.moyamo.bfc.desktop.gui.sprites.BGSprite;
import com.moyamo.bfc.desktop.gui.sprites.BruceSprite;
import com.moyamo.bfc.desktop.gui.sprites.BulletSprite;
import com.moyamo.bfc.desktop.gui.sprites.ChuckSprite;
import com.moyamo.bfc.desktop.gui.sprites.IDrawable;
import com.moyamo.bfc.desktop.gui.sprites.PlayerStatBar;
import com.moyamo.bfc.entities.Bullet;
import com.moyamo.bfc.entities.Entity;
import com.moyamo.bfc.logic.EntityStore;
import com.moyamo.bfc.logic.EventProcessor;
import com.moyamo.bfc.logic.GameEngine;
import com.moyamo.bfc.logic.SpriteManager;

/**
 * This is the component that runs the game.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.1
 * 
 */
public class GameBoard extends JComponent implements GameHolder, Constants{
	GameEngine engine;
	long timeSince;
	public GameBoard() {
		engine = new GameEngine(this);
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
		try {
			SpriteManager.self().addSprite(new BGSprite(this.getClass().getResource("/com/moyamo/bfc/res/images/Back/desert.png").toURI(), this));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpriteManager.self().addSprite(new ChuckSprite(0, this));
		SpriteManager.self().addSprite(new BruceSprite(1, this));
		SpriteManager.self().addSprite(new PlayerStatBar(0));
		SpriteManager.self().addSprite(new PlayerStatBar(1));


	}

	/**
	 * Draws sprites.
	 * 
	 * @param g - Graphics
	 * @throws MalformedURLException 
	 * @throws URISyntaxException 
	 */
	private void drawSprites(Graphics g) throws MalformedURLException, URISyntaxException{
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
		try {
			drawSprites(g);
		} catch (MalformedURLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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