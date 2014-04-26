package com.moyamo.bfc.view.desktop.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;

import com.moyamo.bfc.InputEvent.FocusPlayer;
import com.moyamo.bfc.controller.desktop.GameBoardListener;
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
			} else if (pressed == parent.hostGame) {
				startServer();
				try {
					KeyListener listeners[] = {new GameBoardListener(InetAddress.getByName(parent.ip.getText()), FocusPlayer.PLAYER1, 0)};
					startClient(InetAddress.getByName(parent.ip.getText()), listeners);
				} catch (UnknownHostException e1) {
					parent.status.setText("Unknown Host");
					parent.pack();
				}
			} else if (pressed == parent.directIP) {
				try {
					KeyListener listeners[] = {new GameBoardListener(InetAddress.getByName(parent.ip.getText()), FocusPlayer.PLAYER2, 0)};
					startClient(InetAddress.getByName(parent.ip.getText()), listeners);
				} catch (UnknownHostException e1) {
					parent.status.setText("Unknown Host");
					parent.pack();
				}
			}
		}
	}
	private void startSinglePlayerGame() {
		startServer();
		KeyListener listeners[] = {
				new GameBoardListener(InetAddress.getLoopbackAddress(),
						FocusPlayer.PLAYER1, 0),
				new GameBoardListener(InetAddress.getLoopbackAddress(),
						FocusPlayer.PLAYER2, 1)
			}; // FIXME listeners must be declared after startServer() because
			   // the handshake is sent when GameBoardListener is constructed
		startClient(InetAddress.getLoopbackAddress(),listeners);
	}
	
	private void startServer() {
		new GameLoop().start();
	}
	
	private void startClient(InetAddress address, KeyListener listeners[]) {
		new GameFrame(address, listeners);

	}
}
