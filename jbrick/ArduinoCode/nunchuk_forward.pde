#include <Wire.h>
#include <SoftwareSerial.h>
#include <WProgram.h>
#define pwrpin PORTC3
#define gndpin PORTC2

 uint8_t nunchuck_buf[6];   // array to store nunchuck data
 uint8_t nunchuck_send_buffer[3]; //array to send data to PC host
 int ledPin = 13;
 byte counter=0;
 boolean newData=true;
 
static char nunchuk_decode_byte (char x)
{
    x = (x ^ 0x17) + 0x17;
    return x;
}

void checkSend(){
  if(Serial.available() >= 1){//if data is available,
    if(Serial.read()==0xAC){//read the byte, check if it is equal to 0xAC(the value to request data send by jBrick)
     //value is 0xAC, nunchuck data has been requested
     //check if new data is available
     byte readd= Serial.read();
         if(newData){
           sendData(); 
         }
    }   
  }
}
void sendData(){
    //Serial.print(nunchuck_send_buffer[0], DEC);
    //Serial.write("\0");
    //Serial.print(nunchuck_send_buffer[1], DEC);
   // Serial.write("\0");
   // Serial.print(nunchuck_send_buffer[2], DEC);
   // Serial.write("\0");
    Serial.write(0x01);  //new packet note
    Serial.write(nunchuck_send_buffer[0]); //
    Serial.write(nunchuck_send_buffer[1]);
    Serial.write(nunchuck_send_buffer[2]);
    Serial.write(0xFF);
  newData=false;
}

void setup(){

  newData=false;
   Serial.begin(115200);
    //setup ports for nunchuck communication
    DDRC |= _BV(pwrpin) | _BV(gndpin);
    PORTC &=~ _BV(gndpin);
    PORTC |=  _BV(pwrpin);
    delay(100);  //stabilizing...
    
    Wire.begin();                // join i2c bus as master
    Wire.beginTransmission(0x52);// transmit to device 0x52
        Wire.send(0x40);// sends memory address
        Wire.send(0x00);// sends sent a zero.  
    Wire.endTransmission();// stop transmitting

}

void loop()
{
int cnt=0;

  Wire.requestFrom (0x52, 6); //request 6 bytes from the nunchuck
  checkSend();  //if new data and the PC host requested it, send updated info
  while (Wire.available ()) 
  {
    // receive byte as an 8 bit integer
     nunchuck_buf[cnt] = nunchuk_decode_byte(Wire.receive());
     cnt++;
     counter++;
  }
  checkSend();
    //request next packet
    Wire.beginTransmission(0x52);// transmit to device 0x52
        Wire.send(0x00);// sends one byte
    Wire.endTransmission();// stop transmitting
    if (cnt >= 5) {
        nunchuck_send_buffer[0]=nunchuck_buf[0]; //joyx
        nunchuck_send_buffer[1]=nunchuck_buf[1]; //joyy
        nunchuck_send_buffer[2]=nunchuck_buf[5] & 0x03; //button values - mask off all but lowest 2 bits     
        newData=true;
    }
    delay(100); //short delay. seems without this it does not work!
    checkSend();
    
    
}

