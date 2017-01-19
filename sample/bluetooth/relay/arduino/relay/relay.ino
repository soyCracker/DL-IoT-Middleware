#include <SoftwareSerial.h>

SoftwareSerial btSerial(10, 11);
const int relayPin=13;
char dataFromBT = '0';
 
void setup() {
  Serial.begin(9600);
  
  // The data rate for the SoftwareSerial port needs to 
  // match the data rate for your bluetooth board.
  btSerial.begin(9600);
  pinMode(relayPin, OUTPUT);   
}
 
void loop() {
  if (btSerial.available())
    dataFromBT = btSerial.read();
  
  if (dataFromBT == '0') {
    // Turn off Relay
    digitalWrite(relayPin,LOW);
  } else if (dataFromBT == '1') {
    // Turn on Relay
    digitalWrite(relayPin, HIGH);
  }
   delay(500);
}
