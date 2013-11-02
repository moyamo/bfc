package com.moyamo.bfc.model;

import com.moyamo.bfc.GameHolder;
import com.moyamo.bfc.model.entities.BruceLee;
import com.moyamo.bfc.model.entities.Bullet;
import com.moyamo.bfc.model.entities.ChuckNorris;
import com.moyamo.bfc.model.entities.Entity;
import com.moyamo.bfc.model.entities.IMovable;
import com.moyamo.bfc.model.entities.Player;
import com.moyamo.bfc.model.events.AttackEvent;
import com.moyamo.bfc.model.events.DestroyedEvent;
import com.moyamo.bfc.model.events.PlayerDeathEvent;
import com.moyamo.bfc.model.events.ProjectileEvent;
import com.moyamo.bfc.model.events.ProjectileEvent.projType;


/**
 * This is the class that does all the calculations concerning the game. The main
 * game thread is in this class.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.3
 */
public class GameLoop implements Runnable{
	private Thread game;
	private Thread eventReceiver;
	private GameHolder parent;
	private boolean gameRunning;
	private AttackHandler aHandler; 
	private EntityStore store = EntityStore.self();
	private EventProcessor evProc = EventProcessor.self();
	private CollisionDetector colDetect = new CollisionDetector();
	/**
	 * Creates and instance of the GameEngine. It requires a {@link com.moyamo.bfc.GameHolder GameHolder} to draw images.
	 * 
	 * @param parent - the object which draws the game board
	 */
	public GameLoop (GameHolder parent){
		game = new Thread(this);
		eventReceiver = new Thread(new InputEventReceiver());
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
		eventReceiver.start();
	}
	
	public void run() {		
		store.addCombatant(new ChuckNorris(50, 300, 1));
		store.addCombatant(new BruceLee(450, 300, -1));
		long lastLoopTime = System.currentTimeMillis();
		parent.notifyDraw();

		while (isGameRunning()) {
			long timeNow = System.currentTimeMillis();
			long timeDiff = timeNow - lastLoopTime;
			lastLoopTime = timeNow;
			
			evProc.processEvents();
			
			for (int i = 0; i < 2; i++) {
				Player player = store.getCombatant(i);
				player.applyPassiveEffects(timeDiff);
				player.move(timeDiff);
				for(Object pEvent = player.nextEvent(); pEvent != null; pEvent = player.nextEvent()){
					processEvent(i, pEvent);
				}
			}
			
			for (int i = 2; i < store.totalEntites(); i++){
				Entity e = store.getEntity(i);
				if (e == null) continue;
				if (e instanceof IMovable){
					IMovable m = (IMovable)e;
					m.move(timeDiff);
				}
				for(Object pEvent = e.nextEvent(); pEvent != null; pEvent = e.nextEvent()){
					processEvent(i, pEvent);
				}
			}
			colDetect.checkCollisions();
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
	
	private void processEvent(int entityID, Object e) {
		if (e instanceof AttackEvent){
			((AttackEvent) e).setDefenderID(entityID ^ 0x1);
			if (aHandler.processAttack((AttackEvent)e)){
				store.getCombatant(entityID).onHit((AttackEvent)e);
			}
		} else if (e instanceof PlayerDeathEvent){
			playerDeath(store.getCombatant(entityID));
		} else if (e instanceof ProjectileEvent){
			ProjectileEvent p = (ProjectileEvent)e;
			if(p.type == projType.BULLET){
				int id = store.addEntity(new Bullet(p.origX, p.origY, p.speedX, p.speedY));
				parent.addSprite(id);
				colDetect.addBullet(id);
			}
		} else if (e instanceof DestroyedEvent) {
			store.delEntity(entityID);
		}
	}
}
