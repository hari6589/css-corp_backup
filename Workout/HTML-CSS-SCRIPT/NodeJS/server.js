var http = require('http');
var fs = require('fs');
var mm = require('./myModule');

http.createServer(function (req, res) {
	
	fs.readFile('index.html', function(err, data) {
		res.writeHead(200, {'Content-Type': 'text/html'});
	    
	    res.write(req.url + "<br>");
	    //res.write("<br>");

	    //console.log(__dirname); // Display current directory path

	    res.end(mm.myDateTime());
	    
	});
    
}).listen(8080);