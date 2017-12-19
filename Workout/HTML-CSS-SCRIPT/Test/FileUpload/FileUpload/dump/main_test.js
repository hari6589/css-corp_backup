var fs = require("fs");

// Create a readable stream
var readerStream = fs.createReadStream('C:/Users/css110982/Desktop/new_one.pdf');

// Create a writable stream
var writerStream = fs.createWriteStream('new_output_pdf.pdf');

// Pipe the read and write operations
// read input.txt and write data to output.txt
readerStream.pipe(writerStream);

console.log("Program Ended");