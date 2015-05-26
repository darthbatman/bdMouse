import sys
import win32api
import SendKeys

distance = int(int(sys.argv[1]) / 10)
down = "{DOWN}"
up = "{UP}"

if distance > 0:
	while distance > 0:
		SendKeys.SendKeys(up)
		distance = (distance - 1)
		
if distance < 0:
	distance = (-1*distance)
	while distance > 0:
		SendKeys.SendKeys(down)
		distance = (distance - 1)

