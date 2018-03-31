package com.thanglequoc.aqicalculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;


public class ReadFile {

      public static void main(String[] args) throws IOException {
    	  	FileInputStream in = null;
              try{
                  // Open the file that is the first 
                  // command line parameter
                  FileInputStream fstream = new FileInputStream("C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/CoolTerm Capture.txt");
                  BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                  String strLine;
                  //Read File Line By Line
                  String line0 = Files.readAllLines(Paths.get("C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/CoolTerm Capture.txt")).get(0);
                 // while ((strLine = br.readLine()) != null)   {
                  // Print the content on the console
                  //System.out.println (strLine);
                  
                  //}
                  System.out.print(line0);
                  //Close the input stream
                  in.close();
                    }catch (Exception e){//Catch exception if any
                  System.err.println("Error: " + e.getMessage());
                  }
      }
}

