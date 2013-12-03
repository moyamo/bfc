package com.moyamo.bfc.view.desktop.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.moyamo.bfc.Constants;

public class GameMenu extends JFrame{
	JPanel mainPanel;
	JButton singlePlayer;
	JButton directIP;
	JButton multiplayer;
	JButton server;
	JTextField ip;
	
	public GameMenu() {
		mainPanel = new JPanel();
		singlePlayer = new JButton("Single Player");
		ip = new JTextField(16);
		directIP = new JButton("Direct IP Connect");
		server = new JButton("Server");
		multiplayer = new JButton("Multiplayer");
				
		singlePlayer.addActionListener(new GameMenuListener(this));
		directIP.addActionListener(new GameMenuListener(this));
		server.addActionListener(new GameMenuListener(this));
		multiplayer.addActionListener(new GameMenuListener(this));
	
		directIP.setEnabled(false);
		server.setEnabled(false);
		multiplayer.setEnabled(false);
		ip.setEnabled(false);
		ip.setToolTipText("IP Address");
		
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(singlePlayer);
		mainPanel.add(ip);
		mainPanel.add(directIP);
		mainPanel.add(server);
		mainPanel.add(multiplayer);
		
		this.add(mainPanel);
		this.setTitle(Constants.GAME_NAME);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}
