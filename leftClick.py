import sys
import win32api
import win32con

xCord, yCord = win32api.GetCursorPos()

win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, xCord, yCord, 0, 0)
win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, xCord, yCord, 0, 0)

# get mouse coordinates
#win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN,683,384,0,0)
#win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP,700,384,0,0)