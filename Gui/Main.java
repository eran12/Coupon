package com.ee.Gui;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main extends JComponent{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	protected JFrame mainFrame = new JFrame("Coupon System");
	
	
	
	public Main(){
		EnteranceSystem enter = new EnteranceSystem();

	}

	public boolean loginEntrance(){
		return true;

	}
}






