package com.ee.Gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout.Constraints;

import com.ee.Base.UserBase;
import com.ee.Base.UserType;
import com.ee.Exception.BaseException;
import com.ee.Facade.UserFacade;
import com.ee.System.CouponSystem;

public class EnteranceSystem {

	public EnteranceSystem() {
		new MainView();
	}

}

class MainView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	private CouponSystem system = CouponSystem.getInstance();
	private JPanel loginPanel = new JPanel();
	private JFrame mainFrame = new JFrame("Coupon System Log IN");
	private JTextField name = new JTextField();
	private JLabel userName = new JLabel("USER NAME:");
	private JLabel userpassword = new JLabel("PASSWORD:");
	private JPasswordField password = new JPasswordField();
	private JButton btn_LogIn = new JButton("Log In");
	private JComboBox<UserType> userType = new JComboBox<UserType>();
	private Constraints con = new Constraints();
	UserFacade userFacade = null;

	public MainView(){
		LoginEntrane();

	}


	public void LoginEntrane(){
		
		MainLogin main = new MainLogin();
		GridBagLayout grid = new GridBagLayout();
		name.setColumns(10);	
		password.setColumns(10);
		loginPanel.add(userName);
		loginPanel.add(name);
		loginPanel.add(userpassword, GridBagConstraints.RELATIVE);
		loginPanel.add(password, GridBagConstraints.RELATIVE);
		UserType[] userTypeArray = UserType.values();
		for (int i = 0; i < userTypeArray.length; i++) {
			userType.insertItemAt(userTypeArray[i],i);
		}
		userType.setSelectedIndex(0);
		userType.setVisible(true);
		loginPanel.add(userType, GridBagConstraints.RELATIVE);
		loginPanel.add(btn_LogIn, GridBagConstraints.RELATIVE);
		btn_LogIn.addActionListener(main);
		mainFrame.add(loginPanel);
		mainFrame.getContentPane().add(loginPanel, GridBagConstraints.RELATIVE);
		mainFrame.setSize(500,100);
		mainFrame.setLocationRelativeTo(getOwner());
		mainFrame.setResizable(true);
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		

	}
	
	
	class MainLogin implements ActionListener{
		
		/**
		 * the method get a String from the filed <tt>USER NAME</tt> and Char from the PASSWORD filed.</br>
		 * the Char is uses the FOR-EACH loop to iterate all the Chars and put into a String.</br>
		 * than it send the parameters to the 
		 * 
		 *
		 */
		@Override
		public void actionPerformed(ActionEvent action) {

			String adminName = name.getText() ;
			char[] userPassword = password.getPassword();
			String pass = "";
			for(char p : userPassword){
				pass += p;
			}
			UserType UserType = (com.ee.Base.UserType) userType.getSelectedItem();
			if(btn_LogIn == action.getSource()){
				try {
					userFacade = system .login(adminName, pass, UserType);
					UserBase use = 	userFacade.getUserByName(adminName, UserType);
					CouponsPanel coupons = new CouponsPanel(userFacade);
					mainFrame.getDefaultCloseOperation();
					
					
				} catch (BaseException e) {
					String mesg =  e.toString();
					ErrorHandler error = new ErrorHandler(mesg);
				}
			}
			else{
				System.out.println("nothing");
			}
		}
	}
}