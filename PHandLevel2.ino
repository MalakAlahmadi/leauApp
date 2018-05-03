#include <SoftwareSerial.h>

#define SensorPin 0          //pH meter Analog output to Arduino Analog Input 0
unsigned long int avgValue;  //Store the average value of the sensor feedback
SoftwareSerial WIFI(10, 11);      // RX, TX
int buf[10],temp;
int buf2[10],temp2;
const int trigPin = 2;
const int echoPin = 3;
const String email = "h@gmail.com";
unsigned long wifi_time = 0;
bool is_wifi_connected = false, is_request_done = true;
const String WIFI_CMD_CONNECT   = "Connect";
const String WIFI_CMD_DATA      = "Data";
#define WIFI_CMD_DELIMETER  ':'
#define WIFI_PERIOD 15000        // Period for data sending over WiFi (in milliseconds)
String wifi_ssid = "HomeBroadband";
String wifi_pswd = "FCIT4444";
const String url = "http://leau.000webhostapp.com/update2.php";

const int tankDepth=15;

void setup()
{
  pinMode(13,OUTPUT);  
  Serial.begin(115200);  
  setup_wifi();
  delay(10000);
}
void loop()
{
  for(int i=0;i<10;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    buf[i]=analogRead(SensorPin);
    delay(10);
  }
  for(int i=0;i<9;i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<10;j++)
    {
      if(buf[i]>buf[j])
      {
        temp=buf[i];
        buf[i]=buf[j];
        buf[j]=temp;
      }
    }
  }
  avgValue=0;
  for(int i=2;i<8;i++)                      //take the average value of 6 center sample
    avgValue+=buf[i];
  float phValue=(float)avgValue*5.0/1024/6; //convert the analog into millivolt
  phValue=3.5*phValue;                      //convert the millivolt into pH value
  
  double duration, cm;
  double percentage;
  pinMode(trigPin, OUTPUT);
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
    for(int j=0;j<10;j++)       //Get 10 sample value from the sensor for smooth the value
  { 
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  pinMode(echoPin, INPUT);
  
   buf2[j]=microsecondsToCentimeters(pulseIn(echoPin, HIGH));
    if( buf2[j]<0 || buf2[j]>tankDepth)
    j--;
    delay(10);
  }
for(int i=0;i<9;i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<10;j++)
    {
      if(buf2[i]>buf2[j])
      {
        temp2=buf2[i];
        buf2[i]=buf2[j];
        buf2[j]=temp2;
      }
    }
  }
  avgValue=0;
  for(int i=2;i<8;i++)                      //take the average value of 6 center sample
    avgValue+=buf2[i];

duration =avgValue/6;
  
  cm = duration;
  percentage=100*(1-(cm/tankDepth)); 
  
  send_wifi(phValue,percentage);
  Serial.print(email);
  Serial.print(","); 
  Serial.print(phValue,2);
  Serial.print(",");
  Serial.print(percentage,0);
  Serial.println();
  delay(10000);
}


long microsecondsToCentimeters(long microseconds){
  return microseconds / 29 / 2;
 }

 void setup_wifi()
{
  Serial.println("Wifi initialization...");
  WIFI.begin(115200);
  WIFI.setTimeout(200);
  WIFI.flush();
  delay(1000);

  String connect = WIFI_CMD_CONNECT + WIFI_CMD_DELIMETER;
  connect += String(wifi_ssid.length()) + WIFI_CMD_DELIMETER;
  connect += String(wifi_pswd.length()) + WIFI_CMD_DELIMETER;
  connect += wifi_ssid + WIFI_CMD_DELIMETER;
  connect += wifi_pswd;
  WIFI.print(connect);
  Serial.println("Wifi initialization end");
}
void receive_wifi()
{
    int len;
    if (WIFI.available())
    {
        String wifi_buf = WIFI.readString();
        len = wifi_buf.length();

        Serial.print("Wifi buffer (");
        Serial.print(len);
        Serial.println("):");
        Serial.print(wifi_buf);
        Serial.println();

        if (wifi_buf.indexOf("[WiFi] Connected") != -1)
            is_wifi_connected = true;

        if (wifi_buf.indexOf("WiFi is disconnected") != -1)
            is_wifi_connected = false;

        if (wifi_buf.indexOf("[WiFi] End request") != -1)
            is_request_done = true;
    }
}

void send_wifi(float PHval,double percent)
{
    String data;

    data += "email="; data += email;
    data += "&lvl="; data += (int)percent;
    data += "&PH="; data += PHval;
    String request = WIFI_CMD_DATA + WIFI_CMD_DELIMETER + String(url.length()) + WIFI_CMD_DELIMETER + String(data.length())
                                   + WIFI_CMD_DELIMETER + url + WIFI_CMD_DELIMETER + data;

    is_request_done = false;
    Serial.println("Sending to wifi:");
    Serial.println(request);
    WIFI.print(request);
    wifi_time = millis();
}

