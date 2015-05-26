var app = require("express")();
var http = require("http").Server(app);
var io = require("socket.io")(http);
var PythonShell = require("python-shell");

app.get("/", function(req, res) {
	res.send("BD Rishi, BD Nikhil");
});

io.on("connection", function(socket) {
	
	console.log("Connected.");
	
	socket.on("accel", function(x, y, z) {
		var xC = Math.round(x * 100) / 100;
		//console.log("X = " + xC); //positive = right; negative = left
		var yC = Math.round(y * 100) / 100;
		//console.log("Y = " + yC); //positive = forward; negative = backward
		var zC = Math.round(z * 100) / 100;
		//console.log("Z = " + zC); //positive = up; negative = down
		
		var options = {
		  scriptPath: "C:/Rishi/Programming/iOS/mouseTest/",
		  args: [xC, yC, zC]
		};
		 
		PythonShell.run('mouse.py', options, function (err, results) {
		  if (err) throw err;
		  // results is an array consisting of messages collected during execution 
		  //console.log('results: %j', results);
		});
	});
	
	// all new stuff here
	socket.on("leftClick", function() {
		var options = {
			scriptPath: "C:/Rishi/Programming/iOS/mouseTest/"
		};
		
		PythonShell.run('leftClick.py', options, function (err, results) {
			if (err) throw err;
		});
		
		
	});
	
	socket.on("rightClick", function() {
		var options = {
			scriptPath: "C:/Rishi/Programming/iOS/mouseTest/"
		};
		
		PythonShell.run('rightClick.py', options, function (err, results) {
			if (err) throw err;
		});
		
	});
	
	socket.on("scroll", function(distance){
		console.log("Scroll " + distance);
		var options = {
			scriptPath: "C:/Rishi/Programming/iOS/mouseTest/",
			args: [distance]
		};
		
		PythonShell.run('scroll.py', options, function (err, results) {
			if (err) throw err;
		});
	});
	
	socket.on("key", function(key){
		console.log(key);
		var options = {
			scriptPath: "C:/Rishi/Programming/iOS/mouseTest/",
			args: [key]
		};
		
		PythonShell.run('key.py', options, function (err, results) {
			if (err) throw err;
		});
	});
	
	socket.on("space", function(){
		var options = {
			scriptPath: "C:/Rishi/Programming/iOS/mouseTest/"
		};
		
		PythonShell.run('space.py', options, function (err, results) {
			if (err) throw err;
		});
	});
	
	socket.on("backspace", function(){
		var options = {
			scriptPath: "C:/Rishi/Programming/iOS/mouseTest/"
		};
		
		PythonShell.run('backspace.py', options, function (err, results) {
			if (err) throw err;
		});
	});
	
	socket.on("enter", function(){
		var options = {
			scriptPath: "C:/Rishi/Programming/iOS/mouseTest/"
		};
		
		PythonShell.run('enter.py', options, function (err, results) {
			if (err) throw err;
		});
	});
	
});

http.listen(8080, function() {
	console.log("Listening on *8080");
});