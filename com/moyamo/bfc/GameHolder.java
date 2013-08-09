package com.moyamo.bfc;

/**
 * This interface must be implemented by objects who wish to draw the game. It
 * is used by the {@link com.moyamo.bfc.logic.GameEngine GameEngine} to notify
 * the redrawing of the game board.
 * 
 * @author Mohammed Yaseen Mowzer
 *
 */
public interface GameHolder {
	public void notifyDraw();
	public void addSprite(int id);
}
