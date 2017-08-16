import RPi.GPIO as GPIO
import time


GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(18, GPIO.OUT)

while (True):
  key = raw_input()
  sec = int(key)
  print 'LED on : ', sec
  GPIO.output(18, GPIO.HIGH)
  time.sleep(sec)
  print 'LED off'
  GPIO.output(18, GPIO.LOW)
