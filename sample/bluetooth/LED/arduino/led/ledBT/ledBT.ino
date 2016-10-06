#include <SoftwareSerial.h>

SoftwareSerial btSerial(10, 11);
char dataFromBT = '0';
 
void setup() {
  Serial.begin(9600);
  
  // The data rate for the SoftwareSerial port needs to 
  // match the data rate for your bluetooth board.
  btSerial.begin(9600);
  pinMode(13, OUTPUT);   
}
 
void loop() {
  if (btSerial.available())
    dataFromBT = btSerial.read();
 
  if (dataFromBT == '0') {
    // Turn off LED
    digitalWrite(13, LOW);
  } else if (dataFromBT == '1') {
    // Turn on LEFD
    digitalWrite(13, HIGH);
  }
}
