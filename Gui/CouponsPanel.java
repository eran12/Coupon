package com.ee.Gui;


import java.awt.GridBagConstraints;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;

import com.ee.Base.Coupon;
import com.ee.Facade.UserFacade;

public class CouponsPanel {
	private JFrame mainFrame = new JFrame();
	private JPanel coupon = new JPanel();
	private JScrollBar scrBar = new JScrollBar(1);
	
	public CouponsPanel(UserFacade userFacade){
		MainFrame(userFacade);
	}
	
	public void MainFrame(UserFacade userFacade){
		Collection<Coupon> coupons = userFacade.getAllCoupons();
		for (Coupon c : coupons) {
			JTextPane textPanel = new JTextPane();
			textPanel.setText(c.toString());
		coupon.add(textPanel);
		}
		scrBar.getPreferredSize();
		scrBar.setEnabled(true);
		scrBar.setVisible(true);
		mainFrame.add(scrBar);
		mainFrame.setDefaultCloseOperation(3);
		mainFrame.getContentPane().add(coupon, GridBagConstraints.RELATIVE);
		mainFrame.setAutoRequestFocus(true);
		mainFrame.setVisible(true);
		mainFrame.validate();
	}

}
