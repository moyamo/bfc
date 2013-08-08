package com.moyamo.bfc.logic;

import java.util.ArrayList;
import java.util.Iterator;

import com.moyamo.bfc.events.InputEvent;
import com.moyamo.bfc.events.InputHandle;

/** Passes events to entities **/

public class EventProcessor implements InputHandle{
	private static ArrayList<InputEvent> inputQueue = new ArrayList<InputEvent>();
	private static EventProcessor instance;
	private EntityStore store = EntityStore.self();
	
	private EventProcessor (){}
	
	public static EventProcessor self(){
		if (instance == null) {
			instance = new EventProcessor();
		}
		return instance;
	}
	/**
	 * Adds the InputEvent to the event queue to be processed
	 * 
	 * @param e - InputEvent
	 */
	public synchronized void pressEvent (InputEvent e) {
		inputQueue.add(e);
	}
	
	public synchronized void releaseEvent (InputEvent e) {
		inputQueue.add(e);
	}
	
	/**
	 * Processes the in the queue.
	 */
	void processEvents(){
		InputEvent e;
		Iterator<InputEvent> it;
		
		//Process InputEvents
		it = new ArrayList<InputEvent>(inputQueue).iterator();
		while(it.hasNext()){
			e = it.next();
			if (e.isPress()) {
				if (e.getFocus() == InputEvent.PLAYER1){
					store.getCombatant(0).pressEvent(e);
				}else if (e.getFocus() == InputEvent.PLAYER2){
					store.getCombatant(1).pressEvent(e);
				}
			}else{ 
				if (e.getFocus() == InputEvent.PLAYER1){
					store.getCombatant(0).releaseEvent(e);
				}else if (e.getFocus() == InputEvent.PLAYER2){
					store.getCombatant(1).releaseEvent(e);
				}
			}
		}
		inputQueue.clear();
	}
	
}
