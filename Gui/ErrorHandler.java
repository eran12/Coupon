package com.ee.Gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ErrorHandler {

	String message;
	
	public ErrorHandler(String message) {
		ErrorMessage error = new ErrorMessage(message);
	}
}

class ErrorMessage extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	private JFrame main = new JFrame();
	private JLabel errorMessage = new JLabel();
	private JPanel panel = new JPanel();
	public ErrorMessage(String message){
		System.out.println(message);
		errorMessage.setText(message);
		panel.add(errorMessage);
		main.getContentPane().add(panel);
		main.setLocationByPlatform(true);
		main.setAutoRequestFocus(true);
		main.setSize(400,150);
		main.setLocationRelativeTo(getOwner());
		main.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		main.setAutoRequestFocus(true);
		main.setVisible(true);
		
		
	}
	
}
