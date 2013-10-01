package com.moyamo.bfc.logic;

import java.util.LinkedList;
import java.util.Queue;

import com.moyamo.bfc.events.InputEvent;
import com.moyamo.bfc.events.InputHandle;

/** Passes events to entities **/

public class EventProcessor implements InputHandle{
	private static Queue<InputEvent> inputQueue = new LinkedList<InputEvent>();
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
	@Override
	public synchronized void pressEvent (InputEvent e) {
		inputQueue.add(e);
	}
	@Override
	public synchronized void releaseEvent (InputEvent e) {
		inputQueue.add(e);
	}
	
	/**
	 * Processes the in the queue.
	 */
	void processEvents(){
		InputEvent e;
		
		while(!inputQueue.isEmpty()){
			e = inputQueue.remove();
			if (e.isPress()) {
				if (e.getFocus() == InputEvent.PLAYER1){
					store.getCombatant(0).pressEvent(e);
				} else if (e.getFocus() == InputEvent.PLAYER2){
					store.getCombatant(1).pressEvent(e);
				}
			} else { 
				if (e.getFocus() == InputEvent.PLAYER1){
					store.getCombatant(0).releaseEvent(e);
				} else if (e.getFocus() == InputEvent.PLAYER2){
					store.getCombatant(1).releaseEvent(e);
				}
			}
		}
		inputQueue.clear();
	}
	
}
