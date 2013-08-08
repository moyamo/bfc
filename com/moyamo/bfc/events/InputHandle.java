package com.moyamo.bfc.events;

/**
 * An interface to allow classes to pass Input Events to each other
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.1
 *
 */
public interface InputHandle {
	public void pressEvent(InputEvent e);
	public void releaseEvent(InputEvent e);
}
