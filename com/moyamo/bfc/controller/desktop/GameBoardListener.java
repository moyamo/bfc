package com.moyamo.bfc.controller.desktop;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;

import com.moyamo.bfc.InputEvent.FocusPlayer;
import com.moyamo.bfc.debug.Out;
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
	private PressSwallow swallow;
	
	public GameBoardListener (InetAddress serverAddress, FocusPlayer player,
			int controls) {
		this.passer = new EventPasser(serverAddress, player);
		if (controls == 0) {
			this.passer.setKeys(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W,
					KeyEvent.VK_S, KeyEvent.VK_E, KeyEvent.VK_Q);
		} else {
			this.passer.setKeys(KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_I,
					KeyEvent.VK_K, KeyEvent.VK_U, KeyEvent.VK_O);
		}
		this.swallow = new PressSwallow();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyEvent se = swallow.pressEvent(e);//swallowed key
		if (se != null){
			passer.keyPressed(se);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyEvent se = swallow.releaseEvent(e);//swallowed key
		if (se != null){
			passer.keyReleased(se);
		}
	}

	public void keyTyped(KeyEvent e) {}
	
	
}
