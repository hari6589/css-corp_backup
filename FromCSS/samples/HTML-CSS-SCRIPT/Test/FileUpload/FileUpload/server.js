var express =   require("express");
process.env.PWD = process.cwd();
var multer  =   require('multer');
var app  =   express();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var rp = require('request-promise');

app.use(express.static(process.env.PWD));

var fs = require("fs");

io.on('connection', function(socket){
	socket.on('get_files', function(){
		fileList = [];
		var dir = __dirname + '/documents';
		fs.readdir(dir, function(err, files) {    
				for(var i in files){
					if (!files.hasOwnProperty(i)) continue;
					var name = dir+'/'+files[i];
					if (files[i].indexOf("text_copy_of") != 0 && files[i].indexOf("index_") != 0){
						fileList.push(files[i]);	
					}
					}
				socket.emit("fileList",fileList);		
		});
		
	});
	
	socket.on('get_answer', function(question){
		var host = 'http://localhost:5001/get-data?input='
		var input = question;

		requestAnswer = function(ques) {

		  return getAnswer(ques).then(

			function(response) {

			  console.log('success - received answer for ' + ques);

			  return response.body;

			}

		  );

		};

		

		getAnswer = function(ques) {

		  var options = {

			method: 'GET',

			uri: host + ques,

			resolveWithFullResponse: true

		  };

		  return rp(options);

		};

		requestAnswer(input).then(function(res){
			var answer = JSON.parse(res)
			console.log("answer---"+answer['data']);
			socket.emit("answer",answer['data']);
		
		
		}).catch(function(err){
			console.log(err.statusCode);
			socket.emit("answer",'I dont know the answer for '+input);
		});
			
		});
	socket.on('delete_file', function(filepath){		
		console.log(__dirname+filepath);
		fs.unlinkSync(__dirname+filepath);
	});
});



var storage =   multer.diskStorage({
  destination: function (req, file, callback) {
    callback(null, './/documents');
  },
  filename: function (req, file, callback) {
    callback(null, file.originalname);
  }
});
var upload = multer({ storage : storage}).array('userPhoto',100);

app.get('/',function(req,res){
      res.sendFile(__dirname + "/index.html");
});
		
app.post('/api/photo',function(req,res){
	
    upload(req,res,function(err) {
        if(err) {
            return res.end("Error uploading file."+err.toString());
        }
		//res.end("File is uploaded");
		res.redirect('/');		
    });
});



	
	

http.listen(3022,function(){
    console.log("Working on port 3022");
});
