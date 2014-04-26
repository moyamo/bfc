package com.moyamo.bfc.model;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.EnumMap;

import com.moyamo.bfc.InputEvent.FocusPlayer;

public class AddressBook {
	ArrayList<SocketAddress> viewAddress;
	EnumMap<FocusPlayer, SocketAddress> controllerAddress;
	
	public AddressBook (){
		viewAddress = new ArrayList<SocketAddress>();
		controllerAddress = new EnumMap<FocusPlayer, SocketAddress>(FocusPlayer.class);
	}
	public int viewLength() {
		return viewAddress.size();
	}

	public SocketAddress getViewAddress(int i) {
		return viewAddress.get(i);
	}

	public void addViewAddress(SocketAddress sAddr) {
		viewAddress.add(sAddr);
	}
	
	public void addControllerAddress(SocketAddress socketAddress, FocusPlayer player) {
		if (!controllerAddress.containsKey(player)) {
			controllerAddress.put(player, socketAddress);
		}
	}
	
	public SocketAddress getControllerAddress(FocusPlayer player) {
		return controllerAddress.get(player);
	}
}
