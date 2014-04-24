package com.moyamo.bfc.controller.desktop;

import java.awt.event.KeyEvent;

/**
 * This class swallow the repeated key presses and then passes it to the 
 * 
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.1
 */
class PressSwallow{
	boolean keyTable [];
	
	PressSwallow() {
		 keyTable = new boolean [1000];
	}
	/**
	 * Swallow pressEvents.
	 * 
	 * @param e - KeyEvent
	 * @return swallowed event
	 */
	KeyEvent pressEvent(KeyEvent e){
		if (!keyTable[e.getKeyCode()]) {
			keyTable[e.getKeyCode()] = true;
			return e;
		}
		return null;
	}
	
	/**
	 * Reset pressEvents.
	 * 
	 * @param e - KeyEvent
	 * @return Returns the keyEvent
	 */
	KeyEvent releaseEvent(KeyEvent e){
		keyTable[e.getKeyCode()] = false;
		return e;
	}
}
