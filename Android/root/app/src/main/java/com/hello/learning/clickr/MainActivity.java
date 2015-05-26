package com.hello.learning.clickr;


import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.*;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import android.view.GestureDetector;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URISyntaxException;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;



/*

PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
 PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
 wl.acquire();

 */

public class MainActivity extends Activity implements SensorEventListener, GestureDetector.OnGestureListener {

    private Socket mSocket;

    float sensorX, sensorY, sensorZ;

    TextView textview;
    TextView outputTV;

    PowerManager pm;
    PowerManager.WakeLock wakeLock;

    int scrollDistance = 0;
    int counter = 0;


    private GestureDetector GestureDetect;

    Button leftClickButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.textview);

        sensorX = sensorY = sensorZ = 0;

        leftClickButton = (Button) findViewById(R.id.LeftClick);

        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor s = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);

        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

        GestureDetect = new GestureDetector(this, this);

        // wake lock cheese
        enableWakeLock();
        // init the socket
        try {
            // returns a socket with default options
            // PUT SERVER NAME HERE
            mSocket = IO.socket("http://9a4fb0b0.ngrok.io"); //  http://2c65daed.ngrok.io"
        }

        catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // set a listener for the new message event
//        mSocket.on("new message", onNewMessage);

        mSocket.connect();

        // auto log in
//        mSocket.emit("add user", "Alias Parker");

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        int truAscii;

        // space
        if (event.getKeyCode() == 62) {
            mSocket.emit("space");
        }

        // backspace
        else if (event.getKeyCode() == 67) {
            mSocket.emit("backspace");
        }

        // enter
        else if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            mSocket.emit("enter");
            Log.i("Jonas", "You pressed Enter");
        }

        else {
            truAscii = event.getKeyCode() + 68;

            char letter = (char) truAscii;

            Log.i("Letter Pressed: ", Character.toString(letter));

            mSocket.emit("key", Character.toString(letter));
        }
        return true;
    }

    private void enableWakeLock() {
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "why you up swapnanil");
        wakeLock.acquire();

    }

    private void disableWakeLock() {
        wakeLock.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
//        mSocket.off("new message", onNewMessage);
    }

    // attempt to send a message
    // THIS METHOD IS NOT USED
    public void attemptSend(View view) {

        String message = "X: " + sensorX;


        // partial wakelock
//        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "why you up swapnanil");
//        wakeLock.acquire();

       // mSocket.emit("accel", sensorX * 0.35, sensorY * 0.35);

        while (wakeLock.isHeld()) {
            //Log.d("Wang Liqin: ", sensorX + " " + sensorY + " " + sensorZ);

            try {
                Thread.sleep(16);
            }

            catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

    public void endWakeLockFunction(View view) {

        wakeLock.release();

    }

//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//
//        @Override
//        public void call(final Object... args) {
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    JSONObject data = (JSONObject) args[0];
//                    String username = "";
//                    String message = "";
//
//                    // parse JSON
//                    try {
//                        username = data.getString("username");
//                        message = data.getString("message");
//                    }
//
//                    catch(JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    String output = "Username: " + username + "\nMessage: " + message;
//
//                    Log.d("OUTPUT: ", output);
//
//                    outputTV.setText(output);
//
//
//                }
//            });
//
//        }
//
//    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // 0 is x axis
        // 1 is y axis
        // 2 is z axis

        try {
            Thread.sleep(5);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }

        sensorX = event.values[0];
        sensorY = event.values[1];
        sensorZ = event.values[2];

        // log coordinates
        //textview.setText("X: " + sensorX * -0.8 + "\nY: " + sensorY * -0.8 + "\nZ: " + sensorZ * -1);
        Log.d("Coordinates: ", "\nX: " + sensorX * -0.4 + "\n\nY: " + sensorY * -0.4 + "\n\nZ: " + sensorZ * -1);

        mSocket.emit("accel", sensorX * -0.4, sensorY * -0.4, sensorZ * -1);


    }

    // sends right click events
    public void onLeftButtonClick(View view) {

        mSocket.emit("leftClick");

    }

    public void onRightClickButton(View view) {

        mSocket.emit("rightClick");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


        if (distanceY < 10 && distanceY > -10) {

            scrollDistance += distanceY;
            counter++;
        }

        if (distanceY < 10 && distanceY > -10)
        {
            mSocket.emit("scroll", scrollDistance / counter);
            Log.i("Scrololol", Float.toString(scrollDistance / counter));
            counter = 0;
            scrollDistance = 0;
        }

        //Log.d("Y Distance Scroll: ", Float.toString(distanceY));
        //mSocket.emit("scroll", distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        GestureDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
