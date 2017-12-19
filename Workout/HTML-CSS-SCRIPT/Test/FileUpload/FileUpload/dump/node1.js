var fs = require("fs")
var express = require('express');
var fileUpload = require('express-fileupload');
var app = express();
 
// default options 
app.use(fileUpload());
 
app.post('/upload', function(req, res) {
  var sampleFile;
 
  if (!req.files) {
    res.send('No files were uploaded.');
    return;
  }
 
  // The name of the input field (i.e. "sampleFile") is used to retrieve the uploaded file 
  sampleFile = req.files.sampleFile;
 
  // Use the mv() method to place the file somewhere on your server 
  sampleFile.mv('/upload/filename.jpg', function(err) {
    if (err) {
      res.status(500).send(err);
    }
    else {
      res.send('File uploaded!');
    }
  });
});