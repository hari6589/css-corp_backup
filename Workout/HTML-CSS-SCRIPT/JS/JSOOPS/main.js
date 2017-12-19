$(document).ready(function(){
	var man = new person("John", 27, "Red");
	console.log(man.name);

	//$("#container").html("Hello...");
	$("#container").html(man.name);
});

function person(name, age, color) {
	this.name = name;
	this.age = age;
	this.color = color;
	this.changeName = function(name) {
		this.name = name;
	}
}
