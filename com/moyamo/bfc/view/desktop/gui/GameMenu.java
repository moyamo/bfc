package com.moyamo.bfc.view.desktop.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.moyamo.bfc.Constants;

public class GameMenu extends JFrame{
	JPanel mainPanel;
	JButton singlePlayer;
	JButton directIP;
	JButton multiplayer;
	
	public GameMenu() {
		mainPanel = new JPanel();
		singlePlayer = new JButton("Single Player");
		directIP = new JButton("Direct IP");
		multiplayer = new JButton("Multiplayer");
				
		singlePlayer.addActionListener(new GameMenuListener(this));
		directIP.addActionListener(new GameMenuListener(this));
		multiplayer.addActionListener(new GameMenuListener(this));
		
		mainPanel.add(singlePlayer);
		mainPanel.add(directIP);
		mainPanel.add(multiplayer);
		
		this.add(mainPanel);
		this.setTitle(Constants.GAME_NAME);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
}
