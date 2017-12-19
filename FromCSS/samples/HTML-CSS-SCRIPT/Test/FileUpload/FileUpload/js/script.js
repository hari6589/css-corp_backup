var socket = io();
  var ip = location.host;
  var load_count=0;
  var speaker_boolean=true;
  var toggle_var=true;
  
  $(document).ready(function() {	
	
    socket.emit('get_files');
	
	socket.on("fileList",function(fileList){
		for(var i=0;i<fileList.length;i++){
			var ext = fileList[i].split('.').pop();
			
			var img_path;
			var iconClass;
			if (ext == "pdf") {
				img_path = "images/pdf-icon.png";
				iconClass = "pdf";
			} else if ((ext.indexOf("docx") != -1) || (ext.indexOf("doc") != -1)) {
				img_path = "images/word-icon.png";
				iconClass = "word";
			} else if (ext.indexOf("xls") != -1) {
				img_path = "images/excel-icon.png";
				iconClass = "excel";
			} else if ((ext.indexOf("png") != -1) || (ext.indexOf("jpg") != -1)) {
				img_path = "images/images-icon.png";
				iconClass = "picture";
			} else if (ext.indexOf("ppt") != -1) {
				img_path = "images/ppt-icon.png";
				iconClass = "powerpoint";
			} else if (ext.indexOf("txt") != -1) {
				img_path = "images/text-icon.png";
				iconClass = "text";
			}
			else{
				img_path = "images/circle-icon.png";
				iconClass = "general";
			}
		//alert(fileList[i].indexOf("text_copy_of"));
		$('#files').append('<li class="parent '+iconClass+'"><img class="icon circle" src="'+img_path+'"><a target="_blank" id="filevalue'+i+'" href="'+"http://"+ip+"//documents/"+fileList[i]+'">'+fileList[i]+'</a><img class="delete" onclick=\'delete_file("'+i+'")\' src="images/download1.png"></li>');
		}
	});
	
});

function getAnswer(){
	ques = $('#question').val();	
	socket.emit('get_answer',ques);
	
	socket.on("answer",function(answer){
		$('#answer').val(answer);
		read_back_response(answer);
	});
}

function delete_file(i){
	
	var dir = $("#filevalue"+i).attr("href").split(ip)[1];
	socket.emit('delete_file',dir);
	//$('ul li').remove();
	$('#files').empty();
	socket.emit('get_files');
}

// function interpret_voice
function interpret_voice() 
  {
    if (window.hasOwnProperty('webkitSpeechRecognition'))
	{
      var recognition = new webkitSpeechRecognition();
 
      recognition.continuous = false;
      recognition.interimResults = false;
 
      recognition.lang = "en-US";
      recognition.start();
 
      recognition.onresult = function(e)
	  {
        document.getElementById('question').value
                                 = e.results[0][0].transcript;
								 
		//document.getElementById('voice_output').value=generate_response(document.getElementById('question').value)
        //var socket = io();
        socket.emit('get_answer', document.getElementById('question').value);
		socket.on("answer",function(answer){
			$('#answer').val(answer);
		});
		//read_back_response(document.getElementById('question').value)
		
		//getAnswer();
		
        recognition.stop();
      };
 
      recognition.onerror = function(e)
	  {
        recognition.stop();
		console.log("An Error Occurred")
      }
 
    }
 }
 
 //function speaker_toggle
function speaker_toggle()
{
 try
  {	
	if(toggle_var)
	{
	document.getElementById('speaker_image').src='images/Mute_Icon.png';
	//$(" #speaker_image ").attr('title', "speaker");
	$('#speaker_image').attr('title', 'speaker').tooltip('fixTitle').tooltip('show');
	toggle_var=false;
	speaker_boolean=false;
	}
	else
	{
	document.getElementById('speaker_image').src='images/sound.png';
	$('#speaker_image').attr('title', 'mute').tooltip('fixTitle').tooltip('show');	
	toggle_var=true;
	speaker_boolean=true;
	}
  }
  catch(err)
	{
		console.log("Inside speaker_toggle.Error is : "+err.message);
	}
}

 // new function for fixing voice breaks <@ http://stackoverflow.com/questions/21947730/chrome-speech-synthesis-with-longer-texts ... .replace(/[^a-zA-Z0-9\s\.]/g, '')>
var speechUtteranceChunker = function (utt, settings, callback) {
    settings = settings || {};
    var newUtt;
    var txt = (settings && settings.offset !== undefined ? utt.text.substring(settings.offset) : utt.text);
    if (utt.voice && utt.voice.voiceURI === 'native') { // Not part of the spec
        newUtt = utt;
        newUtt.text = txt;
        newUtt.addEventListener('end', function () {
            if (speechUtteranceChunker.cancel) {
                speechUtteranceChunker.cancel = false;
            }
            if (callback !== undefined) {
                callback();
            }
        });
    }
    else {
        var chunkLength = (settings && settings.chunkLength) || 160;
        var pattRegex = new RegExp('^[\\s\\S]{' + Math.floor(chunkLength / 2) + ',' + chunkLength + '}[.!?,]{1}|^[\\s\\S]{1,' + chunkLength + '}$|^[\\s\\S]{1,' + chunkLength + '} ');
        var chunkArr = txt.match(pattRegex);

        if (chunkArr[0] === undefined || chunkArr[0].length <= 2) {
            //call once all text has been spoken...
            if (callback !== undefined) {
                callback();
            }
            return;
        }
        var chunk = chunkArr[0];
        newUtt = new SpeechSynthesisUtterance(chunk);
        var x;
        for (x in utt) {
            if (utt.hasOwnProperty(x) && x !== 'text') {
                newUtt[x] = utt[x];
            }
        }
        newUtt.addEventListener('end', function () {
            if (speechUtteranceChunker.cancel) {
                speechUtteranceChunker.cancel = false;
                return;
            }
            settings.offset = settings.offset || 0;
            settings.offset += chunk.length - 1;
            speechUtteranceChunker(utt, settings, callback);
        });
    }

    if (settings.modifier) {
        settings.modifier(newUtt);
    }
    console.log(newUtt); //IMPORTANT!! Do not remove: Logging the object out fixes some onend firing issues.
    //placing the speak invocation inside a callback fixes ordering and onend issues.
    setTimeout(function () {
        speechSynthesis.speak(newUtt);
    }, 0);
};

 //function read_back_response
function read_back_response(response)
{
	try
  {
	response=response.replace(/[^a-zA-Z0-9\s\.\%\-\;\,\$]/g, '')
  	var utterance = new SpeechSynthesisUtterance(response);
	//modify it as you normally would
	var voiceArr = speechSynthesis.getVoices();
	utterance.voice = voiceArr[2];	
	if(speaker_boolean)
	{
		if(load_count==0)
		{
			speechUtteranceChunker(utterance, {
				chunkLength: 120
			}, function () {
				//some code to execute when done
				console.log('done');
			});

		}
	}
  }
  catch(err)
  {
	  console.log("Inside read_back_response. Error is : "+err.message);
  }
}

function enable_send(e,obj){
	var code = (e.keyCode ? e.keyCode : e.which);
	if((obj.value).trim() && code != 13){
		$('#send').show();
		$('#microphone').hide();
	}
	else{
		$('#send').hide();
		$('#microphone').show();
	}
}