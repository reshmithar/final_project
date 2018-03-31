package com.thanglequoc.aqicalculator;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class AQICalc extends JFrame {
	AQIResult result1 ;
	private JTextField textField;
	int Co2;
	int No2;
	int Co;
	FileInputStream in = null;
	String line = null;
	String fileName = "C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/myoutput.txt";
	int i=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AQICalc frame = new AQICalc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AQICalc() {
		
	
		getContentPane().setEnabled(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 396);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JButton btnNewButton = new JButton("GET AQI");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					Boolean eof = false;
//					  FileReader fileReader = 
//				                new FileReader("C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/myoutput.txt");
//
//				            // Always wrap FileReader in BufferedReader.
//				            BufferedReader bufferedReader = 
//				                new BufferedReader(fileReader);

					//Files.readAllLines(Paths.get("C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/myoutput.txt"));
					
				 String line0 = Files.readAllLines(Paths.get("C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/myoutput.txt")).get(i);
				 System.out.println(line0);
				 //System.out.println(i);
				 i++;
				 
	                String a = line0.replaceAll("[^0-9]","");
	                                   //System.out.println(a);
	                Co2 = Integer.parseInt(String.valueOf(a).substring(0,3));
	                 No2 = Integer.parseInt(String.valueOf(a).substring(3,6));
	                 Co = Integer.parseInt(String.valueOf(a).substring(6,7));
	                 //i++;
	                 in.close();
				}
                catch (Exception e1){//Catch exception if any
              System.err.println("End of file");
              }
                
			
				AQICalculator calculator = AQICalculator.getAQICalculatorInstance();
				AQIResult result1 = calculator.getAQI("CO2", Co2);
				AQIResult result2 = calculator.getAQI("NO2", No2);
				AQIResult result3 = calculator.getAQI("CO", Co);
				int result1aqi = result1.getAqi();
				Integer.toString(result1aqi);
				int result2aqi = result2.getAqi();
				Integer.toString(result2aqi);
				int result3aqi = result3.getAqi();
				Integer.toString(result3aqi);
				String result = "CO2" + "\t" + result1.getCategory() + "\t"+ result1aqi + "\t" + "NO2" + "\t" + result2.getCategory() + "\t" + result2aqi + "\t" + "CO" + "\t" + result3.getCategory() + "\t" + result3aqi; 
				textField.setText(result);
				
				
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
			}
		});
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		btnNewButton.setHorizontalAlignment(SwingConstants.TRAILING);
		getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		getContentPane().add(textField);
		textField.setColumns(5);
		
	}

}
