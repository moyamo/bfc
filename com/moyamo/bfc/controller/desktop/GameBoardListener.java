package com.moyamo.bfc.controller.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.moyamo.bfc.InputHandle;
import com.moyamo.bfc.view.desktop.gui.GameBoard;

/**
 * Listens for events from {@link GameBoard} and then sends it to
 * {@link PressSwallow} to be parsed and passes the event on to
 * {@link EventPasser}.
 * 
 * @author Mohammed Yaseen Mowzer
 *
 */
public class GameBoardListener implements KeyListener{
	InputHandle handle;
	EventPasser passer;
	
	public GameBoardListener (InputHandle handle) {
		this.handle = handle;
		passer = new EventPasser(handle);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyEvent se = PressSwallow.pressEvent(e);//swallowed key
		if (se != null){
			passer.keyPressed(se);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyEvent se = PressSwallow.releaseEvent(e);//swallowed key
		if (se != null){
			passer.keyReleased(se);
		}
	}

	public void keyTyped(KeyEvent e) {}
	
	
}
