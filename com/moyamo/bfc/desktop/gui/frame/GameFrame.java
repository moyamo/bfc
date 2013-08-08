package com.moyamo.bfc.desktop.gui.frame;

import javax.swing.JFrame;

import com.moyamo.bfc.Constants;
import com.moyamo.bfc.debug.Out;
import com.moyamo.bfc.desktop.gui.board.GameBoard;

/**
 * This is the main JFrame of the program. It contains the {@link GameBoard}.
 * 
 * @author Mohammed Yaseen Mowzer
 * @version 0.0.1
 * @see GameBoard
 * 
 */
public class GameFrame extends JFrame implements Constants{
	
	public GameFrame() {
		String title = GAME_NAME;
		GameBoard game = new GameBoard();
		add(game);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(title);
		setVisible(true);
		pack();
		Out.setDebug(true);
		Out.print("Width: " +getWidth()+ "\n" + "Height: " + getHeight());
		game.start();
	}
}
