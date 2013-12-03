package com.moyamo.bfc.view.desktop.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JButton;

import com.moyamo.bfc.model.GameLoop;

class GameMenuListener implements ActionListener{
	GameMenu parent;

	GameMenuListener(GameMenu parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton pressed = (JButton) e.getSource();
			if (pressed == parent.singlePlayer) {
				startSinglePlayerGame();
				parent.dispose();
			}
		}
	}
	private void startSinglePlayerGame() {
		new GameFrame(InetAddress.getLoopbackAddress());
		new GameLoop().start();
	}
}
