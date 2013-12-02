package com.moyamo.bfc.view.desktop.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.moyamo.bfc.Constants;

public class GameMenu extends JFrame{

	public GameMenu() {
		JPanel mainPanel = new JPanel();
		JButton singlePlayer = new JButton("Single Player");
		JButton directIP = new JButton("Direct IP");
		JButton multiplayer = new JButton("Multiplayer");
		
		singlePlayer.addActionListener(new GameMenuListener(this));
		directIP.addActionListener(new GameMenuListener(this));
		multiplayer.addActionListener(new GameMenuListener(this));
		
		mainPanel.add(singlePlayer);
		mainPanel.add(directIP);
		mainPanel.add(multiplayer);
		
		this.add(mainPanel);
		this.setTitle(Constants.GAME_NAME);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
}
