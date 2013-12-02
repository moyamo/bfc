package com.moyamo.bfc.view.desktop.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameMenuListener implements ActionListener{
	GameMenu parent;
	public GameMenuListener(GameMenu parent) {
		this.parent = parent;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton){
			new GameFrame();
			parent.dispose();
		}
	}

}
