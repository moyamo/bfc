package com.moyamo.bfc.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.moyamo.bfc.events.InputEvent;
import com.moyamo.bfc.events.InputHandle;

/** Passes events to entities **/

public class EventProcessor implements InputHandle{
	private static Queue<InputEvent> inputQueue = new LinkedList<InputEvent>();
	private static EventProcessor instance;
	private EntityStore store = EntityStore.self();
	private String address;
	
	private EventProcessor (){}
	
	public static EventProcessor self(){
		if (instance == null) {
			instance = new EventProcessor();
		}
		return instance;
	}
	/**
	 * Adds the InputEvent to the event queue to be processed
	 * 
	 * @param e - InputEvent
	 */
	@Override
	public synchronized void pressEvent (InputEvent e) {
		inputQueue.add(e);
	}
	@Override
	public synchronized void releaseEvent (InputEvent e) {
		inputQueue.add(e);
	}
	
	/**
	 * Processes the in the queue.
	 */
	void processEvents(){
		InputEvent e;
		if (address == null) {
			JFrame addressFrame = new JFrame();
			JPanel panel = new JPanel();
			JTextArea addressText = new JTextArea();
			JButton accept = new JButton("Accept");
			addressFrame.add(panel);
			panel.add(addressText);
			panel.add(accept);
			addressFrame.setResizable(false);
			addressFrame.pack();
			class Listener implements ActionListener {
				JFrame addressFrame;
				JTextArea addressText;
				Listener(JFrame frame, JTextArea textField) {
					this.addressFrame = frame;
					this.addressText = textField;
				}
				@Override
				public void actionPerformed(ActionEvent e) {
					address = addressText.getText();
					addressFrame.dispose();
				}
			}
			accept.addActionListener(new Listener(addressFrame, addressText));
			addressFrame.setVisible(true);
			while (address == null) System.out.println(address);
		}
		while(!inputQueue.isEmpty()){
			e = inputQueue.remove();
			NetworkTesting.sendPacket(e, address);
			if (e.isPress()) {
				if (e.getFocus() == InputEvent.PLAYER1){
					store.getCombatant(0).pressEvent(e);
				} else if (e.getFocus() == InputEvent.PLAYER2){
					store.getCombatant(1).pressEvent(e);
				}
			} else { 
				if (e.getFocus() == InputEvent.PLAYER1){
					store.getCombatant(0).releaseEvent(e);
				} else if (e.getFocus() == InputEvent.PLAYER2){
					store.getCombatant(1).releaseEvent(e);
				}
			}
		}
		inputQueue.clear();
	}
	
}
