package com.thanglequoc.aqicalculator;

import gnu.io.*; // RXTX
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

public class SerialTest{
public static void main(String[] args) {
	System.out.println("Program started");
	
	//System.out.println(java.library.path);
    CommPortIdentifier serialPortId;
    //static CommPortIdentifier sSerialPortId;
    Enumeration enumComm;
    //SerialPort serialPort;

    enumComm = CommPortIdentifier.getPortIdentifiers();
    while (enumComm.hasMoreElements()) {
     	serialPortId = (CommPortIdentifier) enumComm.nextElement();
     	if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
    		System.out.println(serialPortId.getName());
    		
    	}
    }

	System.out.println("Finished successfully");
}
}