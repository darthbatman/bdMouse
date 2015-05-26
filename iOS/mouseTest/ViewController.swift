//
//  ViewController.swift
//  mouseTest
//
//  Created by Rishi Masand on 5/20/15.
//  Copyright (c) 2015 Rishi Masand. All rights reserved.
//

import UIKit
import CoreMotion
import CoreLocation

class ViewController: UIViewController, CLLocationManagerDelegate, UITextFieldDelegate {
    
    var motionManager = CMMotionManager()
    var locationManager = CLLocationManager()

    let socket = SocketIOClient(socketURL: "http://gronk.party")
    
    @IBOutlet weak var showKeyboardTest: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        socket.connect()
        
        self.showKeyboardTest.becomeFirstResponder()
        //self.showKeyboardTest.addTarget(self, action: "textfieldchanged:", forControlEvents: UIControlEvents.EditingChanged)
        self.showKeyboardTest.delegate = self
        
        locationManager.delegate = self
        locationManager.requestWhenInUseAuthorization()
        locationManager.requestAlwaysAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.distanceFilter = 1;
        locationManager.startUpdatingLocation()
        
        var device = UIDevice.currentDevice()
        device.proximityMonitoringEnabled = true
        
        if (motionManager.accelerometerAvailable) {
            let queue = NSOperationQueue()
            motionManager.startAccelerometerUpdatesToQueue(queue, withHandler:
                {(data: CMAccelerometerData!, error: NSError!) in
                    
//                    println("X = \(data.acceleration.x)")
//                    println("Y = \(data.acceleration.y)")
//                    println("Z = \(data.acceleration.z)")
                    
                    self.socket.emit("accel", data.acceleration.x, data.acceleration.y, data.acceleration.z)
                    
                }
            )
        }
        else {
            println("Accelerometer is not available")
        }
        
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        self.socket.emit("enter")
        return true
    }
    
    var num = 0
    
    @IBAction func textChanger(sender: AnyObject) {
        //print(showKeyboardTest.text)
        let s = String(showKeyboardTest.text)
        var length = countElements(s)
        if (length >= num) {
        let idx = advance(s.startIndex, (length - 1))
        print(s[idx])
        var key = String(s[idx])
            if (key == " ") {
                self.socket.emit("space")
            }
        self.socket.emit("key", key)
        num = length
        }
        else {
            self.socket.emit("backspace")
        }
    }
   

    func locationManager(manager: CLLocationManager!, didUpdateLocations locations: [AnyObject]!) {
        CLGeocoder().reverseGeocodeLocation(manager.location, completionHandler: { (placemarks, error) -> Void in
            
            if (error != nil) {
                println(error.localizedDescription)
                return
            }
            
            if placemarks.count > 0 {
                print("loc")
            }
            
        })
        
        
        
    }

    func locationManagerDidResumeLocationUpdates(manager: CLLocationManager!) {
        println("Resuming")
    }

    @IBAction func leftClick(sender: AnyObject) {
        self.socket.emit("leftClick")
    }
    @IBAction func rightClick(sender: AnyObject) {
        self.socket.emit("rightClick")
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

