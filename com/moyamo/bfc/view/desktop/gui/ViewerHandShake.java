package com.moyamo.bfc.view.desktop.gui;

import java.io.Serializable;

public class ViewerHandShake implements Serializable{
	private static final long serialVersionUID = 1674402131068870964L;
	public int port;
	public ViewerHandShake(int port) {
		this.port = port;
	}
}
