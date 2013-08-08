package com.moyamo.bfc;

import javax.swing.SwingUtilities;

import com.moyamo.bfc.desktop.gui.frame.GameFrame;

public class Run {
	public static void main(String []args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameFrame();
			}
		});
	}
}
