package com.thanglequoc.aqicalculator.aqi;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thanglequoc.aqicalculator.AQICalculator;
import com.thanglequoc.aqicalculator.AQIResult;
import com.thanglequoc.aqicalculator.PollutantCode;

public class AQICalculatorTest {

	AQICalculator calculator;
    	
    	private String pollutantCode;

	@Before
	public void begin() throws IOException, Exception {
	    this.pollutantCode = "";
	    calculator = AQICalculator.getAQICalculatorInstance();
	}

	/**
	 * --------------Begin of AQI Test for PM10-------------------------------------------
	 **/

	@Test
	public void should_ReturnPM10AQI_When_InputConcentration() {
		pollutantCode = PollutantCode.PM10.getLiteral();
		double concentration = 135;
		int expectedAQI = 91;

		AQIResult result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());

		concentration = 175;
		expectedAQI = 111;
		result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());

		concentration = 357;
		expectedAQI = 204;
		result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
	}

	@Test
	public void should_ReturnPM10NoAQI_When_InputInvalidConcentration() {
		this.pollutantCode = PollutantCode.PM10.getLiteral();
		double concentration = -1;
		int expectedAQI = -1;
		AQIResult result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
	}

	@Test
	public void should_ReturnEqualsPM10NowcastAQI_When_InputListOfConcentration() {
		/**
		 * Example Data for Nowcast PM10 12h period - 64, 63, 72, 77, 65, 61,
		 * 70, 71, 64, 57, 58, 64
		 **/
		this.pollutantCode = PollutantCode.PM10.getLiteral();
		double[] data = { 64, 63, 72, 77, 65, 61, 70, 71, 64, 57, 58, 64 };
		int expectedAQI = 57;
		AQIResult result = this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());
	}

	@Test
	public void should_ReturnEqualsPM10NowcastAQI_When_InputValidListOfMissingConcentration() {
		
		this.pollutantCode = PollutantCode.PM10.getLiteral();
		double[] data = { 64, -1, 62, 77, 65, 61, 70, 71, 64, 57, 58, 64 };
		int expectedAQI = 56;
		AQIResult result = this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());

		// Only some data available
		double[] data2 = { 165, 123, 45, 12, -1, -1, -1, -1, -1, 123, 154, -1 };
		expectedAQI = 87;
		result = this.calculator.getNowcastAQI(pollutantCode, data2);
		assertEquals(expectedAQI, result.getAqi());

	}
	
	@Test
	public void shoud_ReturnPM10NoNowcastAQI_When_InputInvalidListOfMissingConcentration() {
		this.pollutantCode = PollutantCode.PM10.getLiteral();
		// missing 3 nearest hour of data
		double[] data = {-1, -1, -1, 77, 65, 61, 70, 71, 64, 57, 58, 64 };
		int expectedAQI = -1;
		AQIResult result = this.calculator.getNowcastAQI(pollutantCode, data);
		
		assertEquals(expectedAQI, result.getAqi());
		
		/**
		 * Example Data for Nowcast PM10 12h period - 64, -1, -1, 77, 65, 61,
		 * 70, 71, 64, 57, 58, 64 , missing 2 of 3 nearest data -> invalid
		 **/
		this.pollutantCode = PollutantCode.PM10.getLiteral();
		double[] data2 = { 64, -1, -1, 77, 65, 61, 70, 71, 64, 57, 58, 64 };
		expectedAQI = -1;
		result = this.calculator.getNowcastAQI(pollutantCode, data2);
		assertEquals(expectedAQI, result.getAqi());
	}
	
	@Test
	public void should_ReturnPM10EmptyAQIResult_When_ConcentrationIsOutOfRange(){
	    this.pollutantCode = PollutantCode.PM10.getLiteral();
	    
	    double concentration = 700;
	    int expectedAQI = -1;
	    AQIResult result = calculator.getAQI(pollutantCode, concentration);
	    
	    assertEquals(expectedAQI, result.getAqi() );
	}

	/**
	 * --------------Begin of AQI Test for PM2.5-------------------------------------------
	 **/

	@Test
	public void should_ReturnPM25AQI_When_InputConcentration(){
		this.pollutantCode = PollutantCode.PM25.getLiteral();
		
		int concentration = 35;
		int expectedAQI = 99;
		AQIResult result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
		
		concentration = 78;
		expectedAQI = 163;
		result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
		
		concentration = 12;
		expectedAQI = 50;
		result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
	}
	
	@Test
	public void should_ReturnPM25NoAQI_When_InputInvalidConcentration(){
		this.pollutantCode = PollutantCode.PM25.getLiteral();
		int concentration = -1;
		int expectedAQI = -1;
		AQIResult result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
	}
	
	@Test
	public void should_ReturnPM25NowcastAQI_When_InputListOfConcentration() {
		/***
		 * 
		 * */
		this.pollutantCode = PollutantCode.PM25.getLiteral();
		double[] data = { 30.5, 28.8, 29.5, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5 };
		int expectedAQI = 89;
		AQIResult result = this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());

		double[] data2 = { 165, 123, 45, 12, 45, 12, 56, 42, 12, 123, 154, 32 };
		expectedAQI = 185;
		result = this.calculator.getNowcastAQI(pollutantCode, data2);
		assertEquals(expectedAQI, result.getAqi());
	}

	@Test
	public void shoud_ReturnPM25NowcastAQI_When_InputValidListOfMissingConcentration() {
		this.pollutantCode = PollutantCode.PM25.getLiteral();

		/**
		 * Example Data for Nowcast PM2.5 12h period - missing 1 of the nearest
		 * 3 hours -> still valid
		 **/
		double[] data = { 30.5, 12.5, -1, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5 };
		int expectedAQI = 79;
		AQIResult result= this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());
	}
	
	@Test
	public void shoud_ReturnPM25NoNowcastAQI_When_InputInvalidListOfMissingConcentration(){
		this.pollutantCode = PollutantCode.PM25.getLiteral();
		double[] data = { 30.5, -1, -1, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5 };
		int expectedAQI = -1; // no AQI
		AQIResult result = this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());
		
		double[] data2 = {-1, -1, -1, 30, 32.4, 31.1, 28.2, 30.7, 32.8, 32.6, 33.1, 28.5};
		expectedAQI = -1;
		result = this.calculator.getNowcastAQI(pollutantCode, data2);
		assertEquals(expectedAQI, result.getAqi());
	}
	
	@Test
	public void should_ReturnPM25EmptyAQIResult_When_ConcentrationIsOutOfRange(){
	    this.pollutantCode = PollutantCode.PM25.getLiteral();
	    
	    double concentration = 700;
	    int expectedAQI = -1;
	    AQIResult result = calculator.getAQI(pollutantCode, concentration);
	    
	    assertEquals(expectedAQI, result.getAqi() );
	}
	
	/***
	 * --------------Begin of AQI Test for Ozone-------------------------------------------
	 * */
	@Test
	public void should_ReturnOzoneAQI_When_InputConcentration(){
		this.pollutantCode = PollutantCode.O3.getLiteral();
		double concentration = 45;
		int expectedAQI = 42;
		AQIResult result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
		
		
		
		concentration = 89;
		expectedAQI = 159;
		result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
		
		
		concentration = 72.71875;
		expectedAQI = 107;
		result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
	}
	
	@Test
	public void should_ReturnOzoneNoAQI_When_InputInvalidConcentration(){
		this.pollutantCode = PollutantCode.O3.getLiteral();
		double concentration = -1;
		int expectedAQI = -1;
		AQIResult result = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result.getAqi());
	}

	@Test
	public void should_ReturnOzoneNowcastAQI_When_InputListOfConcentration(){
		this.pollutantCode = PollutantCode.O3.getLiteral();
		double[] data = { 38.611, 47.406, 54.469, 52.377, 50.754, 43.373, 39.143, 35.334 };
		int expectedAQI = 42;
		AQIResult result= this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());
		
		double[] data1 = {42.12, 32.12,34.12,-1,-1,-1,-1,-1};
		result = this.calculator.getNowcastAQI(pollutantCode, data1);
		expectedAQI = 34;
		assertEquals(expectedAQI, result.getAqi());
		
	}
	
	@Test
	public void shoud_ReturnOzoneNowcastAQI_When_InputValidListOfMissingConcentration(){
		this.pollutantCode = PollutantCode.O3.getLiteral();
		
		double[] data = { 145.32, 167.54, 187.12, 123.12, -1, -1, -1, -1 };
		int expectedAQI = 254;
		AQIResult result = this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());
		
		double[] data2 = {145.32, 167.54, -1, 123.12, 0, 183.2, 32.4, 145.3};
		expectedAQI = 242;
		result = this.calculator.getNowcastAQI(pollutantCode, data2);
		assertEquals(expectedAQI, result.getAqi());
		
		double[] data3 = { -1, 47.406, 54.469, 52.377, 50.754, 43.373, 39.143, 35.334 };
		expectedAQI = 46;
		result = this.calculator.getNowcastAQI(pollutantCode, data3);
		assertEquals(expectedAQI, result.getAqi());
		
	}
	
	@Test
	public void shoud_ReturnOzoneNoNowcastAQI_When_InputInvalidListOfMissingConcentration(){
		this.pollutantCode = PollutantCode.O3.getLiteral();
		double[] data = {-1,-1,-1,12,45,12,45,56};
		int expectedAQI = -1;
		AQIResult result = this.calculator.getNowcastAQI(pollutantCode, data);
		assertEquals(expectedAQI, result.getAqi());
		
		double[] data2 = {-1,-1,1,12,45,12,45,56};
		expectedAQI = -1;
		result = this.calculator.getNowcastAQI(pollutantCode, data2);
		assertEquals(expectedAQI, result.getAqi());
	}
	
	@Test
	public void should_ReturnOzoneEmptyAQIResult_When_ConcentrationIsOutOfRange(){
	    this.pollutantCode = PollutantCode.O3.getLiteral();
	    
	    double concentration = 700;
	    int expectedAQI = -1;
	    AQIResult result = calculator.getAQI(pollutantCode, concentration);
	    
	    assertEquals(expectedAQI, result.getAqi() );
	}

	
	/***
	 * --------------Begin of AQI Test for CO-------------------------------------------
	 * */
	@Test
	public void should_ReturnCOAQI_When_InputConcentration() {
		this.pollutantCode = PollutantCode.CO.getLiteral();
		double concentration = 12000;
		int expectedAQI = 143;
		AQIResult result3 = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result3.getAqi());

		concentration = 7000;
		expectedAQI = 76;
		result3 = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result3.getAqi());
	}
	
	@Test
	public void should_ReturnCONoAQI_When_InputInvalidConcentration(){
		this.pollutantCode = PollutantCode.CO.getLiteral();
		double concentration = -1;
		int expectedAQI = -1;
		AQIResult result3= this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result3.getAqi());
	}
	
	@Test
	public void should_ReturnCOEmptyAQIResult_When_ConcentrationIsOutOfRange(){
	    this.pollutantCode = PollutantCode.CO.getLiteral();
	    
	    double concentration = 70000;
	    int expectedAQI = -1;
	    AQIResult result3 = calculator.getAQI(pollutantCode, concentration);
	    
	    assertEquals(expectedAQI, result3.getAqi() );
	}
	
	
	/**
	 * --------------Begin of AQI Test for NO2-------------------------------------------
	 */

	@Test
	public void should_ReturnNO2AQI_When_InputConcentration() {
		this.pollutantCode = PollutantCode.NO2.getLiteral();
		double concentration = 145.12;
		int expectedAQI = 109;
		AQIResult result2 = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result2.getAqi());

		concentration = 43;
		expectedAQI = 41;
		result2 = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result2.getAqi());
	}
	

	@Test
	public void should_ReturnNO2NoAQI_When_InputInvalidConcentration(){
		this.pollutantCode = PollutantCode.NO2.getLiteral();
		double concentration = -1;
		int expectedAQI = -1;
		AQIResult result2= this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result2.getAqi());
	}
	
	@Test
	public void should_ReturnNO2EmptyAQIResult_When_ConcentrationIsOutOfRange(){
	    this.pollutantCode = PollutantCode.NO2.getLiteral();
	    
	    double concentration = 4000;
	    int expectedAQI = -1;
	    AQIResult result2 = calculator.getAQI(pollutantCode, concentration);
	    
	    assertEquals(expectedAQI, result2.getAqi() );
	}


	/***
	 * --------------Begin of AQI Test for SO2-------------------------------------------
	 */
	
	@Test
	public void should_ReturnSO2AQI_When_InputConcentration() {
		this.pollutantCode = PollutantCode.SO2.getLiteral();
		double concentration = 132;
		int expectedAQI = 126;
		AQIResult result1 = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result1.getAqi());

		concentration = 42;
		expectedAQI = 59;
		result1 = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result1.getAqi());
	}
	
	@Test
	public void should_ReturnSO2EmtyAQI_When_InputInvalidConcentration(){
		this.pollutantCode = PollutantCode.SO2.getLiteral();
		double concentration = -1;
		int expectedAQI = -1;
		AQIResult result1 = this.calculator.getAQI(pollutantCode, concentration);
		assertEquals(expectedAQI, result1.getAqi());
	}
	
	@Test
	public void should_ReturnSO2EmptyAQIResult_When_ConcentrationIsOutOfRange(){
	    this.pollutantCode = PollutantCode.SO2.getLiteral();
	    
	    double concentration = 2000;
	    int expectedAQI = -1;
	    AQIResult result1 = calculator.getAQI(pollutantCode, concentration);
	    
	    assertEquals(expectedAQI, result1.getAqi() );
	}
}
