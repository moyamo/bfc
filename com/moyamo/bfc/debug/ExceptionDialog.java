package com.moyamo.bfc.debug;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ExceptionDialog extends JDialog {
	
	public ExceptionDialog(Exception e) {
	
		e.printStackTrace();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		add(panel);
		
		JLabel message = new JLabel("An error has occured: " + e.getMessage());
		panel.add(message, BorderLayout.NORTH);
		
		JTextArea stackText = new JTextArea();
		stackText.setText(Arrays.toString(e.getStackTrace()).replaceAll(",","\n"));
		stackText.setEditable(false);
		JScrollPane stackPane = new JScrollPane(stackText);
		stackPane.setPreferredSize(new Dimension(600, 200));;
		panel.add(stackPane, BorderLayout.CENTER);
		
		
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
