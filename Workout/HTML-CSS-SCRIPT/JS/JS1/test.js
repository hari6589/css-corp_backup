$(document).ready(function(){

	$('.make-dropdown').append('<option val="1">One</option>');
	$('.make-dropdown').append('<option val="1">Two</option>');
	//////////
	var i = 0;
	start: while(true) {
	  console.log(i);
	  i++;
	  if(i < 5) continue start;
	  break;
	}

	$("#clearDiv").click(function(){
		$(".container").empty();
	});
	//////////
	$("#submitBtn").click(function(){
		//var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var regexEmail = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})+$/;
		var regexZip = /^[\d]{5}$/;
		var email = $("#emailId").val();
		var zip = $("#zip").val();
		$(".result").empty();
  		$(".result").append(email + " : validation result : " + regexEmail.test(email) +"<br/>");
  		$(".result").append(zip + " : validation Zip : " + regexZip.test(zip) +"<br/><br/>");

	});
	//////////
	$("#validate").click(function(){
		$(".zipcodeError").html("Invalid Zipcode");
	});
});