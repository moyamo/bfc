package com.moyamo.bfc.controller.desktop;

import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;

import com.moyamo.bfc.InputEvent;
import com.moyamo.bfc.InputEvent.FocusPlayer;
import com.moyamo.bfc.InputEvent.InputKey;
import com.moyamo.bfc.controller.InputEventSender;
import com.moyamo.bfc.debug.Out;

/**
 * Converts key events to Input Events and then passes it to an {@link
 * com.moyamo.bfc.logic.InputHandle InputHandle}
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.1
 *
 */
class EventPasser{
	private InputEventSender eventSender;
	private FocusPlayer player;
	private Map<Integer, InputKey> keyToInputKey;
	
	public EventPasser(InetAddress serverAddress, FocusPlayer player) {
		this.eventSender = new InputEventSender(serverAddress, player);
		this.player = player;
		this.keyToInputKey = new TreeMap<Integer, InputKey>();
	}
	
	public void setKeys(int left, int right, int up, int down, int atck1,
			int atck2) {
		Out.print("Keys: " + left + " " + right + " " + up + " " + down
				+  " " + atck1 +  " " + atck2);
		keyToInputKey.put(left, InputKey.LEFT);
		keyToInputKey.put(right, InputKey.RIGHT);
		keyToInputKey.put(up, InputKey.UP);
		keyToInputKey.put(down, InputKey.DOWN);
		keyToInputKey.put(atck1, InputKey.ATTACK1);
		keyToInputKey.put(atck2, InputKey.ATTACK2);

	}
	void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (keyToInputKey.containsKey(key)) {
			Out.print(e.getWhen() + ": key " + e.getKeyChar() + " pressed");
			InputEvent iE = new InputEvent(keyToInputKey.get(key), player,
					true);
			eventSender.sendInputEvent(iE);
		} else {
			Out.print(e.getWhen() + ": key " + e.getKeyChar() + " press unrecognized");
		}
	}

	void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (keyToInputKey.containsKey(key)) {
			InputEvent iE = new InputEvent(keyToInputKey.get(key), player, false);
			eventSender.sendInputEvent(iE);
		}
	}
}
