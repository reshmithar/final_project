package com.thanglequoc.aqicalculator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.Console;

public class Calc  {
		public static void main(String args[]) throws IOException{
			int Co2=0;
			int No2=0;
			int Co=0;
			
			FileInputStream in = null;
            try{
                // Open the file that is the first 
                // command line parameter
                //FileInputStream fstream = new FileInputStream("C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/CoolTerm Capture.txt");
                //BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                //String strLine;
                //Read File Line By Line
                String line0 = Files.readAllLines(Paths.get("C:/Users/Reshmitha/Downloads/CoolTermWin/CoolTermWin/CoolTerm Capture.txt")).get(0);
                String a = line0.replaceAll("[^0-9]","");
                                 //  System.out.println(a);
                Co2 = Integer.parseInt(String.valueOf(a).substring(0,3));
                 No2 = Integer.parseInt(String.valueOf(a).substring(3,6));
                 Co = Integer.parseInt(String.valueOf(a).substring(6,7));
//                System.out.println(Co2);
//                System.out.println(No2);
//                System.out.println(Co);

                //System.out.println(part[1]);
             //System.out.println(result);
                //Scanner input = new Scanner(line0);
               //int result = Integer.parseInt(line0);
               //int count=0;
                // while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                //System.out.println (strLine);
                //Console console = System.console();
                //}
                //System.out.print(line0);
                //Close the input stream
             // while (input.hasNext()){
               // int n = Integer.parseInt(System.console().readLine());
            	//System.out.println(result);
            		//count++;
                //}
                in.close();
                  }catch (Exception e){//Catch exception if any
                System.err.println("End of file");
                }

		
		AQICalculator calculator = AQICalculator.getAQICalculatorInstance();
		AQIResult result1 = calculator.getAQI("CO2", Co2);
		AQIResult result2 = calculator.getAQI("NO2", No2);
		AQIResult result3 = calculator.getAQI("CO", Co);
		
		//int val= result1.getAqi();
		
		System.out.println("C02");
		
		System.out.println(result1.getAqi());
		System.out.println(result1.getCategory());
		
		System.out.println(result2.getAqi());
		System.out.println(result2.getCategory());
		System.out.println(result3.getAqi());
		System.out.println(result3.getCategory());
		
		
		
	}
	

}