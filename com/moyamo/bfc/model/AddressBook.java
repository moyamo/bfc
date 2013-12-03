package com.moyamo.bfc.model;

import java.net.InetAddress;

interface AddressBook {
	public void addAddress(InetAddress address, int port);
	public boolean isAddressIn(InetAddress address);
}
