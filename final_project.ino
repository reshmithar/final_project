 
int trigger_pin = 2;
int echo_pin = 3;
int time;
int distance;

int value1,value2, value3;


/************************Hardware Related Macros************************************/
#define         MQ_PIN                       2    //define which analog input channel you are going to use

#define         RO_CLEAN_AIR_FACTOR          9.83  //RO_CLEAR_AIR_FACTOR=(Sensor resistance in clean air)/RO,
                                                     //which is derived from the chart in datashee
/***********************Software Related Macros************************************/
#define         CALIBARAION_SAMPLE_TIMES     500  //define how many samples you are going to take in the calibration phase
#define         CALIBRATION_SAMPLE_INTERVAL  60    //define the time interal(in milisecond) between each samples in the
                                                     //cablibration phase
#define         READ_SAMPLE_INTERVAL         5     //define how many samples you are going to take in normal operation
#define         READ_SAMPLE_TIMES            5     //define the time interal(in milisecond) between each samples in 
                                                     //normal operation
/**********************Application Related Macros**********************************/
#define         GAS_CO2                      2 
#define         GAS_CO                       3 
#define         GAS_No2                      4
#define         GAS_CH3                      6
#define         GAS_CH3_2CO                  7
#define         GAS_C2H5OH                   9 //Alcohol, Ethanol
#define         GAS_CO_sec                  13
/*****************************Globals***********************************************/
   //        float CO2Curve[2]     =  {113.7105289, -3.019713765};
float           CO2Curve[2]     =  {13.7105289, -2.019713765};  //MQ135
//float           CO_secCurve[2]      =  {3.94418, -3.24294658};   
float           CO_secCurve[2]  =  {726.7809737, -4.040111669};  //MQ135
float           No2Curve[2]     =  {45.346963, 1.7432195};   //MQ135
//float           C2H50H_Curve[2] =  {74.77989144, 3.010328075};   //MQ135 
//float           CH3Curve[2]     =  {47.01770503, -3.281901967};  //MQ135
//float           CH3_2COCurve[2] =  {7.010800878, -2.122018939};  //MQ135                                                    
float           Ro           =  10;                 //Ro is initialized to 10 kilo ohms
float           Ro4 = 2.511;    //MQ135   2.51 this has to be tuned 10K Ohm
float           RL4 = 0.990;    //MQ135   FC-22
float           RL_VALUE  = 10  ;  //define the load resistance on the board, in ohms


void setup()
{
   pinMode(A2,INPUT);
 // pinMode(13,OUTPUT);

      Serial.begin(9600);  //UART setup, baudrate = 9600bps
      pinMode (trigger_pin, OUTPUT); 
       pinMode (echo_pin, INPUT);
       pinMode(13, OUTPUT);
   
  Serial.print("Calibrating...\n");                                           //prints "calibrating..."
  Serial.print("This will take approx ");                                     //prints "this will take approx "
  Serial.print(CALIBARAION_SAMPLE_TIMES * CALIBRATION_SAMPLE_INTERVAL / 1000);//prints "# of seconds" NUMSAMPLES X SAMPLEINTERVAL / 1000
  Serial.print(" seconds \n");                                                //prints "seconds"
 //Ro = MQCalibration(MQ_PIN);                                                 //Calibrating the sensor. Please make sure the sensor is in clean air 
                                                                              //when you perform the calibration        
  Serial.print("    MQ135:"); 
 // Ro4 = MQCalibration(MQ_PIN);
  //Serial.println(Ro4);            
  Serial.print("Calibration is done...\n");                                   //prints "calibration is done"
  Serial.print("\n");                                                         //prints a blank line
  Serial.print("Sensor Resistance (R=");                                     //prints "Sensor Resistance (Ro="
  Serial.print(Ro);                                                           //Prints value of sensor resistance
  Serial.print("kohm)\n");                                                    //prints "Kohm)"                                           
  Serial.print("\n");
 
   
  
}



void distSensor(int value1, int value2, int value3){
  
   
  if(value1>400 || value2>2000 || value3>10){ 
  
    digitalWrite (trigger_pin, HIGH);
    delayMicroseconds (10);
    digitalWrite (trigger_pin, LOW);
      delayMicroseconds(10);
    time = pulseIn (echo_pin, HIGH);
    distance = (time/2) / 29.1;
    
    //Serial.println(distance);
   if (distance >=10)
       {
        Serial.println (" Not Detected ");
        Serial.print (" Distance= ");              
        Serial.println (distance);        
        digitalWrite ( 13, LOW);
        delay (500);
        }
  else {
        Serial.println (" Detected ");
        Serial.print ("Distance= ");              
        Serial.println (distance);        
        digitalWrite (13, HIGH);
        delay (500);        
  } 
 }
}

void gasSensor()
{
   int raw_adc =analogRead(A2);
   //MQ135  CO NH4 CH3 CO2
   Serial.print("MQ135  :"); 
   Serial.print("CO2:"); 
   Serial.print(MQGetGasPercentage(MQRead(MQ_PIN)/Ro,GAS_CO2 ) );
  
   Serial.print( "ppm" );
   Serial.print("    ");   
   Serial.print("NO2:"); 
   Serial.print(MQGetGasPercentage(MQRead(MQ_PIN)/Ro,GAS_CO) );
   Serial.print( "ppm" );
   Serial.print("    ");  
     Serial.print("CO:"); 
   Serial.print(MQGetGasPercentage(MQRead(MQ_PIN)/Ro,GAS_No2) );
   Serial.print( "ppm" );
   Serial.print("    ");  
  
   Serial.print("\n");


  
  
   

     delay(1000);
     
}
  
  
/*****************************  MQGetGasPercentage **********************************
Input:   rs_ro_ratio - Rs divided by Ro
         gas_id      - target gas type
Output:  ppm of the target gas
Remarks: This function passes different curves to the MQGetPercentage function which 
         calculates the ppm (parts per million) of the target gas.
/************************************************************************************/
int MQGetGasPercentage(float rs_ro_ratio, int gas_id)
{
   
    if ( gas_id == GAS_CO2 ) {
     value1= MQGetPercentage(rs_ro_ratio,CO2Curve);  //MQ135
     return value1;
    } else if ( gas_id == GAS_No2 ) {
     value2= MQGetPercentage(rs_ro_ratio,No2Curve);     //MQ135
     return value2;
    } else if ( gas_id == GAS_CO ) {
     value3= MQGetPercentage(rs_ro_ratio,CO_secCurve);  //MQ135
     return value3;
    }
    

}
/****************** MQResistanceCalculation ****************************************
Input:   raw_adc - raw value read from adc, which represents the voltage
Output:  the calculated sensor resistance
Remarks: The sensor and the load resistor forms a voltage divider. Given the voltage
         across the load resistor and its resistance, the resistance of the sensor
         could be derived.
************************************************************************************/ 
float MQResistanceCalculation(int raw_adc)
{ 
 return ( ((float)RL_VALUE*(1023-raw_adc)/raw_adc));
 

}
/***************************** MQCalibration ****************************************
Input:   mq_pin - analog channel
Output:  Ro of the sensor
Remarks: This function assumes that the sensor is in clean air. It use  
         MQResistanceCalculation to calculates the sensor resistance in clean air 
         and then divides it with RO_CLEAN_AIR_FACTOR. RO_CLEAN_AIR_FACTOR is about 
         10, which differs slightly between different sensors.
************************************************************************************/ 

float MQCalibration(int mq_pin)
{
  int i;
  float val=0;
 
  for (i=0;i<CALIBARAION_SAMPLE_TIMES;i++) {            //take multiple samples
    val += MQResistanceCalculation(analogRead(A2));
        delay(CALIBRATION_SAMPLE_INTERVAL);
  }
  val = val/CALIBARAION_SAMPLE_TIMES;                   //calculate the average value
 
  val = val/RO_CLEAN_AIR_FACTOR;                        //divided by RO_CLEAN_AIR_FACTOR yields the Ro 
                                                        //according to the chart in the datasheet 
 
  return val; 

}

/*****************************  MQRead *********************************************
Input:   mq_pin - analog channel
Output:  Rs of the sensor
Remarks: This function use MQResistanceCalculation to caculate the sensor resistenc (Rs).
         The Rs changes as the sensor is in the different consentration of the target
         gas. The sample times and the time interval between samples could be configured
         by changing the definition of the macros.
************************************************************************************/ 
float MQRead(int mq_pin)
{
     int i;
  float rs=0;
 
  for (i=0;i<READ_SAMPLE_TIMES;i++) {
    rs += MQResistanceCalculation(analogRead(A2));
    delay(READ_SAMPLE_INTERVAL);
  }
 
  rs = rs/READ_SAMPLE_TIMES;
 
  return rs;  
}
/*****************************  MQGetGasPercentage **********************************
Input:   rs_ro_ratio - Rs divided by Ro
         gas_id      - target gas type
Output:  ppm of the target gas
Remarks: This function passes different curves to the MQGetPercentage function which 
         calculates the ppm (parts per million) of the target gas.
************************************************************************************
int MQGetGasPercentage(float rs_ro_ratio, float Ro, int gas_id, int sensor_id)
{ 
  if (sensor_id == MQ135_SENSOR ){
    if ( gas_id == GAS_CO2 ) {
     return MQGetPercentage(rs_ro_ratio,Ro,CO2Curve);     //MQ135
    } else if ( gas_id == GAS_NH4 ) {
     return MQGetPercentage(rs_ro_ratio,Ro,NH4Curve);     //MQ135
    } else if ( gas_id == GAS_C2H5OH ) {
     return MQGetPercentage(rs_ro_ratio,Ro,C2H50H_Curve); //MQ135
    } else if ( gas_id == GAS_CH3 ) {
     return MQGetPercentage(rs_ro_ratio,Ro,CH3Curve);     //MQ135
    } else if ( gas_id == GAS_CH3_2CO ) {
     return MQGetPercentage(rs_ro_ratio,Ro,CH3_2COCurve); //MQ135
    } else if ( gas_id == GAS_CO_sec ) {
     return MQGetPercentage(rs_ro_ratio,Ro,CO_secCurve);  //MQ135
    }
    return 0;
  }
}/
/*****************************  MQGetPercentage **********************************
Input:   rs_ro_ratio - Rs divided by Ro
         pcurve      - pointer to the curve of the target gas
Output:  ppm of the target gas
Remarks: By using the slope and a point of the line. The x(logarithmic value of ppm) 
         of the line could be derived if y(rs_ro_ratio) is provided. As it is a 
         logarithmic coordinate, power of 10 is used to convert the result to non-logarithmic 
         value.
************************************************************************************/ 
int  MQGetPercentage(float rs_ro_ratio, float *pcurve)
{
  
 return (double)(pcurve[0] * pow(((double)rs_ro_ratio/10), pcurve[1]));
}


void loop()
{
  gasSensor();
 // Serial.println(value1);
  
  //if(value1> 300||value2>1000||value3>10)
  distSensor(value1, value2, value3);
 // NewPing sonar(2, 3, MAX_DISTANCE);
  // Serial.println(sonar.ping_cm());
  delay(1000);
  
}

