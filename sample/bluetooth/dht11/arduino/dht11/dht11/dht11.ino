#include "DHT.h"

#define DHTPIN 2
#include <SoftwareSerial.h>
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);
SoftwareSerial btSerial(10,11);
char dataFromBT = '0';

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  dht.begin();
  btSerial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  //delay(2000);
  
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if(btSerial.available())
  {
    dataFromBT=btSerial.read();
  }
  if(dataFromBT=='1')
  {
    btSerial.write(t);
    dataFromBT='0';
  }
  
}
