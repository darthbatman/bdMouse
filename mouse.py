import sys
import win32api
import time

x = float(sys.argv[1])
xCur, yCur = win32api.GetCursorPos()
xCur = int(xCur + (10*x))
win32api.SetCursorPos((xCur, yCur))

y = float(sys.argv[2])
xCur, yCur = win32api.GetCursorPos()
yCur = int(yCur + (10*y))
win32api.SetCursorPos((xCur, yCur))
#z = float(sys.argv[3])

#while True:
#	xCur, yCur = win32api.GetCursorPos()
#	xNew = int(xCur)
#	yNew = int(yCur)
#	
#	win32api.SetCursorPos((xNew, yNew))
#	time.sleep(0.001)