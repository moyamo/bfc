package com.moyamo.bfc.controller.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;

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
	private EventPasser passer;
	
	public GameBoardListener (InetAddress serverAddress) {
		passer = new EventPasser(serverAddress);
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
