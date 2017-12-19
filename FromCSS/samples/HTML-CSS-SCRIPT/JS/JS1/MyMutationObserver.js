$(document).ready(function() {
	
	$("#clone").click(function(){
		$("#container").append('<span id="ele">asdf</span>');
	});

	// select the target node
	var target = document.getElementById('container');
	 
	// create an observer instance
	var observer = new MutationObserver(function(mutations) {
	  mutations.forEach(function(mutation) {
	  	alert("Mutation happened!");
	  });
	});
	 
	// configuration of the observer:
	var config = { attributes: true, childList: true, characterData: true };
	//var config = { attributes: true };
	 
	// pass in the target node, as well as the observer options
	observer.observe(target, config);
	 
	// later, you can stop observing
	//observer.disconnect();
});
