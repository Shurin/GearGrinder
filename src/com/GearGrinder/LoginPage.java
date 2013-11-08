package com.GearGrinder;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.GearGrinder.Networking.UserVerify;
import java.awt.Color;


public class LoginPage{
  
	public static String usrname = null;
	public static String usrpass = null;
	
	

	public static void main(String[] args){
		LoginPage();
	}
	
	public static void LoginPage(){
		JFrame frmGeargrinder = new JFrame("Demo application");
		frmGeargrinder.setTitle("GearGrinder");
		frmGeargrinder.getContentPane().setBackground(Color.BLACK);
		frmGeargrinder.setSize(300, 150);
		frmGeargrinder.setLocationRelativeTo(null);
		frmGeargrinder.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		placeComponents(frmGeargrinder);
		frmGeargrinder.setVisible(true);
	}
	
	private static void placeComponents(final JFrame frame) {
		frame.getContentPane().setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setForeground(Color.LIGHT_GRAY);
		userLabel.setBounds(10, 10, 80, 25);
		frame.getContentPane().add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		frame.getContentPane().add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Color.LIGHT_GRAY);
		passwordLabel.setBounds(10, 40, 80, 25);
		frame.getContentPane().add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		frame.getContentPane().add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setForeground(Color.GREEN);
		loginButton.setBackground(Color.BLACK);
		loginButton.setBounds(10, 80, 80, 25);
		frame.getContentPane().add(loginButton);

		JButton registerButton = new JButton("Cancel");
		registerButton.setForeground(Color.RED);
		registerButton.setBackground(Color.BLACK);
		registerButton.setBounds(180, 80, 80, 25);
		frame.getContentPane().add(registerButton);

		ActionListener loginButtonListener = new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
				usrname = userText.getText();
				usrpass = passwordText.getText();
				System.out.println("username cached: " + usrname);
				frame.dispose();
				UserVerify.UserVerify();
			}
		};
		loginButton.addActionListener(loginButtonListener);
		
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
	}  
}

