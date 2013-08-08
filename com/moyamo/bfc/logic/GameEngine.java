package com.moyamo.bfc.logic;

import java.io.IOException;
import java.net.URISyntaxException;

import com.moyamo.bfc.GameHolder;
import com.moyamo.bfc.entities.ChuckNorris;
import com.moyamo.bfc.entities.Player;
import com.moyamo.bfc.events.AttackEvent;
import com.moyamo.bfc.events.PlayerDeathEvent;

/**
 * This is the class that does all the calculations concerning the game. The main
 * game thread is in this class.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.3
 */
public class GameEngine implements Runnable{
	private Thread game;
	private GameHolder parent;
	private boolean gameRunning;
	private AttackHandler aHandler; 
	private EntityStore store = EntityStore.self();
	private EventProcessor evProc = EventProcessor.self();
	/**
	 * Creates and instance of the GameEngine. It requires a {@link com.moyamo.bfc.GameHolder GameHolder} to draw images.
	 * 
	 * @param parent - the object which draws the game board
	 */
	public GameEngine (GameHolder parent){
		game = new Thread(this);
		this.parent = parent;
		gameRunning = false;
		aHandler = new AttackHandler();
	}
	
	/**
	 * Begin running the game thread.
	 */
	public void start(){
		setGameRunning(true);
		game.start();
	}
	
	public void run() {		
		try {
			store.addCombatant(new ChuckNorris(50, 300, (byte) 1, this));
			store.addCombatant(new ChuckNorris(450, 300, (byte) -1, this));
		} catch (URISyntaxException e1) {
			// TODO Pipe to Handler
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Pipe to Handler
			e1.printStackTrace();
		}
		long lastLoopTime = System.currentTimeMillis();
		parent.notifyDraw();

		while (isGameRunning()) {
			long timeNow = System.currentTimeMillis();
			long timeDiff = timeNow - lastLoopTime;
			lastLoopTime = timeNow;
			
			evProc.processEvents();
			
			for (int i = 0; i < 2; i++) {
				Player player = store.getCombatant(i);
				player.move(timeDiff);
				for(Object pEvent = player.nextEvent(); pEvent != null; pEvent = player.nextEvent()){
					processEvent(i, pEvent);
				}
			}
			parent.notifyDraw();
			try {
				Thread.sleep(20);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isGameRunning() {
		return gameRunning;
	}
	
	private void setGameRunning(boolean gameRunning){
		this.gameRunning = gameRunning;
	}

	public void playerDeath(Player deadPlayer){
		setGameRunning(false);
	}
	
	private void processEvent(int playerID, Object e) {
		if (e instanceof AttackEvent){
			((AttackEvent) e).setDefenderID(playerID ^ 0x1);
			aHandler.processAttack((AttackEvent)e);
		} else if (e instanceof PlayerDeathEvent){
			playerDeath(store.getCombatant(playerID));
		}
	}

}
