package com.moyamo.bfc.desktop.gui.board;

import java.awt.event.KeyEvent;

import com.moyamo.bfc.events.InputEvent;

/**
 * This class swallow the repeated key presses and then passes it to the 
 * 
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.1
 */
class PressSwallow{
	static boolean keyTable [] = new boolean [1000];
	
	/**
	 * Swallow pressEvents.
	 * 
	 * @param e - KeyEvent
	 * @return swallowed event
	 */
	static KeyEvent pressEvent(KeyEvent e){
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
	static KeyEvent releaseEvent(KeyEvent e){
		keyTable[e.getKeyCode()] = false;
		return e;
	}
}
