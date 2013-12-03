package com.moyamo.bfc.view.desktop.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
			} else if (pressed == parent.server) {
				startServer();
				parent.status.setText("Server should have started");
				parent.pack();
			} else if (pressed == parent.directIP) {
				try {
					startClient(InetAddress.getByName(parent.ip.getText()));
				} catch (UnknownHostException e1) {
					parent.status.setText("Unknown Host");
					parent.pack();
				}
			}
		}
	}
	private void startSinglePlayerGame() {
		startServer();
		startClient(InetAddress.getLoopbackAddress());
	}
	
	private void startServer() {
		new GameLoop().start();
	}
	
	private void startClient(InetAddress address) {
		new GameFrame(address);

	}
}
