package com.moyamo.bfc.desktop.gui.board;

import java.awt.event.KeyEvent;

import com.moyamo.bfc.debug.Out;
import com.moyamo.bfc.events.InputEvent;
import com.moyamo.bfc.events.InputHandle;

/**
 * Converts key events to Input Events and then passes it to the {@link
 * com.moyamo.bfc.logic.GameEngine GameEngine}
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.1
 *
 */
class EventPasser{
	InputHandle engine;
	
	public EventPasser(InputHandle engine) {
		this.engine = engine;
	}
	
	
	void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		Out.print(e.getWhen() + ": " +"key " + e.getKeyChar()
				+ " pressed");
		if (key == KeyEvent.VK_A) {
			InputEvent le = new InputEvent(InputEvent.LEFT, InputEvent.PLAYER1,
					true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_D) {
			InputEvent le = new InputEvent(InputEvent.RIGHT,
			                               InputEvent.PLAYER1, true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_W) {
			InputEvent le = new InputEvent(InputEvent.UP, InputEvent.PLAYER1,
					true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_S) {
			InputEvent le = new InputEvent(InputEvent.DOWN, InputEvent.PLAYER1,
					true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_E) {
			InputEvent le = new InputEvent(InputEvent.ATTACK1,
					InputEvent.PLAYER1, true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_J) {
			InputEvent le = new InputEvent(InputEvent.LEFT, InputEvent.PLAYER2,
					true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_L) {
			InputEvent le = new InputEvent(InputEvent.RIGHT,
			                               InputEvent.PLAYER2, true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_I) {
			InputEvent le = new InputEvent(InputEvent.UP, InputEvent.PLAYER2,
					true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_K) {
			InputEvent le = new InputEvent(InputEvent.DOWN, InputEvent.PLAYER2,
					true);
			engine.pressEvent(le);
		}else if (key == KeyEvent.VK_U) {
			InputEvent le = new InputEvent(InputEvent.ATTACK1,
					InputEvent.PLAYER2, true);
			engine.pressEvent(le);
		}
	}

	void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		Out.print(e.getWhen()+": " + "key " + e.getKeyChar() +
				" released");
		if (key == KeyEvent.VK_A) {
			InputEvent le = new InputEvent(InputEvent.LEFT, InputEvent.PLAYER1,
					false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_D) {
			InputEvent le = new InputEvent(InputEvent.RIGHT,
			                               InputEvent.PLAYER1, false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_W) {
			InputEvent le = new InputEvent(InputEvent.UP, InputEvent.PLAYER1,
					false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_S) {
			InputEvent le = new InputEvent(InputEvent.DOWN, InputEvent.PLAYER1,
					false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_E) {
			InputEvent le = new InputEvent(InputEvent.ATTACK1,
					InputEvent.PLAYER1, false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_J) {
			InputEvent le = new InputEvent(InputEvent.LEFT, InputEvent.PLAYER2,
					false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_L) {
			InputEvent le = new InputEvent(InputEvent.RIGHT,
			                               InputEvent.PLAYER2, false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_I) {
			InputEvent le = new InputEvent(InputEvent.UP, InputEvent.PLAYER2,
					false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_K) {
			InputEvent le = new InputEvent(InputEvent.DOWN, InputEvent.PLAYER2,
					false);
			engine.releaseEvent(le);
		}else if (key == KeyEvent.VK_U) {
			InputEvent le = new InputEvent(InputEvent.ATTACK1,
					InputEvent.PLAYER2, false);
			engine.releaseEvent(le);
		}
	}
}
