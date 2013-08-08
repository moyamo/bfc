package com.moyamo.bfc.desktop.gui.board;

import java.awt.Canvas;

/**
 * This is the component that runs the game.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.1
 * 
 */
public class GameCanvas extends Canvas /*implements GameHolder*/{
//	GameEngine engine;
//	ArrayList<Entity> entities;
//	BufferStrategy strategy;
//	
//	public GameCanvas() {
//		engine = new GameEngine(this);
//		addKeyListener(new GameBoardListener(EventProcessor.self()));
//		setIgnoreRepaint(true);
//		
//	}
//	
//	@Override
//	public void addNotify() {
//		super.addNotify();
//		createBufferStrategy(2);
//		strategy = getBufferStrategy();
//	}
//	/**
//	 * Begins running the game.
//	 */
//	public void start(){
//		requestFocus();
//		engine.start();
//	}
//
//	/**
//	 * Iterates over entities and turns them into sprites. It then draws the
//	 * sprites.
//	 * 
//	 * @param g - Graphics
//	 */
//	private void drawSprites(Graphics g){
//		entities = engine.getEntities();
//		Iterator<Entity> iterator = entities.iterator();
//		Entity entity;
//		
//		while (iterator.hasNext()){
//			entity = iterator.next();
//		//	Sprite sprite = new Sprite(entity);
//			//g.drawImage(sprite.getImage(),sprite.getX(),sprite.getY(),this);
//		}
//	}
//	
//	
//	public void paint(){
//		
//		Graphics g = strategy.getDrawGraphics();
//		if(engine.isGameRunning()){
//			g.setColor(getBackground());
//			g.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
//			drawSprites(g);
//		}
//		Toolkit.getDefaultToolkit().sync();
//		g.dispose();
//		strategy.show();
//	}
//	
//	@Override
//	public void notifyDraw() {
//		paint();
//	}
}